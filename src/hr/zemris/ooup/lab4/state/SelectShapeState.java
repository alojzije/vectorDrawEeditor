package hr.zemris.ooup.lab4.state;

import hr.zemris.ooup.lab4.DocumentModel;
import hr.zemris.ooup.lab4.Renderer;
import hr.zemris.ooup.lab4.model.CompositeShape;
import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.util.Point;

import java.awt.*;
import java.awt.event.KeyEvent;
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
        // if ctrl is down deselect all selected  objects
        // select hot point if only one object selected
        if (!ctrlDown) {
            for (Object go : model.list()) {
                ((GraphicalObject) go).setSelected(false);
            }
        }
        GraphicalObject obj = model.findSelectedGraphicalObject(mousePoint);
        if (obj != null) obj.setSelected(true);


    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (model.getSelectedObjects().size() == 1) {
            GraphicalObject obj = (GraphicalObject) model.getSelectedObjects().get(0);
            int hpIndex = model.findSelectedHotPoint(obj, mousePoint);
            System.out.println(hpIndex);
            if (hpIndex != -1) {
                obj.setHotPointSelected(hpIndex, true);
                obj.setHotPoint(hpIndex, mousePoint);
            }
        }

    }

    @Override
    public void keyPressed(int keyCode) {
        if ( keyCode == KeyEvent.VK_UP) {
            model.translateSelected(new Point(0, -1));

        }else if ( keyCode == KeyEvent.VK_DOWN) {
            model.translateSelected(new Point(0, 1));

        }else if ( keyCode == KeyEvent.VK_LEFT) {
            model.translateSelected(new Point(-1, 0));

        }else if ( keyCode == KeyEvent.VK_RIGHT) {
            model.translateSelected(new Point(1, 0));

        }else if ( keyCode == KeyEvent.VK_PLUS) {
            if(model.getSelectedObjects().size()==1)
                model.increaseZ((GraphicalObject)model.getSelectedObjects().get(0));

        }else if ( keyCode == KeyEvent.VK_MINUS) {
            if(model.getSelectedObjects().size()==1)
                model.decreaseZ((GraphicalObject)model.getSelectedObjects().get(0));
        }else if ( keyCode == KeyEvent.VK_G) {
            ArrayList<GraphicalObject> newSelect = new ArrayList<GraphicalObject>();
            List selected = model.getSelectedObjects();
            for (int i = selected.size()-1; i >= 0; i--) {
                newSelect.add((GraphicalObject) selected.get(i));
                model.removeGraphicalObject((GraphicalObject) selected.get(i));
            }
            model.addGraphicalObject(new CompositeShape(newSelect, true));

        }else if ( keyCode == KeyEvent.VK_U) {
            if (model.getSelectedObjects().size() == 1 && ((GraphicalObject) model.getSelectedObjects().get(0)).getShapeName() == "composite") {
                CompositeShape cs = (CompositeShape)model.getSelectedObjects().get(0);

                for (Object o : cs.list()) {
                    System.out.println(((GraphicalObject) o).getShapeName());
                    model.addGraphicalObject((GraphicalObject) o);
                }
                model.removeGraphicalObject(cs);

            }

        }


    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject graphicalObject) {

    }

    @Override
    public void afterDraw(Renderer r, Graphics graphics) {

    }

    @Override
    public void onLeaving() {
        List selected = model.getSelectedObjects();
        for (int i = selected.size()-1; i >= 0; i--) {
            ((GraphicalObject) selected.get(i)).setSelected(false);
        }

    }
}
