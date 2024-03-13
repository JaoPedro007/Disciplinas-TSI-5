package com.example.petshop;

import java.io.Serializable;

public class Animal implements Serializable {

    private int tipo; // 0 - Cão, 1 - Gato, 2 - Passaro, 3 - Réptil
    private int idade; //meses
    private String raca;
    private String nome;
    private String nomeDono;
    private String telefone;

    public Animal(int tipo, int idade, String raca, String nome, String nomeDono, String telefone) {
        this.tipo = tipo;
        this.idade = idade;
        this.raca = raca;
        this.nome = nome;
        this.nomeDono = nomeDono;
        this.telefone = telefone;
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

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
