package br.edu.utfpr.td.tsi.delegacia.eletronica.api.regras;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.BoletimFurtoVeiculo;
import java.util.List;

public interface RegrasBoletimFurtoVeiculo {
   void persistir(BoletimFurtoVeiculo bo);

   void excluir(String identificador);

   List<BoletimFurtoVeiculo> listarTodos();

   List<BoletimFurtoVeiculo> procurarPorCidade(String cidade);

   List<BoletimFurtoVeiculo> procurarPorPeriodo(String periodo);

   BoletimFurtoVeiculo procurarPorIdentificador(String identificador);
}
