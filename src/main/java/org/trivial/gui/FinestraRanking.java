package org.trivial.gui;

import com.iesebre.usefulcode.DirectAccessFile;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class FinestraRanking extends JFrame {
    private JPanel panelJ;
    private JLabel titolRanking;
    public JTable tableResultats;

    //Model de dades de la taula
    private static DirectAccessFile<Usuari> dafUs;

    //Model de dades de la taula
    public static DefaultTableModel taulaResultats;

    private static Object[][] dadesUsuaris;

    public FinestraRanking() throws IOException, ClassNotFoundException {
        //Per poder visualitzar la finestra farem...
        this.setContentPane(panelJ);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);

        // Que no puguin modificar la taula
        tableResultats.setEnabled(false);

        //Altres parametres
        this.setMinimumSize(getPreferredSize());
        this.setLocationRelativeTo(null);

        // Instanciem el fitxer
        dafUs = new DirectAccessFile<>("Usuaris.dat");

        // Carregar usuaris
        dadesUsuaris = new Object[dafUs.size()][3];
        for (int i = 0; i < dafUs.size(); i++) {
            Usuari u = dafUs.readObject(i);
            dadesUsuaris[i][0] = u.getNom();
            dadesUsuaris[i][1] = u.getPuntuacioTotal();
        }

        // Preparar la taula
        //Anem a establir el model de dades de la taula
        taulaResultats = new DefaultTableModel(
                //Dades a mostrar
                dadesUsuaris,
                //Definim les columnes de la taula
                new Object[]{"Nom","Puntuació total"}
        );

        tableResultats.setModel(taulaResultats);

        // Posar un color diferent a les 3 primeres files de la taula, indicant el primer lloc, segon i tercer
        tableResultats.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == 0) {
                    c.setBackground(Color.YELLOW);
                } else if (row == 1) {
                    c.setBackground(Color.LIGHT_GRAY);
                } else if (row == 2) {
                    c.setBackground(Color.ORANGE);
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });
    }

    public void actualitzarRanking(Usuari u, boolean acertat) throws IOException, ClassNotFoundException {
        if (acertat) {
            // Afegir 3 punts al jugador
            u.setPuntuacioTotal(u.getPuntuacioTotal()+3);
        }
        else {
            // Restar 1 punt al jugador
            u.setPuntuacioTotal(u.getPuntuacioTotal()-1);
        }

        // Actualitzar la puntuació del jugador
        for (int i = 0; i < dafUs.size(); i++) {
            Usuari usuari = dafUs.readObject(i);
            if (usuari.getNumeroJugador() == u.getNumeroJugador()) {
                dadesUsuaris[i][1] = u.getPuntuacioTotal();
                //dafUs.updateObject(new Usuari(u.getNom(), u.getNumeroJugador(), u.getPuntuacioTotal()), i);
            }
        }

        // Ordenar els usuaris per puntuació (de més a menys)
        Arrays.sort(dadesUsuaris, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return (int) o2[1] - (int) o1[1];  // Ordena de més a menys
            }
        });

        // Actualitzar la taula
//        for (int i = 0; i < dafUs.size(); i++) {
//            taulaResultats.setValueAt(dadesUsuaris[i][0], i, 0);
//            taulaResultats.setValueAt(dadesUsuaris[i][1], i, 1);
//        }

        tableResultats.setModel(taulaResultats);
    }
}