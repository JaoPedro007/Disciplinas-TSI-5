package br.edu.utfpr.td.tsi.gerenciadorconta;

import java.io.Serializable;

public class Categoria implements Serializable {

    private String descricao;

    public Categoria(String descricao) {
        this.descricao=descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
