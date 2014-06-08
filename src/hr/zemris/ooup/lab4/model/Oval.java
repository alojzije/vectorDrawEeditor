package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.Renderer;
import hr.zemris.ooup.lab4.util.GeometryUtil;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

import java.util.ArrayList;

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
    public void render(Renderer r) {
        Rectangle rect = getBoundingBox();
        int width = rect.getWidth();
        int height = rect.getHeight();

        ArrayList<Point>points = new ArrayList<Point>();
        for (int t = 0; t<=360; t+=10) {
            int eX = center.getX() + (int) (width  * Math.cos(Math.toRadians(t)));
            int eY = center.getY() + (int) (height * Math.sin(Math.toRadians(t)));
            points.add(new Point(eX, eY));
        }

        r.fillPolygon( points.toArray(new Point[points.size()]));
    }

    @Override
    public String getShapeName() {
        return "oval";
    }

    @Override
    public GraphicalObject duplicate() {
        return new Oval(this.getHotPoint(0), this.getHotPoint(1));
    }
    @Override
    public void translate(Point delta) {
        Point curr = getHotPoint(0);
        int deltaX =  delta.getX() - curr.getX();
        int deltaY =  delta.getY() - curr.getY();
        for (int i = 0; i<getNumberOfHotPoints(); i++) {
            curr = getHotPoint(i);
            setHotPoint(i, curr.translate(new Point(deltaX, deltaY)));
        }
        center = new Point(getHotPoint(0).getX(),getHotPoint(1).getY());
    }

}
