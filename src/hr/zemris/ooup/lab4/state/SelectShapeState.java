package hr.zemris.ooup.lab4.state;

import hr.zemris.ooup.lab4.DocumentModel;
import hr.zemris.ooup.lab4.Renderer;
import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;
import hr.zemris.ooup.lab4.util.myGraphic;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alojzije on 8.6.2014..
 */
public class SelectShapeState implements State{
    DocumentModel model;
    Renderer r;
   List objects = new ArrayList();

    public SelectShapeState(DocumentModel model, List objects, Renderer r) {
        this.model = model;
        this.objects = objects;
        this.r = r;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (!ctrlDown) {
            for (Object go:model.list())  {
                ((GraphicalObject)go).setSelected(false);
            }
        }
        GraphicalObject obj = model.findSelectedGraphicalObject(mousePoint);
        if (obj != null) obj.setSelected(true);


        afterDraw(r);

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
    public void afterDraw(Renderer r) {


    }

    @Override
    public void onLeaving() {

    }
}
