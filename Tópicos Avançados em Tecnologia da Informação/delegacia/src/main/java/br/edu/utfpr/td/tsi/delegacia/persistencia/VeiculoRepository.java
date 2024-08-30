package br.edu.utfpr.td.tsi.delegacia.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;
import jakarta.annotation.PostConstruct;
import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;

@Repository
public class VeiculoRepository implements IVeiculoRepository {

    @Autowired
    private LeitorArquivo leitorArquivo;

    private List<Veiculo> listaVeiculos = new ArrayList<>();

    @PostConstruct
    public void init() {
        List<BoletimFurtoVeiculo> boletins = new ArrayList<>(this.leitorArquivo.getBoletins());
        listaVeiculos = boletins.stream()
                                .map(BoletimFurtoVeiculo::getVeiculoFurtado)
                                .filter(veiculo -> veiculo != null)
                                .collect(Collectors.toList());
    }

    @Override
    public List<Veiculo> listarTodos() {
        return new ArrayList<>(listaVeiculos);
    }

    @Override
    public void registrarFurto(Veiculo veiculo) {
        if (veiculo != null) {
            listaVeiculos.add(veiculo);
            System.out.println("Veículo adicionado: " + veiculo);
        }
    }

    @Override
    public List<Veiculo> listarVeiculosComFiltros(String placa, String cor, String tipo, int page, int size) {
        List<Veiculo> resultados = listaVeiculos.stream()
            .filter(v -> (placa == null || placa.isEmpty() || v.getEmplacamento().getPlaca().equals(placa)))
            .filter(v -> (cor == null || cor.isEmpty() || v.getCor().equalsIgnoreCase(cor)))
            .filter(v -> (tipo == null || tipo.isEmpty() || v.getTipo().equalsIgnoreCase(tipo)))
            .collect(Collectors.toList());

        int start = page * size;
        int end = Math.min(start + size, resultados.size());

        if (start >= resultados.size()) {
            return new ArrayList<>();
        }

        return resultados.subList(start, end);
    }
}
