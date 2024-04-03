package br.edu.utfpr.td.tsi.gerenciadorconta;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Categoria implements Serializable {

    private String descricao;
    private List<Conta> contas = new LinkedList<>();

    public Categoria(String descricao) {
        this.descricao=descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    public void adicionarConta(Conta conta){
        contas.add(conta);
    }

}
