package interpreteurgraphic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author mahamat
 */
public class Label extends JLabel {

    StringBuilder valeur;
    private boolean haveMargin;

    public Label(StringBuilder valeur, String text) {
        super(valeur.toString());
        this.valeur = valeur;
        haveMargin = true;
        setName(text != null ? text : "");
        this.setForeground(Color.WHITE);
    }

    @Override
    public void setName(String name) {
        super.setName(name); //To change body of generated methods, choose Tools | Templates.
        setMargin(0, 40, 0, 0);
    }

    public void addReferer(Component toReferer) {
        Fleche.flechePanel.addFleche(toReferer, this);
        Fleche.flechePanel.repaint();
    }

    public void setToReferer(Component toReferer) {
        Fleche.flechePanel.removeFlecheTo(this);
        Fleche.flechePanel.addFleche(toReferer, this);
        Fleche.flechePanel.repaint();
    }

    public void setMargin(int top, int left, int bottom, int right) {
        Font font = new Font("times new roman", Font.PLAIN, 12);
        if (!"".equals(getName())) {
            FontMetrics fontMetrics = this.getFontMetrics(font);
            left = fontMetrics.stringWidth(getName()) + 10;
        }
        Border margin = BorderFactory.createEmptyBorder(top, left, bottom, right);

        Border textBorder = BorderFactory.createTitledBorder(null, this.getName(), 1, TitledBorder.DEFAULT_POSITION, font, Color.getHSBColor((float) (136 / 256.0), (float) (240 / 256.0), (float) (256 / 256.0)));
        Border combinaisonBorder = BorderFactory.createCompoundBorder(textBorder, margin);
        if (haveMargin) {
            Border margin2 = BorderFactory.createEmptyBorder(0, 40, 0, 0);
            Border combinaisonBorder2 = BorderFactory.createCompoundBorder(margin2, combinaisonBorder);
            this.setBorder(combinaisonBorder2);
        } else {
            this.setBorder(combinaisonBorder);
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag); //To change body of generated methods, choose Tools | Templates.
        if (!aFlag) {
            Fleche.flechePanel.removeFlecheTo(this);
            Fleche.flechePanel.removeFlecheFrom(this);
            Fleche.flechePanel.repaint();
        }
    }

    public void setHaveMargin(boolean haveMargin) {
        this.haveMargin = haveMargin;
    }

    @Override
    public Rectangle getBounds() {
        Rectangle coords = SwingUtilities.convertRectangle(this, new Rectangle(40, 5, 0, 0), Fleche.flechePanel);    
        coords.setSize(this.getWidth()-40, this.getHeight()-10);
        return coords; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        setText(valeur.toString());
    }

}
