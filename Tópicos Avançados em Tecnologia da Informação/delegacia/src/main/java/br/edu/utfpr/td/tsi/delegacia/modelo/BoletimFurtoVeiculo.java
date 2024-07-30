package br.edu.utfpr.td.tsi.delegacia.modelo;

import java.time.LocalDate;

public class BoletimFurtoVeiculo {
	
	private String identificador;
	private LocalDate dataOcorrencia;
	private int periodoOcorrencia;
	private Parte partes;
	private Endereco localOcorrencia;
	private Veiculo veiculoFurtado;
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public LocalDate getDataOcorrencia() {
		return dataOcorrencia;
	}
	public void setDataOcorrencia(LocalDate dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}
	public int getPeriodoOcorrencia() {
		return periodoOcorrencia;
	}
	public void setPeriodoOcorrencia(int periodoOcorrencia) {
		this.periodoOcorrencia = periodoOcorrencia;
	}
	public Veiculo getVeiculoFurtado() {
		return veiculoFurtado;
	}
	public void setVeiculoFurtado(Veiculo veiculoFurtado) {
		this.veiculoFurtado = veiculoFurtado;
	}
	public Endereco getLocalOcorrencia() {
		return localOcorrencia;
	}
	public void setLocalOcorrencia(Endereco localOcorrencia) {
		this.localOcorrencia = localOcorrencia;
	}
	public Parte getPartes() {
		return partes;
	}
	public void setPartes(Parte partes) {
		this.partes = partes;
	}
	
	
	
	
	

}
