package br.edu.utfpr.td.tsi.delegacia.eletronica.api.model;

import java.time.LocalDate;
import java.util.List;

public class BoletimFurtoVeiculo {
   private final String crime = "Furto (art. 155) - VEICULO";
   private String identificador;
   private LocalDate dataOcorrencia;
   private String periodoOcorrencia;
   private List<Parte> partes;
   private Endereco localOcorrencia;
   private Veiculo veiculoFurtado;

   public BoletimFurtoVeiculo() {
   }

   public BoletimFurtoVeiculo(String identificador) {
      this.identificador = identificador;
   }

   public void setIdentificador(String identificador) {
      this.identificador = identificador;
   }

   public String getIdentificador() {
      return this.identificador;
   }

   public void setVeiculoFurtado(Veiculo veiculoFurtado) {
      this.veiculoFurtado = veiculoFurtado;
   }

   public String getCrime() {
      return "Furto (art. 155) - VEICULO";
   }

   public Veiculo getVeiculoFurtado() {
      return this.veiculoFurtado;
   }

   public void setLocalOcorrencia(Endereco localOcorrencia) {
      this.localOcorrencia = localOcorrencia;
   }

   public Endereco getLocalOcorrencia() {
      return this.localOcorrencia;
   }

   public LocalDate getDataOcorrencia() {
      return this.dataOcorrencia;
   }

   public void setDataOcorrencia(LocalDate dataOcorrencia) {
      this.dataOcorrencia = dataOcorrencia;
   }

   public String getPeriodoOcorrencia() {
      return this.periodoOcorrencia;
   }

   public void setPeriodoOcorrencia(String periodoOcorrencia) {
      this.periodoOcorrencia = periodoOcorrencia;
   }

   public List<Parte> getPartes() {
      return this.partes;
   }

   public void setPartes(List<Parte> partes) {
      this.partes = partes;
   }
}
