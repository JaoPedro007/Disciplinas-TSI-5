package br.edu.utfpr.td.tsi.gerenciadorconta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Categoria implements Serializable {

    private String descricao;
    private double valorTotalContas=0.00;



    private List<Conta> contas;

    public Categoria(String descricao) {
        this.contas=new ArrayList<>();
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
    public double getValorTotalContas() {
        return valorTotalContas;
    }

    public void setValorTotalContas(double valorTotalContas) {
        this.valorTotalContas = valorTotalContas;
    }
}
