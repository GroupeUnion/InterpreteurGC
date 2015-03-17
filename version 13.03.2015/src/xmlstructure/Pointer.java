package xmlstructure;

import interpreteurgraphic.Composant;
import interpreteurgraphic.Label;
import java.awt.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Asus
 */
public class Pointer extends Variable {

    private Array array;
    private Composant composant;

    public Pointer(Array array) {
        super(String.valueOf(array.getIdent()), array.getType(), array.getNom(), "", String.valueOf(array.getLigne()), array.getFichier());
        this.type = array.type;
        this.nom = array.nom;
        this.array = array;
    }

    /*Initialisation d'un tableau de structure*/
    public void set(Array array) {
        this.array = array;
    }

    public void set(Variable variable) {
        this.array.set(variable);
    }

    @Override
    public Variable getVariable(String name) {
        if (this.nom.equals(name)) {
            return this;
        }
        return array.getVariable(name, this.nom);
    }

    @Override
    public Variable getVariable(String name, String parent) {
        return array.getVariable(name, parent);
    }

    @Override
    public String getType() {
        return type.replaceAll("\\(.*?\\)", "*").trim();
    }

    @Override
    public StringBuilder getValeur() {
        return array.getValeur();
    }

    @Override
    public String getNomComplet(String parent) {
        return array.getNomComplet(parent);
    }

    @Override
    public void setValeur(String valeur) {
        this.array.setValeur(valeur);
    }

    @Override
    public void setValeur(StringBuilder valeur) {
        this.array.setValeur(valeur);
    }

    public void set(Variable[] listVariable) {
        this.array.set(listVariable);
    }

    public Array dereference() {
        return this.array;
    }

    @Override
    public Variable copie() {
        return new Pointer((Array) array.copie());
    }

    @Override
    public String toString() {
        return array.getType() + " " + array.getNom() + " " + this.array.getValeur();
    }

    @Override
    public Component produireComposant() {
        if (composant == null) {
            composant = new Composant(getType() + " " + nom);
        }
            if (array.listCases != null) {
                for (int i = 0; i < array.listCases.length; i++) {
                    Variable variable = array.listCases[i];
                    Component component = variable.produireComposant();
                    if (component instanceof Label) {
                        ((Label) component).setHaveMargin(false);
                    }
                    component.setName("[" + i + "]");

                    composant.add(component);
                }
            }
        return composant;
    }
}
