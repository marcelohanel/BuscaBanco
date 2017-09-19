package br.com.mmmobile.buscabanco.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "banco_dados.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONFIG = "CREATE TABLE " +
            "IF NOT EXISTS config (" +
            " chave TEXT NOT NULL PRIMARY KEY, " +
            " valor TEXT " +
            ")";

    private static final String TABLE_BANCOS = "CREATE TABLE " +
            "IF NOT EXISTS bancos (" +
            " id INTEGER NOT NULL PRIMARY KEY, " +
            " nome_banco TEXT, " +
            " cod_agencia TEXT, " +
            " nome_agencia TEXT, " +
            " tipo TEXT, " +
            " endereco TEXT, " +
            " complemento TEXT, " +
            " bairro TEXT, " +
            " cep TEXT, " +
            " cidade TEXT, " +
            " uf TEXT, " +
            " fone TEXT, " +
            " latitude TEXT, " +
            " longitude TEXT " +
            ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        /*
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
        */
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CONFIG);
        db.execSQL(TABLE_BANCOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/*
        if (oldVersion < 2){
            db.execSQL(TABLE_QUESTIONARIO_1);
        }
*/
    }
}
