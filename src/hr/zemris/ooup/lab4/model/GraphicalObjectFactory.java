package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.util.Point;

/**
 * Created by alojzije on 8.6.2014..
 */
public class GraphicalObjectFactory {
    public static GraphicalObject getGraphicalObject(String type) {
        if(type == "line") return new LineSegment(new Point(0, 20), new Point(30,0));
        else               return new Oval(new Point(0, 15), new Point(10,0));
    }
}
