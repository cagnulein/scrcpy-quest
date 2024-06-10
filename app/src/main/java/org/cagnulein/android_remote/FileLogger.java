import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger extends Logger {
    private static final String LOG_FILE = "log.txt";
    private BufferedWriter writer;

    public FileLogger(String name) {
        super(name, null);
        try {
            writer = new BufferedWriter(new FileWriter(LOG_FILE, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(Level level, String msg) {
        super.log(level, msg);
        try {
            writer.write(String.format("[%s] %s: %s%n", java.time.LocalDateTime.now(), level, msg));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}