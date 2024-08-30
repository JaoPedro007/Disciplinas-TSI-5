package br.edu.utfpr.td.tsi.delegacia.eletronica.api.regras;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.dao.VeiculoDAO;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Veiculo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegrasVeiculoImpl implements RegrasVeiculo {
   @Autowired
   private VeiculoDAO veiculoDAO;

   public List<Veiculo> listarTodos() {
      return this.veiculoDAO.listarTodos();
   }

   public Veiculo procurarPorPlaca(String placa) {
      return this.veiculoDAO.procurarPorPlaca(placa);
   }

   public List<Veiculo> procurarPorCor(String cor) {
      return this.veiculoDAO.procurarPorCor(cor);
   }

   public List<Veiculo> procurarPorTipo(String tipo) {
      return this.veiculoDAO.procurarPorTipo(tipo);
   }
}
