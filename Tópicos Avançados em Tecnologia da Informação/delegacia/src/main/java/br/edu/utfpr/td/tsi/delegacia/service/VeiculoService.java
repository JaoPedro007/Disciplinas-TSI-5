package br.edu.utfpr.td.tsi.delegacia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;
import br.edu.utfpr.td.tsi.delegacia.persistencia.IVeiculoRepository;

@Component
public class VeiculoService implements IVeiculoService{
	
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
        veiculoRepository.registrarFurto(veiculo);
		
	}

}
