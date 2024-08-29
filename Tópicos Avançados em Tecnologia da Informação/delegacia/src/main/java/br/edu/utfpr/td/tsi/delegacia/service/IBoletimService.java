package br.edu.utfpr.td.tsi.delegacia.service;


import java.io.IOException;
import java.util.List;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;

public interface IBoletimService {
	
	void registrar(BoletimFurtoVeiculo b);
	List<BoletimFurtoVeiculo> listarTodos();
	BoletimFurtoVeiculo listar(String id);
	BoletimFurtoVeiculo editar(BoletimFurtoVeiculo b);
	boolean excluir(String id);
    List<BoletimFurtoVeiculo> listarComFiltros(String identificador, String cidade, String periodo);
    List<Veiculo> listarVeiculosComFiltros(String placa, String cor, String tipo);
    void carregarBoletinsDeCSV(String caminhoArquivoCSV) throws IOException;

}