package xmlstructure;

import interpreteurgraphic.Fenetre;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.jdom2.*;
import org.jdom2.input.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadXml {

    Map<Integer, Instruction> hashDeclaration;
    Element racine;

    public ReadXml() {
        hashDeclaration = new HashMap<Integer, Instruction>();
    }

    void chargement_xml(String file) {
        try {
            SAXBuilder sxb = new SAXBuilder();
            Document document = sxb.build(new File(file));
            racine = document.getRootElement();
            extraire_instruction(racine.getChild("declaration"), false);
        } catch (JDOMException ex) {
            Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void chargement_xml(BufferedReader input) {
        try {
            SAXBuilder sxb = new SAXBuilder();
            Document document = sxb.build(input);
            racine = document.getRootElement();
            extraire_instruction(racine.getChild("declaration"), false);
            for (Map.Entry<Integer, Instruction> entry : hashDeclaration.entrySet()) {
                System.out.println((Instruction) entry.getValue());
            }
        } catch (JDOMException ex) {
            System.out.println("Erreur XML :" + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ReadXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Variable VariableGet(String id, String type, String name) {
        Variable variable;
        if (id != null) {
            variable = (Variable) hashDeclaration.get(Integer.parseInt(id));
            return variable.getVariable(name);
        } else {
            for (Map.Entry<Integer, Instruction> entry : hashDeclaration.entrySet()) {
                if (entry.getValue() instanceof Variable) {
                    variable = (Variable) entry.getValue();
                    if (variable.getType().equals(type) && variable.getNom().equals(name)) {
                        return variable;
                    }
                }
            }
        }
        return null;
    }

    private Instruction extraire_instruction(Element e, boolean isRunMode) {
        Bloc bloc;
        Array array = null;
        Structure struct;
        boolean ispointer = false;
        Affectation affectation;
        Instruction instruction;
        List<Variable> listAffectation = null;
        List<Instruction> listeInstruction;
        List<Element> noeudMembre;

        Variable source = null, destination, variable = null;

        String id, line, file, type, name, valeur;
        switch (e.getName()) {
            case "affect": {
                if (e.getAttributeValue("nsrc") != null) {
                    source = VariableGet(e.getAttributeValue("id_src"), "", e.getAttributeValue("nsrc"));
                }
                destination = VariableGet(e.getAttributeValue("id_dest"), "", e.getAttributeValue("ndest"));
                if (e.getAttributeValue("typeid").equals("primitif") || e.getContentSize() == 0) {
                    affectation = new Affectation(e.getAttributeValue("id"), e.getAttributeValue("line"),
                            e.getAttributeValue("file"), source, destination, e.getAttributeValue("typeid"),
                            e.getAttributeValue("value"));
                } else {
                    listAffectation = getListValue(e);
                    affectation = new Affectation(e.getAttributeValue("id"), e.getAttributeValue("line"),
                            e.getAttributeValue("file"), source, destination, e.getAttributeValue("typeid"), listAffectation);
                }
                if (!isRunMode) {
                    //affectation.affect();
                    hashDeclaration.put(affectation.getIdent(), affectation);
                }
                return isRunMode ? affectation : null;
            }
            case "appel":
                listeInstruction = new ArrayList<Instruction>();
                if ((noeudMembre = e.getChildren()) != null) {
                    for (Element membre : noeudMembre) {
                        instruction = (Instruction) extraire_instruction(membre, true);
                        listeInstruction.add(instruction);
                    }
                }
                bloc = new Bloc(e.getAttributeValue("id"), null, listeInstruction, e.getAttributeValue("retour"),
                        e.getAttributeValue("line"), e.getAttributeValue("file"), e.getAttributeValue("name"));
                return bloc;
            case "return":
                line = e.getAttributeValue("line");
                file = e.getAttributeValue("file");
                type = e.getAttributeValue("type");
                valeur = e.getValue();
                System.out.println("retour: " + "line=" + line + " type=" + type + " file= " + file + " return " + valeur);
                break;
            case "boucleFor":
                line = e.getAttributeValue("line");
                file = e.getAttributeValue("file");
                System.out.println("boucleFor: " + " line=" + line + " file= " + file);
                break;
            case "var": {
                switch (e.getAttributeValue("typeid")) {
                    case "primitif":
                        variable = new Variable(e.getAttributeValue("id"), e.getAttributeValue("type"),
                                e.getAttributeValue("name"), e.getAttributeValue("value"),
                                e.getAttributeValue("line"), e.getAttributeValue("file"));

                        break;
                    case "struct":
                        if ((struct = (Structure) VariableGet(null, e.getAttributeValue("type"), "")) == null) {
                            struct = new Structure(e.getAttributeValue("id"), e.getAttributeValue("type"),
                                    e.getAttributeValue("name"), e.getAttributeValue("line"), e.getAttributeValue("file"));
                            noeudMembre = e.getChildren();
                            for (Element membre : noeudMembre) {
                                struct.addMembre((Variable) extraire_instruction(membre, true));
                            }
                        } else {
                            struct = (Structure) struct.copie();
                            struct.setNom(e.getAttributeValue("name"));
                            struct.setIdent(e.getAttributeValue("id"));
                            struct.setLigne(e.getAttributeValue("line"));
                            noeudMembre = e.getChildren();
                            if (!noeudMembre.isEmpty()) {
                                struct.clearMembre();
                            }
                            for (Element membre : noeudMembre) {
                                struct.addMembre((Variable) extraire_instruction(membre, isRunMode));
                            }
                        }
                        variable = struct;
                        break;
                    case "pointer":
                        ispointer = true;
                    case "array":
                        type = e.getAttributeValue("type");
                        if (e.getChild("var") != null) {
                            listAffectation = getListValue(e);
                            array = new Array(e.getAttributeValue("id"), e.getAttributeValue("type"),
                                    e.getAttributeValue("name"), ispointer, e.getAttributeValue("line"),
                                    e.getAttributeValue("file"), listAffectation);
                        } else if (!ispointer && type.contains("struct")) {
                            if (type.contains("struct")) {/*tableau de structure*/
                                /*recherche de la structure à copier*/

                                struct = (Structure) VariableGet(null, type.replaceAll("\\[\\]", "").trim(), "");
                                if (struct != null) {
                                    struct = (Structure) struct.copie();
                                    struct.setNom(e.getAttributeValue("name"));
                                }
                                array = new Array(e.getAttributeValue("id"), e.getAttributeValue("type"),
                                        e.getAttributeValue("name"), ispointer, e.getAttributeValue("line"),
                                        e.getAttributeValue("file"), struct, Integer.parseInt(e.getAttributeValue("count")));
                            }
                        } else {
                            array = new Array(e.getAttributeValue("id"), e.getAttributeValue("type"),
                                    e.getAttributeValue("name"), ispointer, e.getAttributeValue("line"),
                                    e.getAttributeValue("file"), Integer.parseInt(e.getAttributeValue("count")));
                        }
                        variable = (ispointer) ? new Pointer(array) : array;
                        break;
                }
                if (e.getAttributeValue("nsrc") != null) {
                    source = VariableGet(e.getAttributeValue("id_src"), null, e.getAttributeValue("nsrc"));
                    variable.setValeur("");
                    affectation = new Affectation(e.getAttributeValue("id"), e.getAttributeValue("line"),
                            e.getAttributeValue("file"), source, variable, e.getAttributeValue("typeid"), e.getAttributeValue("value"));

                    affectation.affect();

                }
                if (!isRunMode) {
                    hashDeclaration.put(variable.getIdent(), variable);
                }
                return isRunMode ? variable : null;
            }
            case "param":
                type = e.getAttributeValue("type");
                name = e.getAttributeValue("name");
                System.out.println("param: " + "type= " + type + "name= " + name);
                break;
        }
        if (!isRunMode) {
            List<Element> list_element = e.getChildren();
            for (Element element : list_element) {
                extraire_instruction(element, isRunMode);
            }
        }

        return null;
    }

    List<Variable> getListValue(Element e) {
        int i = Integer.parseInt(e.getAttributeValue("count"));
        List<Variable> variable = Arrays.asList(new Variable[i]);
        List<Element> noeudMembre = e.getChildren();
        for (i = 0; i < noeudMembre.size(); i++) {
            Element element = noeudMembre.get(i);
            variable.set(i, (Variable) extraire_instruction(element, true));
            variable.get(i).setNom("[" + noeudMembre.indexOf(element) + "]");
        }
        for (; i < variable.size(); i++) {
            variable.set(i, new Variable(null, "", "[" + i + "]", "", null, null));
        }
        return variable;
    }

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Veuillez passez en argument les files C suivi potentiellement des args liées aux files C "
                    + "ou le file xml a afficher");
            return;
        }
        //SAXBuilder sxb = new SAXBuilder();
        ReadXml readxml = new ReadXml();
        try {
            String programme[] = new String[args.length+1];
            programme[0] = "./picoc";
            for (int i = 0; i < args.length; i++) {
                programme[i+1] = args[i];
            }
            ProcessBuilder pb = new ProcessBuilder(programme);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            System.out.println("Chargement ... Patientez");
            readxml.chargement_xml(reader);
            int exitVal = process.waitFor();
            if (exitVal != 0) {
                process = pb.start();
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.contains("<") && !line.contains(">")) {
                        System.out.println(line);
                    }
                }
                exitVal = process.waitFor();
                System.out.println("Code d'erreur " + exitVal);
            } else {
                System.out.println("Chargement réussi !!! ");
                Fenetre fenetre = new Fenetre("Ma fenetre", 1040, 480, readxml.hashDeclaration);
                fenetre.chargement(args);
                fenetre.revalidate();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println(e.toString());
        }

    }

}
