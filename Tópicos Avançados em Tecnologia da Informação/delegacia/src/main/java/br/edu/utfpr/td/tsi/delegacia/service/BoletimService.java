package br.edu.utfpr.td.tsi.delegacia.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.persistencia.IBoletimRepository;

@Component
public class BoletimService implements IBoletimService {

	private static final Logger logger = Logger.getLogger(BoletimService.class.getName());

	@Autowired
	IBoletimRepository boletim;

	@Override
	public void registrar(BoletimFurtoVeiculo b) {
		if (!boletimValido(b)) {
			throw new IllegalArgumentException("Boletim inválido: Campos obrigatórios estão ausentes.");

		}
		try {
			boletim.registrar(b);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Ocorreu um erro ao registrar o boletim: ", e);
			throw e;
		}

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

	private boolean boletimValido(BoletimFurtoVeiculo b) {
		return b != null && b.getDataOcorrencia() != null && b.getLocalOcorrencia() != null && b.getPartes() != null
				&& b.getVeiculoFurtado() != null && b.getPeriodoOcorrencia() != -1;
	}

}
