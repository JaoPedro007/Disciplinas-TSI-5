package br.edu.utfpr.td.tsi.delegacia.persistencia;

import java.util.List;

import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;

public interface IVeiculoRepository {
	
	   List<Veiculo> listarTodos();
	   List<Veiculo> listarVeiculosComFiltros(String placa, String cor, String tipo, int page, int size);
	   void registrarFurto(Veiculo veiculo);
	   void excluir(Veiculo v);
}
