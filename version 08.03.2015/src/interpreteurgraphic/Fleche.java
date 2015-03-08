package interpreteurgraphic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Asus
 */
public class Fleche extends JPanel {

    public static Fleche flechePanel = new Fleche();
    Map<Component, List<Component>> listComponent;

    public Fleche() {
        listComponent = new HashMap<Component, List<Component>>();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        this.setSize(this.getParent().getSize());
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.WHITE);
        for (Map.Entry<Component, List<Component>> entry : listComponent.entrySet()) {
            Component fromControl = entry.getKey();
            Rectangle rectangleF = fromControl.getBounds();
            for (Component toControl : entry.getValue()) {
                Rectangle rectangleT = toControl.getBounds();
                Point2D centreF = new Point2D.Double(rectangleF.getCenterX(), rectangleF.getCenterY());
                Point2D centreT = new Point2D.Double(rectangleT.getCenterX(), rectangleT.getCenterY());
                
//                g.setColor(Color.ORANGE);
//                g.fillRect((int) centreF.getX() - 3, (int) centreF.getY() - 3, 6, 6);
//                g.setColor(Color.WHITE);
//                g.fillRect((int) centreT.getX() - 3, (int) centreT.getY() - 3, 6, 6);
//                
//                g.fillRect((int) rectangleF.getX(), (int) rectangleF.getY(), (int)rectangleF.getWidth() , (int)rectangleF.getHeight());
//                g.fillRect((int) rectangleT.getX(), (int) rectangleT.getY(), (int) rectangleT.getWidth(), (int) rectangleT.getHeight());

                boolean coin = Math.abs(rectangleF.x - rectangleT.x) > rectangleF.width
                        && Math.abs(rectangleF.y - rectangleT.y) > rectangleF.height;
                Point2D equaSegment;
                Point2D pPoint1, pPoint2, pPoint3, pPoint4;
                if (rectangleF.x >= rectangleT.x) {
                    if (rectangleF.y >= rectangleT.y) {
                        if (coin) {
                            centreF = new Point2D.Double(rectangleF.x, rectangleF.y);
                            centreT = new Point2D.Double(rectangleT.x + rectangleT.width, rectangleT.y + rectangleT.height);
                        }
                        equaSegment = equationDroite(centreF.getX(), centreF.getY(), centreT.getX(), centreT.getY());
                        pPoint1 = new Point2D.Double(rectangleF.x, rectangleF.x * equaSegment.getX() + equaSegment.getY());
                        pPoint2 = new Point2D.Double((rectangleF.y - equaSegment.getY()) / equaSegment.getX(), rectangleF.y);
                        pPoint3 = new Point2D.Double(rectangleT.x + rectangleT.width, (rectangleT.x + rectangleT.width) * equaSegment.getX() + equaSegment.getY());
                        pPoint4 = new Point2D.Double(((rectangleT.y + rectangleT.height) - equaSegment.getY()) / equaSegment.getX(), (rectangleT.y + rectangleT.height));
                    } else {
                        if (coin) {
                            centreF = new Point2D.Double(rectangleF.x, rectangleF.y + rectangleF.height);
                            centreT = new Point2D.Double(rectangleT.x + rectangleT.width, rectangleT.y);
                        }
                        equaSegment = equationDroite(centreF.getX(), centreF.getY(), centreT.getX(), centreT.getY());
//                        g.drawLine((int) centreF.getX(), (int) centreF.getY(), (int) centreT.getX(), (int) centreT.getY());
                        pPoint1 = new Point2D.Double(rectangleF.x, rectangleF.x * equaSegment.getX() + equaSegment.getY());
                        pPoint2 = new Point2D.Double(((rectangleF.y + rectangleF.height) - equaSegment.getY()) / equaSegment.getX(), (rectangleF.y + rectangleF.height));
                        pPoint3 = new Point2D.Double(rectangleT.x + rectangleT.width, (rectangleT.x + rectangleT.width) * equaSegment.getX() + equaSegment.getY());
                        pPoint4 = new Point2D.Double((rectangleT.y - equaSegment.getY()) / equaSegment.getX(), rectangleT.y);
                    }
                } else {
                    if (rectangleF.y >= rectangleT.y) {
                        if (coin) {
                            centreT = new Point2D.Double(rectangleT.x, rectangleT.y + rectangleT.height);
                            centreF = new Point2D.Double(rectangleF.x + rectangleF.width, rectangleF.y);
                        }
                        equaSegment = equationDroite(centreF.getX(), centreF.getY(), centreT.getX(), centreT.getY());
//                        g.drawLine((int) centreF.getX(), (int) centreF.getY(), (int) centreT.getX(), (int) centreT.getY());
                        pPoint1 = new Point2D.Double(rectangleF.x + rectangleF.width, (rectangleF.x + rectangleF.width) * equaSegment.getX() + equaSegment.getY());
                        pPoint2 = new Point2D.Double((rectangleF.y - equaSegment.getY()) / equaSegment.getX(), rectangleF.y);
                        pPoint3 = new Point2D.Double(rectangleT.x, rectangleT.x * equaSegment.getX() + equaSegment.getY());
                        pPoint4 = new Point2D.Double(((rectangleT.y + rectangleT.height) - equaSegment.getY()) / equaSegment.getX(), (rectangleT.y + rectangleT.height));
                    } else {
                        if (coin) {
                            centreT = new Point2D.Double(rectangleT.x, rectangleT.y);
                            centreF = new Point2D.Double(rectangleF.x + rectangleF.width, rectangleF.y + rectangleF.height);
                        }
                        equaSegment = equationDroite(centreF.getX(), centreF.getY(), centreT.getX(), centreT.getY());
                        pPoint1 = new Point2D.Double(rectangleF.x + rectangleF.width, (rectangleF.x + rectangleF.width) * equaSegment.getX() + equaSegment.getY());
//                        g.drawLine((int) centreF.getX(), (int) centreF.getY(), (int) centreT.getX(), (int) centreT.getY());
                        pPoint2 = new Point2D.Double(((rectangleF.y + rectangleF.height) - equaSegment.getY()) / equaSegment.getX(), (rectangleF.y + rectangleF.height));
                        pPoint3 = new Point2D.Double(rectangleT.x, rectangleT.x * equaSegment.getX() + equaSegment.getY());
                        pPoint4 = new Point2D.Double((rectangleT.y - equaSegment.getY()) / equaSegment.getX(), rectangleT.y);
                    }
                }
                rectangleF.setRect(rectangleF.x - 1, rectangleF.y - 1, rectangleF.width + 2, rectangleF.height + 2);
                rectangleT.setRect(rectangleT.x - 1, rectangleT.y - 1, rectangleT.width + 2, rectangleT.height + 2);
               
//                g.setColor(Color.BLUE);
//                g.fillRect((int) pPoint1.getX() - 3, (int) pPoint1.getY() - 3, 6, 6);
//                g.setColor(Color.RED);
//                g.fillRect((int) pPoint2.getX() - 3, (int) pPoint2.getY() - 3, 6, 6);
//                g.setColor(Color.YELLOW);
//                g.fillRect((int) pPoint3.getX() - 3, (int) pPoint3.getY() - 3, 6, 6);
//                g.setColor(Color.GREEN);
//                g.fillRect((int) pPoint4.getX() - 3, (int) pPoint4.getY() - 3, 6, 6);
//                g.setColor(Color.WHITE);

                if (!rectangleF.contains(pPoint1)) {
                    pPoint1 = pPoint2;
                }
                if (!rectangleT.contains(pPoint3)) {
                    pPoint3 = pPoint4;
                }
                double decalage = ((int) (pPoint1.getX() - pPoint3.getX()) / Math.abs(pPoint1.getX() - pPoint3.getX())) * 0.3 * -1;
                double angle = decalage + calculateAngle((int) pPoint1.getX(), (int) pPoint1.getY(), (int) pPoint3.getX(), (int) pPoint3.getY());
                Path2D path = new Path2D.Double();
                path.moveTo((int) pPoint1.getX(), (int) pPoint1.getY());
                path.quadTo(((int) pPoint1.getX() + (int) pPoint3.getX()) / 2, ((int) pPoint1.getY() + (int) pPoint3.getY()) / 2 - 10, (int) pPoint3.getX(), (int) pPoint3.getY());
                g2.draw(path);
//                g.drawLine((int) pPoint1.getX(), (int) pPoint1.getY(), (int) pPoint3.getX(), (int) pPoint3.getY());
                drawArrowHeadAtLocation(g2, (int) pPoint3.getX(), (int) pPoint3.getY(), angle);
            }
        }
    }

    public void addFleche(Component fromControl, Component toControl) {
        List<Component> listC;
        if ((listC = listComponent.get(fromControl)) == null) {
            listC = new ArrayList<Component>();
            listComponent.put(fromControl, listC);
        }
        listC.add(toControl);
    }

    public void removeFlecheTo(Component toControl) {
        for (Iterator<Map.Entry<Component, List<Component>>> it = listComponent.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Component, List<Component>> entry = it.next();
            List<Component> list = entry.getValue();
            list.remove(toControl);
            if (list.isEmpty()) {
                it.remove();
            }
        }
    }

    public void removeFlecheFrom(Component fromControl) {
        listComponent.remove(fromControl);
    }

    private double calculateAngle(double x1, double y1, double x2, double y2) {
        double a = (x2 - x1);
        double b = (y2 - y1);
        double length = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        if (length == 0) {
            return 0;
        }
        double p = length; //Math.sqrt(a*a+b*b);
        double theta = Math.asin(Math.abs(b) / p);
        if (b < 0) {
            theta = -theta;
        }
        if (a < 0) {
            theta = Math.PI - theta;
        }
        // Rescale into [0, 2*PI[.
        theta %= 2 * Math.PI;
        if (theta < 0) {
            theta += 2 * Math.PI;
        }
        return theta;
    }

    private void drawArrowHeadAtLocation(Graphics2D graphics, double x, double y, double angle) {
        graphics.translate(x, y);
        graphics.rotate(angle);
        drawArrowHead(graphics);
        graphics.rotate(-angle);
        graphics.translate(-x, -y);
    }

    private void drawArrowHead(Graphics2D graphics) {

        int xPoints[] = {0, -10, -10};
        int yPoints[] = {0, 5, -5};
        graphics.fillPolygon(xPoints, yPoints, 3);
        //graphics.drawLine(0, 0, -10, 5);
        //graphics.drawLine(0, 0, -10, -5);
        //graphics.drawLine(-10, 5, -10, -5);
    }

    private Point2D equationDroite(double xz1, double yz1, double xz2, double yz2) {

        if (xz1 == xz2) {
            return new Point2D.Double(xz1, 0);
        }
        if (yz1 == yz2) {
            return new Point2D.Double(0, yz2);
        }
        double denominateur = (xz2 - xz1);
        double nominateur = (yz2 - yz1);
        double a = nominateur / denominateur;
        double y = a * xz1;
        double b = yz1 - y;
        return new Point2D.Double(a, b);

    }

    private Point2D getIntersection(Point2D equation1, Point2D equation2) {
        if (equation2.getX() != equation1.getX()) {
            double x1 = (equation1.getY() - equation2.getY()) / (equation2.getX() - equation1.getX());
            double y1 = x1 * equation1.getX() + equation1.getY();
            return new Point2D.Double(x1, y1);
        }
        return null;
    }

}
