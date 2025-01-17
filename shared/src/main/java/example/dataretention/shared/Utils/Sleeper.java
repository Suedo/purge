package example.dataretention.shared.Utils;

public class Sleeper {
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
