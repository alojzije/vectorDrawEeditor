package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.model.Oval;
import hr.zemris.ooup.lab4.state.AddShapeState;
import hr.zemris.ooup.lab4.state.IdleState;
import hr.zemris.ooup.lab4.state.State;
import hr.zemris.ooup.lab4.util.myGraphic;
import hr.zemris.ooup.lab4.util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alojzije on 8.6.2014..
 */
public class GUI extends JFrame {
    List objects;
    JToolBar toolbar;
    Canvas canvas;
    DocumentModel docModel;
    private State currentState;
    Renderer r = new G2DRendererImpl(new myGraphic());
    Graphics2D myGraphic = new myGraphic();
    ArrayList<JButton> buttons = new ArrayList<JButton>();

    public GUI(List objects) {
        this.objects = objects;
        docModel = new DocumentModel();
        toolbar = new JToolBar();
        canvas = new Canvas(docModel);
        currentState = new IdleState();
        initializeFrame();
    }

    private void initializeFrame() {
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        for (Object obj : objects) {
            docModel.addGraphicalObject((GraphicalObject) obj);
            buttons.add(new JButton(((GraphicalObject) obj).getShapeName()));
           toolbar.add(buttons.get(buttons.size() - 1));
        }
        canvas.paintComponent(myGraphic);
        ListenForMouse lForMouse = new ListenForMouse();
        canvas.addMouseListener(lForMouse);
        this.add(toolbar,BorderLayout.NORTH);
        this.add(canvas, BorderLayout.CENTER);

        KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kfm.addKeyEventDispatcher(new MyDispatcher());
    }
    private void rePaint() {
        this.repaint();
    }

    private class ListenForMouse implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

            GraphicalObject obj = new Oval(new Point(100, 100), new Point(110, 90));
            currentState = new AddShapeState(docModel, obj);
            currentState.mouseDown(new Point(e.getX(), e.getY()), false, false);

            System.out.println("addShapeState obj nb " + docModel.list().size());

            rePaint();

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class MyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if(e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    currentState = new IdleState();
                    System.out.println("idleState");
                    rePaint();
                }
            }
            return false;
        }
    }

}
