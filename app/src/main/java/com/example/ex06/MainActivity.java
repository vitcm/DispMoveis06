package com.example.ex06;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private List<Entidade> entidadesList = new ArrayList<>();
    private EntidadeAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivityForResult(intent, 1); // 1 é um código de solicitação
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new EntidadeAdapter(entidadesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        carregarEntidades();
    }


    private void carregarEntidades() {
        entidadesList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT entidades.id, entidades.nome, entidades.descricao, avistamentos.data_hora " +
                "FROM entidades " +
                "INNER JOIN avistamentos ON entidades.id = avistamentos.entidade_id";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int columnIndexId = cursor.getColumnIndex("id");
                int columnIndexNome = cursor.getColumnIndex("nome");
                int columnIndexDescricao = cursor.getColumnIndex("descricao");
                int columnIndexAvistamento = cursor.getColumnIndex("data_hora");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                long id = cursor.getLong(columnIndexId);
                String nome = cursor.getString(columnIndexNome);
                String descricao = cursor.getString(columnIndexDescricao);
                String avistamentoStr = cursor.getString(columnIndexAvistamento);
                if (avistamentoStr != null) {
                    try {
                        Date avistamento = dateFormat.parse(avistamentoStr);
                        entidadesList.add(new Entidade(nome, descricao, avistamento));
                        Log.d("Entidade", " Lista: " + entidadesList );
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        carregarEntidades();
        adapter.notifyDataSetChanged();
    }
}
