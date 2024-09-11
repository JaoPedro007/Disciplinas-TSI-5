package com.example.agendaservicos.modelo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Servico implements Serializable {

    public Servico(){

    }

    public Servico(String descricao, String unidade, double preco){
        this.descricao = descricao;
        this.unidadeMedida = unidade;
        this.valor = preco;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String descricao;

    @ColumnInfo(name = "unidade_medida")
    private String unidadeMedida;

    private double valor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servico)) return false;
        Servico servico = (Servico) o;
        return id == servico.id;
    }

    @Override
    public String toString() {
        return  descricao + " - "
                + unidadeMedida + " - R$ "
                + String.format("%.2f", valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
