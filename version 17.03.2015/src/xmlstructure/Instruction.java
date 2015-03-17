package xmlstructure;


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
public class Instruction {

    protected String ident, ligne, fichier;
    protected boolean isActive;

    public Instruction(String ident, String ligne, String fichier) {
        this.ident = ident;
        this.ligne = ligne;
        this.fichier = fichier;
        isActive = true;
    }

    public int getIdent() {
        return (ident==null)? -1 : Integer.parseInt(ident);
    }

    public int getLigne() {
        return (ident==null)? -1 : Integer.parseInt(ligne);
    }

    public String getFichier() {
        return fichier;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public Component produireComposant() {
        return null;
    }

}
