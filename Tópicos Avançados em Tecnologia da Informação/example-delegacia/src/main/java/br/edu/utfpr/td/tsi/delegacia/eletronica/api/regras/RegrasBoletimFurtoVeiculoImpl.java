package br.edu.utfpr.td.tsi.delegacia.eletronica.api.regras;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.dao.BoletimFurtoVeiculoDAO;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.dao.VeiculoDAO;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Veiculo;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegrasBoletimFurtoVeiculoImpl implements RegrasBoletimFurtoVeiculo {
   @Autowired
   private BoletimFurtoVeiculoDAO boletimFurtoVeiculoDAO;
   @Autowired
   private VeiculoDAO veiculoDAO;

   public void persistir(BoletimFurtoVeiculo bo) {
      if (bo.getIdentificador() == null) {
         bo.setIdentificador(UUID.randomUUID().toString());
      }

      this.validar(bo);
      this.boletimFurtoVeiculoDAO.persistir(bo);
      Veiculo veiculoFurtado = bo.getVeiculoFurtado();
      veiculoFurtado.setEnvolvidoEm(new BoletimFurtoVeiculo(bo.getIdentificador()));
      this.veiculoDAO.registrarFurto(veiculoFurtado);
   }

   private void validar(BoletimFurtoVeiculo bo) {
   }

   public List<BoletimFurtoVeiculo> listarTodos() {
      List<BoletimFurtoVeiculo> listarTodos = this.boletimFurtoVeiculoDAO.listarTodos();
      Iterator var2 = listarTodos.iterator();

      while(var2.hasNext()) {
         BoletimFurtoVeiculo boletimFurtoVeiculo = (BoletimFurtoVeiculo)var2.next();
         this.removerPartes(boletimFurtoVeiculo);
      }

      return listarTodos;
   }

   public List<BoletimFurtoVeiculo> procurarPorCidade(String cidade) {
      List<BoletimFurtoVeiculo> bos = this.boletimFurtoVeiculoDAO.procurarPorCidade(cidade);
      Iterator var3 = bos.iterator();

      while(var3.hasNext()) {
         BoletimFurtoVeiculo boletimFurtoVeiculo = (BoletimFurtoVeiculo)var3.next();
         this.removerPartes(boletimFurtoVeiculo);
      }

      return bos;
   }

   public List<BoletimFurtoVeiculo> procurarPorPeriodo(String periodo) {
      List<BoletimFurtoVeiculo> bos = this.boletimFurtoVeiculoDAO.procurarPorPeriodo(periodo);
      Iterator var3 = bos.iterator();

      while(var3.hasNext()) {
         BoletimFurtoVeiculo boletimFurtoVeiculo = (BoletimFurtoVeiculo)var3.next();
         this.removerPartes(boletimFurtoVeiculo);
      }

      return bos;
   }

   private void removerPartes(BoletimFurtoVeiculo bo) {
      bo.setPartes((List)null);
   }

   public BoletimFurtoVeiculo procurarPorIdentificador(String identificador) {
      BoletimFurtoVeiculo bo = this.boletimFurtoVeiculoDAO.procurarPorIdentificador(identificador);
      if (bo == null) {
         return null;
      } else {
         this.removerPartes(bo);
         return bo;
      }
   }

   public void excluir(String identificador) {
      this.boletimFurtoVeiculoDAO.excluir(identificador);
   }
}
