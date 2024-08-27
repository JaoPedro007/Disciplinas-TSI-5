package br.edu.utfpr.td.tsi.delegacia.modelo;

import java.time.LocalDate;

public class Veiculo {
	
	private LocalDate anoFabricacao;
	private String cor;
	private String marca;
	private String modelo;
	private Emplacamento emplacamento;
	
	public LocalDate getAnoFabricacao() {
		return anoFabricacao;
	}
	public void setAnoFabricacao(LocalDate anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	
	public int getAnoFabricacaoAno() {
		return anoFabricacao != null ? anoFabricacao.getYear() : 0;
	}

	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Emplacamento getEmplacamento() {
		return emplacamento;
	}
	public void setEmplacamento(Emplacamento emplacamento) {
		this.emplacamento = emplacamento;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	

}
