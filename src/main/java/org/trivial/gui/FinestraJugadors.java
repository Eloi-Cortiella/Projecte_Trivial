package org.trivial.gui;

import com.iesebre.usefulcode.DirectAccessFile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static org.trivial.gui.BancPreguntes.*;

// Classe per a la finestra de selecció de jugadors
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
    private JSpinner spinnerPreguntes;
    private JSpinner spinnerPuntuacio;
    private JTable tableJugadors;
    private JLabel labelTaulaUsuaris;
    private JLabel labelTaulaJugadors;
    private JButton buttonEliminar;
    private JComboBox comboBoxCategoria;
    private JLabel labelCategoria;
    private JLabel labelPuntuacioPregunta;
    private JLabel labelTempsMaxim;
    private JLabel labelPenalitzacioTempsIncorrecta;
    private JSlider sliderPuntuacioCorrecta;
    private JSlider sliderTempsMaxim;
    private JSlider sliderPuntuacioPenalTempsIncorrecte;
    private JTextField textFieldPreguntes;
    private int numJugador = 0;
    
    //Model de dades de la taula
    private DefaultTableModel taulaUsuaris;

    //Model de dades de la taula jugadors
    static DefaultTableModel taulaResultats;

    //Fitxer d'accés directe a usuaris
    static DirectAccessFile<Usuari> dafUs;

    //Variable per a guardar el nombre de preguntes
    public static int numPreguntes;

    // Variable per a guardar la puntuació
    public static int puntuacioGuanyar;

    // Variable per a la categoria
    public static String categoria;
    static Configuracio configuracio;


    /**
     * Constructor de la finestra de selecció de jugadors
     * @throws IOException Si hi ha algun error d'entrada/sortida
     * @throws ClassNotFoundException Si no es troba la classe
     */
    public FinestraJugadors() throws IOException, ClassNotFoundException {
        //Per poder visualitzar la finestra farem...
        this.setTitle("Configuració de la partida");
        this.setContentPane(panelJ);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setSize(1200,600);

        //Altres parametres
        this.setLocationRelativeTo(null);

        //Instanciem el fitxer
        dafUs = new DirectAccessFile<>("src/usuaris.dat");

        //Carreguem les dades del fitxer al model de dades de la taula
        Object[][] dadesUsuaris=new Object[dafUs.size()][2];
        for (int i = 0; i < dafUs.size(); i++) {
            Usuari u = dafUs.readObject(i);
            dadesUsuaris[i][0]=u.getNom();
            dadesUsuaris[i][1]= u.getPuntuacioTotal();
        }

        Object[][] dadesJugadors=new Object[0][2];

        //Anem a establir el model de dades de la taula
        taulaUsuaris =new DefaultTableModel(
                //Dades a mostrar
                dadesUsuaris,
                //Definim les columnes de la taula
                new Object[]{"Nom","Puntuació Total"}
        );

        //Assignem el model de la taula
        tableUsuaris.setModel(taulaUsuaris);

        //Poso el model de selecció de la taula de manera que només es pugui seleccionar una fila
        tableUsuaris.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Anem a establir el model de dades de la taula jugadors
        taulaResultats = new DefaultTableModel(
                //Dades a mostrar
                dadesJugadors,
                //Definim les columnes de la taula
                new Object[]{"Nom","Puntuació"}
        );

        //Assignem el model de la taula
        tableJugadors.setModel(taulaResultats);

        //Poso el model de selecció de la taula de manera que només es pugui seleccionar una fila
        tableJugadors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Inicialitzem els spinners
        spinnerPreguntes.setValue(15);
        spinnerPuntuacio.setValue(15);

        // Inicialitzem el combo box
        comboBoxCategoria.addItem("Videojocs");
        comboBoxCategoria.addItem("Cinema");
        comboBoxCategoria.addItem("Història");
        comboBoxCategoria.addItem("Ciència");
        comboBoxCategoria.addItem("Geografia");
        comboBoxCategoria.addItem("Cultura");


        this.addWindowListener(new WindowAdapter() {
            /**
             * Accio per a tancar l'aplicacio
             * @param e Event de finestra
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    dafUs.close();
                    System.out.println("Tancant el programa");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(e.getComponent(),"Hi ha hagut algun error d'I/O al tancar el programa");
                }
            }
        });

        botoAfegirUsuari.addActionListener(new ActionListener() {
            /**
             * Accions del boto per a afegir un usuari a la llista de jugadors
             * @param e Event de boto
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Bucle de comprovació per a veure si el nom ja esta a la llista de jugadors¡
                for (int i = 0; i < taulaUsuaris.getRowCount(); i++) {
                    if (campNom.getText().strip().equals(taulaUsuaris.getValueAt(i, 0))) {
                        JOptionPane.showMessageDialog((Component)e.getSource(),"Aquest usuari ja esta a la llista de jugadors");
                    }
                }

                // Condició per a afegir o no un usuari a la llista de jugadors
                if (!campNom.getText().isBlank()) {
                    try {
                        dafUs.writeObject(new Usuari(campNom.getText().strip(),0));
                        taulaUsuaris.addRow(new Object[]{campNom.getText().strip(), 0});
                    } catch (IOException Exception) {
                        JOptionPane.showMessageDialog((Component)e.getSource(),"Hi ha algun error a l'insertar!!") ;
                    }
                }
                else JOptionPane.showMessageDialog((Component)e.getSource(),"No pots insertar un text en blank burro");
            }
        });

        /**
         *
         */
        iniciarJoc.addActionListener(new ActionListener() {
            /**
             * Accions del botó per a iniciar el joc amb la configuració seleccionada
             * @param e Event de boto
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numJugador < FinestraInicial.numJugadors) {
                    JOptionPane.showMessageDialog((Component)e.getSource(),"Has d'afegir tots els jugadors abans de començar el joc");
                }
                else {

                    try {
                        // Guardem les dades de la configuració de la partida
                        categoria = (String) comboBoxCategoria.getSelectedItem();
                        configuracio = new Configuracio((int) spinnerPuntuacio.getValue(),sliderPuntuacioCorrecta.getValue(), sliderTempsMaxim.getValue(), sliderPuntuacioPenalTempsIncorrecte.getValue(), (int) spinnerPreguntes.getValue());

                        // Guardem la configuració de la partida
                        GestorConfiguracio gestorConfiguracio = new GestorConfiguracio();
                        gestorConfiguracio.guardarConfiguracio(configuracio);

                        // Carregar preguntes usant metode de classe BancPreguntes
                        importarJsonToDat();
                        exportarDatToJSON(categoria);
                        carregarJsonFiltrades();

                        setVisible(false);
                        new FinestraJoc();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        tableUsuaris.addMouseListener(new MouseAdapter() {
            /**
             * Accio amb doble click per a afegir un usuari a la llista de jugadors
             * @param e Event de taula
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    int filaSel = tableUsuaris.getSelectedRow();
                    if (filaSel != -1) {
                        if (numJugador+1 > FinestraInicial.numJugadors) {
                            JOptionPane.showMessageDialog(null, "Ja has afegit tots els jugadors");
                        }
                        else {
                            String nom = (String) taulaUsuaris.getValueAt(filaSel, 0);
                            int puntuacio = 0;
                            taulaResultats.addRow(new Object[]{nom, puntuacio});
                            numJugador++;
                            taulaUsuaris.removeRow(filaSel);
                        }
                    }
                }
            }
        });

        tableJugadors.addMouseListener(new MouseAdapter() {
            /**
             * Accio amb doble click per a eliminar un usuari de la llista de jugadors
             * @param e Event de taula
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    int filaSel = tableJugadors.getSelectedRow();
                    if (filaSel != -1) {
                        String nom = (String) taulaResultats.getValueAt(filaSel, 0);
                        // Han de recuperar la puntuació total de l'usuari recorrent el fitxer i si coincideix amb el nom
                        // de l'usuari seleccionat, s'ha de posar la puntuació total de l'usuari
                        int puntuacio = 0;
                        for (int i = 0; i < dafUs.size(); i++) {
                            Usuari u = null;
                            try {
                                u = dafUs.readObject(i);
                                if (u.getNom().equals(nom)) {
                                    puntuacio = u.getPuntuacioTotal();
                                    taulaUsuaris.addRow(new Object[]{nom, puntuacio});
                                    numJugador--;
                                    taulaResultats.removeRow(filaSel);
                                    break;
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        });


        spinnerPreguntes.addChangeListener(new ChangeListener() {
            /**
             * Listener del spinner per a controlar el nombre de preguntes
             * @param e Event de spinner
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spinnerPreguntes.getValue() > 20) {
                    spinnerPreguntes.setValue(20);
                    JOptionPane.showMessageDialog(null, "El nombre de preguntes no pot ser superior a 30");
                } else if ((int) spinnerPreguntes.getValue() < 10) {
                    spinnerPreguntes.setValue(10);
                    JOptionPane.showMessageDialog(null, "El nombre de preguntes no pot ser inferior a 15");
                }
            }
        });

        spinnerPuntuacio.addChangeListener(new ChangeListener() {
            /**
             * Listener del spinner per a controlar la puntuació
             * @param e Event de spinner
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) spinnerPuntuacio.getValue() > 30) {
                    spinnerPuntuacio.setValue(30);
                    JOptionPane.showMessageDialog(null, "La puntuació total no pot ser superior a 30");
                } else if ((int) spinnerPuntuacio.getValue() < 15) {
                    spinnerPuntuacio.setValue(15);
                    JOptionPane.showMessageDialog(null, "La puntuació total no pot ser inferior a 15");
                }
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            /**
             * Botó per a eliminar un usuari de la llista de jugadors
             * @param e Event de boto
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abans de res li demanar si esta segur de que vol eliminar l'usuari
                int filaSel = tableUsuaris.getSelectedRow();
                if (filaSel != -1) {
                    int confirmacio = JOptionPane.showConfirmDialog(null, "Estas segur de que vols eliminar l'usuari?");
                    if (confirmacio == JOptionPane.YES_OPTION) {
                        taulaUsuaris.removeRow(filaSel);
                        // Eliminar l'usuari del fitxer
                        try {
                            dafUs.deleteObject(filaSel);
                        } catch (IOException | ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Hi ha hagut un error al eliminar l'usuari");
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Selecciona un usuari per a eliminar");
                }
            }
        });
    }

    // Mètode main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FinestraJugadors fus;
                    fus = new FinestraJugadors();
                    fus.setSize(600,400);
                } catch (ClassNotFoundException | IOException e) {
                    JOptionPane.showMessageDialog(null, "Hi ha hagut algun error al programa");
                    System.exit(1);
                }
            }
        });
    }
}
