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
        this.setOpaque(false);
        setMargin(0, 0, 0, 0);
    }

    private void initComponents() {
        experimentLayout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(experimentLayout); // use no layout manager
        experimentLayout.setAlignment(FlowLayout.LEFT);
        Border margin = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        Border textBorder = BorderFactory.createTitledBorder(this.getName());
        Border combinaisonBorder = BorderFactory.createCompoundBorder(margin, textBorder);
        this.setBorder(combinaisonBorder);
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
        Border margin2 = BorderFactory.createEmptyBorder(0, 40, 0, 0);
        Border textBorder = BorderFactory.createTitledBorder(null, this.getName(), 1, TitledBorder.DEFAULT_POSITION, font, Color.getHSBColor((float) (136 / 256.0), (float) (240 / 256.0), (float) (256 / 256.0)));
        Border combinaisonBorder = BorderFactory.createCompoundBorder(textBorder, margin);
        Border combinaisonBorder2 = BorderFactory.createCompoundBorder(margin2, combinaisonBorder);
        this.setBorder(combinaisonBorder2);
    }

    //explication
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag); //To change body of generated methods, choose Tools | Templates.        
        if (!aFlag) {
            Fleche.flechePanel.removeFlecheTo(this);
            Fleche.flechePanel.removeFlecheFrom(this);
            Fleche.flechePanel.repaint();
        }
    }

    @Override
    public Rectangle getBounds() {
//        Rectangle rect = super.getBounds();
//        Insets margin = this.getBorder().getBorderInsets(this);
//        rect.x += 40;
//        rect.y = rect.y - margin.top + 5;
//        rect.width -= 40;
//        rect.height -= margin.top - margin.bottom - 5;
//        rect.setRect(rect.x, rect.y + margin.top,
//                rect.width, rect.height);
//        return rect; //To change body of generated methods, choose Tools | Templates.
         Rectangle coords = SwingUtilities.convertRectangle(this, new Rectangle(40, 5, 0, 0), Fleche.flechePanel);         
        coords.setSize(this.getWidth()-40, this.getHeight()-5);
        return coords;
    }
}
