package br.edu.utfpr.td.tsi.delegacia.eletronica.api.dao;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.BoletimFurtoVeiculo;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemoriaBoletimFurtoVeiculoDAO implements BoletimFurtoVeiculoDAO {
   @Autowired
   private LeitorArquivoFurtoVeiculos leitorArquivoFurtoVeiculos;
   private Map<String, BoletimFurtoVeiculo> boletins = new HashMap();

   @PostConstruct
   public void init() {
      this.boletins = this.leitorArquivoFurtoVeiculos.getBoletins();
   }

   public void persistir(BoletimFurtoVeiculo bo) {
      this.boletins.put(bo.getIdentificador(), bo);
   }

   public List<BoletimFurtoVeiculo> listarTodos() {
      return new ArrayList(this.boletins.values());
   }

   public List<BoletimFurtoVeiculo> procurarPorCidade(String cidade) {
      List<BoletimFurtoVeiculo> bosDesejados = new ArrayList();
      Iterator var3 = this.boletins.values().iterator();

      while(var3.hasNext()) {
         BoletimFurtoVeiculo boletimFurtoVeiculo = (BoletimFurtoVeiculo)var3.next();
         if (boletimFurtoVeiculo.getLocalOcorrencia().getCidade().equalsIgnoreCase(cidade)) {
            bosDesejados.add(boletimFurtoVeiculo);
         }
      }

      return bosDesejados;
   }

   public List<BoletimFurtoVeiculo> procurarPorPeriodo(String periodo) {
      List<BoletimFurtoVeiculo> bosDesejados = new ArrayList();
      Iterator var3 = this.boletins.values().iterator();

      while(var3.hasNext()) {
         BoletimFurtoVeiculo boletimFurtoVeiculo = (BoletimFurtoVeiculo)var3.next();
         if (boletimFurtoVeiculo.getPeriodoOcorrencia().equalsIgnoreCase(periodo)) {
            bosDesejados.add(boletimFurtoVeiculo);
         }
      }

      return bosDesejados;
   }

   public BoletimFurtoVeiculo procurarPorIdentificador(String identificador) {
      return (BoletimFurtoVeiculo)this.boletins.get(identificador);
   }

   public void excluir(String identificador) {
      this.boletins.remove(identificador);
   }
}
