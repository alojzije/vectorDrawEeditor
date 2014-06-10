package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.util.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alojzije on 8.6.2014..
 */
public class GraphicalObjectFactory {
    public static final Map<String, GraphicalObject> graphicalObjectMap = new HashMap<String, GraphicalObject>();

    static {
        graphicalObjectMap.put("@LINE", getGraphicalObject("line"));
        graphicalObjectMap.put("@OVAL", getGraphicalObject("oval"));
        graphicalObjectMap.put("@COMP", getGraphicalObject("composite"));
    }

    public static GraphicalObject getGraphicalObject(String type) {
        if      (type == "line")  return new LineSegment(new Point(0, 20), new Point(30,0));
        else if (type == "oval")  return new Oval(new Point(0, 15), new Point(10,0));
        else                      return new CompositeShape(new ArrayList<GraphicalObject>(), false);
    }
}
