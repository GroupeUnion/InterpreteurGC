package interpreteurgraphic;




import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

public class TestF extends JLabel  {

    public TestF(Color col) {
        this.col = col;
    }
    
    Path2D.Double arrow = createArrow();
    double theta = 0;
    Color col;

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(fg); //To change body of generated methods, choose Tools | Templates.
        setCol(fg);
    }
    
    

    /*public void stateChanged(ChangeEvent e) {
        int value = ((JSlider) e.getSource()).getValue();
        theta = Math.toRadians(value);
        repaint();
    }*/
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        AffineTransform at = AffineTransform.getTranslateInstance(cx - 8, cy);
        at.rotate(theta);
        at.scale(2.0, 2.0);
        Shape shape = at.createTransformedShape(arrow);
        g2.setPaint(col);
        g2.draw(shape);
    }

    private Path2D.Double createArrow() {
        int length = 10;
        int barb = 3;
        double angle = Math.toRadians(20);
        Path2D.Double path = new Path2D.Double();
        path.moveTo(-length / 2, 0);
        path.lineTo(length / 2, 0);
        double x = length / 2 - barb * Math.cos(angle);
        double y = barb * Math.sin(angle);
        path.lineTo(x, y);
        x = length / 2 - barb * Math.cos(-angle);
        y = barb * Math.sin(-angle);
        path.moveTo(length / 2, 0);
        path.lineTo(x, y);
        return path;
    }

    public void setMargin(int top, int left, int bottom, int right)
    {
        Border margin = BorderFactory.createEmptyBorder(top, left, bottom, right);
       // Border textBorder = BorderFactory.createTitledBorder("int a");
        //Border combinaisonBorder = BorderFactory.createCompoundBorder(margin,textBorder );
        this.setBorder(margin);
    }
   
   /* private JSlider getSlider() {
        JSlider slider = new JSlider(-180, 180, 0);
        slider.addChangeListener(this);
        return slider;
    }*/

//    public static void main(String[] args) {
//        
//        TestF test = new TestF(Color.BLACK);
//        TestF test1 = new TestF(Color.YELLOW);
//        TestF test2 = new TestF(Color.GREEN);
//        test.setMargin(0, 0, 0, 0);
//        test.setPreferredSize(new Dimension(30, 15));
//        test1.setPreferredSize(new Dimension(30, 15));
//        test.setVisible(true);
//        
//        //test.setSize(100, 200);
//        test.setBackground(Color.YELLOW);
//        test.setBackground(Color.black);
//        //test1.setMargin(25, 25, 100, 100);
//        
//        JFrame f = new JFrame();
//        //f.setLayout(null);
//        
//        //test.setLocation(0, 0);
//        /*JPanel mainPanel =  new JPanel();
//        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        mainPanel.setOpaque(true);
//        mainPanel.setLocation(300, 0);*/
//        //mainPanel.setLayout(null);
//       /* mainPanel.setSize(f.getWidth(), f.getHeight());
//        mainPanel.add(test);*/
//        
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        
//        
//        
//       
//        //f.add(test.getSlider(), "Last");
//        f.setSize(400, 400);
//        f.setLocationRelativeTo(null);
//        
//        
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//        mainPanel.setOpaque(true);
//        f.setLayout(null);
//        mainPanel.setLocation(40, 0);
////        mainPanel.setSize(f.getWidth() - mainPanel.getX(), f.getHeight());
//        mainPanel.setSize(40, f.getHeight());
//
//        
//        
//        mainPanel.setBackground(Color.red);
//        
//        test.setLocation(30, 0);
//        test.setLocation(30, 0);
//       
//        mainPanel.add(test);
//        mainPanel.add(test1);
//        /*mainPanel.add(test);
//        mainPanel.add(test1);
//        mainPanel.add(test1);*/
//        
//        f.add(mainPanel);
//        System.out.println("test.getWidth() = "+test.getWidth());
//        System.out.println("test.getHeight() = "+test.getHeight());
//        f.setVisible(true);
//    }
}
