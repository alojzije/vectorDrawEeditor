package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.state.IdleState;
import hr.zemris.ooup.lab4.state.State;
import hr.zemris.ooup.lab4.util.myGraphic;

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
    JPanel toolbar;
    Canvas canvas;
    DocumentModel docModel;
    private State currentState;
    ArrayList<JButton> buttons = new ArrayList<JButton>();

    public GUI(List objects) {
        this.objects = objects;
        docModel = new DocumentModel();
        toolbar = new JPanel();
        canvas = new Canvas(docModel);
        currentState = new IdleState();
        initializeFrame();
    }

    private void initializeFrame() {
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
        for (Object obj : objects) {
            docModel.addGraphicalObject((GraphicalObject) obj);
            buttons.add(new JButton(((GraphicalObject) obj).getShapeName()));
           toolbar.add(buttons.get(buttons.size() - 1));
        }
        canvas.paintComponent(new myGraphic());
        ListenForMouse lForMouse = new ListenForMouse();
        canvas.addMouseListener(lForMouse);
        ListenForKey lForKey = new ListenForKey();
        this.addKeyListener(lForKey);
        this.add(toolbar,BorderLayout.NORTH);
        this.add(canvas, BorderLayout.CENTER);

    }

    private class ListenForMouse implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("click");
        }

        @Override
        public void mousePressed(MouseEvent e) {

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

    private class ListenForKey implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("la");
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.out.println("esc");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
