package org.las2mile.scrcpy;


import static android.org.apache.commons.codec.binary.Base64.encodeBase64String;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import io.github.muntashirakon.adb.AbsAdbConnectionManager;
import io.github.muntashirakon.adb.AdbConnection;
import io.github.muntashirakon.adb.AdbPairingRequiredException;
import io.github.muntashirakon.adb.AdbStream;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class SendCommands {

    private Thread thread = null;
    private Context context;
    private int status;


    public SendCommands() {

    }

    public int SendAdbCommands(Context context, final byte[] fileBase64, final String ip, final int port, String localip, int bitrate, int size) {
        this.context = context;
        status = 1;
        final StringBuilder command = new StringBuilder();
        command.append(" CLASSPATH=/data/local/tmp/scrcpy-server.jar app_process / com.genymobile.scrcpy.Server 2.4 scid=100000 log_level=VERBOSE");
        //command.append(" /" + localip + " " + Long.toString(size) + " " + Long.toString(bitrate) + ";");

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    adbWrite(ip, port, fileBase64, command.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        int count = 0;
        while (status == 1 && count < 100) {
            Log.e("ADB", "Connecting...");
            try {
                Thread.sleep(100);
                count ++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(count == 100){
            status = 2;
        }
        return status;
    }


    private void adbWrite(String ip, int port, byte[] fileBase64, String command) throws Exception {

        AbsAdbConnectionManager manager = AdbConnectionManager.getInstance(context.getApplicationContext());
        boolean connected = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                connected = manager.connect(ip, port);
            } catch (AdbPairingRequiredException e) {

                return;
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        AdbStream stream = manager.openStream("shell:");

        if (stream != null && status ==1) {
            try {
                String s = " " + '\n';
                stream.write(s.getBytes(), 0 ,s.length());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }


        String responses = "";
        boolean done = false;
        while (!done && stream != null && status ==1) {
            try {
                byte[] responseBytes = new byte[1024];
                int len = stream.read(responseBytes, 0 , 1024);
                String response = new String(responseBytes, 0, len, StandardCharsets.US_ASCII);
                if (response.endsWith("$ ") ||
                        response.endsWith("# ")) {
                    done = true;
                    Log.i("ADB_Shell","Prompt ready");
                    responses += response;
                    break;
                } else {
                    responses += response;
                }
            } catch (IOException e) {
                status = 2;
                e.printStackTrace();
            }
        }

        if (stream != null && status ==1) {
            int len = fileBase64.length;
            byte[] filePart = new byte[4056];
            int sourceOffset = 0;
            try {
                String s = " cd /data/local/tmp " + '\n';
                stream.write(s.getBytes(), 0, s.length());
                while (sourceOffset < len) {
                    if (len - sourceOffset >= 4056) {
                        System.arraycopy(fileBase64, sourceOffset, filePart, 0, 4056);  //Writing in 4KB pieces. 4096-40  ---> 40 Bytes for actual command text.
                        sourceOffset = sourceOffset + 4056;
                        String ServerBase64part = new String(filePart, StandardCharsets.US_ASCII);
                        s = " echo " + ServerBase64part + " >> serverBase64" + '\n';
                        stream.write(s.getBytes(), 0, s.length());
                        done = false;
                        while (!done) {
                            byte[] responseBytes = new byte[1024000];
                            int l = stream.read(responseBytes, 0 , 1024000);
                            String response = new String(responseBytes, 0, l, StandardCharsets.US_ASCII);
                            if (response.endsWith("$ ") || response.endsWith("# ")) {
                                done = true;
                            }
                        }
                    } else {
                        int rem = len - sourceOffset;
                        byte[] remPart = new byte[rem];
                        System.arraycopy(fileBase64, sourceOffset, remPart, 0, rem);
                        sourceOffset = sourceOffset + rem;
                        String ServerBase64part = new String(remPart, StandardCharsets.US_ASCII);
                        s = " echo " + ServerBase64part + " >> serverBase64" + '\n';
                        stream.write(s.getBytes(), 0, s.length());
                        done = false;
                        while (!done) {
                            byte[] responseBytes = new byte[1024];
                            int l = stream.read(responseBytes, 0 , 1024);
                            String response = new String(responseBytes,0, l, StandardCharsets.US_ASCII);
                            if (response.endsWith("$ ") || response.endsWith("# ")) {
                                done = true;
                            }
                        }
                    }
                }
                s = " base64 -d < serverBase64 > scrcpy-server.jar && rm serverBase64" + '\n';
                stream.write(s.getBytes(), 0, s.length());
                Thread.sleep(100);
                s = command + '\n';
                stream.write(s.getBytes(), 0, s.length());
                Thread.sleep(100);
                int ll = 999;
                while(ll > 0) {
                    byte[] responseBytes = new byte[102400];
                    ll = stream.read(responseBytes, 0, 102400);
                    String response = new String(responseBytes, 0, ll, StandardCharsets.US_ASCII);
                    Log.v("ADB", response);
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                status =2;
                return;
            }
	}
        if (status ==1);
        status = 0;

    }

}
