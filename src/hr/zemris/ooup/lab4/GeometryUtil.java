package hr.zemris.ooup.lab4;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by alojzije on 7.6.2014..
 */
public class GeometryUtil {

    //euklidska udlajenost izmedju dvije tocke
    public static double distanceFromPoint(Point p1, Point p2) {
        return sqrt(pow((p1.getX() - p2.getX()),2) + pow((p1.getY() - p2.getY()),2));
    }

    public static double distanceFromLineSegment(Point s, Point e, Point p) {
        if (p.getX() < s.getX())            // tocka p prije pocetne tocke s
            return GeometryUtil.distanceFromPoint(s, p);
        else if (p.getX()> e.getX())        // tocka p iza konacne pocke e
            return GeometryUtil.distanceFromPoint(e, p);
        else {                               // racuna se duljina okomice spustene iz p na duzinu se

            double A =   (e.getY() - s.getY());
            double B = - (e.getX() - s.getX());
            double C = e.getX()*s.getY() - s.getX()*e.getY();
            double distance =  abs(A * p.getX() + B * p.getY() + C)/sqrt(pow(A,2)+ pow(B,2));
            return distance;
        }
    }


}
