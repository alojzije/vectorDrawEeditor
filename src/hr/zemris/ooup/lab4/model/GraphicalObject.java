package hr.zemris.ooup.lab4.model;

import hr.zemris.ooup.lab4.GraphicalObjectListener;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

/**
 * Created by alojzije on 7.6.2014..
 */
public interface GraphicalObject {

    // podrska za uredjivanje objekata

    boolean isSelected();
    void    setSelected(boolean selected);

    int     getNumberOfHotPoints();
    Point getHotPoint(int index);
    void    setHotPoint(int index, Point point);
    boolean isHotPointSelected(int index);
    void    setHotPointSelected(int index, boolean selected);
    double  getHotPointDistance(int index, Point mousePoint);


    // Geometrijske opracija nad oblikom
    void      translate(Point delta);
    Rectangle getBoundingBox();
    double    selectionDistance(Point mousePoint);


    // podrska za crtanje (dio mosta)
//    void render (hr.zemris.ooup.lab4.Renderer r);


    // Observer za dojavu promjena modelu
    public void addGraphicalObjectListener(GraphicalObjectListener l);
    public void removeGraphicalObjectListener(GraphicalObjectListener l);


    // podrska za prototip(Alatna trakam stvaranje objekata u crtezu)
    String          getShapeName();
    GraphicalObject duplicate();


    // podrska za snimanje i ucitavanje
//    public String getShapeID();
//    public void   load(Stack<hr.zemris.ooup.lab4.model.GraphicalObject> stack, String data);
//    public void   save(List<String> rows);

}
