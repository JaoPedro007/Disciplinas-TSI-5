package br.edu.utfpr.td.tsi.delegacia.persistencia;

import java.util.List;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;

public interface IBoletim {
	
	void registrar(BoletimFurtoVeiculo b);
	List<BoletimFurtoVeiculo> listarTodos();
	BoletimFurtoVeiculo listar();
	BoletimFurtoVeiculo editar(BoletimFurtoVeiculo b);
	void excluir(BoletimFurtoVeiculo b);
	
	
}
