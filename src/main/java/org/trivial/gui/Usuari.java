package org.trivial.gui;

import java.io.Serializable;

/**
 * Classe que representa un usuari amb el seu nom i puntuació total.
 */
public class Usuari implements Serializable {
    private String nom;
    private int puntuacioTotal;

    // Constructor per defecte
    public Usuari(String nom, int puntuacioTotal) {
        this.nom = nom;
        this.puntuacioTotal = puntuacioTotal;
    }

    // Getters i setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPuntuacioTotal() {
        return puntuacioTotal;
    }

    public void setPuntuacioTotal(int puntuacioTotal) {
        this.puntuacioTotal = puntuacioTotal;
    }
}
