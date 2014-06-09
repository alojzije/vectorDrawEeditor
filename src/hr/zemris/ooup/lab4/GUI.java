package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.model.GraphicalObjectFactory;
import hr.zemris.ooup.lab4.model.LineSegment;
import hr.zemris.ooup.lab4.model.Oval;
import hr.zemris.ooup.lab4.state.*;
import hr.zemris.ooup.lab4.util.*;
import hr.zemris.ooup.lab4.util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alojzije on 8.6.2014..
 */
public class GUI extends JFrame implements DocumentModelListener {
    private State currentState = new IdleState();

    DocumentModel docModel = new DocumentModel();
    Canvas canvas          = new Canvas(docModel);

    JToolBar toolbar            = new JToolBar();
    ArrayList<JButton> buttons  = new ArrayList<JButton>();
    ToolbarListener lForToolbar = new ToolbarListener();


    Graphics2D myGraphic = new myGraphic();
    Renderer r           = new G2DRendererImpl(myGraphic);

    List objects;

    public GUI(List objects) {
        this.objects = objects;
        docModel.addDocumentModelListener(GUI.this);
        addObjectsToDoc();
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
        ListenForMouseMotion lForMouseMotion = new ListenForMouseMotion();
        canvas.addMouseMotionListener(lForMouseMotion);
        this.add(canvas, BorderLayout.CENTER);


    }

    private void addToolbar() {

        buttons.add(new JButton("line"));
        buttons.add(new JButton("oval"));
        buttons.add(new JButton("selektiraj"));
        buttons.add(new JButton("brisi"));
        for (JButton b : buttons) {
            b.addActionListener(lForToolbar);
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

    @Override
    public void documentChange() {
        this.repaint();

        currentState.afterDraw(r, canvas.getGraphics());
    }

    private class ListenForMouse implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            currentState.mouseDown(new Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
        }
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {
            currentState.mouseUp(new Point(e.getX(), e.getY()), e.isShiftDown(), e.isControlDown());
        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private class ListenForMouseMotion implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            currentState.mouseDragged(new Point(e.getX(), e.getY()));
        }
        @Override
        public void mouseMoved(MouseEvent e) {}
    }
    private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if(e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    currentState.onLeaving();
                    currentState = new IdleState();
                    System.out.println("idleState");
                }else {
                    currentState.keyPressed(e.getKeyCode());
                }
            }
            return false;
        }

    }

    private class ToolbarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonType = ((JButton) e.getSource()).getText();
            if (buttonType == "line") {
                currentState.onLeaving();
                GraphicalObject obj = GraphicalObjectFactory.getGraphicalObject(buttonType);
                objects.add(obj);
                currentState = new AddShapeState(docModel, obj);
            }else if (buttonType == "oval") {
                currentState.onLeaving();
                GraphicalObject obj = GraphicalObjectFactory.getGraphicalObject(buttonType);
                objects.add(obj);
                currentState = new AddShapeState(docModel, obj);
            }else if (buttonType == "selektiraj") {
                currentState.onLeaving();
                currentState = new SelectShapeState(docModel, objects, r);
            }else if (buttonType == "brisi") {
                currentState.onLeaving();
                currentState = new EraserState(docModel, GUI.this, canvas);

            }

        }
    }


}
