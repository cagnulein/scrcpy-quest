package org.cagnulein.android_remote;
import android.content.Context;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger extends Logger {
    private static final String LOG_FILE = "log.txt";
    private BufferedWriter writer;
    private static boolean debuglog = false;
    private static final String PREFERENCE_KEY = "default";
    private Context context;

    public FileLogger(String name, Context context) {
        super(name, null);
        this.context = context;
        //debuglog = context.getSharedPreferences(PREFERENCE_KEY, 0).getBoolean("Debug Log", false);
        if(true/*debuglog*/) {
            try {
                File docsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                File logFile = new File(docsDir, "android_remote.txt");                
                writer = new BufferedWriter(new FileWriter(logFile, true));
            } catch (IOException e) {
                e.printStackTrace();
            }            
        }
    }

    @Override
    public void log(Level level, String msg) {
        super.log(level, msg);
        if(true/*debuglog*/) {
            try {
                writer.write(String.format("[%s] %s: %s%n", java.time.LocalDateTime.now(), level, msg));
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if(true/*debuglog*/) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}