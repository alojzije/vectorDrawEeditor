package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.GraphicalObjectListener;
import hr.zemris.ooup.lab4.renderer.Renderer;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by alojzije on 9.6.2014..
 */
public class CompositeShape implements GraphicalObject {
    ArrayList<GraphicalObject> objects = new ArrayList<GraphicalObject>();
    boolean selected;
    List<GraphicalObjectListener> listeners;

    public CompositeShape(ArrayList<GraphicalObject> objects,boolean selected) {
        this.objects = objects;
        this.selected = selected;
        listeners = new ArrayList<GraphicalObjectListener>();

    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        notifySelectionListeners();
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
    public void setHotPointSelected(int index, boolean selected) {}

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return 100000;
    }

    @Override
    public void translate(Point delta) {
        for (GraphicalObject o : objects)
            o.translate(delta);
        notifyListeners();
    }
    // Vrati nepromjenjivu listu postojecih objekata (izmjene smiju ici samo kroz metode modela)
    public List list() {
        return Collections.unmodifiableList(objects);
    }

    @Override
    public Rectangle getBoundingBox() {

        Rectangle rect = objects.get(0).getBoundingBox();
        int sx = rect.getX();
        int sy = rect.getY();
        int ex = rect.getX();
        int ey = rect.getY();

        for (GraphicalObject o : objects) {
            rect = o.getBoundingBox();
            sx = rect.getX() < sx ? rect.getX() : sx;
            sy = rect.getY() < sy ? rect.getY() : sy;
            if (rect.getX()+rect.getWidth()  > ex) ex = rect.getX() + rect.getWidth();
            if (rect.getY()+rect.getHeight() > ey) ey = rect.getY() + rect.getHeight();
        }

        return new Rectangle(sx,sy, ex-sx, ey-sy);
    }

    @Override
    public double selectionDistance(Point mousePoint) {
        double dist = objects.get(0).selectionDistance(mousePoint);
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
    public void addGraphicalObjectListener(GraphicalObjectListener l) {
        listeners.add(l);
    }

    @Override
    public void removeGraphicalObjectListener(GraphicalObjectListener l) {
        if (listeners.contains(l))
            listeners.remove(l);
    }

    @Override
    public String getShapeName() {
        return "composite";
    }

    @Override
    public GraphicalObject duplicate() {
        return new CompositeShape(this.objects, this.selected);
    }

    @Override
    public String getShapeID() {
        return "@COMP";
    }

    @Override
    public void load(Stack<GraphicalObject> stack, String data) {
        int childNb = Integer.parseInt(data);
        ArrayList<GraphicalObject> objs = new ArrayList<GraphicalObject>();
        for (int i = 0; i < childNb; i++)
            this.objects.add(stack.pop());
        stack.push(this.duplicate());
    }

    @Override
    public void save(List<String> rows) {
        for (GraphicalObject o : objects)
            o.save(rows);
        rows.add(getShapeID() + " " + objects.size());
    }

    void notifyListeners() {
        for (GraphicalObjectListener l : listeners) {
            l.graphicalObjectChanged(this);
        }
    }

    void notifySelectionListeners() {
        for (GraphicalObjectListener l : listeners) {
            l.graphicalObjectSelectionChanged(this);
        }
    }

}
