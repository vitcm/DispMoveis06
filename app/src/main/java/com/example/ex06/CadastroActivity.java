package com.example.ex06;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CadastroActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        dbHelper = new DatabaseHelper(this);

        Button cadastrarButton = findViewById(R.id.buttonCadastrar);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextNome = findViewById(R.id.editTextNome);
                String nome = editTextNome.getText().toString();

                EditText editTextDescricao = findViewById(R.id.editTextDescricao);
                String descricao = editTextDescricao.getText().toString();

                EditText editTextAvistamento = findViewById(R.id.editTextAvistamento);
                String avistamentoStr = editTextAvistamento.getText().toString();

                if (nome.isEmpty() || descricao.isEmpty() || avistamentoStr.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date avistamento = dateFormat.parse(avistamentoStr);
                    long entidadeId = inserirEntidade(nome, descricao);
                    inserirAvistamento(entidadeId, avistamento);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("nome", nome);
                    resultIntent.putExtra("descricao", descricao);
                    resultIntent.putExtra("avistamento", avistamentoStr);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(CadastroActivity.this, "Data inválida. Use o formato yyyy-MM-dd", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long inserirEntidade(String nome, String descricao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("descricao", descricao);
        long id = db.insert("entidades", null, values);
        db.close();
        return id;
    }

    private long inserirAvistamento(long entidadeId, Date avistamento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("entidade_id", entidadeId);
        values.put("data_hora", avistamento.getTime()); // Insere o timestamp da data
        long id = db.insert("avistamentos", null, values);
        db.close();
        return id;
    }
}
