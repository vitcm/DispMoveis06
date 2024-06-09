package com.example.ex06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "entidades_avistamentos.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela para as entidades
    private static final String CREATE_TABLE_ENTIDADES = "CREATE TABLE entidades (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT," +
            "descricao TEXT," +
            "avistamento TEXT)";

    // Tabela para os avistamentos
    private static final String CREATE_TABLE_AVISTAMENTOS = "CREATE TABLE avistamentos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "entidade_id INTEGER," +
            "data_hora TEXT, " +  // Supondo que data_hora seja uma string representando a data
            "FOREIGN KEY(entidade_id) REFERENCES entidades(id))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar as tabelas
        db.execSQL(CREATE_TABLE_ENTIDADES);
        db.execSQL(CREATE_TABLE_AVISTAMENTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualizar o banco de dados, se necessário
        // Aqui você pode adicionar lógica para lidar com atualizações de esquema
        db.execSQL("DROP TABLE IF EXISTS entidades");
        db.execSQL("DROP TABLE IF EXISTS avistamentos");
        onCreate(db);
    }
}
