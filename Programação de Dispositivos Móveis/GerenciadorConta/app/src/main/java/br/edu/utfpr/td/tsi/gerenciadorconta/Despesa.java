package br.edu.utfpr.td.tsi.gerenciadorconta;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Despesa implements Serializable {

    public Despesa(String descricaoDespesa, double valor, Date vencimentoDespesa) {
        this.descricaoDespesa = descricaoDespesa;
        this.valor=valor;
        this.vencimentoDespesa=vencimentoDespesa;
    }

    private String descricaoDespesa;
    private double valor;
    private Date vencimentoDespesa;


    public Date getVencimentoDespesa() {
        return vencimentoDespesa;
    }

    public void setVencimentoDespesa(Date vencimentoDespesa) {
        this.vencimentoDespesa = vencimentoDespesa;
    }

    public String getDescricaoDespesa() {
        return descricaoDespesa;
    }

    public void setDescricaoDespesa(String descricaoDespesa) {
        this.descricaoDespesa = descricaoDespesa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
