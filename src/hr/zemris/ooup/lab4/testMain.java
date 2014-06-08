package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.model.LineSegment;
import hr.zemris.ooup.lab4.model.Oval;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alojzije on 7.6.2014..
 */
public class testMain {
    public static void main(String[] args) {
        List objects = new ArrayList();
        objects.add(new LineSegment(new Point(10,10), new Point(20,30)));
        //objects.add(new Oval(new Point (200, 200), new Point (220,150)));

        GUI gui = new GUI(objects);
        gui.setVisible(true);
    }
}
