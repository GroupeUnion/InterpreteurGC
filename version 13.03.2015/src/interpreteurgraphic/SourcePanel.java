package interpreteurgraphic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Asus
 */
public class SourcePanel extends JPanel implements ListSelectionListener {

    private List<JList> listePanel;
    private JTabbedPane pagePanel;
    private JPanel flechePanel;
    private int numPreced, numSuiv;
    private int flecheNumber = 0;

    public SourcePanel(String fichierSource[]) {
        listePanel = new ArrayList<JList>();

        pagePanel = new JTabbedPane();
        this.setLayout(new BorderLayout());
        this.pagePanel.setLocation(40, 0);
        this.pagePanel.getAccessibleContext().setAccessibleDescription("");
        this.flechePanel = new JPanel();
        this.flechePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.flechePanel.setOpaque(true);
        this.flechePanel.setLocation(0, 0);
        this.flechePanel.setPreferredSize(new Dimension(30, this.getHeight() - 5));

        this.flechePanel.setBackground(Color.getHSBColor((float) (212 / 256.0), (float) (17 / 256.0), (float) (67 / 256.0)));

        this.flechePanel.setBorder(BorderFactory.createEmptyBorder(23, 0, 0, 0));
        for (String string : fichierSource) {
            addFichier(string);
        }

        this.add(this.flechePanel, BorderLayout.WEST);
        this.add(pagePanel, BorderLayout.CENTER);
    }

    private void addFichier(String fichier_src) {
        try {
            File file = new File(fichier_src);
            if (!file.exists()) {
                return;
            }
            List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
            JList<String> newList = new JList<String>(lines.toArray(new String[lines.size()]));
            //numeroter les lignes
            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (int i = 0; i < newList.getModel().getSize(); i++) {
                String str = "";
                if (i < 10) {
                    str = (i + 1) + "     " + newList.getModel().getElementAt(i);
                } else {
                    str = (i + 1) + "    " + newList.getModel().getElementAt(i);
                }

                //str_tab[i] = str;
                listModel.add(i, str);

            }
            newList.setModel(listModel);
            JScrollPane scroll = new JScrollPane();
            newList.setBackground(Color.getHSBColor((float) (140 / 256.0), (float) (111 / 256.0), (float) (194 / 256.0)));
            newList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            newList.setFixedCellHeight(20);
            scroll.setViewportView(newList);

            pagePanel.addTab(file.getName(), scroll);
            addFleche(listModel.size());
            //newList.clearSelection();
            newList.setSelectedValue(null, true);
            newList.addListSelectionListener(this);
            listePanel.add(newList);

        } catch (IOException ex) {
        }

    }

    private void addFleche(int newCount) {
        int difference;
        if ((difference = newCount - this.flechePanel.getComponentCount()) > 0) {
            for (int i = 0; i < difference; i++) {
                TestF test = new TestF(this.flechePanel.getBackground());
                test.setMargin(0, 0, 0, 0);
                test.setPreferredSize(new Dimension(30, 15));
                test.setVisible(true);
                this.flechePanel.add(test);
            }
        }
    }

    public void activation(int numFleche, int suivant) {
        if (numFleche != -1) {
            this.flechePanel.getComponent(this.numPreced).setForeground(this.flechePanel.getBackground());
            this.flechePanel.getComponent(this.numSuiv).setForeground(this.flechePanel.getBackground());
        }
        if (numFleche != -1) {
            this.flechePanel.getComponent(numFleche).setForeground(Color.getHSBColor((float) (136 / 256.0), (float) (240 / 256.0), (float) (256 / 256.0)));
            this.numPreced = numFleche;
            listePanel.get(this.pagePanel.getSelectedIndex()).setSelectedIndex(numFleche);

        }
        if (suivant != -1) {
            this.flechePanel.getComponent(suivant).setForeground(Color.WHITE);
            this.numSuiv = suivant;
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height - 15);
        if (this.pagePanel != null) {
            this.pagePanel.setSize(width - 30, height - 15);
            pagePanel.setLocation(30, 0);
            this.flechePanel.setSize(30, height - 15);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList lsm = (JList) e.getSource();
        if (!lsm.isSelectedIndex(this.numPreced)) {
            listePanel.get(this.pagePanel.getSelectedIndex()).setSelectedIndex(numPreced);
        }
    }

}
