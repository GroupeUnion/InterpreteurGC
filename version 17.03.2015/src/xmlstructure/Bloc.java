package xmlstructure;

import interpreteurgraphic.Composant;
import interpreteurgraphic.FunctionFigure;
import interpreteurgraphic.Label;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Bloc extends Instruction {
    /*je fais un classe bloc qui a comme membres un variable return "type et la valeur de retour d'une fonction " et une liste d'instructions qui representent l'esemble 
     des instructions qui sont r�alis�es par la fonction.

     bloc (int id , variable a, liste<instruction> , string ligne, string nom )
     il doit avoir get next et get ped et un id pour savoir ou on ai c tt  */

    private String nom;
    private int index;
    private String type, typeid;
    private Variable retour;
    private List<Instruction> list;
    private List<Variable> param;
    private Container composant;

    public Bloc(String id, Variable retour, List<Variable> param, List<Instruction> list,
            String type, String typeid, String ligne, String fichier, String nom) {
        // TODO Auto-generated constructor stub
        super(id, ligne, fichier);
        this.index = -1;
        this.retour = retour;
        this.type = type;
        this.list = list;
        this.param = param;
        this.typeid = typeid;
        this.nom = nom;
        switch (this.typeid) {
            case "function":
                if (param.isEmpty()) {
                    this.nom += "()";
                } else {
                    this.nom += "(" + param.get(0).getType() + " " + param.get(0).getNom();
                    for (int i = 1; i < param.size(); i++) {
                        Variable variable = param.get(i);
                        this.nom += ", " + variable.getType() + " " + variable.getNom();
                    }
                    this.nom += ")";
                }
                break;
            default:
                this.type = "stacklist";
                this.nom = this.typeid;
                break;
        }
    }

    @Override
    public String toString() {
        return type + " " + nom; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component produireComposant() {
        if (composant == null) {            
            Composant composantGlobale = new Composant("");            
            composantGlobale.setMargin(0, 0, 0, 0);
            Container mainBloc = produireComposant(composantGlobale);
            mainBloc.setVisible(true);
            //JScrollPane pane = new JScrollPane(composantGlobale);
            composant = composantGlobale;            
            //composant = pane;
            //return pane;
        }
        return composant;
    }

    private Container produireComposant(Composant composantGlobale) {
        if (composant == null) {
            Bloc bloc;
            Instruction instruction;
            composant = new FunctionFigure(type + " " + nom);
            switch (typeid) {
                case "function": {
                    Composant composant_param;                    
                    composantGlobale.add(composant);
                    if (!param.isEmpty()) {
                        /*figure @param*/
                        composant_param = new Composant("@param");                        
                        composant.add(composant_param);
                        for (int i = 0; i < param.size(); i++) {
                            instruction = param.get(i);
                            Label label = (Label) instruction.produireComposant();
                            composant_param.add(label);
                            label.setForeground(Color.GRAY);
                            label.setHaveMargin(false);
                            label.setMargin(0, 0, 0, 0);
                            label.setVisible(false);
                        }
                    }
                }
                default:
                    FunctionFigure composant_bloc;
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            instruction = list.get(i);
                            if (!(instruction instanceof Affectation)) {
                                if (instruction instanceof Bloc) {
                                    bloc = (Bloc) instruction;
                                    composant_bloc = (FunctionFigure)bloc.produireComposant(composantGlobale);
                                    if (!"function".equals(bloc.typeid)) {
                                        composant.add(composant_bloc);
                                    } else {
                                        composant_bloc.addReferer(composant);
                                    }
                                    composant_bloc.setVisible(false);
                                } else {
                                    composant.add(instruction.produireComposant());
                                    instruction.produireComposant().setVisible(false);
                                }
                            }
                        }
                    }
                    if (retour != null) {
                        composant.add(retour.produireComposant());
                        retour.produireComposant().setVisible(false);
                    }                   
                    break;
            }
        }
        return composant;
    }
}
