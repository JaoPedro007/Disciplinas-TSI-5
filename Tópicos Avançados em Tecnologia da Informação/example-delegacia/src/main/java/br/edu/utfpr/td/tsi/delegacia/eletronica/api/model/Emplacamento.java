package br.edu.utfpr.td.tsi.delegacia.eletronica.api.model;

public class Emplacamento {
   private String placa;
   private String estado;
   private String cidade;

   public Emplacamento(String placa, String estado, String cidade) {
      this.placa = placa;
      this.estado = estado;
      this.cidade = cidade;
   }

   public String getPlaca() {
      return this.placa;
   }

   public void setPlaca(String placa) {
      this.placa = placa;
   }

   public String getEstado() {
      return this.estado;
   }

   public void setEstado(String estado) {
      this.estado = estado;
   }

   public String getCidade() {
      return this.cidade;
   }

   public void setCidade(String cidade) {
      this.cidade = cidade;
   }

   public String toString() {
      return "Emplacamento [placa=" + this.placa + ", estado=" + this.estado + ", cidade=" + this.cidade + "]";
   }
}
