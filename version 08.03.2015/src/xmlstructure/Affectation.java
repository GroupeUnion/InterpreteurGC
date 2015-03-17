package xmlstructure;

import interpreteurgraphic.Composant;
import interpreteurgraphic.Label;
import java.util.List;
import java.util.Map;

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
                if (source != null) {
                    ((Label) source.produireComposant()).addReferer(destination.produireComposant());
                }
                break;
            case "pointer":
                Pointer pointer = (Pointer) destination;
                if (source == null) {
                    pointer.set(listValues);
                } else if (source instanceof Array) {
                    pointer.set((Array) source);
                    ((Composant) source.produireComposant()).addReferer(pointer.produireComposant());
                } else if (source instanceof Pointer) {
                    pointer.set(((Pointer) source).dereference());
                    ((Composant) source.produireComposant()).addReferer(pointer.produireComposant());
                } else {
                    pointer.set(source);
                    if (source.produireComposant() instanceof Label) {
                        ((Label) source.produireComposant()).addReferer(pointer.produireComposant());
                    }
                    else
                    {
                        ((Composant) source.produireComposant()).addReferer(pointer.produireComposant());
                    }
                }
                break;
            case "array":
                Array array = (Array) destination;
                array.set(listValues);
                break;
        }
    }
}
