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
    private JRadioButton unSolJugadorRadioButton;
    private JLabel labelMulti;
    private JRadioButton multijugador2A4RadioButton;

    //Variable per a guardar el nombre de jugadors
    public static int numJugadors;


    public FinestraInicial() throws IOException, ClassNotFoundException {
        this.setTitle("Inici");
        this.setContentPane(pantallaInicial);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setMinimumSize(getSize());
        this.setLocationRelativeTo(null);

        labelMulti.setVisible(false);
        spinnerJugadors.setVisible(false);

        /**
         * Accions dels botons de la finestra inicial
         */
        botoIniciarPartida.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                // Si el mode de joc es sol un jugador, s'iniciara una partida d'un sol jugador
                if (unSolJugadorRadioButton.isSelected()) {
                    numJugadors = 1;
                    JOptionPane.showMessageDialog(null, "S'iniciarà una partida d'un sol jugador.");
                    // Aquí pots obrir la següent pantalla del joc
                    setVisible(false);
                    try {
                        new FinestraJugadors();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                // Si el mode de joc es multijugador, s'iniciara una partida amb el nombre de jugadors seleccionat
                else {
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
            }
        });

        // Accio per a tancar l'aplicacio
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        /**
         * Acció per a mostrar un tutorial de com jugar al joc
         */
        botoTutorial.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Regles del joc:\n 1. Abans d'iniciar el joc, l'usuari decidira quin mode vol escollir: Multijugador o Sol un jugador. Si es multijugador, Ha de decidir quants jugadors." +
                        "\n 2. Seguidament haurà de realitzar la configuració de la partida, com el número de preguntes, els punts necessaris per a guanyar... entre altres." +
                        "\n 3. Guanyarà aquell jugador que tingui més punts al final de les rondes o aquell que assoleixi els punts necessaris per a guanyar.");
            }
        });

        /**
         * Acció per a controlar el nombre de jugadors seleccionat en el spinner
         */
        spinnerJugadors.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spinnerJugadors.getValue() > 4) {
                    spinnerJugadors.setValue(4);
                    JOptionPane.showMessageDialog(null, "El nombre de jugadors no pot ser superior a 4");
                } else if ((int) spinnerJugadors.getValue() < 2) {
                    spinnerJugadors.setValue(2);
                    JOptionPane.showMessageDialog(null, "El nombre de jugadors no pot ser inferior a 2");
                }
            }
        });

        /**
         * Accions dels radio buttons de la finestra inicial
         */
        multijugador2A4RadioButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si es selecciona el mode de joc multijugador, es mostrara el spinner de seleccio de jugadors
                labelMulti.setVisible(true);
                spinnerJugadors.setVisible(true);
                spinnerJugadors.setValue(2);
                unSolJugadorRadioButton.setSelected(false);
            }
        });

        /**
         * Accions dels radio buttons de la finestra inicial
         */
        unSolJugadorRadioButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si es selecciona el mode de joc sol un jugador, es desactivara el spinner de seleccio de jugadors
                labelMulti.setVisible(false);
                spinnerJugadors.setVisible(false);
                multijugador2A4RadioButton.setSelected(false);
            }
        });
    }

    // Metode main de la finestra inicial
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