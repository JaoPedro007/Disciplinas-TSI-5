package br.edu.utfpr.td.tsi.delegacia.eletronica.api.dao;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.BoletimFurtoVeiculo;
import java.util.List;

public interface BoletimFurtoVeiculoDAO {
   void persistir(BoletimFurtoVeiculo bo);

   void excluir(String identificador);

   List<BoletimFurtoVeiculo> listarTodos();

   BoletimFurtoVeiculo procurarPorIdentificador(String identificador);

   List<BoletimFurtoVeiculo> procurarPorCidade(String cidade);

   List<BoletimFurtoVeiculo> procurarPorPeriodo(String periodo);
}
