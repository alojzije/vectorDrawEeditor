package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.renderer.Renderer;
import hr.zemris.ooup.lab4.util.GeometryUtil;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by alojzije on 7.6.2014..
 */
public class LineSegment extends AbstractGraphicalObject {
    // hotpoints[0] = pocetna
    // hotpoints[1] = konacna
    public LineSegment() {
        super(new Point[]{new Point(0, 0), new Point(10, 0)});
    }

    public LineSegment(Point start, Point end) {
      super(new Point[]{new Point(0, 0), new Point(10, 0)});
       Point s,e;
        if (start.getX() == end.getX()) {
            if (start.getY() < end.getY()) {
                s = start;
                e = end;
            } else {
                s = end;
                e = start;
            }
        } else {
            if (start.getX() < end.getX()) {
                s = start;
                e = end;
            } else {
                s = end;
                e = start;
            }
        }
       this.setHotPoint(0,s);
       this.setHotPoint(1,e);

    }


    @Override
    public Rectangle getBoundingBox() {
        Point s = this.getHotPoint(0);
        Point e = this.getHotPoint(1);
        int x = s.getX() < e.getX()? s.getX() : e.getX();
        int y = s.getY() < e.getY()? s.getY() : e.getY();
        int width  = abs(e.getX() - s.getX());
        int height = abs(e.getY() - s.getY());

        return new Rectangle(x, y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        return GeometryUtil.distanceFromLineSegment(this.getHotPoint(0), this.getHotPoint(1), mousePoint);
    }

    @Override
    public void render(Renderer r) {
        r.drawLine(this.getHotPoint(0), this.getHotPoint(1));
    }

    @Override
    public String getShapeName() {
        return "line";
    }

    @Override
    public GraphicalObject duplicate() {
        return new LineSegment(this.getHotPoint(0), this.getHotPoint(1));
    }

    @Override
    public String getShapeID() {
        return "@LINE";
    }

    @Override
    public void save(List<String> rows) {
        String e = getHotPoint(1).getX() + " " + getHotPoint(1).getY();
        String s = getHotPoint(0).getX() + " " + getHotPoint(0).getY();
        rows.add(getShapeID() + " " + s + " " + e);

    }
}
