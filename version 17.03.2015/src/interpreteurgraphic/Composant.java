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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    private FlowLayout experimentLayout;
    private boolean haveMargin, bordBold;
    private Color color;
    private Dimension titleSize;
    private Font pFont;

    public Composant(String typeObject) {
        super();
        this.pFont = new Font("times new roman", Font.PLAIN, 12);
        this.setName(typeObject);
        this.haveMargin = true;
        this.experimentLayout = new FlowLayout(FlowLayout.LEFT);
        this.setLayout(experimentLayout); // use no layout manager
        this.setOpaque(false);
        this.color = Color.getHSBColor((float) (136 / 256.0), (float) (240 / 256.0), (float) (256 / 256.0));
        if (!"".equals(typeObject)) {
            FontMetrics fontMetrics = this.getFontMetrics(pFont);
            titleSize = new Dimension(fontMetrics.stringWidth(getName() + 10), 30);
        }
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
            Border colorBorder = BorderFactory.createMatteBorder(1, (this.bordBold) ? 20 : 1, 2, 1, color);
            Border textBorder = BorderFactory.createTitledBorder(colorBorder,
                    this.getName(), TitledBorder.LEFT, TitledBorder.TOP, pFont, color);
            Border marginTxt = BorderFactory.createEmptyBorder(0, (titleSize != null) ? titleSize.width : left, 0, 0);
            Border combinaisonBorder = BorderFactory.createCompoundBorder(textBorder, marginTxt);
            if (haveMargin) {
                Border margin = BorderFactory.createEmptyBorder(0, 10, 0, 0);
                Border combinaison = BorderFactory.createCompoundBorder(margin, combinaisonBorder);
                this.setBorder(combinaison);
            } else {
                this.setBorder(combinaisonBorder);
            }

        }
    }

    @Override
    public Rectangle getBounds() {
        Rectangle coords = SwingUtilities.convertRectangle(this, new Rectangle(40, 5, 0, 0), Fenetre.flechePanel);
        coords.setSize(this.getWidth() - 40, this.getHeight() - 5);
        return coords;
    }

    public void setHaveMargin(boolean haveMargin) {
        this.haveMargin = haveMargin;
    }

    public void setBordBold(boolean bordBold) {
        this.bordBold = bordBold;
        setMargin(0, 0, 0, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if (this.bordBold && this.getComponentCount() > 0) {
            Component[] c = this.getComponents();
            List<Component> sortedByAgePersons = Arrays.asList(this.getComponents());
            Collections.sort(sortedByAgePersons, new Comparator<Component>()  {
                @Override
                public int compare(Component p1, Component p2) {
                    Rectangle rect1 = p1.getBounds(), rect2 = p2.getBounds();
                int somme1 = rect1.width + rect1.height, somme2 = rect2.width + rect2.height;
                if (somme1 == somme2) {
                    return 0;
                } else if (somme1 > somme2) {
                    return 1;
                }
                return -1;
                }
            });

            for (Component sortedByAgePerson : sortedByAgePersons) {
                this.add(sortedByAgePerson);
            }
        }
    }

}
