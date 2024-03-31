package com.example.petshop;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Animal implements Serializable {
    public static final int CAO = 0;
    public static final int GATO = 1;
    public static final int PASSARO = 2;
    public static final int REPTIL = 3;
    public static final int PEIXE = 4;

    private int tipo;
    private int idade; // meses
    private String nome;
    private String raca;
    private String nomeDono;
    private String telefone;
    private List<Atendimento> atendimentos = new LinkedList<>();

    public Animal(int tipo, int idade, String nome, String raca,
                  String nomeDono, String telefone) {
        this.tipo = tipo;
        this.idade = idade;
        this.nome = nome;
        this.raca = raca;
        this.nomeDono = nomeDono;
        this.telefone = telefone;
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(List<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public void adicionarAtendimento( Atendimento at ) {
        atendimentos.add( at );
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
