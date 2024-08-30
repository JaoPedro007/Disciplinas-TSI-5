package br.edu.utfpr.td.tsi.delegacia.eletronica.api.model;

public class Veiculo {
   private Emplacamento emplacamento;
   private int anoFabricacao;
   private String cor;
   private String marca;
   private String tipoVeiculo;
   private BoletimFurtoVeiculo envolvidoEm;

   public void setEnvolvidoEm(BoletimFurtoVeiculo envolvidoEm) {
      this.envolvidoEm = envolvidoEm;
   }

   public BoletimFurtoVeiculo getEnvolvidoEm() {
      return this.envolvidoEm;
   }

   public Emplacamento getEmplacamento() {
      return this.emplacamento;
   }

   public void setEmplacamento(Emplacamento emplacamento) {
      this.emplacamento = emplacamento;
   }

   public int getAnoFabricacao() {
      return this.anoFabricacao;
   }

   public void setAnoFabricacao(int anoFabricacao) {
      this.anoFabricacao = anoFabricacao;
   }

   public String getCor() {
      return this.cor;
   }

   public void setCor(String cor) {
      this.cor = cor;
   }

   public String getMarca() {
      return this.marca;
   }

   public void setMarca(String marca) {
      this.marca = marca;
   }

   public String getTipoVeiculo() {
      return this.tipoVeiculo;
   }

   public void setTipoVeiculo(String tipoVeiculo) {
      this.tipoVeiculo = tipoVeiculo;
   }
}
