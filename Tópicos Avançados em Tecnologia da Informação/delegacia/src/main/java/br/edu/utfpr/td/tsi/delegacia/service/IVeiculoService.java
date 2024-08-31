package br.edu.utfpr.td.tsi.delegacia.service;

import java.util.List;

import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;


public interface IVeiculoService {
	
	   List<Veiculo> listarTodos();
	   List<Veiculo> listarVeiculosComFiltros(String placa, String cor, String tipo, int page, int size);
	   void excluir(Veiculo v);
	   void registrarFurto(Veiculo veiculo);
}
