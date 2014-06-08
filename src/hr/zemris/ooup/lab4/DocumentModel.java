package hr.zemris.ooup.lab4;


import hr.zemris.ooup.lab4.model.GraphicalObject;
import hr.zemris.ooup.lab4.util.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alojzije on 8.6.2014..
 */
public class DocumentModel {
    public final static double SELECION_PROXIMITY = 10;

    // kolekcija svih grafickih objekata
    private List objects = new ArrayList<GraphicalObject>();
    // Read-only proxy oko kolekcije grafickih objekata
    private List roObjects = Collections.unmodifiableList(objects);
    // kolekcija prijavljenih promatraca:
    private List listeners = new ArrayList<DocumentModelListener>();
    // kolekcija selektiranih objekata:
    private List selectedObjects = new ArrayList<GraphicalObject>();
    // Read-only proxy oko kolekcije selektianih objekata
    private List roSelectedObjects = Collections.unmodifiableList(selectedObjects);

    //promatrac koji ce biti registriranih nad svim objektima crteza
    private final GraphicalObjectListener goListener = new GraphicalObjectListener() {
        @Override
        public void graphicalObjectChanged(GraphicalObject go) {
            for(Object l: listeners){
                ((DocumentModelListener)l).documentChange();
            }
        }

        @Override
        public void graphicalObjectSelectionChanged(GraphicalObject go) {
            for(Object l: listeners){
                ((DocumentModelListener)l).documentChange();
            }
        }
    };

    // konstruktor
    public DocumentModel(){}

    // brisanje svih objekta iz modela (pripaziti da se sve potrebno odregistrira)
    // i potom obavijeste svi promatraci modela
    public void clear() {
        for (int i = objects.size(); i <= 0; i--) {
            removeGraphicalObject((GraphicalObject)objects.get(i));
        }
        notifyListeners();
    }

    // Dodavanje objekta u dokument (pazite je li vec selektiran; registrirajte model kao promatraca)
    public void addGraphicalObject(GraphicalObject obj) {
        objects.add(obj);
        if (obj.isSelected()) selectedObjects.add(obj);
        obj.addGraphicalObjectListener(goListener);
    }
    
    // Uklanjanje objekta iz dokumenta (pazite je li vec selektiran; odregistrirajte model kao promatraca)
    public void removeGraphicalObject(GraphicalObject obj) {
        if (objects.contains(obj)) {
            objects.remove(obj);
            if (obj.isSelected()) selectedObjects.remove(obj);
            obj.removeGraphicalObjectListener(goListener);
        }
    }

    // Vrati nepromjenjivu listu postojecih objekata (izmjene smiju ici samo kroz metode modela)
    public List list() {
        return roObjects;
    }

    // Prijava...
    public void addDocumentModelListener(DocumentModelListener l) {
        listeners.add(l);
    }

    // Odjava...
    public void removeDocumentModelListener(DocumentModelListener l) {
        if( listeners.contains(l))
            listeners.remove(l);
    }

    // Obavjestavanje...
    public void notifyListeners() {
        for (int i= 0; i<listeners.size(); i++) {
           DocumentModelListener l = (DocumentModelListener)listeners.get(i);
           l.documentChange();
        }
    }

    // Vrati nepromjenjivu listu selektiranih objekata
    public List getSelectedObjects() {
        return roSelectedObjects;
    }

    // Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
    // Time ce se on iscrtati kasnije (pa ce time mozda veci dio biti vidljiv)
    public void increaseZ(GraphicalObject go) {
        if (objects.contains(go)) {
            int index = objects.indexOf(go);
            objects.remove(go);
            objects.add(index+1, go);
        }
    }

    // Pomakni predani objekt u listi objekata na jedno mjesto ranije...
    public void decreaseZ(GraphicalObject go) {
        if (objects.contains(go)) {
            int index = objects.indexOf(go);
            objects.remove(go);
            objects.add(index-1, go);
        }
    }

    // Pronadji postoji li u modelu neki objekt koji klik na tocku koja je
    // predana kao argument selektira i vrati ga ili vrati null. Tocka selektira
    // objekt kojemu je najbliza uz uvjet da ta udaljenost nije veca od
    // SELECTION_PROXIMITY. Status selektiranosti objekta ova metoda NE dira.
    public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
        GraphicalObject closest = null;
        double minDist = SELECION_PROXIMITY;
        double currDist;
        for (Object obj : objects) {
            currDist = ((GraphicalObject) obj).selectionDistance(mousePoint);
            if (currDist <= minDist) {
                minDist = currDist;
                closest = (GraphicalObject) obj;
            }
        }
        return closest;
    }


    // Pronadji da li u predanom objektu predana tocka misa selektira neki hot-point.
    // Tocka misa selektira onaj hot-point objekta kojemu je najbliza uz uvjet da ta
    // udaljenost nije veca od SELECTION_PROXIMITY. Vraca se indeks hot-pointa
    // kojeg bi predana tocka selektirala ili -1 ako takve nema. Status selekcije
    // se pri tome NE dira.
    public int findSelectedHotPoint(GraphicalObject object, Point mousePoint) {
        int hpIndex = -1;
        double minDist = SELECION_PROXIMITY;
        double currDist;
        for (int i = 0; i < object.getNumberOfHotPoints(); i++) {
            currDist = object.getHotPointDistance(i, mousePoint);
            if (currDist <= minDist) {
                minDist = currDist;
                hpIndex = i;
            }
        }
        return hpIndex;
    }


}
