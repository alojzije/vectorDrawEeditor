package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.model.LineSegment;

/**
 * Created by alojzije on 7.6.2014..
 */
public class testMain {
    public static void main(String[] args) {
        Point s = new Point(2,5);
        System.out.println("Point s: (" + s.getX() + ", " + s.getY() +")");
        Point e = new Point(4,5);
        System.out.println("Point e: (" + e.getX() + ", " + e.getY() +")");

        Point t = s.translate(e);
        System.out.println("Point t: (" + t.getX() + ", " + t.getY() +")");
        Point d = s.difference(e);
        System.out.println("Point d: (" + d.getX() + ", " + d.getY() +")");



        LineSegment ls = new LineSegment(e,s);
        System.out.println(ls.getShapeName() + " nb of points: " + ls.getNumberOfHotPoints());
        System.out.println("Line = (" +ls.getHotPoint(0).getX()+ ","+ ls.getHotPoint(0).getY()+"), ("+
                            ls.getHotPoint(1).getX()+" " +ls.getHotPoint(1).getY()+")");
        System.out.println("Line selected = {" +ls.isHotPointSelected(0)+ "," +ls.isHotPointSelected(1)+"}");
        System.out.println("Line selected = {" +ls.isHotPointSelected(0)+ "," +ls.isHotPointSelected(1)+"}");
        Rectangle r = ls.getBoundingBox();
        System.out.println("rect r: (" + r.getX() + ", " + r.getY() + "), "+ r.getWidth() + "x" + r.getHeight());
        double dist= ls.selectionDistance(t);
        System.out.println("distance line from t: "+ dist);

        GraphicalObject dupl = ls.duplicate();
        System.out.println(dupl.getShapeName() + " nb of points: " + dupl.getNumberOfHotPoints());
        System.out.println("Dupl line  = (" +dupl.getHotPoint(0).getX()+ ","+ dupl.getHotPoint(0).getY()+"), ("+
                dupl.getHotPoint(1).getX()+" " +dupl.getHotPoint(1).getY()+")");

    }
}
