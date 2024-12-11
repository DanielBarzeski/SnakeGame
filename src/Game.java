
public class Game {
    private static boolean FINISHED, PAUSED;
    public final static int CELL_SIZE = 20, WIDTH = 440, HEIGHT = 400;

    public static void START() {
        PAUSED = false;
        FINISHED = false;
        System.out.println("THE GAME HAS STARTED!");
    }

    public static void END() {
        PAUSED = true;
        FINISHED = true;
        System.out.println("THE GAME HAS FINISHED!");
    }

    public static void PAUSE() {
        PAUSED = true;
    }

    public static void CONTINUE() {
        PAUSED = false;
    }

    public static boolean isFINISHED() {
        return FINISHED;
    }

    public static boolean isPAUSED() {
        return PAUSED;
    }

}