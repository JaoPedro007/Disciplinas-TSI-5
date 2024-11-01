package br.edu.utfpr.td.tsi.delegacia.modelo;

import java.time.LocalDate;
import java.util.UUID;

public class BoletimFurtoVeiculo {

	
	private String identificador;
	private LocalDate dataOcorrencia;
	private String periodoOcorrencia;
	private Parte partes;
	private Endereco localOcorrencia;
	private Veiculo veiculoFurtado;
	
	public BoletimFurtoVeiculo() {
		this.identificador = UUID.randomUUID().toString();
	}
	

	public BoletimFurtoVeiculo(LocalDate dataOcorrencia, String periodoOcorrencia, Parte partes,
			Endereco localOcorrencia, Veiculo veiculoFurtado) {
		super();
		this.dataOcorrencia = dataOcorrencia;
		this.periodoOcorrencia = periodoOcorrencia;
		this.partes = partes;
		this.localOcorrencia = localOcorrencia;
		this.veiculoFurtado = veiculoFurtado;
	}

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

	public String getPeriodoOcorrencia() {
		return periodoOcorrencia;
	}

	public void setPeriodoOcorrencia(String periodoOcorrencia) {
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
