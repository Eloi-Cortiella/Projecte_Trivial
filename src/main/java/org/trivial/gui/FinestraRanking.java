package org.trivial.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

// Classe que crea la finestra del Ranking
public class FinestraRanking extends JFrame {
    private JPanel panelJ;
    private JLabel titolRanking;
    public JTable tableResultats;

    //Model de dades de la taula
    static DefaultTableModel taulaResultats;

    private Object[][] dadesUsuaris;

    // Constructor de la finestra del Ranking
    public FinestraRanking() throws IOException, ClassNotFoundException {
        //Per poder visualitzar la finestra farem...
        this.setTitle("Ranking de la Partida");
        this.setContentPane(panelJ);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);

        // Que no puguin modificar la taula
        tableResultats.setEnabled(false);

        //Altres parametres
        this.setMinimumSize(getPreferredSize());
        this.setLocationRelativeTo(null);

        // Carregar usuaris
        dadesUsuaris = new Object[FinestraJugadors.taulaResultats.getRowCount()][3];
        for (int i = 0; i < FinestraJugadors.taulaResultats.getRowCount(); i++) {
            Usuari usuari = new Usuari((String) FinestraJugadors.taulaResultats.getValueAt(i, 0), (int) FinestraJugadors.taulaResultats.getValueAt(i, 1));
            dadesUsuaris[i][0] = usuari.getNom();
            dadesUsuaris[i][1] = usuari.getPuntuacioTotal();
        }

        omplirTaula();
    }

    /**
     * Mètode per omplir la taula amb els usuaris i les seves puntuacions
     * @throws IOException Si hi ha algun problema amb l'entrada/sortida
     * @throws ClassNotFoundException Si no es troba la classe
     */
    private void omplirTaula() throws IOException, ClassNotFoundException {
        // Preparar la taula
        // Establir el model de dades de la taula
        taulaResultats = new DefaultTableModel(
                //Dades a mostrar
                dadesUsuaris,
                //Definim les columnes de la taula
                new Object[]{"Nom","Puntuació de la partida"}
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

        // Ordenar la taula de més a menys puntuació
        Arrays.sort(dadesUsuaris, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return (int) o2[1] - (int) o1[1];  // Ordena de més a menys
            }
        });
    }

    /**
     * Mètode per actualitzar el ranking
     * @param u Usuari actual
     * @param acertat Boolean que indica si ha acertat la pregunta
     * @throws IOException Si hi ha algun problema amb l'entrada/sortida
     * @throws ClassNotFoundException Si no es troba la classe
     */
    public void actualitzarRanking(Usuari u, boolean acertat) throws IOException, ClassNotFoundException {
        if (acertat) {
            // Afegir punts al jugador
            u.setPuntuacioTotal(u.getPuntuacioTotal()+FinestraJoc.puntuacioPregunta);
        }
        else {
            // Penalitzar al jugador restant-li punts
            u.setPuntuacioTotal(u.getPuntuacioTotal()-FinestraJoc.penalitzacioTemps);
        }

        // Actualitzar la puntuació del jugador
        for (int i = 0; i < dadesUsuaris.length; i++) {
            if (Objects.equals(dadesUsuaris[i][0], u.getNom())) {
                dadesUsuaris[i][1] = u.getPuntuacioTotal();
                break;
            }
        }

        omplirTaula();
    }

    // Mètode main de la finestra del Ranking
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FinestraRanking finestraRanking = new FinestraRanking();
            } catch (ClassNotFoundException | IOException e) {
                JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al programa");
                System.exit(1);
            }
        });
    }
}