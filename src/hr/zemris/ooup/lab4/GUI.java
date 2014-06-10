package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.model.GraphicalObjectFactory;
import hr.zemris.ooup.lab4.renderer.*;
import hr.zemris.ooup.lab4.state.*;
import hr.zemris.ooup.lab4.util.*;
import hr.zemris.ooup.lab4.util.Point;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    hr.zemris.ooup.lab4.renderer.Renderer r           = new G2DRendererImpl(myGraphic);

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
        buttons.add(new JButton("Line"));
        buttons.add(new JButton("Oval"));
        buttons.add(new JButton("Selektiraj"));
        buttons.add(new JButton("Brisi"));
        buttons.add(new JButton("SVGexport"));
        buttons.add(new JButton("Pohrani"));
        buttons.add(new JButton("Ucitaj"));
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
        //currentState.afterDraw(r, canvas.getGraphics());
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
            if (buttonType == "Line") {
                currentState.onLeaving();
                GraphicalObject obj = GraphicalObjectFactory.getGraphicalObject("line");
                objects.add(obj);
                currentState = new AddShapeState(docModel, obj);
            }else if (buttonType == "Oval") {
                currentState.onLeaving();
                GraphicalObject obj = GraphicalObjectFactory.getGraphicalObject("oval");
                objects.add(obj);
                currentState = new AddShapeState(docModel, obj);
            }else if (buttonType == "Selektiraj") {
                currentState.onLeaving();
                currentState = new SelectShapeState(docModel, objects, r);
            }else if (buttonType == "Brisi") {
                currentState.onLeaving();
                currentState = new EraserState(docModel, GUI.this, canvas);
            }else if (buttonType == "SVGexport") {
                currentState.onLeaving();
                currentState = new IdleState();
                String fileName = pitajIme();
                SVGRendererImpl svgRenderer = new SVGRendererImpl(fileName);
                for(Object o: docModel.list())
                    ((GraphicalObject)o).render(svgRenderer);
                svgRenderer.close();
                currentState = new EraserState(docModel, GUI.this, canvas);
            }else if (buttonType == "Pohrani") {
                String fileName = pitajIme();
                List<String> rows = new ArrayList<String>();
                for(Object o: docModel.list())
                    ((GraphicalObject)o).save(rows);
                writeOut(fileName, rows);
            }else if (buttonType == "Ucitaj") {
                List<String> rows = loadFile();
                Stack<GraphicalObject> objStack = new Stack<GraphicalObject>();
                for (String r : rows) {
                    String ID = r.substring(0, r.indexOf(" "));
                    GraphicalObject o = GraphicalObjectFactory.graphicalObjectMap.get(ID);
                    o.load(objStack, r.substring(r.indexOf(" ") + 1));

                }
                for (GraphicalObject o : objStack)
                    docModel.addGraphicalObject(o);


            }

        }
    }

    private void writeOut(String fileName, List<String> rows) {
        try {
            PrintWriter txtWriter = new PrintWriter(fileName + ".txt");
            for (String line : rows)
                txtWriter.println(line);
            txtWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private List<String> loadFile() {
        List<String> rows = new ArrayList<String>();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", new String[]{"txt"});
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
               rows = Files.readAllLines(Paths.get(file.getAbsolutePath()), Charset.defaultCharset());

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

        }
        for (String r : rows) {
            System.out.println(r);
        }
        return rows;
    }


    private String pitajIme() {
        String path = "";
        FileNameExtensionFilter filter = new FileNameExtensionFilter("svg & txt", new String[]{"svg","txt"});
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filter);
        if (fileChooser.showSaveDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            path = file.getAbsolutePath() ;
        }
        System.out.println(path);
        return path;
    }


}
