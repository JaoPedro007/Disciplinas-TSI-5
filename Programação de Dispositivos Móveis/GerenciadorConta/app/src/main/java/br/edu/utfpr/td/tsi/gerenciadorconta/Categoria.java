package br.edu.utfpr.td.tsi.gerenciadorconta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Categoria implements Serializable {
    double valorTotal=0.00;

    private String descricao;

    private List<Conta> contas = new ArrayList<>();

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
        valorTotal += conta.getValorConta();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }


}
