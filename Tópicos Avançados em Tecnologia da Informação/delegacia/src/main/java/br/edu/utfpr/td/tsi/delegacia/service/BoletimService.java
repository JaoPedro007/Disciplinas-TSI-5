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
	IBoletimRepository boletimRepository;
	
    @Autowired
    IVeiculoService veiculoService;

	@Override
	public void registrar(BoletimFurtoVeiculo b) {
		if (!boletimValido(b)) {
			throw new IllegalArgumentException("Boletim inválido: Campos obrigatórios estão ausentes.");

		}
		
		try {
			boletimRepository.registrar(b);
			
            if (b.getVeiculoFurtado() != null) {
                veiculoService.registrarFurto(b.getVeiculoFurtado());
            }
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Ocorreu um erro ao registrar o boletim: ", e);
			throw e;
		}

	}

	@Override
	public List<BoletimFurtoVeiculo> listarTodos() {
		return boletimRepository.listarTodos();
	}


	@Override
	public BoletimFurtoVeiculo listar(String id) {
		return boletimRepository.listar(id);
	}

	@Override
	public BoletimFurtoVeiculo editar(BoletimFurtoVeiculo b) {
		return boletimRepository.editar(b);

	}

	@Override
	public boolean excluir(String id) {
		return boletimRepository.excluir(id);
	}

	private boolean boletimValido(BoletimFurtoVeiculo b) {
		return b != null && b.getDataOcorrencia() != null && b.getLocalOcorrencia() != null && b.getPartes() != null
				&& b.getVeiculoFurtado() != null && b.getPeriodoOcorrencia() !=null;
	}

	@Override
	public List<BoletimFurtoVeiculo> listarComFiltros(String identificador, String cidade, String periodo, int pageNumber, int pageSize) {
		return boletimRepository.listarComFiltros(identificador, cidade, periodo, pageNumber, pageSize);
	}
	
    

}
