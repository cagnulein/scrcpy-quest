package org.cagnulein.android_remote;

import android.os.BatteryManager;

import java.io.File;
import java.io.IOException;

public final class Server {

    private static String ip = null;
    public static final String SERVER_PATH;

    private Server() {
        // not instantiable
    }

    static {
        String[] classPaths = System.getProperty("java.class.path").split(File.pathSeparator);
        // By convention, scrcpy is always executed with the absolute path of scrcpy-server.jar as the first item in the classpath
        SERVER_PATH = classPaths[0];
    }


    private static void scrcpy(Options options) throws IOException {
        final Device device = new Device(options);

        /*if (options.getStayAwake())*/ {
            int stayOn = BatteryManager.BATTERY_PLUGGED_AC | BatteryManager.BATTERY_PLUGGED_USB | BatteryManager.BATTERY_PLUGGED_WIRELESS;
            try {
                String oldValue = Settings.getAndPutValue(Settings.TABLE_GLOBAL, "stay_on_while_plugged_in", String.valueOf(stayOn));
                try {
                    int restoreStayOn = Integer.parseInt(oldValue);
                    if (restoreStayOn != stayOn) {
                        // Restore only if the current value is different
                        if (!cleanUp.setRestoreStayOn(restoreStayOn)) {
                            Ln.e("Could not restore stay on on exit");
                        }
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
            } catch (SettingsException e) {
                Ln.e("Could not change \"stay_on_while_plugged_in\"", e);
            }
        }

        try (DroidConnection connection = DroidConnection.open(ip)) {
            ScreenEncoder screenEncoder = new ScreenEncoder(options.getBitRate());

            // asynchronous
            startEventController(device, connection);

            try {
                // synchronous
                screenEncoder.streamScreen(device, connection.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                // this is expected on close
                Ln.d("Screen streaming stopped");

            }
        }
    }

    private static void startEventController(final Device device, final DroidConnection connection) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new EventController(device, connection).control();
                } catch (IOException e) {
                    // this is expected on close
                    Ln.d("Event controller stopped");
                }
            }
        }).start();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private static Options createOptions(String... args) {
        Options options = new Options();

        if (args.length < 1) {
            return options;
        }
        ip = String.valueOf(args[0]);


        if (args.length < 2) {
            return options;
        }
        int maxSize = Integer.parseInt(args[1]) & ~7; // multiple of 8
        options.setMaxSize(maxSize);

        if (args.length < 3) {
            return options;
        }
        int bitRate = Integer.parseInt(args[2]);
        options.setBitRate(bitRate);

        if (args.length < 4) {
            return options;
        }
        // use "adb forward" instead of "adb tunnel"? (so the server must listen)
        boolean tunnelForward = Boolean.parseBoolean(args[3]);
        options.setTunnelForward(tunnelForward);
        return options;
    }

    public static void main(String... args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Ln.e("Exception on thread " + t, e);
            }
        });

        try {
            Process cmd = Runtime.getRuntime().exec("rm /data/local/tmp/scrcpy-server.jar");
            cmd.waitFor();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        Options options = createOptions(args);
        scrcpy(options);
    }
}

