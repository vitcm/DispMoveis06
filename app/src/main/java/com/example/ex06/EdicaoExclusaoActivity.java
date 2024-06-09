package com.example.ex06;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EdicaoExclusaoActivity extends AppCompatActivity {

    private String nome;
    private String descricao;
    private String avistamento;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_exclusao);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        descricao = intent.getStringExtra("descricao");
        avistamento = intent.getStringExtra("avistamento");

        EditText editTextNome = findViewById(R.id.editTextNome);
        EditText editTextDescricao = findViewById(R.id.editTextDescricao);
        EditText editTextAvistamento = findViewById(R.id.editTextAvistamento);

        editTextNome.setText(nome);
        editTextDescricao.setText(descricao);
        editTextAvistamento.setText(avistamento);

        Button buttonSalvar = findViewById(R.id.buttonSalvar);
        Button buttonExcluir = findViewById(R.id.buttonExcluir);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novoNome = editTextNome.getText().toString();
                String novaDescricao = editTextDescricao.getText().toString();
                String novoAvistamento = editTextAvistamento.getText().toString();
                atualizarEntidade(nome, descricao, avistamento, novoNome, novaDescricao);
                atualizarAvistamento(nome, avistamento, novoAvistamento);
                finish();
            }
        });

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirEntidade(nome, descricao);
                finish();
            }
        });
    }

    private void excluirEntidade(String nomeEntidade, String descricaoEntidade) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("avistamentos", "entidade_id=(SELECT id FROM entidades WHERE nome=? AND descricao=?)", new String[]{nomeEntidade, descricaoEntidade});
        db.delete("entidades", "nome=? AND descricao=?", new String[]{nomeEntidade, descricaoEntidade});
        db.close();
    }

    private void atualizarEntidade(String nomeAntigo, String descricaoAntiga, String avistamentoAntigo, String novoNome, String novaDescricao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", novoNome);
        values.put("descricao", novaDescricao);
        db.update("entidades", values, "nome=? AND descricao=?", new String[]{nomeAntigo, descricaoAntiga});
        db.close();
    }

    private void atualizarAvistamento(String nomeEntidade, String avistamentoAntigo, String novoAvistamento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("data_hora", novoAvistamento);
        db.update("avistamentos", values, "entidade_id=(SELECT id FROM entidades WHERE nome=?) AND data_hora=?", new String[]{nomeEntidade, avistamentoAntigo});
        db.close();
    }

}