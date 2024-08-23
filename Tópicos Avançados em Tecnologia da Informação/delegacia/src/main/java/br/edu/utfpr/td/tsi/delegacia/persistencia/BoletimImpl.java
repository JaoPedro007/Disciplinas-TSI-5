package br.edu.utfpr.td.tsi.delegacia.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;

@Component
public class BoletimImpl implements IBoletim{
	
	List<BoletimFurtoVeiculo> bd = new ArrayList<BoletimFurtoVeiculo>();

	@Override
	public void registrar(BoletimFurtoVeiculo b) {
			bd.add(b);
	}

	@Override
	public List<BoletimFurtoVeiculo> listarTodos() {
		BoletimFurtoVeiculo b = new BoletimFurtoVeiculo("ID:1", 1);
		bd.add(b);
		return bd;
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
