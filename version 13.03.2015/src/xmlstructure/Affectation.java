package xmlstructure;

import interpreteurgraphic.Composant;
import interpreteurgraphic.Fenetre;
import interpreteurgraphic.Label;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Asus
 */
public class Affectation extends Instruction {

    Variable source, destination;
    String value, typeid;
    Variable[] listValues;

    public Affectation(String ident, String ligne, String fichier,
            Variable source, Variable destination, String typeid, String value) {
        super(ident, ligne, fichier);
        this.source = source;
        this.destination = destination;
        this.value = value;
        this.typeid = typeid;
    }

    public Affectation(String ident, String ligne, String fichier,
            Variable source, Variable destination, String typeid, List<Variable> listValues) {
        super(ident, ligne, fichier);
        this.source = source;
        this.destination = destination;
        this.value = null;
        this.listValues = new Variable[listValues.size()];
        listValues.toArray(this.listValues);
        this.typeid = typeid;
    }

    public String getValue() {
        return value;
    }

    public void affect() {
        switch (typeid) {
            case "primitif":
                destination.setValeur(value);
                destination.produireComposant().setForeground(Color.red);
                addFleche();
                break;
            case "pointer":
                Pointer pointer = (Pointer) destination;
                if (source == null) {
                    pointer.set(listValues);
                } else if (source instanceof Array) {
                    pointer.set((Array) source);
                    addFleche();
                } else if (source instanceof Pointer) {
                    pointer.set(((Pointer) source).dereference());
                    addFleche();
                } else {
                    pointer.set(source);
                    addFleche();
                }
                break;
            case "array":
                Array array = (Array) destination;
                array.set(listValues);
                break;
        }
    }

    private void addFleche() {
        if (source != null) {
            switch (typeid) {
                case "pointer":
                    Component component;
                    Composant composant = (Composant) destination.produireComposant();
                    if (composant.getComponentCount() != 0) {
                        component = composant.getComponent(0);
                        if (component instanceof Label) {
                            ((Label) component).addReferer(source.produireComposant());
                        } else {
                            ((Composant) component).addReferer(source.produireComposant());
                        }
                    }

                    break;
                case "primitif":
                    ((Label) destination.produireComposant()).setToReferer(source.produireComposant());
                    break;
            }
        }
    }
    
    public void removeFlecheTemporaire()
    {
        if (source != null && "primitif".equals(typeid)) {
            ((Label) destination.produireComposant()).removeReferer(source.produireComposant());
        }
        destination.produireComposant().setForeground(Color.WHITE);
    }
}
