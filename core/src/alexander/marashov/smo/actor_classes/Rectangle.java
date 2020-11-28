package alexander.marashov.smo.actor_classes;

public class Rectangle {
    private Point first, second;

    public Rectangle(Point first, Point second) {
        this.first = first;
        this.second = second;
    }

    public Rectangle(float x1, float y1, float x2, float y2) {
        first = new Point(x1, y1);
        second = new Point(x2, y2);
    }

    public void setX1(float x) {
        first.x = x;
    }

    public void setY1(float y) {
        first.y = y;
    }

    public void setWidth(float width) {
        second.x = first.x + width;
    }

    public void setHeight(float height) {
        second.y = first.y + height;
    }

    public void setX2(float x) {
        first.x = x;
    }

    public void setY2(float y) {
        first.y = y;
    }

    public Point getCenter() {
        return new Point((second.x + first.x) / 2, (second.y + first.y) / 2);
    }

    public void scale(float amountX, float amountY) {
        first.x *= amountX;
        second.x *= amountX;

        first.y *= amountY;
        second.y *= amountY;
    }

    public Point getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }

    public float getWidth() {
        return second.x - first.x;
    }

    public float getHeight() {
        return second.y - first.y;
    }

    public float getX() { return first.x; }
    public float getY() { return first.y; }

    public boolean isPointInside(float x, float y) {
        return x > first.x && x < second.x && y > first.y && y < second.y;
    }

    @Override
    public String toString() {
        return first.x + " " + first.y + " " + second.x + " " + second.y;
    }
}
