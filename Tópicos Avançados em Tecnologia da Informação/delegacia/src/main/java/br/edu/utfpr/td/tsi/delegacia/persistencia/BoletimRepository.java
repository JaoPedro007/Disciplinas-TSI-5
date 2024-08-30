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
        if (b != null) {
            boletins.add(b);
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
        return null;
    }

    @Override
    public boolean excluir(String id) {
        return boletins.removeIf(b -> b.getIdentificador().equals(id));
    }

    @Override
    public List<BoletimFurtoVeiculo> listarComFiltros(String identificador, String cidade, String periodo, int pageNumber, int pageSize) {
        List<BoletimFurtoVeiculo> resultados = boletins.stream()
            .filter(b -> (identificador == null || identificador.isEmpty() || b.getIdentificador().equals(identificador)))
            .filter(b -> (cidade == null || cidade.isEmpty() || b.getLocalOcorrencia().getCidade().equalsIgnoreCase(cidade)))
            .filter(b -> (periodo == null || periodo.isEmpty() || b.getPeriodoOcorrencia().equalsIgnoreCase(periodo)))
            .collect(Collectors.toList());

        int start = pageNumber * pageSize;
        int end = Math.min(start + pageSize, resultados.size());

        if (start >= resultados.size()) {
            return new ArrayList<>();
        }

        return resultados.subList(start, end);
    }
}
