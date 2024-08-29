package br.edu.utfpr.td.tsi.delegacia.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;

@Component
public class BoletimRepository implements IBoletimRepository{
	
	List<BoletimFurtoVeiculo> bd = new ArrayList<BoletimFurtoVeiculo>();

	@Override
	public void registrar(BoletimFurtoVeiculo b) {
			bd.add(b);
	}

	@Override
	public List<BoletimFurtoVeiculo> listarTodos() {
		return bd;
		
	}

	@Override
	public BoletimFurtoVeiculo listar(String id) {
		for (BoletimFurtoVeiculo boletimFurtoVeiculo : bd) {
			if(boletimFurtoVeiculo.getIdentificador().equals(id)) {
				return boletimFurtoVeiculo;
			}
		}
		return null;
	}

	@Override
	public BoletimFurtoVeiculo editar(BoletimFurtoVeiculo b) {
	    for (int i = 0; i < bd.size(); i++) {
	        BoletimFurtoVeiculo boletimFurtoVeiculo = bd.get(i);
	        if (boletimFurtoVeiculo.getIdentificador().equals(b.getIdentificador())) {
	            bd.set(i, b);
	            return b;
	        }
	    }
	    return null;
	}

	@Override
	public boolean excluir(String id) {
		for (BoletimFurtoVeiculo boletimFurtoVeiculo : bd) {
			if(boletimFurtoVeiculo.getIdentificador().equals(id)){
				bd.remove(boletimFurtoVeiculo);
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public List<BoletimFurtoVeiculo> listarComFiltros(String identificador, String cidade, String periodo) {
	    List<BoletimFurtoVeiculo> resultados = new ArrayList<>();

	    for (BoletimFurtoVeiculo boletim : bd) {
	        boolean match = true;

	        if (identificador != null && !identificador.isEmpty()) {
	            if (!boletim.getIdentificador().equals(identificador)) {
	                match = false;
	            }
	        }

	        if (cidade != null && !cidade.isEmpty()) {
	            if (!boletim.getLocalOcorrencia().getCidade().equalsIgnoreCase(cidade)) {
	                match = false;
	            }
	        }

	        if (periodo != null && !periodo.isEmpty()) {
	            if (!boletim.getPeriodoOcorrencia().equalsIgnoreCase(periodo)) {
	                match = false;
	            }
	        }

	        if (match) {
	            resultados.add(boletim);
	        }
	    }

	    return resultados;
	}
	
	
	@Override
	public List<Veiculo> listarVeiculosComFiltros(String placa, String cor, String tipo) {
	    List<Veiculo> resultados = new ArrayList<>();

	    for (BoletimFurtoVeiculo boletim : bd) {
	        Veiculo veiculo = boletim.getVeiculoFurtado();

	        if (veiculo != null) {
	            boolean match = true;

	            if (placa != null && !placa.isEmpty()) {
	                if (!veiculo.getEmplacamento().getPlaca().equalsIgnoreCase(placa)) {
	                    match = false;
	                }
	            }

	            if (cor != null && !cor.isEmpty()) {
	                if (!veiculo.getCor().equalsIgnoreCase(cor)) {
	                    match = false;
	                }
	            }

	            if (tipo != null && !tipo.isEmpty()) {
	                if (!veiculo.getTipo().equalsIgnoreCase(tipo)) {
	                    match = false;
	                }
	            }

	            if (match) {
	                resultados.add(veiculo);
	            }
	        }
	    }

	    return resultados;
	}

}
