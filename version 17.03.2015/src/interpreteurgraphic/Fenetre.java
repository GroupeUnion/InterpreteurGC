package interpreteurgraphic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import xmlstructure.Instruction;
import xmlstructure.Affectation;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import xmlstructure.Bloc;
import xmlstructure.Variable;

/**
 *
 * @author Asus
 */
public class Fenetre extends JFrame implements ComponentListener, ActionListener , AdjustmentListener{

    public static Fleche flechePanel = new Fleche();
    private SourcePanel sourcePanel;
    private JButton buttonNext, buttonPrev;
    private int flecheNumber;
    private JPanel mainPanel, declarationPanel;
    private JScrollPane mainScrollPane, declarationScrollPane;
    private Map<Integer, Instruction> hashDeclaration;
    private Color colorbg;

    public Fenetre(String title, int largeur, int hauteur, Map<Integer, Instruction> hashDeclaration) {
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(largeur, hauteur);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.flecheNumber = 1;
        
        this.colorbg = Color.getHSBColor((float) (212 / 256.0), (float) (17 / 256.0), (float) (67 / 256.0));

        this.declarationPanel = new JPanel();
        this.declarationPanel.setLayout(new WrapLayout (FlowLayout.LEFT));
        this.declarationPanel.setOpaque(true);
        this.declarationPanel.setBackground(colorbg);
        
        this.declarationScrollPane = new JScrollPane(this.declarationPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.declarationScrollPane.setLocation(300, 15);
        this.declarationScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.declarationScrollPane.getHorizontalScrollBar().addAdjustmentListener(this);
        this.declarationScrollPane.getVerticalScrollBar().addAdjustmentListener(this);
        
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.mainPanel.setOpaque(true);
        this.mainPanel.setBackground(colorbg);
        
        this.mainScrollPane = new JScrollPane(this.mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.mainScrollPane.setOpaque(false);
        this.mainScrollPane.setLocation(300, 265);
        Border margin = BorderFactory.createMatteBorder(3, 0, 0, 0, Color.getHSBColor((float) (136 / 256.0), (float) (240 / 256.0), (float) (256 / 256.0)));
        Border textBorder = BorderFactory.createTitledBorder(margin, "Execution", TitledBorder.CENTER, TitledBorder.TOP, this.getFont(), Color.WHITE);
        this.mainScrollPane.setBorder(textBorder);
        
        this.mainScrollPane.getHorizontalScrollBar().addAdjustmentListener(this);
        this.mainScrollPane.getVerticalScrollBar().addAdjustmentListener(this);
        
        this.buttonNext = new JButton("Suivant");
        this.buttonNext.setSize(new Dimension(120, 25));
        this.buttonNext.addActionListener(this);

        Fenetre.flechePanel.setLocation(300, 15);

        this.buttonPrev = new JButton("Rejouer l'instruction");
        this.buttonPrev.setSize(new Dimension(150, 25));
        this.buttonPrev.addActionListener(this);

        this.getContentPane().add(Fenetre.flechePanel);
        this.getContentPane().add(this.mainScrollPane);
        this.getContentPane().add(this.declarationScrollPane);
        this.getContentPane().add(this.buttonNext);
        this.getContentPane().add(this.buttonPrev);

        this.getContentPane().setBackground(colorbg);
        this.setVisible(true);
        this.addComponentListener(this);
        this.hashDeclaration = hashDeclaration;

//        for (Map.Entry<Integer, Instruction> entry : this.hashDeclaration.entrySet()) {
//            if (entry.getValue().produireComposant() != null) {
//                mainPanel.add(entry.getValue().produireComposant());
//            }
//        }
    }

    public void chargement(String fichierSource[]) {
        this.sourcePanel = new SourcePanel(fichierSource);
        this.sourcePanel.setLocation(0, 30);
        this.sourcePanel.setSize(this.mainPanel.getX(), this.getHeight());
        this.getContentPane().add(sourcePanel);
        this.sourcePanel.repaint();
        this.revalidate();

        //initialisation position fleche
        this.sourcePanel.activation(getLigne(1), getLigne(0));
        //force la mise a jour des positionnements
        componentResized(null);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (this.mainPanel != null) {
            this.mainScrollPane.setSize(this.getWidth() - this.mainScrollPane.getX()-15, this.getHeight() - 300);
            this.declarationScrollPane.setSize(this.getWidth() - this.declarationScrollPane.getX()-15, 250);
            Fenetre.flechePanel.setSize(this.getWidth() - this.flechePanel.getX(), this.getHeight());
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
        if (this.sourcePanel != null) {
            this.sourcePanel.setSize(this.mainScrollPane.getX()+1, this.getHeight() - 85);
            if (this.buttonPrev != null) {
                this.buttonPrev.setLocation(this.sourcePanel.getX() + 30, (this.sourcePanel.getHeight() + 35));
            }
            if (this.buttonNext != null) {
                this.buttonNext.setLocation(this.sourcePanel.getX() + this.buttonPrev.getWidth() + 30, (this.sourcePanel.getHeight() + 35));
            }
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    private int getLigne(int id) {
        Instruction instruction;
        if ((instruction = this.hashDeclaration.get(id)) != null) {
            return instruction.getLigne() - 1;
        }
        return -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Suivant": {
                if (showInstruction(flecheNumber)) {
                    removeFlecheTemporaire(flecheNumber - 1);
                    this.sourcePanel.activation(getLigne(++flecheNumber), getLigne(flecheNumber - 1));
                }
                componentResized(null);
                break;
            }
            case "Precedent": {
//                if(flecheNumber > hashDeclaration.size())
//                    flecheNumber = hashDeclaration.size() + 1;
//                if (removeLabel(flecheNumber - 1)) {
//                    this.sourcePanel.activation(getLigne(--flecheNumber), getLigne(flecheNumber + 1));
//                }
                break;
            }
        }
    }

    public boolean showInstruction(int id) {
        if (!this.hashDeclaration.isEmpty()) {
            Instruction instruction;
            if ((instruction = this.hashDeclaration.get(id)) != null) {
                if (instruction instanceof Affectation) {
                    ((Affectation) instruction).affect();
                } else {
                    if (!mainPanel.isAncestorOf(instruction.produireComposant())
                            && !declarationPanel.isAncestorOf(instruction.produireComposant())) {
                        if (instruction instanceof Bloc) {
                            mainPanel.add(instruction.produireComposant());
                        } else {
                            declarationPanel.add(instruction.produireComposant());
                        }
                    }
                    instruction.produireComposant().setVisible(true);     
                }
                return true;
            }
        }
        return false;
    }

    public void removeFlecheTemporaire(int id) {
        if (!this.hashDeclaration.isEmpty()) {
            Instruction instruction;
            Component component;
            if ((instruction = this.hashDeclaration.get(id)) != null) {
                if (instruction instanceof Affectation) {
                    ((Affectation) instruction).removeFlecheTemporaire();
                } else if (instruction instanceof Variable
                        && "@return".equals(((Variable) instruction).getNom())) {
                    component = instruction.produireComposant().getParent();
                    while (component.getName().contains("stacklist")) {
                        component = component.getParent();
                    }
                    component.setVisible(false);
                }
            }
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        componentResized(null);
    }
    
   
}
