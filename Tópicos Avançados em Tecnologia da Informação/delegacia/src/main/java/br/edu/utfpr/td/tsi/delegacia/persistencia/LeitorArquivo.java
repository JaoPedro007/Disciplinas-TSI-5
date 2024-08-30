package br.edu.utfpr.td.tsi.delegacia.persistencia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.delegacia.modelo.BoletimFurtoVeiculo;
import br.edu.utfpr.td.tsi.delegacia.modelo.Emplacamento;
import br.edu.utfpr.td.tsi.delegacia.modelo.Endereco;
import br.edu.utfpr.td.tsi.delegacia.modelo.Veiculo;

@Component
public class LeitorArquivo {

    private List<BoletimFurtoVeiculo> boletins = new ArrayList<>();

    public LeitorArquivo() {
        this.boletins = this.lerArquivo();
    }

    public List<BoletimFurtoVeiculo> getBoletins() {
        return this.boletins;
    }

    public List<BoletimFurtoVeiculo> lerArquivo() {
        List<BoletimFurtoVeiculo> boletins = new ArrayList<>();

        try {
            Reader leitorArquivo = new FileReader("furtos.csv");
            try (CSVParser csvParser = CSVFormat.TDF.withFirstRecordAsHeader().withQuoteMode(QuoteMode.ALL).parse(leitorArquivo)) {
				List<CSVRecord> records = csvParser.getRecords();
				Iterator<CSVRecord> iterator = records.iterator();

				while (iterator.hasNext()) {
				    CSVRecord record = iterator.next();
				    Veiculo veiculo = this.processarDadosVeiculo(record);
				    if (veiculo != null) {
				        BoletimFurtoVeiculo bo = this.processarDadosCrime(record);
				        bo.setIdentificador(UUID.randomUUID().toString());
				        bo.setVeiculoFurtado(veiculo);
				        boletins.add(bo);
				    }
				}
			}
        } catch (IOException e) {
            System.out.println("Erro ao abrir arquivo CSV");
        }

        return boletins;
    }

    private Veiculo processarDadosVeiculo(CSVRecord record) {
        Veiculo veiculoFurtado = null;
        String placa = record.get("PLACA_VEICULO");
        if (!placa.isBlank()) {
            veiculoFurtado = new Veiculo();
            String estadoPlaca = record.get("UF_VEICULO");
            String cidadePlaca = record.get("CIDADE_VEICULO");
            Emplacamento emplacamento = new Emplacamento(placa, estadoPlaca, cidadePlaca);
            veiculoFurtado.setEmplacamento(emplacamento);
            String cor = record.get("DESCR_COR_VEICULO");
            String marca = record.get("DESCR_MARCA_VEICULO");
            String anoFabricacao = record.get("ANO_FABRICACAO");
            String tipo = record.get("DESCR_TIPO_VEICULO");
            veiculoFurtado.setAnoFabricacao(anoFabricacao);
            veiculoFurtado.setCor(cor);
            veiculoFurtado.setMarca(marca);
            veiculoFurtado.setTipo(tipo);
        }

        return veiculoFurtado;
    }

    private BoletimFurtoVeiculo processarDadosCrime(CSVRecord record) {
        BoletimFurtoVeiculo crime = new BoletimFurtoVeiculo();
        
        String stringDataOcorrencia = record.get("DATAOCORRENCIA");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataOcorrencia = LocalDate.parse(stringDataOcorrencia, dtf);
        String periodo = record.get("PERIDOOCORRENCIA");
        crime.setDataOcorrencia(dataOcorrencia);
        crime.setPeriodoOcorrencia(periodo);
        String logradouro = record.get("LOGRADOURO");
        String numero = record.get("NUMERO");
        String bairro = record.get("BAIRRO");
        String cidade = record.get("CIDADE");
        String estado = record.get("UF");
        Endereco endereco = new Endereco(logradouro, numero, bairro, cidade, estado);
        crime.setLocalOcorrencia(endereco);
        
        return crime;
    }
}
