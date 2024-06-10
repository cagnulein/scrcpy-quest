import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger extends Logger {
    private static final String LOG_FILE = "log.txt";
    private BufferedWriter writer;
    private static boolean debuglog = false;

    public FileLogger(String name) {
        super(name, null);
        debuglog = context.getSharedPreferences(PREFERENCE_KEY, 0).getBoolean("Debug Log", false);
        if(debuglog) {
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
        if(debuglog) {
            try {
                writer.write(String.format("[%s] %s: %s%n", java.time.LocalDateTime.now(), level, msg));
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        if(debuglog) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}