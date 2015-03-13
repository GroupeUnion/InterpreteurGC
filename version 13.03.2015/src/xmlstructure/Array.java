package xmlstructure;

import interpreteurgraphic.Composant;
import interpreteurgraphic.Label;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;

public class Array extends Variable {

    protected Variable listCases[];
    private boolean isPointer;
    private int nbElement;
    private Composant composant;

    public Array(String ident, String type, String nom, boolean pointeur,
            String ligne, String fichier, int count) {
        super(ident, type, nom, "", ligne, fichier);
        this.isPointer = pointeur;
        if (!this.isPointer) {
            this.nbElement = count;
            listCases = new Variable[nbElement];
            String typeCase = type.replaceAll("\\[\\]", "").trim();
            for (int i = 0; i < listCases.length; i++) {
                listCases[i] = new Variable(this.ident, typeCase, "[" + i + "]", "0", this.ligne, this.fichier);
            }
        }
    }

    public Array(String ident, String type, String nom, boolean pointeur,
            String ligne, String fichier, List<Variable> listVariable) {
        super(ident, type, nom, "", ligne, fichier);
        this.isPointer = pointeur;
        this.nbElement = listVariable.size();
        this.listCases = new Variable[listVariable.size()];
        listVariable.toArray(this.listCases);
    }

    public Array(String ident, String type, String nom, boolean pointeur,
            String ligne, String fichier, Structure modele, int count) {
        super(ident, type, nom, "", ligne, fichier);
        this.isPointer = pointeur;
        this.nbElement = count;
        listCases = new Variable[nbElement];
        for (int i = 0; i < listCases.length; i++) {
            listCases[i] = modele.copie();
            listCases[i].setNom("[" + i + "]");
        }
    }

    @Override
    public String getType() {
        return type.replaceAll("\\(.*?\\)", "*").trim(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Variable getVariable(String name, String parent) {
        if (isPointer && listCases == null) {
            return null;
        }
        for (Variable variable : listCases) {
            String newParentName = variable.getNomComplet(parent);
            if (newParentName.equals(name)) {
                return variable;
            } else if ((variable = variable.getVariable(name, newParentName)) != null) {
                return variable;
            }
        }
        return null;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public void setValeur(String valeur) {
        if (listCases != null) {
            listCases[0].setValeur(valeur);
        }
    }

    /*clonage du tableau*/
    @Override
    public Variable copie() {
        if(listCases == null)
            return new Array(ident, type, nom, isPointer, ligne, fichier, 0);
        return new Array(ident, type, nom, isPointer, ligne, fichier,  Arrays.asList(listCases));
    }

    public boolean isPointer() {
        return isPointer;
    }

    public void set(Variable[] listVariable) {
        listCases = listVariable;
    }

    /*pointeur identique*/
    public void set(Variable variable) {
        if (variable instanceof Array) {
            listCases = ((Array) variable).listCases;
        } else if (variable instanceof Structure) {
            if (listCases == null) {
                listCases = new Variable[1];
            }
            Structure indirection = new Structure(null, variable.getType(), (isPointer) ? "" : "[0]", null, null);
            Variable[] tabMembre = ((Structure) variable).getValeurs();
            for (Variable membre : tabMembre) {
                indirection.addMembre(membre);
            }
            listCases[0] = indirection;
        } else {
            if (listCases == null) {
                listCases = new Variable[1];
            }
            listCases[0] = new Variable(null, variable.getType(), (isPointer) ? "" : "[0]", "", null, null);
            listCases[0].setValeur(variable.getValeur());
        }
    }

    @Override
    public String toString() {
        if (isPointer && listCases == null) {
            return super.toString() + " NULL";
        }
        String e = " [ " + listCases[0].toString();
        for (int i = 1; i < listCases.length && i < 15; i++) {
            e += " , " + listCases[i].toString();
        }
        return super.toString() + e + " ]"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component produireComposant() {
        if (composant == null) {
            composant = new Composant(getType() + " " + nom);
            if (listCases != null) {
                for (int i = 0; i < listCases.length; i++) {
                    Variable variable = listCases[i];
                    Component component = variable.produireComposant();
                    if (component instanceof Label) {
                        ((Label) component).setHaveMargin(false);
                    }
                    component.setName("[" + i + "]");
                    composant.add(component);
                }
            }
        }
        return composant;
    }

}
