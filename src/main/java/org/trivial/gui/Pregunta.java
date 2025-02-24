package org.trivial.gui;

import java.util.List;

public class Pregunta {
    private String enunciat;
    private List<String> opcions;
    private int respostaCorrecta;

    // Default constructor
    public Pregunta() {
    }

    // Parameterized constructor
    public Pregunta(String enunciat, List<String> opcions, int respostaCorrecta) {
        this.enunciat = enunciat;
        this.opcions = opcions;
        this.respostaCorrecta = respostaCorrecta;
    }

    // Getters and setters
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

    public int getRespostaCorrecta() {
        return respostaCorrecta;
    }

    public void setRespostaCorrecta(int respostaCorrecta) {
        this.respostaCorrecta = respostaCorrecta;
    }
}