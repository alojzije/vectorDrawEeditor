package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.renderer.*;
import hr.zemris.ooup.lab4.util.Point;
import hr.zemris.ooup.lab4.util.Rectangle;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alojzije on 8.6.2014..
 */
public class Canvas extends JPanel {
    DocumentModel docModel;

    public Canvas(DocumentModel docModel) {
        this.docModel = docModel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        hr.zemris.ooup.lab4.renderer.Renderer r = new G2DRendererImpl(g2d);
        for(Object o: docModel.list())
            ((GraphicalObject)o).render(r);


        // selected objects
        g.setColor(Color.blue);
        if (docModel.getSelectedObjects().size() == 1) {
            GraphicalObject obj = (GraphicalObject)docModel.getSelectedObjects().get(0);
            for (int i = 0; i < obj.getNumberOfHotPoints(); i++) {
                Point hp = obj.getHotPoint(i);
                g.drawRect(hp.getX()-2, hp.getY()-2,4,4);
            }
        }

        for (Object o : docModel.getSelectedObjects()) {
            Rectangle rect = ((GraphicalObject) o).getBoundingBox();
            g.drawRect(rect.getX(), rect.getY(), rect.getWidth(),rect.getHeight());
        }

    }

}
