package br.edu.utfpr.td.tsi.delegacia.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import jakarta.annotation.PostConstruct;

@Repository
public class BoletimRepository implements IBoletimRepository {

    @Autowired
    private LeitorArquivo leitorArquivo;

    private List<BoletimFurtoVeiculo> boletins = new ArrayList<>();

    @PostConstruct
    public void init() {
        boletins = new ArrayList<>(this.leitorArquivo.getBoletins());
    }

    @Override
    public void registrar(BoletimFurtoVeiculo b) {
        if (b != null && boletins.stream().noneMatch(boletim -> boletim.getIdentificador().equals(b.getIdentificador()))) {
            boletins.add(0, b);
        } else {
            throw new IllegalArgumentException("Boletim com identificador duplicado.");
        }
    }


    @Override
    public List<BoletimFurtoVeiculo> listarTodos() {
        return new ArrayList<>(boletins);
    }

    @Override
    public BoletimFurtoVeiculo listar(String id) {
        return boletins.stream()
                       .filter(b -> b.getIdentificador().equals(id))
                       .findFirst()
                       .orElse(null);
    }

    @Override
    public BoletimFurtoVeiculo editar(BoletimFurtoVeiculo b) {
        for (int i = 0; i < boletins.size(); i++) {
            if (boletins.get(i).getIdentificador().equals(b.getIdentificador())) {
                boletins.set(i, b);
                return b;
            }
        }
        throw new IllegalArgumentException("Boletim com o identificador especificado não encontrado.");
    }


    @Override
    public boolean excluir(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID do boletim não localizado para exclusão.");
        }
        
        boolean removed = boletins.removeIf(b -> b.getIdentificador().equals(id));
        
        if (!removed) {
            throw new IllegalArgumentException("Boletim com o ID fornecido não encontrado.");
        }
        
        return removed;
    }



    @Override
    public List<BoletimFurtoVeiculo> listarComFiltros(String identificador, String cidade, String periodo, int pageNumber, int pageSize) {
        
        String periodoNormalizado = normalizarPeriodo(periodo);
        
        List<BoletimFurtoVeiculo> resultados = boletins.stream()
            .filter(b -> (identificador == null || identificador.isEmpty() || b.getIdentificador().equals(identificador)))
            .filter(b -> (cidade == null || cidade.isEmpty() || b.getLocalOcorrencia().getCidade().equalsIgnoreCase(cidade)))
            .filter(b -> (periodoNormalizado == null || periodoNormalizado.isEmpty() || b.getPeriodoOcorrencia().toLowerCase().contains(periodoNormalizado.toLowerCase())))
            .collect(Collectors.toList());

        int start = pageNumber * pageSize;
        int end = Math.min(start + pageSize, resultados.size());

        if (start >= resultados.size()) {
            return new ArrayList<>();
        }

        return resultados.subList(start, end);
    }

    
    private String normalizarPeriodo(String periodo) {
        if (periodo == null) {
            return null;
        }

        String periodoLower = periodo.trim().toLowerCase();
        
        if (periodoLower.contains("manhã") || periodoLower.contains("manha")) {
            return "Manhã";
        } else if (periodoLower.contains("tarde")) {
            return "Tarde";
        } else if (periodoLower.contains("madrugada") || periodoLower.contains("madruga")) {
            return "Madrugada";
        }else if (periodoLower.contains("noite") || periodoLower.contains("noite")) {
	        return "Noite";
	    }
        
        return null;
    }

}
