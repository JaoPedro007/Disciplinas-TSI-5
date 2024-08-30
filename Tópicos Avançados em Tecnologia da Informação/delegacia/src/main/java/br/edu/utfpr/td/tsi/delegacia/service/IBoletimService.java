package br.edu.utfpr.td.tsi.delegacia.service;


import java.util.List;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;

public interface IBoletimService {
	
	void registrar(BoletimFurtoVeiculo b);
	List<BoletimFurtoVeiculo> listarTodos();
	BoletimFurtoVeiculo listar(String id);
	BoletimFurtoVeiculo editar(BoletimFurtoVeiculo b);
	boolean excluir(String id);
	List<BoletimFurtoVeiculo> listarComFiltros(String identificador, String cidade, String periodo, int pageNumber, int pageSize);
}