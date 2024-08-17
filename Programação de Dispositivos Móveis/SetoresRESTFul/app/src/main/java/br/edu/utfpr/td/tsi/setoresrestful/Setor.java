package br.edu.utfpr.td.tsi.setoresrestful;

import java.io.Serializable;
import java.util.Objects;

public class Setor implements Serializable {
    private int id;
    private String descricao;
    private double margem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getMargem() {
        return margem;
    }

    public void setMargem(double margem) {
        this.margem = margem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setor setor = (Setor) o;
        return id == setor.id && Double.compare(margem, setor.margem) == 0 && Objects.equals(descricao, setor.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, margem);
    }

    @Override
    public String toString() {
        return String.valueOf(id)+" - "+descricao+"\nMargem de lucro: " +margem;
    }
}
