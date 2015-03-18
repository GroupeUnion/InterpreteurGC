package xmlstructure;

import interpreteurgraphic.Composant;
import interpreteurgraphic.Label;
import interpreteurgraphic.WrapLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;

public class Structure extends Variable {

    private Composant composant;
    private List<Variable> listMembres;

    public Structure(String ident, String type, String nom, String ligne, String fichier) {
        super(ident, type, nom, "", ligne, fichier);
        listMembres = new ArrayList<Variable>();
    }

    public Variable[] getValeurs() {
        Variable[] valeur = new Variable[listMembres.size()];
        listMembres.toArray(valeur);
        return valeur;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public Variable copie() {
        Structure copy = new Structure(ident, type, nom, ligne, fichier);
        for (Variable variable : listMembres) {
            copy.listMembres.add(variable.copie());
        }
        return copy;
    }

    public void addMembre(Variable membre) {
        listMembres.add(membre);
    }

    public void clearMembre() {
        listMembres.clear();
    }

    @Override
    public Variable getVariable(String name, String parent) {
        for (Variable variable : listMembres) {
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
    public String toString() {
        String e = "{ " + listMembres.get(0).toString();
        for (int i = 1; i < listMembres.size(); i++) {
            Variable variable = listMembres.get(i);
            e += " , " + variable.toString();
        }
        return super.toString() + e + " }"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component produireComposant() {
        if (composant == null) {
            composant = new Composant(getType() + " " + getNom());            
            composant.setLayout(new WrapLayout(FlowLayout.LEFT));
            for (int i = 0; i < listMembres.size(); i++) {
                Variable variable = listMembres.get(i);
                Component component = variable.produireComposant();
                if (component instanceof Label) {
                    ((Label) component).setHaveMargin(false);
                    ((Label) component).setMargin(0, 0, 0, 0);
                } else {
                    ((Composant) component).setHaveMargin(false);
                    ((Composant) component).setMargin(0, 0, 0, 0);
                }
                composant.add(component);
            }
            composant.setBordBold(true);
        }
        return composant;
    }
}
