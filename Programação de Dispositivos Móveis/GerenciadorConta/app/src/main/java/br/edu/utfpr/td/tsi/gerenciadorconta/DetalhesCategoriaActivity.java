package br.edu.utfpr.td.tsi.gerenciadorconta;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class DetalhesCategoriaActivity extends AppCompatActivity {

    class DespesaAdapter extends ArrayAdapter<Despesa>{
        public DespesaAdapter(){
            super(DetalhesCategoriaActivity.this, android.R.layout.simple_list_item_1, cadastro);
        }

        @Override
        public View getView(int position, View reciclada, ViewGroup grupo){
            if(reciclada == null){
                reciclada = getLayoutInflater().inflate(
                        R.layout.item_lista_despesas, null);
            }
            Despesa despesa = cadastro.get(position);

            TextView descricaoDespesa = reciclada.findViewById(R.id.descricao_despesa);
            TextView dataValorDespesa = reciclada.findViewById(R.id.data_valor_despesa);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dataFormatada = dateFormat.format(despesa.getVencimentoDespesa());

            descricaoDespesa.setText(despesa.getDescricaoDespesa());
            dataValorDespesa.setText(dataFormatada + " - " + despesa.getValor());

            if (position == selecionado){
                reciclada.setBackgroundColor(Color.LTGRAY);
            }else{
                reciclada.setBackgroundColor(Color.WHITE);
            }
            return reciclada;
        }
    }

    DespesaAdapter adapter;
    EditText descricaoDespesa;
    EditText valor;
    EditText vencimentoDespesa;
    ListView listaDespesas;
    LinkedList<Despesa> cadastro;
    Date dataSelecionada;
    int selecionado = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_categoria);

        String descricao = getIntent().getStringExtra("descricao");

        TextView descricaoCategoria = findViewById(R.id.descricaoCategoria);
        descricaoCategoria.setText(descricao);

        descricaoDespesa = (EditText) findViewById(R.id.descricaoDespesa);
        valor = (EditText) findViewById(R.id.valorDespesa);
        listaDespesas = (ListView) findViewById(R.id.listaDespesas);
        vencimentoDespesa = (EditText) findViewById(R.id.vencimentoDespesa);

        vencimentoDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        cadastro = new LinkedList<>();

        if(savedInstanceState != null){
            cadastro = (LinkedList<Despesa>) savedInstanceState.getSerializable("LISTA_DESPESAS");
            selecionado = savedInstanceState.getInt("SELECIONADO", -1);
        }
        adapter = new DespesaAdapter();
        listaDespesas.setAdapter(adapter);

        listaDespesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selecionado = position;
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void onSaveInstanceState(Bundle dados){
        super.onSaveInstanceState(dados);
        dados.putSerializable("LISTA_DESPESAS", cadastro);
        dados.putInt("SELECIONADO", selecionado);
    }



    public void adicionar(View view){

        Despesa novaDespesa = new Despesa(
                descricaoDespesa.getText().toString(),
                Double.parseDouble(valor.getText().toString()),
                dataSelecionada);
        cadastro.add(novaDespesa);
        adapter.notifyDataSetChanged();

    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DetalhesCategoriaActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        dataSelecionada = calendar.getTime();
                        vencimentoDespesa.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
