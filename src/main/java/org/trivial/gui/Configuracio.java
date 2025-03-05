package org.trivial.gui;

import java.io.Serializable;

/**
 * Classe que conté la configuració del joc.
 * Aquesta classe és serializable per poder guardar-la en un fitxer.
 */
public class Configuracio implements Serializable {
    static int puntuacioGuanyar;  // Punts necessaris per guanyar
    static int puntuacioPregunta; // Punts per cada resposta correcta
    static int tempsMaximResposta; // Segons màxims per respondre
    static int penalitzacioTemps; // Segons per perdre un punt si no es respon
    static int numPreguntes; // Número de preguntes per partida

    /**
     * Constructor per defecte.
     */
    public Configuracio() {
    }

    /**
     * Constructor amb paràmetres.
     * @param puntuacioGuanyar Punts necessaris per guanyar
     * @param puntuacioPregunta Punts per cada resposta correcta
     * @param tempsMaximResposta Segons màxims per respondre
     * @param penalitzacioTemps Punts per perdre si no es respon
     * @param numPreguntes Número de preguntes per partida
     */
    public Configuracio(int puntuacioGuanyar, int puntuacioPregunta, int tempsMaximResposta, int penalitzacioTemps, int numPreguntes) {
        Configuracio.puntuacioGuanyar = puntuacioGuanyar;
        Configuracio.puntuacioPregunta = puntuacioPregunta;
        Configuracio.tempsMaximResposta = tempsMaximResposta;
        Configuracio.penalitzacioTemps = penalitzacioTemps;
        Configuracio.numPreguntes = numPreguntes;
    }

    // Getters i setters
    public int getPuntuacioGuanyar() { return puntuacioGuanyar; }
    public void setPuntuacioGuanyar(int puntuacioGuanyar) { Configuracio.puntuacioGuanyar = puntuacioGuanyar; }

    public int getPuntuacioPregunta() { return puntuacioPregunta; }
    public void setPuntuacioPregunta(int puntuacioPregunta) { Configuracio.puntuacioPregunta = puntuacioPregunta; }

    public int getTempsMaximResposta() { return tempsMaximResposta; }
    public void setTempsMaximResposta(int tempsMaximResposta) { Configuracio.tempsMaximResposta = tempsMaximResposta; }

    public int getPenalitzacioTemps() { return penalitzacioTemps; }
    public void setPenalitzacioTemps(int penalitzacioTemps) { Configuracio.penalitzacioTemps = penalitzacioTemps; }

    public int getNumPreguntes() { return numPreguntes; }
    public void setNumPreguntes(int numPreguntes) { Configuracio.numPreguntes = numPreguntes; }
}
