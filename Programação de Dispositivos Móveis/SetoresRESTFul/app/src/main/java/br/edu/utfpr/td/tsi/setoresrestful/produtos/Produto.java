package br.edu.utfpr.td.tsi.setoresrestful.produtos;

import java.io.Serializable;
import java.util.Objects;

import br.edu.utfpr.td.tsi.setoresrestful.Setor;

public class Produto implements Serializable {
    private int id;
    private String descricao;
    private float estoque;
    private double preco;
    private Setor setor;

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

    public float getEstoque() {
        return estoque;
    }

    public void setEstoque(float estoque) {
        this.estoque = estoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public int getSetorId() {
        return setor != null ? setor.getId() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id &&
                Float.compare(produto.estoque, estoque) == 0 &&
                Double.compare(produto.preco, preco) == 0 &&
                Objects.equals(descricao, produto.descricao) &&
                Objects.equals(setor, produto.setor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, estoque, preco, setor);
    }

    @Override
    public String toString() {
        return String.valueOf(id) + " - "
                + descricao +
                "\n Estoque: "+  + estoque +
                " - R$ " + preco +
                "\n Setor: " + (setor != null ? setor.getDescricao() : "Sem setor");
    }

}
