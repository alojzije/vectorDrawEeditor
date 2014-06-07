/**
 * Created by alojzije on 7.6.2014..
 */
public interface GraphicalObject {

    // podrska za uredjivanje objekata

    boolean isSelected();
    void    setSelected(boolean selected);

    int     getNumberOfHotPoints();
    Point   getHotPoint(int index);
    void    setHotPoint(int index, Point point);
    boolean isHotPointSelected(int index);
    void    setHotPointSelected(int index, boolean selected);
    double  getHotPointDistance(int index, Point mousePoin);


    // Geometrijska opracija nad oblikom
    void translate(Point delta);
    

}
