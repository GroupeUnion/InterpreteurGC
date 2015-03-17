/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreteurgraphic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Asus
 */
public class FunctionFigure extends JPanel {
    FlowLayout experimentLayout;
    Dimension sizeTitle;
    
    public FunctionFigure(String typeObject) {
        super();
        //this.setLayout(null);
        //this.setSize(100, 100);        
        this.setName(typeObject);
        this.experimentLayout = new FlowLayout(FlowLayout.LEFT);
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setOpaque(false);     
        
        this.experimentLayout = new FlowLayout(FlowLayout.LEFT);
        FontMetrics fontMetrics = this.getFontMetrics(this.getFont());
        sizeTitle = new Dimension(fontMetrics.stringWidth(typeObject)+40, 30);
        this.setMargin(sizeTitle.height/2, sizeTitle.width/2,sizeTitle.height/2,sizeTitle.width/2);
        //this.setPreferredSize(sizeTitle);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.     
        g.setColor(Color.getHSBColor((float) (136 / 256.0), (float) (240 / 256.0), (float) (256 / 256.0)));
        int[] x = new int[]{0,20,this.getWidth()-20,this.getWidth()-1, this.getWidth()-1, this.getWidth()-20, 20, 0};
        int[] y = new int[]{20,0,0,20,this.getHeight()-20,  this.getHeight()-1, this.getHeight()-1, this.getHeight()-20};
        g.drawPolygon (x, y, x.length);
        //g.fillOval(-10, -10, this.getWidth()+20, this.getHeight()+20);
        g.setColor(Color.getHSBColor((float) (136 / 256.0), (float) (240 / 256.0), (float) (256 / 256.0)));
        g.setColor(Color.WHITE);
        FontMetrics fontMetrics = this.getFontMetrics(this.getFont());
        g.drawString(this.getName(), this.getWidth()/2 - ((fontMetrics.stringWidth(getName()))/2), 15);
        
    }    
    
    public void setMargin(int top, int left, int bottom, int right) {
        Border margin = BorderFactory.createEmptyBorder(top, left, bottom, right);
        this.setBorder(margin);
    }
    
    @Override
    public Rectangle getBounds() {
        Rectangle coords = SwingUtilities.convertRectangle(this, new Rectangle(40, 5, 0, 0), Fenetre.flechePanel);
        coords.setSize(this.getWidth() - 40, this.getHeight() - 5);
        return coords;
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
}
