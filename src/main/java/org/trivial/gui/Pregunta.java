package org.trivial.gui;

import java.io.Serializable;
import java.util.List;

/**
 * Classe que representa una pregunta amb el seu enunciat, opcions, resposta correcta i categoria.
 */
public class Pregunta implements Serializable {
    private String enunciat;
    private List<String> opcions;
    private String respostaCorrecta;
    private String categoria;

    // Constructor per defecte
    public Pregunta() {
    }

    /**
     * Constructor amb paràmetres.
     * @param enunciat és el enunciat de la pregunta.
     * @param opcions són les opcions de resposta.
     * @param respostaCorrecta és la resposta correcta.
     * @param categoria és la categoria de la pregunta.
     */
    public Pregunta(String enunciat, List<String> opcions, String respostaCorrecta, String categoria) {
        this.enunciat = enunciat;
        this.opcions = opcions;
        this.respostaCorrecta = respostaCorrecta;
        this.categoria = categoria;
    }

    // Getters i setters
    public String getEnunciat() {
        return enunciat;
    }

    public void setEnunciat(String enunciat) {
        this.enunciat = enunciat;
    }

    public List<String> getOpcions() {
        return opcions;
    }

    public void setOpcions(List<String> opcions) {
        this.opcions = opcions;
    }

    public String getRespostaCorrecta() {
        return respostaCorrecta;
    }

    public void setRespostaCorrecta(String respostaCorrecta) {
        this.respostaCorrecta = respostaCorrecta;
    }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }
}