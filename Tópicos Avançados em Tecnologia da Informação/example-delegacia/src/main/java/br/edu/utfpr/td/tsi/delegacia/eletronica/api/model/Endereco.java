package br.edu.utfpr.td.tsi.delegacia.eletronica.api.model;

public class Endereco {
   private String logradouro;
   private int numero;
   private String bairro;
   private String cidade;
   private String estado;

   public Endereco(String logradouro, int numero, String bairro, String cidade, String estado) {
      this.logradouro = logradouro;
      this.numero = numero;
      this.bairro = bairro;
      this.cidade = cidade;
      this.estado = estado;
   }

   public String getLogradouro() {
      return this.logradouro;
   }

   public void setLogradouro(String logradouro) {
      this.logradouro = logradouro;
   }

   public int getNumero() {
      return this.numero;
   }

   public void setNumero(int numero) {
      this.numero = numero;
   }

   public String getBairro() {
      return this.bairro;
   }

   public void setBairro(String bairro) {
      this.bairro = bairro;
   }

   public String getCidade() {
      return this.cidade;
   }

   public void setCidade(String cidade) {
      this.cidade = cidade;
   }

   public String getEstado() {
      return this.estado;
   }

   public void setEstado(String estado) {
      this.estado = estado;
   }

   public String toString() {
      return "Endereco [logradouro=" + this.logradouro + ", numero=" + this.numero + ", bairro=" + this.bairro + ", cidade=" + this.cidade + ", estado=" + this.estado + "]";
   }
}
