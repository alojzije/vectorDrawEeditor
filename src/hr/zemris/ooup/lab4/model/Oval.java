package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.util.GeometryUtil;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

import static java.lang.Math.abs;

/**
 * Created by alojzije on 7.6.2014..
 */
public class Oval extends AbstractGraphicalObject {
    // hotpoints[0] = donji
    // hotpoints[1] = desni
    Point center;
    public Oval() {
        super(new Point[]{new Point(0, 10), new Point(10, 0)});
        center = new Point(0,0);
    }
    public Oval(Point p1, Point p2) {
        super(new Point[]{new Point(0, 10), new Point(10, 0)});
        Point bottom, right;
        if (p1.getX() < p2.getX()) {
            bottom = p1;
            right  = p2;
        } else {
            bottom = p2;
            right = p1;
        }
        this.setHotPoint(0, bottom);
        this.setHotPoint(1, right);
        center = new Point(bottom.getX(),right.getY());
    }


    @Override
    public Rectangle getBoundingBox() {
        Point b = this.getHotPoint(0); //bottom
        Point r = this.getHotPoint(1); //right
        int x = b.getX() - (r.getX() - b.getX());
        int y = b.getY();
        int width = r.getX() - x;
        int height = abs(r.getY() - y)*2;

        return new Rectangle(x,y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return GeometryUtil.distanceFromPoint(center, mousePoint);
    }

    @Override
    public String getShapeName() {
        return "oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(this.getHotPoint(0), this.getHotPoint(1));
    }
}
