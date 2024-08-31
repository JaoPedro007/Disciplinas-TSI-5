package br.edu.utfpr.td.tsi.delegacia.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.Emplacamento;
import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;
import br.edu.utfpr.td.tsi.delegacia.persistencia.IVeiculoRepository;

@Component
public class VeiculoService implements IVeiculoService {

	private static final Logger logger = Logger.getLogger(VeiculoService.class.getName());

	@Autowired
	IVeiculoRepository veiculoRepository;

	@Override
	public List<Veiculo> listarTodos() {
		return veiculoRepository.listarTodos();
	}

	@Override
	public List<Veiculo> listarVeiculosComFiltros(String placa, String cor, String tipo, int page, int size) {
		return veiculoRepository.listarVeiculosComFiltros(placa, cor, tipo, page, size);
	}

	@Override
	public void registrarFurto(Veiculo veiculo) {
		if (!veiculoValido(veiculo)) {
			throw new IllegalArgumentException("Campos obrigatórios do veículo estão ausentes ou inválidos.");
		}

		try {
			veiculoRepository.registrarFurto(veiculo);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Ocorreu um erro ao registrar o furto do veículo: ", e);
			throw e;
		}
	}

	private boolean veiculoValido(Veiculo veiculo) {
		if (veiculo == null)
			return false;

		return (caracteristicasValidas(veiculo)
				&& emplacamentoValido(veiculo));
	}

	private boolean caracteristicasValidas(Veiculo veiculo) {

		if (veiculo.getAnoFabricacao() == null || veiculo.getAnoFabricacao().isEmpty()) {
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

	private boolean emplacamentoValido(Veiculo veiculo) {
		Emplacamento emplacamento = veiculo.getEmplacamento();
		if (emplacamento == null) {
			throw new IllegalArgumentException("Emplacamento do veículo é obrigatório.");
		}
		if (emplacamento.getPlaca() == null || emplacamento.getPlaca().isEmpty()) {
			throw new IllegalArgumentException("Placa do veículo inválida.");
		}
		if (emplacamento.getCidade() == null || emplacamento.getCidade().isEmpty()) {
			throw new IllegalArgumentException("Cidade da placa do veículo inválida.");
		}
		if (emplacamento.getEstado() == null || emplacamento.getEstado().isEmpty()) {
			throw new IllegalArgumentException("Estado da placa do veículo inválido.");
		}

		return true;
	}

	@Override
	public void excluir(Veiculo v) {
		veiculoRepository.excluir(v);
	}
}
