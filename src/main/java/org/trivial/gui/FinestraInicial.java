package org.trivial.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.io.IOException;


public class FinestraInicial extends JFrame {
    private JPanel pantallaInicial;
    private JSpinner spinnerJugadors;
    private JButton botoIniciarPartida;
    private JLabel PreguntaNjugadors;
    private JButton botoTutorial;

    //Variable per a guardar el nombre de jugadors
    public static int numJugadors;


    public FinestraInicial() throws IOException, ClassNotFoundException {
        //Per poder visualitzar la finestra farem...
        this.setContentPane(pantallaInicial);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

        //Altres parametres
        this.setMinimumSize(getPreferredSize());
        this.setLocationRelativeTo(null);

        // Accio boto per a iniciar la partida
        botoIniciarPartida.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                numJugadors = (int) spinnerJugadors.getValue();
                if (numJugadors == 0) {
                    JOptionPane.showMessageDialog(null, "El nombre de jugadors no pot ser 0");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Iniciant partida amb " + numJugadors + " jugadors.");
                    // Aquí pots obrir la següent pantalla del joc
                    setVisible(false);
                    try {
                        new FinestraJugadors();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        // Accio per a tancar l'aplicacio
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        // Boto per a mostrar una finestra on hi ha una llista de regles de l'aplicacio de trivial
        botoTutorial.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Regles del joc:\n 1. Abans d'iniciar el joc, el joc decidira quin jugador començara a contestar les preguntes a l'atzar." +
                        "\n 2. Si el jugador encerta la pregunta, obtindrà tres punts (Les preguntes són aleatories), però si falla, se li restarà 1 punt del total." +
                        "\n 3. Guanyarà aquell jugador que tingui més punts al final de les rondes.");
            }
        });

        // Accio per a limitar al spinner a un minim de 1 jugadors i un maxim de 4 i mostrara un missatge d'error si es supera el limit
        spinnerJugadors.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spinnerJugadors.getValue() > 4) {
                    spinnerJugadors.setValue(4);
                    JOptionPane.showMessageDialog(null, "El nombre de jugadors no pot ser superior a 4");
                } else if ((int) spinnerJugadors.getValue() < 0) {
                    spinnerJugadors.setValue(1);
                    JOptionPane.showMessageDialog(null, "El nombre de jugadors no pot ser inferior a 0");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FinestraInicial f=null;
                try {
                    f = new FinestraInicial();
                    f.setSize(400,200);
                } catch (ClassNotFoundException | IOException e) {
                    f.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al programa");
                    System.exit(1);
                }
            }
        });
    }
}