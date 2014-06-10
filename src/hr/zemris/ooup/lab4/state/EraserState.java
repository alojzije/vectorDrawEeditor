package hr.zemris.ooup.lab4.state;

import hr.zemris.ooup.lab4.Canvas;
import hr.zemris.ooup.lab4.DocumentModel;
import hr.zemris.ooup.lab4.GUI;
import hr.zemris.ooup.lab4.renderer.Renderer;
import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.util.Point;

import java.awt.*;
import java.util.List;

/**
 * Created by alojzije on 10.6.2014..
 */
public class EraserState implements State {
    GUI gui;
    Canvas canvas;
    DocumentModel model;

    public EraserState(DocumentModel docModel, GUI gui, Canvas canvas) {
        this.gui = gui;
        this.canvas = canvas;
        this.model = docModel;
    }

    @Override
    public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {

    }

    @Override
    public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
        List selected = model.getSelectedObjects();
        for (int i = selected.size() - 1; i >= 0; i--)
            model.removeGraphicalObject((GraphicalObject) selected.get(i));
        gui.repaint();
    }

    @Override
    public void mouseDragged(Point mousePoint) {
        canvas.getGraphics().drawOval(mousePoint.getX(), mousePoint.getY(), 1, 1);
        GraphicalObject selected = model.findSelectedGraphicalObject(mousePoint);
        if (selected != null)
            selected.setSelected(true);

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
