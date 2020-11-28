package alexander.marashov.smo.actor_classes;

public class Item {
    static public final int none = 0;

    static public final int red_button = 1;
    static public final int garlic = 2;
    static public final int graffiti = 3;

    static public final int butter = 4;

    static public final int fangs = 5;
    static public final int bat = 6;
    static public final int trick = 7;

    static public String getName(int item) {
        switch (item) {
            case Item.none:
                return "none";
            case Item.red_button:
                return "red_button";
            case Item.garlic:
                return "garlic";
            case Item.graffiti:
                return "graffiti";
            case Item.butter:
                return "butter";
            case Item.fangs:
                return "fangs";
            case Item.bat:
                return "bat";
            case Item.trick:
                return "trick";
            default:
                return "unknown item";
        }
    }
}
