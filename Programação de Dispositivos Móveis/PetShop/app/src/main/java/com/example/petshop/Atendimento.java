package com.example.petshop;

import java.io.Serializable;
import java.util.Date;

public class Atendimento implements Serializable {
    private Date dataHora;
    private String nomeVeterinario;
    private String registro;

    public Atendimento() {
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getNomeVeterinario() {
        return nomeVeterinario;
    }

    public void setNomeVeterinario(String nomeVeterinario) {
        this.nomeVeterinario = nomeVeterinario;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
}
