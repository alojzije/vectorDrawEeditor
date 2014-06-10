package hr.zemris.ooup.lab4.state;

import hr.zemris.ooup.lab4.DocumentModel;
import hr.zemris.ooup.lab4.renderer.Renderer;
import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.util.Point;

import java.awt.*;

/**
 * Created by alojzije on 8.6.2014..
 */
public class AddShapeState implements State {
    private GraphicalObject protoype;
    private DocumentModel model;


    public AddShapeState(DocumentModel model, GraphicalObject protoype) {
        this.model = model;
        this.protoype = protoype;

    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        GraphicalObject obj = protoype.duplicate();
        obj.translate(mousePoint);
        model.addGraphicalObject(obj);

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
    public void afterDraw(Renderer renderer, Graphics graphics) {


    }

    @Override
    public void onLeaving() {

    }
}
