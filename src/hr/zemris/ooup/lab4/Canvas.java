package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alojzije on 8.6.2014..
 */
public class Canvas extends JPanel {
    DocumentModel docModel;

    public Canvas(DocumentModel docModel) {
        this.docModel = docModel;
       // this.setBorder(BorderFactory.createLineBorder(Color.black));

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Renderer r = new G2DRendererImpl(g2d);
        for(Object o: docModel.list())
            ((GraphicalObject)o).render(r);
    }

}
