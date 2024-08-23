package br.edu.utfpr.td.tsi.delegacia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.persistencia.IBoletim;

@Component
public class BoletimService implements IBoletimService{
	
	@Autowired
	IBoletim boletim;

	@Override
	public void registrar(BoletimFurtoVeiculo b) {
		boletim.registrar(b);
	}

	@Override
	public List<BoletimFurtoVeiculo> listarTodos() {
		return boletim.listarTodos();
	}

	@Override
	public BoletimFurtoVeiculo listar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoletimFurtoVeiculo editar(BoletimFurtoVeiculo b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(BoletimFurtoVeiculo b) {
		// TODO Auto-generated method stub
		
	}

}
