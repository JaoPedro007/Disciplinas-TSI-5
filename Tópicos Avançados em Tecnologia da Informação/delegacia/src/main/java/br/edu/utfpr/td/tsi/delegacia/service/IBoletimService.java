package br.edu.utfpr.td.tsi.delegacia.service;


import java.util.List;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;

public interface IBoletimService {
	
	void registrar(BoletimFurtoVeiculo b);
	List<BoletimFurtoVeiculo> listarTodos();
	BoletimFurtoVeiculo listar();
	BoletimFurtoVeiculo editar(BoletimFurtoVeiculo b);
	void excluir(BoletimFurtoVeiculo b);

}
