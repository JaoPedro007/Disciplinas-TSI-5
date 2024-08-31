package br.edu.utfpr.td.tsi.delegacia.service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.modelo.Emplacamento;
import br.edu.utfpr.td.tsi.delegacia.modelo.Endereco;
import br.edu.utfpr.td.tsi.delegacia.modelo.Parte;
import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;
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
			throw new IllegalArgumentException("Campos obrigatórios estão ausentes.");
		}
		
	    if (b.getVeiculoFurtado() == null) {
	        throw new IllegalArgumentException("Não foi possível registrar o veículo do furto.");
	    }
		
		try {
			boletimRepository.registrar(b);
			veiculoService.registrarFurto(b.getVeiculoFurtado());
         
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
	    if (!boletimValido(b)) {
	        throw new IllegalArgumentException("Campos obrigatórios estão ausentes ou incorretos.");
	    }

	    try {
	        return boletimRepository.editar(b);
	    } catch (Exception e) {
	        logger.log(Level.SEVERE, "Ocorreu um erro ao editar o boletim: ", e);
	        throw e;
	    }
	}


	@Override
	public boolean excluir(String id) {
		return boletimRepository.excluir(id);
	}
	
	
	@Override
	public List<BoletimFurtoVeiculo> listarComFiltros(String identificador, String cidade, String periodo, int pageNumber, int pageSize) {
		return boletimRepository.listarComFiltros(identificador, cidade, periodo, pageNumber, pageSize);
	}

	private boolean boletimValido(BoletimFurtoVeiculo b) {
	    if (b == null) return false;
	    
	    return (localValido(b) && partesValida(b) && veiculoValido(b) && dataValida(b)) ;
	}

		
	private boolean localValido(BoletimFurtoVeiculo b) {
		
	    Endereco local = b.getLocalOcorrencia();
	    if (local == null || local.getLogradouro() == null || local.getLogradouro().isEmpty()) {
	        throw new IllegalArgumentException("Logradouro do local de ocorrência inválido.");
	    }
	    if (local.getNumero() == null || local.getNumero().isEmpty()) {
	        throw new IllegalArgumentException("Número do local de ocorrência inválido.");
	    }
	    if (local.getBairro() == null || local.getBairro().isEmpty()) {
	        throw new IllegalArgumentException("Bairro do local de ocorrência inválido.");
	    }
	    if (local.getCidade() == null || local.getCidade().isEmpty()) {
	        throw new IllegalArgumentException("Cidade do local de ocorrência inválida.");
	    }
	    if (local.getEstado() == null || local.getEstado().isEmpty()) {
	        throw new IllegalArgumentException("Estado do local de ocorrência inválido.");
	    }
	    return true;
	}
	
	
	private boolean partesValida(BoletimFurtoVeiculo b) {
	    Parte partes = b.getPartes();
	    if (partes == null || partes.getNome() == null || partes.getNome().isEmpty()) {
	        throw new IllegalArgumentException("Nome da vítima envolvida inválido.");
	    }
	    if (partes.getEmail() == null || !emailValido(partes.getEmail())) {
	        throw new IllegalArgumentException("E-mail da vítima inválido.");
	    }
	    if (partes.getTelefone() == null || partes.getTelefone().isEmpty()) {
	        throw new IllegalArgumentException("Telefone da vítima envolvida inválido.");
	    }
	    return true;
	}
	
	private boolean veiculoValido(BoletimFurtoVeiculo b) {
		
		Veiculo veiculo = b.getVeiculoFurtado();
	    if (veiculo == null) {
	        throw new IllegalArgumentException("Informações do veículo furtado são obrigatórias.");
	    }
	    
	    Emplacamento emplacamento = veiculo.getEmplacamento();
	    if (emplacamento == null || emplacamento.getPlaca() == null || emplacamento.getPlaca().isEmpty()) {
	        throw new IllegalArgumentException("Placa do veículo inválida.");
	    }
	    if (emplacamento.getCidade() == null || emplacamento.getCidade().isEmpty()) {
	        throw new IllegalArgumentException("Cidade da placa do veículo inválida.");
	    }
	    if (emplacamento.getEstado() == null || emplacamento.getEstado().isEmpty()) {
	        throw new IllegalArgumentException("Estado da placa do veículo inválido.");
	    }
	    if (veiculo.getAnoFabricacao() == null) {
	        throw new IllegalArgumentException("Ano de fabricação do veículo inválido.");
	    }
	    if (veiculo.getCor() == null || veiculo.getCor().isEmpty()) {
	        throw new IllegalArgumentException("Cor do veículo inválida.");
	    }
	    if (veiculo.getMarca() == null || veiculo.getMarca().isEmpty()) {
	        throw new IllegalArgumentException("Marca do veículo inválida.");
	    }
	    if (veiculo.getTipo() == null || veiculo.getTipo().isEmpty()) {
	        throw new IllegalArgumentException("Tipo do veículo inválido.");
	    }
	    
	    return true;

	}
	
	private boolean dataValida(BoletimFurtoVeiculo b) {
	    if (b.getDataOcorrencia() == null || b.getDataOcorrencia().isAfter(LocalDate.now())) {
	        throw new IllegalArgumentException("Data de ocorrência inválida. A data deve ser anterior a data atual");
	    }
	    if (b.getPeriodoOcorrencia() == null || b.getPeriodoOcorrencia().isEmpty()) {
	        throw new IllegalArgumentException("Período da ocorrência inválido");
	    }
	    return true;
		
	}
	
	
	private boolean emailValido(String email) {
	    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	    return email.matches(emailRegex);
	}


	
    

}
