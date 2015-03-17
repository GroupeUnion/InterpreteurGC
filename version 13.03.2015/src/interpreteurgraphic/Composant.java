package interpreteurgraphic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author mahamat
 */
public class Composant extends JPanel { //le cadre qui contient le composant

    FlowLayout experimentLayout;

    public Composant(String typeObject) {
        super();
        this.setName(typeObject);
        initComponents();
    }

    private void initComponents() {
        this.experimentLayout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(experimentLayout); // use no layout manager
        this.experimentLayout.setAlignment(FlowLayout.LEFT);
        this.setOpaque(false);
        this.setMargin(0, 0, 0, 0);
    }

    public void addReferer(Component toReferer) {
        Fenetre.flechePanel.addFleche(toReferer, this);
        Fenetre.flechePanel.repaint();
    }

    public void setToReferer(Component toReferer) {
        Fenetre.flechePanel.removeFlecheTo(this);
        Fenetre.flechePanel.addFleche(toReferer, this);
        Fenetre.flechePanel.repaint();
    }

    public void removeReferer(Component toReferer) {
        Fenetre.flechePanel.removeFleche(this, toReferer);
    }

    public void setMargin(int top, int left, int bottom, int right) {
        if (!"".equals(getName())) {
            Font font = new Font("times new roman", Font.PLAIN, 12);
            FontMetrics fontMetrics = this.getFontMetrics(font);
            left = fontMetrics.stringWidth(getName()) + 10;           
            Border margin = BorderFactory.createEmptyBorder(top, left, bottom, right);
            Border margin2 = BorderFactory.createEmptyBorder(0, 40, 0, 0);
            Border textBorder = BorderFactory.createTitledBorder(null, this.getName(), 1, TitledBorder.DEFAULT_POSITION, font, Color.getHSBColor((float) (136 / 256.0), (float) (240 / 256.0), (float) (256 / 256.0)));
            Border combinaisonBorder = BorderFactory.createCompoundBorder(textBorder, margin);
            Border combinaisonBorder2 = BorderFactory.createCompoundBorder(margin2, combinaisonBorder);
            this.setBorder(combinaisonBorder2);
        }
    }

    @Override
    public Rectangle getBounds() {
        Rectangle coords = SwingUtilities.convertRectangle(this, new Rectangle(40, 5, 0, 0), Fenetre.flechePanel);
        coords.setSize(this.getWidth() - 40, this.getHeight() - 5);
        return coords;
    }

}
