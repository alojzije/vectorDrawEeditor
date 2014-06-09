package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.GraphicalObjectListener;
import hr.zemris.ooup.lab4.Renderer;
import hr.zemris.ooup.lab4.util.GeometryUtil;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alojzije on 9.6.2014..
 */
public class CompositeShape implements GraphicalObject {
    ArrayList<AbstractGraphicalObject> objects;
    boolean selected;

    public CompositeShape(boolean selected, ArrayList<AbstractGraphicalObject> objects) {
        this.selected = selected;
        this.objects = objects;
    }


    @Override
    public int getNumberOfHotPoints() {
        return 0;
    }

    @Override
    public Point getHotPoint(int index) {
        return null;
    }

    @Override
    public void setHotPoint(int index, Point point) {}

    @Override
    public boolean isHotPointSelected(int index) {
        return false;
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return 100000;
    }

    @Override
    public void translate(Point delta) {
        for (GraphicalObject o : objects)
            o.translate(delta);
    }


    @Override
    public Rectangle getBoundingBox() {

        Rectangle rect = objects.get(0).getBoundingBox()
        int x = rect.getX();
        int y = rect.getY();
        int width = 0;
        int height = 0;
        for (GraphicalObject o : objects) {
            rect = o.getBoundingBox();
            x = x > rect.getX() ? rect.getX() : x;
            y = y > rect.getY() ? rect.getY() : y;
            width += rect.getWidth();
            height += rect.getHeight();
        }

        return new Rectangle(x,y, width, height);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        double dist = objects.get(0).selectionDistance(mousePoint)
        for (GraphicalObject o : objects) {
            double tempDist = o.selectionDistance(mousePoint);
            dist = tempDist < dist ? tempDist : dist;
        }
        return dist;
    }

    @Override
    public void render(Renderer r) {
        for (GraphicalObject o : objects)
            o.render(r);
    }

    @Override
    public String getShapeName() {
        return "composite";
    }

    @Override
    public GraphicalObject duplicate() {
        return new CompositeShape(this.objects, this.selected);
    }

}
