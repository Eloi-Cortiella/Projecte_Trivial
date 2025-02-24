package org.trivial.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesebre.usefulcode.DirectAccessFile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

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
    private List<Pregunta> preguntes;
    private int indexPreguntaActual = 0;
    private int numJugadorTorn;
    private Usuari uSeg;
    private Usuari u;
    private int numRonda = 1;
    private FinestraRanking finestraRanking;

    public FinestraJoc(FinestraRanking finestraRanking) throws IOException, ClassNotFoundException {
        this.setTitle("Trivial");
        this.setContentPane(pantallaJoc);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setSize(500, 300);

        //Altres parametres
        this.setMinimumSize(getPreferredSize());
        this.setLocationRelativeTo(null);

        this.finestraRanking = finestraRanking;

        // Carregar preguntes i barrejar-les cada vegada que es comença una partida
        carregarPreguntes();

        // Carregar usuaris
        //Instanciem el fitxer
        DirectAccessFile<Usuari> dafUs = new DirectAccessFile<>("Usuaris.dat");

        // Llegir usuaris que es troben al fitxer usuaris.dat
        Object[][] dadesUsuaris = new Object[dafUs.size()][3]; // Correct the array length to 3
        for (int i = 0; i < dafUs.size(); i++) {
            Usuari u = dafUs.readObject(i);
            dadesUsuaris[i][0] = u.getNom();
            dadesUsuaris[i][1] = u.getNumeroJugador();
            dadesUsuaris[i][2] = u.getPuntuacioTotal(); // This line is now correct
        }

        // Començara el joc amb el primer jugador que de la llista
        numJugadorTorn = 0;

        u = dafUs.readObject(numJugadorTorn);
        // Mostrar el nom del jugador que comença
        JOptionPane.showMessageDialog(null, "Primera Ronda.\n"+"Comença el jugador " + u.getNom());

        // Mostrar la primera pregunta canviant les varibles label i radio button
        Pregunta preguntaPantalla = new Pregunta( preguntes.get(indexPreguntaActual).getEnunciat(), preguntes.get(indexPreguntaActual).getOpcions(), preguntes.get(indexPreguntaActual).getRespostaCorrecta());
        mostrarPregunta(preguntaPantalla, u);

        // Afegir una progressBar de temps restant a contestar la pregunta, i cada segon que passa que vagi progressant la barra


        // Boto per a confirmar la resposta escollida
        botoConfirmar.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Comprovar si la resposta seleccionada és correcta
                int respostaCorrecta = preguntaPantalla.getRespostaCorrecta();
                // Comprovar quina resposta ha seleccionat el jugador
                int respostaSeleccionada = -1;
                if (radioButton1.isSelected()) {
                    respostaSeleccionada = 0;
                } else if (radioButton2.isSelected()) {
                    respostaSeleccionada = 1;
                } else if (radioButton3.isSelected()) {
                    respostaSeleccionada = 2;
                } else if (radioButton4.isSelected()) {
                    respostaSeleccionada = 3;
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una resposta!");
                    return;
                }

                if (respostaSeleccionada == respostaCorrecta) {
                    JOptionPane.showMessageDialog(null, "Resposta correcta! +3 punts");
                    // **ACTUALITZAR EL RANKING**
                    try {
                        finestraRanking.actualitzarRanking(u, true);
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error en actualitzar el ranking.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Resposta incorrecta! -1 punt");
                    // **ACTUALITZAR EL RANKING**
                    try {
                        finestraRanking.actualitzarRanking(u, false);
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error en actualitzar el ranking.");
                    }

                }

                // Guardar la puntuació del jugador
                try {
                    dafUs.writeObject(u);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // CANVIAR DE JUGADOR //
                // Següent jugador que no sigui el mateix que ha contestat
                numJugadorTorn++;
                numRonda++;
                // Si el número de jugador és més gran que la quantitat de jugadors introduïts, tornar a començar amb el primer jugador
                if (numJugadorTorn > dafUs.size()-1) {
                    numJugadorTorn = 0;
                }
                // Llegir el següent jugador
                try {
                    uSeg = dafUs.readObject(numJugadorTorn);
                    u = uSeg;
                    JOptionPane.showMessageDialog(null, "Ronda "+ numRonda+"!\n"+"Torn del jugador " + uSeg.getNom());
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                // Mostrar la següent pregunta canviant les variAbles label i radiobutton
                indexPreguntaActual++;
                if (indexPreguntaActual <= FinestraJugadors.numPreguntes) {
                    Pregunta preguntaPantalla = preguntes.get(indexPreguntaActual);
                    // Cridem a la funció per a mostrar la següent pregunta
                    mostrarPregunta(preguntaPantalla, uSeg);
                } else {
                    JOptionPane.showMessageDialog(null, "Has acabat totes les preguntes!");
                    // S'ha acabat el joc, tancar finestra
                    try {
                        dafUs.close();
                    } catch (IOException ex) {

                        JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al tancar el programa");
                    }
                    // Obrir la finestra dels resultats
                    setVisible(false);
                }
            }
        });
    }

    // Funció per a mostrar la pregunta i les opcions
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

    // Funció per a carregar les preguntes i barrejarles usant la classe Pregunta
    private void carregarPreguntes() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = new String(Files.readAllBytes(Paths.get("src/preguntes.json")));
        preguntes = objectMapper.readValue(json, new TypeReference<List<Pregunta>>() {});
        Collections.shuffle(preguntes); // Barrejar preguntes
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FinestraRanking finestraRanking = new FinestraRanking();
                FinestraJoc finestraJoc = new FinestraJoc(finestraRanking);
            } catch (ClassNotFoundException | IOException e) {
                JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al programa");
                System.exit(1);
            }
        });
    }
}
