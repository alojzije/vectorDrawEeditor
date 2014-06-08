package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.model.GraphicalObjectFactory;
import hr.zemris.ooup.lab4.model.LineSegment;
import hr.zemris.ooup.lab4.model.Oval;
import hr.zemris.ooup.lab4.state.AddShapeState;
import hr.zemris.ooup.lab4.state.IdleState;
import hr.zemris.ooup.lab4.state.State;
import hr.zemris.ooup.lab4.util.myGraphic;
import hr.zemris.ooup.lab4.util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alojzije on 8.6.2014..
 */
public class GUI extends JFrame {
    int i = 100;
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
        addObjectsToDoc();
        toolbar = new JToolBar();
        canvas = new Canvas(docModel);
        currentState = new IdleState();
        initializeFrame();
        addToolbar();
        addCanvas();
    }

    private void addObjectsToDoc() {
        for (Object obj : objects) {
            docModel.addGraphicalObject((GraphicalObject)obj);
        }
    }

    private void addCanvas() {
        canvas.paintComponent(myGraphic);
        ListenForMouse lForMouse = new ListenForMouse();
        canvas.addMouseListener(lForMouse);
        this.add(canvas, BorderLayout.CENTER);


    }

    private void addToolbar() {
        ButtonListener lForClick = new ButtonListener();
        buttons.add(new JButton("line"));
        buttons.get(0).addActionListener(lForClick);
        buttons.add(new JButton("oval"));
        buttons.get(1).addActionListener(lForClick);
        for (JButton b : buttons) {
            toolbar.add(b);
        }

        this.add(toolbar, BorderLayout.NORTH);

    }

    private void initializeFrame() {
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kfm.addKeyEventDispatcher(new MyDispatcher());

    }
    private void rePaint() {
        this.repaint();
    }

    private class ListenForMouse implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            currentState.mouseDown(new Point(e.getX(), e.getY()), false, false);
            //System.out.println("addShapeState obj nb " + docModel.list().size());
            rePaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
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

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String type = ((JButton) e.getSource()).getText();
            GraphicalObject obj = GraphicalObjectFactory.getGraphicalObject(type);
            currentState = new AddShapeState(docModel, obj);
        }
    }


}
