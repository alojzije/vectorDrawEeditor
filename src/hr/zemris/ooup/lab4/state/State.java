package hr.zemris.ooup.lab4.state;

import hr.zemris.ooup.lab4.renderer.Renderer;
import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.util.Point;

import java.awt.*;

/**
 * Created by alojzije on 8.6.2014..
 */
public interface State {
    void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown);

    void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown);

    void mouseDragged(Point mousePoint);

    void keyPressed(int keyCode);

    //poziva se nakon sto je platno nacrtalo graficki objekt predan kao arg
    void afterDraw(Renderer r, GraphicalObject graphicalObject);

    //poziva se nakon sto je platno nacrtalo citav crtez
    void afterDraw(Renderer renderer, Graphics graphics);

    //poziva se kada program napusta ovo stanje kako bi preslo u drugo
    void onLeaving();


}
