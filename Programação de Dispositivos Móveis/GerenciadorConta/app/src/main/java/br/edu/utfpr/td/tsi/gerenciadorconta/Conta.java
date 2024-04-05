package br.edu.utfpr.td.tsi.gerenciadorconta;

import java.io.Serializable;
import java.util.Date;

public class Conta implements Serializable {

    public Conta(String descricaoConta, double valorConta, Date vencimentoConta, Categoria categoria) {
        this.descricaoConta = descricaoConta;
        this.valorConta = valorConta;
        this.vencimentoConta = vencimentoConta;
        this.categoria = categoria;
    }
    private Categoria categoria;
    private String descricaoConta;
    private double valorConta;
    private Date vencimentoConta;


    public String getDescricaoConta() {
        return descricaoConta;
    }

    public void setDescricaoConta(String descricaoConta) {
        this.descricaoConta = descricaoConta;
    }

    public double getValorConta() {
        return valorConta;
    }

    public void setValorConta(double valorConta) {
        this.valorConta = valorConta;
    }

    public Date getVencimentoConta() {
        return vencimentoConta;
    }

    public void setVencimentoConta(Date vencimentoConta) {
        this.vencimentoConta = vencimentoConta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
