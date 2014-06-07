/**
 * Created by alojzije on 7.6.2014..
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Point translate(Point dp) {
        return new Point(x + dp.x, y + dp.y);
    }

    public Point difference(Point p) {
        return new Point(x - p.x, y - p.y);
    }
}
