package br.edu.utfpr.td.tsi.delegacia.modelo;


public class Veiculo {
	
	private String anoFabricacao;
	private String cor;
	private String marca;
	private String tipo;
	private Emplacamento emplacamento;
	
	public String getAnoFabricacao() {
		return anoFabricacao;
	}
	public void setAnoFabricacao(String anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	

}
