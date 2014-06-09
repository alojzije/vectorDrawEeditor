package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.GraphicalObjectListener;
import hr.zemris.ooup.lab4.util.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alojzije on 7.6.2014..
 */
public abstract class AbstractGraphicalObject implements GraphicalObject{
    Point[] hotPoints;
    boolean[] hotPointSelected;
    boolean selected;
    List<GraphicalObjectListener> listeners;

    protected AbstractGraphicalObject(Point[] hotPoints) {
        this.hotPoints = hotPoints;
        hotPointSelected = new boolean[hotPoints.length];
        selected = false;
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
        return hotPoints.length;
    }

    @Override
    public Point getHotPoint(int index) {
        return hotPoints[index];
    }

    @Override
    public void setHotPoint(int index, Point point) {
        hotPoints[index] = point;
    }

    @Override
    public boolean isHotPointSelected(int index) {
        return hotPointSelected[index];
    }

    @Override
    public void setHotPointSelected(int index, boolean selected) {
        hotPointSelected[index] = true;
    }

    @Override
    public double getHotPointDistance(int index, Point mousePoint) {
        return 0;
    }

    @Override
    public void translate(Point delta) {
        setHotPoint(0, getHotPoint(0).translate(delta));
        setHotPoint(1, getHotPoint(1).translate(delta));
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
