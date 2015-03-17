package xmlstructure;

import java.util.ArrayList;
import java.util.List;

public class Bloc extends Instruction {
    /*je fais un classe bloc qui a comme membres un variable return "type et la valeur de retour d'une fonction " et une liste d'instructions qui representent l'esemble 
     des instructions qui sont r�alis�es par la fonction.

     bloc (int id , variable a, liste<instruction> , string ligne, string nom )
     il doit avoir get next et get ped et un id pour savoir ou on ai c tt  */

    private int index = 0;
    private String type;
    private Variable retour;
    private List<Instruction> list;

    public Bloc(String id,  Variable retour, List<Instruction> list, 
            String type, String ligne, String fichier, String nom) {
        // TODO Auto-generated constructor stub
        super(id, ligne, fichier);
        this.retour = retour;
        this.list = list;
    }

    public Instruction getNext() {
        if (this.list.get(index) instanceof Bloc &&
                (((Bloc) this.list.get(index)).hasNext())) {
            return ((Bloc) this.list.get(index)).getNext();
        }
        if (!hasNext()) {
            return null;
        }
        return this.list.get(index++);
    }

    public Instruction getPrev() {
        if (this.list.get(index) instanceof Bloc && 
                (((Bloc) this.list.get(index)).hasPrev())) {
            return ((Bloc) this.list.get(index)).getPrev();
        }
        if (!hasPrev()) {
            return null;
        }
        return this.list.get(index--);
    }

    public List<Instruction> getList() {
        return list;
    }

    public boolean hasNext() {
        return index < list.size();
    }

    public boolean hasPrev() {
        return index != 0;
    }
}
