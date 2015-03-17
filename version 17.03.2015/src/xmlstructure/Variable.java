package xmlstructure;

import interpreteurgraphic.Label;
import java.awt.Component;

public class Variable extends Instruction {

    protected String type, nom;
    private StringBuilder valeur;
    private Label label;

    public Variable() {
        super(null, null, null);
    }

    public Variable(String ident, String type, String nom, String valeur,
            String ligne, String fichier) {
        super(ident, ligne, fichier);
        this.nom = nom;
        this.type = type;
        this.valeur = new StringBuilder(valeur != null ? valeur : "");
    }

    public String getType() {
        return this.type;
    }

    public StringBuilder getValeur() {
        return this.valeur;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomComplet(String parent) {
        if (this.nom == null || "".equals(this.nom)) {
            if (parent == null) {
                return "";
            }
            return parent;
        }
        return (parent != null) ? this.nom.contains("[") ? parent + this.nom
                : parent + "." + this.nom
                : this.nom;
    }

    public void setValeur(String valeur) {
        this.valeur.delete(0, this.valeur.length());
        this.valeur.append(valeur);
    }

    public void setValeur(StringBuilder valeur) {
        this.valeur = valeur;
    }

    public Variable copie() {
        return new Variable(this.ident, this.type, this.nom, this.valeur.toString(), this.ligne, this.fichier);
    }

    @Override
    public String toString() {
        return getType() + " " + nom + " " + this.valeur.toString();
    }

    @Override
    public Component produireComposant() {
        if (label == null) {
            this.label = new Label(valeur, type + " " + nom);
        }
        return label;
    }

    public Variable getVariable(String name) {
        if (this.nom.equals(name)) {
            return this;
        }
        return getVariable(name, this.getNomComplet(null));
    }

    public Variable getVariable(String name, String parent) {
        return null;
    }
}
