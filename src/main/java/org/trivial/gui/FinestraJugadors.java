package org.trivial.gui;

import com.iesebre.usefulcode.DirectAccessFile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class FinestraJugadors extends JFrame {
    private JPanel panelJ;
    private JButton botoAfegirUsuari;
    private JTextField campNom;
    private JTable tableUsuaris;
    private JLabel labelUsuari;
    private JButton iniciarJoc;
    private JLabel labelNpreguntes;
    private JLabel labelConfiguracio;
    private JLabel labelPuntuacio;
    private JLabel labelConfiguracioPartida;
    private JSpinner spinnerPreguntes;
    private JSpinner spinnerPuntuacio;
    private JTextField textFieldPreguntes;
    private int numJugador;
    
    //Model de dades de la taula
    private DefaultTableModel taulaUsuaris;
    //Fitxer d'accés directe a usuaris
    private DirectAccessFile<Usuari> dafUs;

    //Variable per a guardar el nombre de preguntes
    public static int numPreguntes;

    // Variable per a guardar la puntuació
    public static int puntuacioGuanyar;

    public FinestraJugadors() throws IOException, ClassNotFoundException {
        //Per poder visualitzar la finestra farem...
        this.setContentPane(panelJ);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

        //Altres parametres
        this.setMinimumSize(getPreferredSize());
        this.setLocationRelativeTo(null);

        //Instanciem el fitxer
        dafUs = new DirectAccessFile<>("Usuaris.dat");

        //Eliminar dades dels usuaris.dat de la anterior partida
        dafUs.deleteAll();

        //Carreguem les dades del fitxer al model de dades de la taula
        Object[][] dadesUsuaris=new Object[dafUs.size()][2];
        for (int i = 0; i < dafUs.size(); i++) {
            Usuari u = dafUs.readObject(i);
            dadesUsuaris[i][0]=u.getNom();
            dadesUsuaris[i][1]= numJugador;
        }

        //Anem a establir el model de dades de la taula
        taulaUsuaris =new DefaultTableModel(
                //Dades a mostrar
                dadesUsuaris,
                //Definim les columnes de la taula
                new Object[]{"Nom","Numero Jugador"}
        );

        //Assignem el model de la taula
        tableUsuaris.setModel(taulaUsuaris);

        //Poso el model de selecció de la taula de manera que només es pugui seleccionar una fila
        tableUsuaris.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        spinnerPreguntes.setValue(15);
        spinnerPuntuacio.setValue(15);

        // Accio per a tancar l'aplicacio
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    dafUs.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(e.getComponent(),"Hi ha hagut algun error d'I/O al tancar el programa");
                }
            }
        });

        botoAfegirUsuari.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numJugador >=FinestraInicial.numJugadors) {
                    JOptionPane.showMessageDialog((Component)e.getSource(),"Ja has afegit tots els jugadors");
                }
                else if (!campNom.getText().isBlank()) {
                    try {
                        dafUs.writeObject(new Usuari(campNom.getText().strip(), numJugador++,0));
                        taulaUsuaris.addRow(new Object[]{campNom.getText().strip(), numJugador});
                    } catch (IOException Exception) {
                        JOptionPane.showMessageDialog((Component)e.getSource(),"Hi ha algun error a l'insertar!!") ;
                    }
                }
                else JOptionPane.showMessageDialog((Component)e.getSource(),"No pots insertar un text en blank burro");
            }
        });
        iniciarJoc.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                numPreguntes = (int) spinnerPreguntes.getValue();
                if (numJugador < FinestraInicial.numJugadors) {
                    JOptionPane.showMessageDialog((Component)e.getSource(),"Has d'afegir tots els jugadors abans de començar el joc");
                }
                else {
                    setVisible(false);
                    try {
                        new FinestraJoc(new FinestraRanking());
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        spinnerPreguntes.addChangeListener(new ChangeListener() {
            /**
             * Invoked when the target of the listener has changed its state.
             *
             * @param e a ChangeEvent object
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spinnerPreguntes.getValue() > 20) {
                    spinnerPreguntes.setValue(30);
                    JOptionPane.showMessageDialog(null, "El nombre de preguntes no pot ser superior a 30");
                } else if ((int) spinnerPreguntes.getValue() < 15) {
                    spinnerPreguntes.setValue(15);
                    JOptionPane.showMessageDialog(null, "El nombre de preguntes no pot ser inferior a 15");
                }
            }
        });

        spinnerPuntuacio.addChangeListener(new ChangeListener() {
            /**
             * Invoked when the target of the listener has changed its state.
             *
             * @param e a ChangeEvent object
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spinnerPuntuacio.getValue() > 20) {
                    spinnerPuntuacio.setValue(30);
                    JOptionPane.showMessageDialog(null, "La puntuació total no pot ser superior a 30");
                } else if ((int) spinnerPuntuacio.getValue() < 15) {
                    spinnerPuntuacio.setValue(15);
                    JOptionPane.showMessageDialog(null, "La puntuació total no pot ser inferior a 15");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FinestraJugadors fus=null;
                try {
                    fus = new FinestraJugadors();
                    fus.setSize(600,400);
                } catch (ClassNotFoundException | IOException e) {
                    fus.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al programa");
                    System.exit(1);
                }
            }
        });
    }
}
