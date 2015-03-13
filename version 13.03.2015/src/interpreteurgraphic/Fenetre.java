package interpreteurgraphic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import xmlstructure.Instruction;
import xmlstructure.Affectation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import xmlstructure.Bloc;
import xmlstructure.Variable;

/**
 *
 * @author Asus
 */
public class Fenetre extends JFrame implements ComponentListener, ActionListener {

    public static Fleche flechePanel = new Fleche();
    private SourcePanel sourcePanel;
    private JPanel mainPanel;
    private JButton buttonNext, buttonPrev;
    private int flecheNumber;
    private Map<Integer, Instruction> hashDeclaration;

    public Fenetre(String title, int largeur, int hauteur, Map<Integer, Instruction> hashDeclaration) {
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(largeur, hauteur);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.flecheNumber = 1;
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.mainPanel.setOpaque(true);
        this.mainPanel.setLocation(300, 15);
        this.mainPanel.setBackground(Color.getHSBColor((float) (212 / 256.0), (float) (17 / 256.0), (float) (67 / 256.0)));

        this.buttonNext = new JButton("Suivant");
        this.buttonNext.setSize(new Dimension(120, 25));
        this.buttonNext.addActionListener(this);

        Fenetre.flechePanel.setLocation(300, 15);

        this.buttonPrev = new JButton("Rejouer l'instruction");
        this.buttonPrev.setSize(new Dimension(150, 25));
        this.buttonPrev.addActionListener(this);

        this.getContentPane().add(Fenetre.flechePanel);
        this.getContentPane().add(this.mainPanel);
        this.getContentPane().add(this.buttonNext);
        this.getContentPane().add(this.buttonPrev);

        this.getContentPane().setBackground(Color.getHSBColor((float) (212 / 256.0), (float) (17 / 256.0), (float) (67 / 256.0)));
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
            this.mainPanel.setSize(this.getWidth() - this.mainPanel.getX(), this.getHeight());
            Fenetre.flechePanel.setSize(this.getWidth() - this.mainPanel.getX(), this.getHeight());
            this.getContentPane().revalidate();
            this.getContentPane().repaint();

        }
        if (this.sourcePanel != null) {
            this.sourcePanel.setSize(this.mainPanel.getX(), this.getHeight() - 85);
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
                    if (!mainPanel.isAncestorOf(instruction.produireComposant())) {
                        mainPanel.add(instruction.produireComposant());
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
            if ((instruction = this.hashDeclaration.get(id)) != null) {
                if (instruction instanceof Affectation) {
                    ((Affectation) instruction).removeFlecheTemporaire();
                } else if (instruction instanceof Variable
                        && "@return".equals(((Variable) instruction).getNom())) {
                    instruction.produireComposant().getParent().getParent().setVisible(false);
                }
            }
        }
    }
}
