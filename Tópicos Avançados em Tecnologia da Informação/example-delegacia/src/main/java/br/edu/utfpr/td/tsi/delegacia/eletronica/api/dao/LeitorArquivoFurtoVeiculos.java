package br.edu.utfpr.td.tsi.delegacia.eletronica.api.dao;

import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Emplacamento;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Endereco;
import br.edu.utfpr.td.tsi.delegacia.eletronica.api.model.Veiculo;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;

@Component
public class LeitorArquivoFurtoVeiculos {
   private Map<String, BoletimFurtoVeiculo> boletins = new HashMap();

   public LeitorArquivoFurtoVeiculos() {
      this.boletins = this.lerArquivo();
   }

   public Map<String, BoletimFurtoVeiculo> getBoletins() {
      return this.boletins;
   }

   public Map<String, BoletimFurtoVeiculo> lerArquivo() {
      HashMap mapa = new HashMap();

      try {
         Reader leitorArquivo = new FileReader("furtos.csv");
         CSVParser csvParser = CSVFormat.TDF.withFirstRecordAsHeader().withQuoteMode(QuoteMode.ALL).parse(leitorArquivo);
         List<CSVRecord> records = csvParser.getRecords();
         Iterator var5 = records.iterator();

         while(var5.hasNext()) {
            CSVRecord record = (CSVRecord)var5.next();
            Veiculo veiculo = this.processarDadosVeiculo(record);
            if (veiculo != null) {
               BoletimFurtoVeiculo bo = this.processarDadosCrime(record);
               bo.setIdentificador(UUID.randomUUID().toString());
               bo.setVeiculoFurtado(veiculo);
               veiculo.setEnvolvidoEm(new BoletimFurtoVeiculo(UUID.randomUUID().toString()));
               mapa.put(veiculo.getEmplacamento().getPlaca(), bo);
            }
         }

         System.out.println("veiculos unicos processados: " + mapa.keySet().size());
      } catch (IOException var9) {
         System.out.println("Erro ao abrir arquivo CSV");
      }

      Map<String, BoletimFurtoVeiculo> boletinsVeiculosUnicos = new HashMap();
      Collection<BoletimFurtoVeiculo> values = mapa.values();
      Iterator var12 = values.iterator();

      while(var12.hasNext()) {
         BoletimFurtoVeiculo boletimFurtoVeiculo = (BoletimFurtoVeiculo)var12.next();
         boletinsVeiculosUnicos.put(boletimFurtoVeiculo.getIdentificador(), boletimFurtoVeiculo);
      }

      return boletinsVeiculosUnicos;
   }

   private Veiculo processarDadosVeiculo(CSVRecord record) {
      Veiculo veiculoFurtado = null;
      String placa = record.get("PLACA_VEICULO");
      if (!placa.isBlank()) {
         veiculoFurtado = new Veiculo();
         String estadoPlaca = record.get("UF_VEICULO");
         String cidadePlaca = record.get("CIDADE_VEICULO");
         Emplacamento emplacamaneto = new Emplacamento(placa, estadoPlaca, cidadePlaca);
         veiculoFurtado.setEmplacamento(emplacamaneto);
         String cor = record.get("DESCR_COR_VEICULO");
         String marca = record.get("DESCR_MARCA_VEICULO");
         boolean var9 = false;

         int anoFabriccao;
         try {
            anoFabriccao = Integer.parseInt(record.get("ANO_FABRICACAO"));
         } catch (NumberFormatException var11) {
            anoFabriccao = 0;
         }

         String tipo = record.get("DESCR_TIPO_VEICULO");
         veiculoFurtado.setAnoFabricacao(anoFabriccao);
         veiculoFurtado.setCor(cor);
         veiculoFurtado.setMarca(marca);
         veiculoFurtado.setTipoVeiculo(tipo);
      }

      return veiculoFurtado;
   }

   private BoletimFurtoVeiculo processarDadosCrime(CSVRecord record) {
      BoletimFurtoVeiculo crime = null;
      crime = new BoletimFurtoVeiculo();
      String especie = record.get("ESPECIE");
      String rubrica = record.get("RUBRICA");
      String stringDataOcorrencia = record.get("DATAOCORRENCIA");
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate dataOcorrencia = LocalDate.parse(stringDataOcorrencia, dtf);
      String periodo = record.get("PERIDOOCORRENCIA");
      crime.setDataOcorrencia(dataOcorrencia);
      crime.setPeriodoOcorrencia(periodo);
      String logradouro = record.get("LOGRADOURO");
      int numero = Integer.parseInt(record.get("NUMERO"));
      String bairro = record.get("BAIRRO");
      String cidade = record.get("CIDADE");
      String estado = record.get("UF");
      Endereco endereco = new Endereco(logradouro, numero, bairro, cidade, estado);
      crime.setLocalOcorrencia(endereco);
      return crime;
   }
}
