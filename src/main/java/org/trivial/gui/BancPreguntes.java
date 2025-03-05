package org.trivial.gui;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iesebre.usefulcode.DirectAccessFile;

public class BancPreguntes {
    // Crear fitxer binari amb directaccessfile
    static DirectAccessFile<Pregunta> dafPre;
    static {
        try {
            dafPre = new DirectAccessFile<>("src/preguntes.dat");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper objectMapper = new ObjectMapper();
    static List<Pregunta> preguntes = new ArrayList<>();
    private static List<Pregunta> preguntesFiltrades = new ArrayList<>();


    /**
     * Guarda les preguntes en un fitxer binari
      * @param preguntesBin llista de preguntes a guardar
     */
    static void guardarBinari(List<Pregunta> preguntesBin) {
        try {
            for (Pregunta p : preguntesBin) {
                dafPre.writeObject(p);
            }
            System.out.println("Preguntes guardades correctament.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error en guardar les preguntes", e);
        }
    }

    /**
     * Carrega les preguntes del fitxer binari
     */
    static void carregarJsonFiltrades() {  // Parametre un fitxer json
        // Llegir el fitxer json i guardar les preguntes en una llista
        try {
            preguntes = objectMapper.readValue(Paths.get("src/preguntesFiltrades.json").toFile(), new TypeReference<List<Pregunta>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Barrejar les preguntes
        Collections.shuffle(preguntes);
    }

    /**
     * Carrega les preguntes del fitxer binari
     * @throws IOException si hi ha algun problema en llegir el fitxer
     */
    static void importarJsonToDat() throws IOException {
        // Comprovar si ja existeix el fitxers de dades de preguntes
        if (Files.size(Paths.get(dafPre.getName())) > 0) {
            System.out.println("Ja hi ha preguntes guardades.");
            return;
        }
        List<Pregunta> preguntesImportades = objectMapper.readValue(Paths.get("src/preguntes.json").toFile(), new TypeReference<List<Pregunta>>() {});
        guardarBinari(preguntesImportades);
    }

    /**
     * Exporta les preguntes del fitxer binari a un fitxer json
     * @param categoria categoria de les preguntes a exportar
     */
    static void exportarDatToJSON(String categoria) {
        try {
            // Llegim el dafPre amb un bucle i guardem les preguntes en una llista
            for (int i = 0; i < dafPre.size(); i++) {
                // Comprovar si la categoria de la pregunta coincideix amb la categoria passada per paràmetre
                if (dafPre.readObject(i).getCategoria().equals(categoria)) {
                    preguntesFiltrades.add(dafPre.readObject(i));
                }
            }
            // Transformar la llista de preguntes filtrades a un fitxer json
            objectMapper.writeValue(Paths.get("src/preguntesFiltrades.json").toFile(), preguntesFiltrades);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

   /**
     * Buida el fitxer JSON de preguntes filtrades
     */
    static void buidarPreguntesFiltrades() {
        try {
            Files.deleteIfExists(Paths.get("src/preguntesFiltrades.json"));
            preguntesFiltrades.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
