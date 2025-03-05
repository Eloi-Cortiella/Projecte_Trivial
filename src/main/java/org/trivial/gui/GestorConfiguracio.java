package org.trivial.gui;

import com.iesebre.usefulcode.DirectAccessFile;

import java.io.*;

public class GestorConfiguracio {
    // Crear fitxer binari amb directaccessfile
    static DirectAccessFile<Configuracio> dafConfig;

    /**
     * Guarda la configuració en un fitxer binari
     * @param configuracio Configuració a guardar
     */
    public void guardarConfiguracio(Configuracio configuracio) {
        try {
            dafConfig = new DirectAccessFile<>("src/configuracio.dat");
            dafConfig.writeObject(configuracio);
            System.out.println("Configuració guardada correctament.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
