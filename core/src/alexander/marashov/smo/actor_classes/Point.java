package alexander.marashov.smo.actor_classes;

public class Point {
    public float x, y;
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point moveBy(float dx, float dy) {
        return new Point(x + dx, y + dy);
    }
}
