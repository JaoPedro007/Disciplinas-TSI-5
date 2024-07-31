package br.edu.utfpr.td.tsi.gerenciadorconta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Categoria implements Serializable {
    private String descricao;
    private ArrayList<Conta> contas;
    private double valorTotal;
    private int quantidadeContas=0;


    public Categoria(String descricao) {
        this.descricao = descricao;
        this.contas = new ArrayList<>();
        this.valorTotal = 0.00;
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

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public double getValorTotal() {
        return valorTotal;
    }
    public int getQuantidadeContas(){
        return quantidadeContas = contas.size();
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
