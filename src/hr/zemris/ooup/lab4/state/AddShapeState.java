package hr.zemris.ooup.lab4.state;

import hr.zemris.ooup.lab4.DocumentModel;
import hr.zemris.ooup.lab4.GUI;
import hr.zemris.ooup.lab4.Renderer;
import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.model.Oval;
import hr.zemris.ooup.lab4.util.Point;

import java.awt.event.KeyEvent;

/**
 * Created by alojzije on 8.6.2014..
 */
public class AddShapeState implements State {
    private GraphicalObject protoype;
    private DocumentModel model;
    private GUI gui;

    public AddShapeState(DocumentModel model, GraphicalObject protoype, GUI gui) {
        this.model = model;
        this.protoype = protoype;
        this.gui = gui;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject obj = protoype.duplicate();
        obj.translate(mousePoint);
        model.addGraphicalObject(obj);
        gui.repaint();

    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

    }

    @Override
    public void mouseDragged(Point mousePoint) {

    }

    @Override
    public void keyPressed(int keyCode) {


    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject graphicalObject) {

    }

    @Override
    public void afterDraw(Renderer renderer) {


    }

    @Override
    public void onLeaving() {

    }
}
