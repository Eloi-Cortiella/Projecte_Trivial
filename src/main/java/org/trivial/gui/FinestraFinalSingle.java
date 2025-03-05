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

public class FinestraFinalSingle extends JFrame {
    private JPanel panel1;
    private JLabel labelResultatsFinals;
    private JLabel labelImatgeFinal;
    private JButton botoSortir;
    private JLabel labelResultatsPunts;


    // Constructor de la finestra final
    public FinestraFinalSingle() throws IOException, ClassNotFoundException {
        this.setTitle("Resultat de la Partida");
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        //Altres parametres
        this.setLocationRelativeTo(null);

        // Si el resultat es 0 o menor a 0
        if (FinestraJoc.u.getPuntuacioTotal() == 0 || FinestraJoc.u.getPuntuacioTotal()  < 0 ) {
            this.setSize(800, 300);
            labelResultatsFinals.setText("Que malo que ets contestant preguntes!\n");
            labelResultatsPunts.setText("Has aconseguit " + FinestraJoc.u.getPuntuacioTotal()  + " punts en aquesta partida");
            ImageIcon icon = new ImageIcon("src/main/resources/loser1.gif");
            labelImatgeFinal.setIcon(icon);
        }
        // Si es els punts son la meitat dels punts a guanyar o es superior a la meitat pero inferior a la punts a guanyar
        else if (FinestraJoc.u.getPuntuacioTotal()  == FinestraJugadors.puntuacioGuanyar / 2 || FinestraJoc.u.getPuntuacioTotal() > FinestraJugadors.puntuacioGuanyar / 2 && FinestraJoc.u.getPuntuacioTotal() < FinestraJugadors.puntuacioGuanyar) {
            this.setSize(800, 800);
            labelResultatsFinals.setText("No esta malament, pero potser millor :).");
            labelResultatsPunts.setText("Has aconseguit " + FinestraJoc.u.getPuntuacioTotal()  + " punts en aquesta partida");
            ImageIcon icon = new ImageIcon("src/main/resources/ok1.gif");
            labelImatgeFinal.setIcon(icon);
        }
        // Si ha assolit la puntuació per a guanyar o superada a aquesta
        else if (FinestraJoc.u.getPuntuacioTotal()  >= FinestraJugadors.puntuacioGuanyar) {
            this.setSize(800, 450);
            labelResultatsFinals.setText("Felicitats! Has guanyat la partida!");
            labelResultatsPunts.setText("Has aconseguit " + FinestraJoc.u.getPuntuacioTotal()  + " punts en aquesta partida");
            ImageIcon icon = new ImageIcon("src/main/resources/winner1.gif");
            labelImatgeFinal.setIcon(icon);
        }

        /**
         * Accions del boto de sortir
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
                    // Bucle per afegir la puntuació de l'usuari al fitxer de usuaris.dat actualitzant l'objecte usuari
                    for (int i = 0; i < dafUs.size(); i++) {
                        Usuari u = dafUs.readObject(i);
                        if (u.getNom().equals(FinestraJoc.u.getNom())) {
                            u.setPuntuacioTotal(u.getPuntuacioTotal() + FinestraJoc.u.getPuntuacioTotal());
                            dafUs.updateObject(u, i);
                        }
                    }

                    // Tancar tots els fitxers i buidar les preguntes filtrades
                    dafUs.close();
                    dafPre.close();
                    dafConfig.deleteAll();
                    dafConfig.close();
                    buidarPreguntesFiltrades();
                    // Missatge de sortida
                    JOptionPane.showMessageDialog((Component) e.getSource(), "Fins aviat!");
                    System.exit(0);
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog((Component) e.getSource(),"Hi ha hagut algun error d'I/O al tancar el programa");
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // Mètode main de la finestra final de la partida singleplayer
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FinestraFinalSingle finestraFinalSingle = new FinestraFinalSingle();
            } catch (ClassNotFoundException | IOException e) {
                JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al programa");
                System.exit(1);
            }
        });
    }
}
