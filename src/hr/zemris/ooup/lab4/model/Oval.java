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

        int rY = (int) (GeometryUtil.distanceFromPoint(center, b));
        int rX = (int) (GeometryUtil.distanceFromPoint(center, r));
        int x = center.getX() - rX;
        int y = center.getY() - rY;

        return new Rectangle(x,y, 2*rX, 2*rY);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        Point b = this.getHotPoint(0); //bottom
        Point r = this.getHotPoint(1); //right
        int rx = (int) (GeometryUtil.distanceFromPoint(center, r));
        int ry = (int) (GeometryUtil.distanceFromPoint(center, b));
        double distX = GeometryUtil.distanceFromPoint(center, new Point(mousePoint.getX(), center.getY()));
        double distY = GeometryUtil.distanceFromPoint(center, new Point(center.getX(), mousePoint.getY()));
        double dist = GeometryUtil.distanceFromPoint(center, mousePoint);
        if (distX <= rx && distY <=ry)
            dist *= 0.01;

        return dist;
    }

    @Override
    public void render(Renderer r) {
        Rectangle rect = getBoundingBox();
        int width = rect.getWidth()/2;
        int height = rect.getHeight()/2;

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
        center = center.translate(delta);
        setHotPoint(0, getHotPoint(0).translate(delta));
        setHotPoint(1, getHotPoint(1).translate(delta));
    }

}
