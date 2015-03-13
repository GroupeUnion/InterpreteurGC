package xmlstructure;

import interpreteurgraphic.Composant;
import interpreteurgraphic.Label;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

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
    private Composant composant;

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
            composant = new Composant("");
            composant.setMargin(0, 0, 0, 0);
            Composant cadreBloc = new Composant(type + " " + nom);
            composant.add(cadreBloc);
            if (param != null && !param.isEmpty()) {
                Composant componentParam = new Composant("@param");
                cadreBloc.add(componentParam);
                for (int i = 0; i < param.size(); i++) {
                    Instruction instruction = param.get(i);
                    Label label = (Label) instruction.produireComposant();
                    componentParam.add(label);
                    label.setForeground(Color.GRAY);
                    label.setHaveMargin(false);
                    label.setMargin(0, 0, 0, 0);
                    label.setVisible(false);
                }
            }
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Instruction instruction = list.get(i);
                    if (!(instruction instanceof Affectation)) {
                        Component component = instruction.produireComposant();
                        if (instruction instanceof Bloc) {                            
                            ((Composant) component).addReferer(cadreBloc);
                            composant.add(component);
                        } else {
                            cadreBloc.add(component);
                        }
                        component.setVisible(false);
                    }
                }
            }
            if (retour != null) {
                cadreBloc.add(retour.produireComposant());
                retour.produireComposant().setVisible(false);
            }
        }
        return composant;
    }

}
