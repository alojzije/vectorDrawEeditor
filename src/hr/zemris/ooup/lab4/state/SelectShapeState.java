package hr.zemris.ooup.lab4.state;

import hr.zemris.ooup.lab4.DocumentModel;
import hr.zemris.ooup.lab4.renderer.Renderer;
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
        if (!ctrlDown) {
            for (Object go : model.list()) {
                ((GraphicalObject) go).setSelected(false);
            }
        }
        GraphicalObject obj = model.findSelectedGraphicalObject(mousePoint);
        if (obj != null) obj.setSelected(true);

        //is hot point selected
        if (!ctrlDown && model.getSelectedObjects().size() == 1) {
            int hpIndex = model.findSelectedHotPoint(obj, mousePoint);
            if (hpIndex != -1)
                obj.setHotPointSelected(hpIndex, true);

        }


    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        if (model.getSelectedObjects().size() == 1) {
            GraphicalObject obj = (GraphicalObject) model.getSelectedObjects().get(0);
            for (int i = 0; i < obj.getNumberOfHotPoints(); i++) {
                if (obj.isHotPointSelected(i))
                    obj.setHotPointSelected(i, false);
            }
        }
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        if (model.getSelectedObjects().size() == 1) {
            int hpIndex = -1;
            GraphicalObject obj = (GraphicalObject) model.getSelectedObjects().get(0);
            for (int i = 0; i < obj.getNumberOfHotPoints(); i++) {
                if (obj.isHotPointSelected(i))
                    hpIndex = i;
            }
            if (hpIndex != -1)
                obj.setHotPoint(hpIndex, mousePoint);
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
                    GraphicalObject obj = (GraphicalObject) o;
                    model.addGraphicalObject(obj);
                    if(!obj.isSelected()) obj.setSelected(true);
                }
                model.removeGraphicalObject(cs);

            }

        }


    }

    @Override
    public void afterDraw(Renderer r, GraphicalObject graphicalObject) {

    }

    @Override
    public void afterDraw(Renderer r, Graphics g) {
//        g.setColor(Color.blue);
//        if (model.getSelectedObjects().size() == 1) {
//            GraphicalObject obj = (GraphicalObject)model.getSelectedObjects().get(0);
//            for (int i = 0; i < obj.getNumberOfHotPoints(); i++) {
//                Point hp = obj.getHotPoint(i);
//                g.drawRect(hp.getX()-2, hp.getY()-2,4,4);
//            }
//        }
//
//        for (Object o : model.getSelectedObjects()) {
//            Rectangle rect = ((GraphicalObject) o).getBoundingBox();
//            g.drawRect(rect.getX(), rect.getY(), rect.getWidth(),rect.getHeight());
//        }



    }

    @Override
    public void onLeaving() {
        List selected = model.getSelectedObjects();
        for (int i = selected.size()-1; i >= 0; i--) {
            ((GraphicalObject) selected.get(i)).setSelected(false);
        }

    }
}
