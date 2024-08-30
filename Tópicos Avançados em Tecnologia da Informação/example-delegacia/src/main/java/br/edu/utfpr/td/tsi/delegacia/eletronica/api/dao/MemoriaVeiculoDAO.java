package br.edu.utfpr.td.tsi.delegacia.eletronica.api.dao;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Veiculo;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemoriaVeiculoDAO implements VeiculoDAO {
   @Autowired
   private LeitorArquivoFurtoVeiculos leitorArquivoFurtoVeiculos;
   private Map<String, Veiculo> mapa = new HashMap();

   @PostConstruct
   public void init() {
      List<BoletimFurtoVeiculo> boletins = new ArrayList(this.leitorArquivoFurtoVeiculos.getBoletins().values());
      Iterator var2 = boletins.iterator();

      while(var2.hasNext()) {
         BoletimFurtoVeiculo boletimFurtoVeiculo = (BoletimFurtoVeiculo)var2.next();
         this.mapa.put(boletimFurtoVeiculo.getVeiculoFurtado().getEmplacamento().getPlaca(), boletimFurtoVeiculo.getVeiculoFurtado());
      }

   }

   public List<Veiculo> listarTodos() {
      return new ArrayList(this.mapa.values());
   }

   public Veiculo procurarPorPlaca(String placa) {
      return (Veiculo)this.mapa.get(placa);
   }

   public List<Veiculo> procurarPorCor(String cor) {
      List<Veiculo> veiculos = new ArrayList();
      Collection<Veiculo> values = this.mapa.values();
      Iterator var4 = values.iterator();

      while(var4.hasNext()) {
         Veiculo veiculo = (Veiculo)var4.next();
         if (veiculo.getCor().equalsIgnoreCase(cor)) {
            veiculos.add(veiculo);
         }
      }

      return veiculos;
   }

   public List<Veiculo> procurarPorTipo(String tipo) {
      List<Veiculo> veiculos = new ArrayList();
      Collection<Veiculo> values = this.mapa.values();
      Iterator var4 = values.iterator();

      while(var4.hasNext()) {
         Veiculo veiculo = (Veiculo)var4.next();
         if (veiculo.getTipoVeiculo().equalsIgnoreCase(tipo)) {
            veiculos.add(veiculo);
         }
      }

      return veiculos;
   }

   public void registrarFurto(Veiculo veiculo) {
      this.mapa.put(veiculo.getEmplacamento().getPlaca(), veiculo);
   }
}
