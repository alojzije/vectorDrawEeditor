package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

/**
 * Created by alojzije on 7.6.2014..
 */
public interface Renderer {
    void drawLine(Point s, Point e);
    void fillPolygon(Point[] points);



}
