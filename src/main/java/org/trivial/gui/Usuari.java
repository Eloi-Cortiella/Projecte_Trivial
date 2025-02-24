package org.trivial.gui;

import java.io.Serializable;

public class Usuari implements Serializable {
    private String Nom;
    private int NumeroJugador;
    private int PuntuacioTotal;

    public Usuari(String Nom, int Numero, int PuntuacioTotal) {
        this.Nom = Nom;
        this.NumeroJugador=Numero;
        this.PuntuacioTotal=PuntuacioTotal;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public int getNumeroJugador() {
        return NumeroJugador;
    }

    public void setNumeroJugador(int numeroJugador) {
        NumeroJugador = numeroJugador;
    }

    public int getPuntuacioTotal() {
        return PuntuacioTotal;
    }

    public void setPuntuacioTotal(int puntuacioTotal) {
        PuntuacioTotal = puntuacioTotal;
    }
}
