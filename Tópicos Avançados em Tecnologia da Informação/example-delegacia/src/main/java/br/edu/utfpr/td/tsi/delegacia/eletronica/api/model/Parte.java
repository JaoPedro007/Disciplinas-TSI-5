package br.edu.utfpr.td.tsi.delegacia.eletronica.api.model;

public class Parte {
   private String nome;
   private String email;
   private String tipoEnvolvimento;
   private String telefoneContato;

   public String getNome() {
      return this.nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getTipoEnvolvimento() {
      return this.tipoEnvolvimento;
   }

   public void setTipoEnvolvimento(String tipoEnvolvimento) {
      this.tipoEnvolvimento = tipoEnvolvimento;
   }

   public String getTelefoneContato() {
      return this.telefoneContato;
   }

   public void setTelefoneContato(String telefoneContato) {
      this.telefoneContato = telefoneContato;
   }
}
