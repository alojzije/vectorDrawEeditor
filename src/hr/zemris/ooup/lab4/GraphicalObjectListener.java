package hr.zemris.ooup.lab4;

import hr.zemris.ooup.lab4.model.GraphicalObject;

/**
 * Created by alojzije on 7.6.2014..
 */
public interface GraphicalObjectListener {
    // Poziva se kad se nad objektom promijeni bilo sto
    void graphicalObjectChanged(GraphicalObject go);

    // Poziva se iskljucivo ako je nad objektom promjenjen status selektiranosti objekta (ne hot-pointova)
    void graphicalObjectSelectionChanged(GraphicalObject go);
}
