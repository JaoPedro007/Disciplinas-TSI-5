package br.edu.utfpr.td.tsi.delegacia.eletronica.api.dao;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Veiculo;
import java.util.List;

public interface VeiculoDAO {
   List<Veiculo> listarTodos();

   Veiculo procurarPorPlaca(String placa);

   List<Veiculo> procurarPorCor(String cor);

   List<Veiculo> procurarPorTipo(String tipo);

   void registrarFurto(Veiculo veiculo);
}
