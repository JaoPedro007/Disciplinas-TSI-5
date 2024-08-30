package br.edu.utfpr.td.tsi.delegacia.eletronica.api.regras;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Veiculo;
import java.util.List;

public interface RegrasVeiculo {
   List<Veiculo> listarTodos();

   Veiculo procurarPorPlaca(String placa);

   List<Veiculo> procurarPorCor(String cor);

   List<Veiculo> procurarPorTipo(String tipo);
}
