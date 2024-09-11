package com.example.agendaservicos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agendaservicos.banco.Banco;
import com.example.agendaservicos.banco.DatabaseClient;
import com.example.agendaservicos.dao.ItemAgendamentoDAO;
import com.example.agendaservicos.dao.ServicoDAO;
import com.example.agendaservicos.modelo.Servico;

import java.util.ArrayList;
import java.util.List;

public class TelaServicos extends AppCompatActivity {

    ArrayAdapter<Servico> adapter;
    ArrayList<Servico> servicos;

    Banco bd;
    ServicoDAO dao;
    ItemAgendamentoDAO itemDao;

    ListView lista;
    EditText edNome, edUnidade, edPreco;
    Servico servicoAtual;
    boolean editando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_servicos);

        lista = findViewById(R.id.lista_tipos_servico);
        edNome = findViewById(R.id.ed_nome_servico);
        edUnidade = findViewById(R.id.ed_unidade_medida);
        edPreco = findViewById(R.id.ed_preco);

        servicos = new ArrayList<>();
        adapter = new ArrayAdapter<Servico>(this, R.layout.list_item_servico, R.id.servico_descricao, servicos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_servico, parent, false);
                }

                Servico servico = getItem(position);

                TextView descricao = convertView.findViewById(R.id.servico_descricao);
                Button btnEditar = convertView.findViewById(R.id.btn_editar);
                Button btnRemover = convertView.findViewById(R.id.btn_remover);

                descricao.setText(servico.getDescricao());

                btnEditar.setOnClickListener(v -> editarServico(servico));
                btnRemover.setOnClickListener(v -> removerServico(servico));

                return convertView;
            }
        };
        lista.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bd = DatabaseClient.getInstance(this).getDatabase();
        dao = bd.getServicoDAO();
        itemDao = bd.getItemAgendamentoDAO();

        dao.listar().observe(this, new ObservadorServico());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void confirmar(View v) {
        if (!validarCampos()) {
            return;
        }
        new Thread() {
            public void run() {
                if (editando) {
                    servicoAtual.setDescricao(edNome.getText().toString());
                    servicoAtual.setUnidadeMedida(edUnidade.getText().toString());
                    servicoAtual.setValor(Double.parseDouble(edPreco.getText().toString()));
                    dao.alterar(servicoAtual);
                } else {
                    Servico s = new Servico(edNome.getText().toString(),
                            edUnidade.getText().toString(),
                            Double.parseDouble(edPreco.getText().toString()));
                    dao.inserir(s);
                }
                limparCampos();
                editando = false;
            }
        }.start();
    }


    private void editarServico(Servico servico) {
        servicoAtual = servico;
        edNome.setText(servico.getDescricao());
        edUnidade.setText(servico.getUnidadeMedida());
        edPreco.setText(String.valueOf(servico.getValor()));
        editando = true;
    }

    private void removerServico(Servico servico) {
        new Thread(() -> {
            int count = itemDao.contarItensPorServico(servico.getId());
            runOnUiThread(() -> {
                if (count > 0) {
                    new AlertDialog.Builder(this)
                            .setTitle("Erro")
                            .setMessage("Este serviço não pode ser excluído porque está associado a um agendamento.")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Remover Serviço")
                            .setMessage("Tem certeza que deseja remover este serviço?")
                            .setPositiveButton("Sim", (dialog, which) -> {
                                new Thread(() -> {
                                    dao.remover(servico);
                                }).start();
                            })
                            .setNegativeButton("Não", (dialog, which) -> dialog.dismiss())
                            .show();
                }
            });
        }).start();
    }


    private void limparCampos() {
        edNome.setText("");
        edUnidade.setText("");
        edPreco.setText("");
    }

    private boolean validarCampos() {
        if (edNome.getText().toString().trim().isEmpty()) {
            mostrarMensagemErro("Nome do serviço é obrigatório.");
            return false;
        }
        if (edUnidade.getText().toString().trim().isEmpty()) {
            mostrarMensagemErro("Unidade de medida é obrigatória.");
            return false;
        }
        try {
            Double.parseDouble(edPreco.getText().toString().trim());
        } catch (NumberFormatException e) {
            mostrarMensagemErro("Preço deve ser um número válido.");
            return false;
        }
        return true;
    }

    private void mostrarMensagemErro(String mensagem) {
        new AlertDialog.Builder(this).setTitle("Erro").setMessage(mensagem).setPositiveButton("OK", null).show();
    }


    class ObservadorServico implements Observer<List<Servico>> {
        @Override
        public void onChanged(List<Servico> servicos) {
            TelaServicos.this.servicos.clear();
            TelaServicos.this.servicos.addAll(servicos);
            adapter.notifyDataSetChanged();
        }
    }


}
