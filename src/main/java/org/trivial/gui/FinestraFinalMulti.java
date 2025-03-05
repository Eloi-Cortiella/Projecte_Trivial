package org.trivial.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static org.trivial.gui.BancPreguntes.buidarPreguntesFiltrades;
import static org.trivial.gui.BancPreguntes.dafPre;
import static org.trivial.gui.FinestraJugadors.dafUs;
import static org.trivial.gui.GestorConfiguracio.dafConfig;

public class FinestraFinalMulti extends JFrame {
    private JPanel panel1;
    private JLabel titolResultats;
    private JLabel labelPrimer;
    private JLabel labelSegon;
    private JLabel labelTercer;
    private JLabel labelPrimerLloc;
    private JLabel labelSegonLloc;
    private JLabel labelTercerLloc;
    private JButton botoSortir;

    // Constructor de la finestra final de la partida multijugador
    public FinestraFinalMulti() throws IOException, ClassNotFoundException{
        this.setTitle("Guanyadors de la Partida");
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        //Altres parametres
        this.setMinimumSize(getPreferredSize());
        this.setLocationRelativeTo(null);

        // Obtenir dades a partir de la taula de resultats del ranking
        DefaultTableModel model = FinestraRanking.taulaResultats;
        Object[][] dades = new Object[model.getRowCount()][2];
        for (int i = 0; i < model.getRowCount(); i++) {
            dades[i][0] = model.getValueAt(i, 0);
            dades[i][1] = model.getValueAt(i, 1);
        }

        // Condicionals d'inicialitzar els labels amb les dades en base a la quantitat de jugadors
        if (dades.length > 0) {
            labelPrimer.setText("1. " + dades[0][0] + " amb " + dades[0][1] + " punts");
            if (dades.length > 1) {
                labelSegon.setText("2. " + dades[1][0] + " amb " + dades[1][1] + " punts");
                if (dades.length > 2) {
                    labelTercer.setText("3. " + dades[2][0] + " amb " + dades[2][1] + " punts");
                }
            }
        }

        // Altres condicionals d'inicialitzar els labels amb les dades en base a la quantitat de jugadors
        if (dades.length < 3) {
            labelTercer.setText("No hi ha tercer lloc");
            if (dades.length < 2) {
                labelSegon.setText("No hi ha segon lloc");
            }
        }

        // Canviar mida dels iconos dels labels
        labelSegonLloc.setSize(100, 100);

        /**
         * Acció de tancar la finestra i guardar les puntuacions dels usuaris al fitxer de usuaris.dat
         */
        botoSortir.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Bucle que recorre la taulaResultats per afegir la puntuació dels usuaris de la taulaResultats al fitxer de usuaris.dat actualitzant l'objecte usuari del fitxer
                    for (int i = 0; i < FinestraRanking.taulaResultats.getRowCount(); i++) {
                        Usuari u = dafUs.readObject(i);
                        for (int j = 0; j < FinestraRanking.taulaResultats.getRowCount(); j++) {
                            if (u.getNom().equals(FinestraRanking.taulaResultats.getValueAt(j, 0))) {
                                u.setPuntuacioTotal(u.getPuntuacioTotal() + (int) FinestraRanking.taulaResultats.getValueAt(j, 1));
                                dafUs.updateObject(u, i);
                            }
                        }
                    }

                    // Tancar fitxers i esborrar totes les preguntes filtrades
                    dafUs.close();
                    dafPre.close();
                    dafConfig.deleteAll();
                    dafConfig.close();
                    buidarPreguntesFiltrades();
                    // Missatge de sortida
                    JOptionPane.showMessageDialog((Component) e.getSource(), "Fins aviat!");
                    System.exit(0);
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog((Component) e.getSource(), "Hi ha hagut algun error d'I/O al tancar el programa");
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // Mètode main de la finestra final de la partida multijugador
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FinestraFinalMulti finestraFinalMulti = new FinestraFinalMulti();
            } catch (ClassNotFoundException | IOException e) {
                JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al programa");
                System.exit(1);
            }
        });
    }


}
