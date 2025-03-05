package org.trivial.gui;

import static org.trivial.gui.BancPreguntes.*;
import static org.trivial.gui.FinestraJugadors.*;
import static org.trivial.gui.GestorConfiguracio.dafConfig;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Classe que implementa la finestra de joc del trivial
 */
public class FinestraJoc extends JFrame {
    private JPanel pantallaJoc;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JButton botoConfirmar;
    private JLabel pregunta;
    private JLabel enunciatPregunta;
    private JLabel puntacioJugador;
    private JProgressBar progressBarTemps;

    //Altres components
    private int indexPreguntaActual = 0;
    private int numJugadorTorn;
    private Usuari uSeg;
    static Usuari u;
    private int numRonda = 1;
    private FinestraRanking finestraRanking;
    private int tempsRestant;
    private String respostaCorrecta;
    private Timer t;
    static int puntuacioGuanyar;
    static int puntuacioPregunta;
    static int penalitzacioTemps;
    static int numPreguntes;
    private ActionListener temporitzador;

    // Constructor de la finestra de joc
    public FinestraJoc() throws IOException, ClassNotFoundException {
        this.setTitle("Trivial");
        this.setContentPane(pantallaJoc);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setSize(600, 400);

        //Altres parametres
        this.setMinimumSize(getPreferredSize());
        this.setLocationRelativeTo(null);

        // Llegir la configuració del fitxer de configuració
        tempsRestant = configuracio.getTempsMaximResposta();
        puntuacioGuanyar = configuracio.getPuntuacioGuanyar();
        puntuacioPregunta = configuracio.getPuntuacioPregunta();
        penalitzacioTemps = configuracio.getPenalitzacioTemps();
        numPreguntes = configuracio.getNumPreguntes();

        // Finestra de ranking
        this.finestraRanking = new FinestraRanking();

        // Si es sol un jugador, no mostrar la finestra de ranking
        if (FinestraInicial.numJugadors == 1) {
            finestraRanking.setVisible(false);
            finestraRanking.setSize(0, 0);
        }

        // Versió llegir usuaris de la taula jugadors
        Object[][] dadesJugadors = new Object[taulaResultats.getRowCount()][3];
        for (int i = 0; i < taulaResultats.getRowCount(); i++) {
            Usuari usuari = new Usuari((String) taulaResultats.getValueAt(i, 0), (int) taulaResultats.getValueAt(i, 1));
            dadesJugadors[i][0] = usuari.getNom();
            dadesJugadors[i][1] = usuari.getPuntuacioTotal();
        }

        // Començara el joc amb el primer jugador que de la llista
        numJugadorTorn = 0;

        // Llegir el primer jugador amb les dades de la taula
        u = new Usuari((String) taulaResultats.getValueAt(numJugadorTorn, 0), (int) taulaResultats.getValueAt(numJugadorTorn, 1));

        if (taulaResultats.getRowCount() == 1) {
            JOptionPane.showMessageDialog(null, "Ronda " + numRonda + "!\nComença la partida! Bona sort!");
        }
        else {
            // Mostrar el nom del jugador que comença
            JOptionPane.showMessageDialog(null, "Primera Ronda.\n"+"Comença el jugador " + u.getNom());
        }

        // Mostrar la primera pregunta canviant les varibles label i radio button
        Pregunta preguntaPantalla = new Pregunta(preguntes.get(indexPreguntaActual).getEnunciat(), preguntes.get(indexPreguntaActual).getOpcions(), preguntes.get(indexPreguntaActual).getRespostaCorrecta(), preguntes.get(indexPreguntaActual).getCategoria());
        mostrarPregunta(preguntaPantalla, u);
        respostaCorrecta = preguntaPantalla.getRespostaCorrecta();

        // Començar el timer
        progressBarTemps.setMaximum(tempsRestant);
        progressBarTemps.setValue(tempsRestant);
        progressBarTemps.setString(tempsRestant+" segons");

        temporitzador = new ActionListener() {
            /**
             * Timer per a la barra de progrés
             *
             * @param e l'event a processar del timer
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBarTemps.setValue(progressBarTemps.getValue() - 1);
                progressBarTemps.setString(progressBarTemps.getValue()+" segons");
                if (progressBarTemps.getValue() == 0) {
                    // Penalitzar al jugador
                    JOptionPane.showMessageDialog(null, "Temps esgotat! -" + penalitzacioTemps + " punts");
                    try {
                        finestraRanking.actualitzarRanking(u, false);
                        t.stop();
                        canviarTorn();
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error en actualitzar el ranking.");
                    }
                }
            }
        };

        t = new Timer(1000, temporitzador);
        t.start();


        botoConfirmar.addActionListener(new ActionListener() {
            /**
             *  Boto per a confirmar la resposta escollida
             * @param e l'event a processar del boto
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Comprovar quina resposta ha seleccionat el jugador
                String respostaSeleccionada;
                t.stop();
                if (radioButton1.isSelected()) {
                    respostaSeleccionada = radioButton1.getText();
                } else if (radioButton2.isSelected()) {
                    respostaSeleccionada = radioButton2.getText();
                } else if (radioButton3.isSelected()) {
                    respostaSeleccionada = radioButton3.getText();
                } else {
                    respostaSeleccionada = radioButton4.getText();
                }

                if (respostaSeleccionada.equals(respostaCorrecta)) {
                    JOptionPane.showMessageDialog(null, "Resposta correcta! +"+puntuacioPregunta+" punts");
                    try {
                        finestraRanking.actualitzarRanking(u, true);
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error en actualitzar el ranking.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Resposta incorrecta! -"+penalitzacioTemps+" punts\nLa resposta correcta era: " + respostaCorrecta);
                    try {
                        finestraRanking.actualitzarRanking(u, false);
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error en actualitzar el ranking.");
                    }
                }

                canviarTorn();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            /**
             * Event per a tancar la finestra
             * @param e l'event a processar de la finestra
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    dafUs.close();
                    dafPre.close();
                    dafConfig.deleteAll();
                    dafConfig.close();
                    buidarPreguntesFiltrades();
                    System.out.println("Tancant el programa");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(e.getComponent(),"Hi ha hagut algun error d'I/O al tancar el programa");
                }
            }
        });
        progressBarTemps.addComponentListener(new ComponentAdapter() {
        });
    }

    /**
     * Funció per a canviar de torn
     */
    private void canviarTorn() {

        // Mostrar la següent pregunta canviant les variAbles label i radiobutton
        indexPreguntaActual++;
        if (indexPreguntaActual <= numPreguntes-1) {
            // Si algun jugador ha arribat a la puntuació guanyadora, acabar el joc
            if (u.getPuntuacioTotal() >= puntuacioGuanyar) {
                // Comprovar si es sol un jugador o multiples jugadors
                if (FinestraInicial.numJugadors == 1) {
                    JOptionPane.showMessageDialog(null, "Has arribat a la puntuació guanyadora!");
                    // Obrir la finestra dels resultats de un sol jugador
                    // S'ha acabat el joc, tancar finestra
                    try {
                        // Obrir la finestra dels resultats
                        setVisible(false);
                        finestraRanking.setVisible(false);
                        new FinestraFinalSingle();
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al tancar el programa");
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "El jugador " + u.getNom() + " ha arribat a la puntuació guanyadora!");
                    // Obrir la finestra dels resultats
                    try {
                        setVisible(false);
                        finestraRanking.setVisible(false);
                        new FinestraFinalMulti();
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al tancar el programa");
                        throw new RuntimeException(ex);
                    }
                }
            }

            // Si es un sol jugador no cal canviar de torn, ja que es el mateix jugador
            else if (FinestraInicial.numJugadors == 1) {
                numRonda++;
                JOptionPane.showMessageDialog(null, "Ronda " + numRonda + "!\n");

                Pregunta preguntaPantalla = preguntes.get(indexPreguntaActual);
                // Cridem a la funció per a mostrar la següent pregunta
                respostaCorrecta = preguntaPantalla.getRespostaCorrecta();
                mostrarPregunta(preguntaPantalla, u);

                // Reiniciar els paràmetres de la barra de progrés
                progressBarTemps.setMaximum(tempsRestant);
                progressBarTemps.setValue(tempsRestant);
                progressBarTemps.setString(tempsRestant+" segons");

                // Reiniciar el timer
                t.restart();
            }
            else {
                // CANVIAR DE JUGADOR //
                // Següent jugador que no sigui el mateix que ha contestat
                numJugadorTorn++;
                numRonda++;
                // Si el número de jugador és més gran que la quantitat de jugadors introduïts, tornar a començar amb el primer jugador
                if (numJugadorTorn > taulaResultats.getRowCount()-1) {
                    numJugadorTorn = 0;
                }

                // Llegir el següent jugador a partir de la taula de jugadors
                uSeg = new Usuari((String) FinestraRanking.taulaResultats.getValueAt(numJugadorTorn, 0), (int) FinestraRanking.taulaResultats.getValueAt(numJugadorTorn, 1));
                u.setNom(uSeg.getNom());
                u.setPuntuacioTotal(uSeg.getPuntuacioTotal());

                JOptionPane.showMessageDialog(null, "Ronda " + numRonda + "!\n" + "Torn del jugador " + uSeg.getNom());

                Pregunta preguntaPantalla = preguntes.get(indexPreguntaActual);
                // Cridem a la funció per a mostrar la següent pregunta
                respostaCorrecta = preguntaPantalla.getRespostaCorrecta();
                mostrarPregunta(preguntaPantalla, u);

                // Reiniciar els paràmetres de la barra de progrés
                progressBarTemps.setMaximum(tempsRestant);
                progressBarTemps.setValue(tempsRestant);
                progressBarTemps.setString(tempsRestant+" segons");

                // Reiniciar el timer
                t.restart();
            }
        }

        else {
            // Comprovar si es sol un jugador o multiples jugadors
            if (FinestraInicial.numJugadors == 1) {
                JOptionPane.showMessageDialog(null, "S'han acabat les preguntes!");
                // Obrir la finestra dels resultats de un sol jugador
                // S'ha acabat el joc, tancar finestra
                try {
                    // Obrir la finestra dels resultats
                    setVisible(false);
                    finestraRanking.setVisible(false);
                    new FinestraFinalSingle();
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al tancar el programa");
                    throw new RuntimeException(ex);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "S'han acabat les preguntes!");
                // Obrir la finestra dels resultats
                try {
                    setVisible(false);
                    finestraRanking.setVisible(false);
                    new FinestraFinalMulti();
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al tancar el programa");
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    /**
     * Funció per a mostrar la pregunta
     * @param Pregunta Pregunta a mostrar
     * @param u Usuari actual
     */
    private void mostrarPregunta(Pregunta Pregunta, Usuari u) {
        pregunta.setText("Pregunta " + (indexPreguntaActual + 1));
        puntacioJugador.setText("Puntuació de " + u.getNom() + ": " + u.getPuntuacioTotal());
        enunciatPregunta.setText(Pregunta.getEnunciat());
        radioButton1.setText(Pregunta.getOpcions().get(0));
        radioButton2.setText(Pregunta.getOpcions().get(1));
        radioButton3.setText(Pregunta.getOpcions().get(2));
        radioButton4.setText(Pregunta.getOpcions().get(3));
        radioButton1.setSelected(false);
        radioButton2.setSelected(false);
        radioButton3.setSelected(false);
        radioButton4.setSelected(false);
    }

    // Funció principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FinestraJoc finestraJoc = new FinestraJoc();
            } catch (ClassNotFoundException | IOException e) {
                JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al programa");
                System.exit(1);
            }
        });
    }
}
