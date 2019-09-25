package com.laisa.formativa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {

    private final String codigos = "CREATE TABLE codigo(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "chave VARCHAR NOT NULL," +
            "autenticacao VARCHAR NOT NULL," +
            "dataHora REAL NOT NULL," +
            "status INTEGER NOT NULL);";


    public BancoDeDados(Context context, String name, int version) {
        super(context, "bd_codigo", null, version );
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //habilita a chave estrangeira
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    public void InserirCodigo(){
        SQLiteDatabase bd = getReadableDatabase(); //Abre a conexão
        String sql = null;
        //O método insert para inserir os dados que a empresa forneceu
        sql = "INSERT INTO codigo (chave,autenticacao,dataHora,status) VALUES" +
                " ('JH91HC','812j97f4298jnc2',1568026065,1),('PA83B7','m9ads78cj208jf4',1568026123,0)," +
                "('HJ272A','981m98cmc8a12',1568026778,1),('99CY2P','98mcaks326174',1568027060,0)," +
                "('278JMA','7812nca6123kas',1568027165,0);";
        bd.execSQL(sql);
    }

    public List<Chaves> mostrarTabela(){
        List<Chaves> listaChave = new ArrayList<Chaves>();
        SQLiteDatabase bd = getReadableDatabase(); //Abre a conexão
        //utilizando a classe Cursor para armazenar o conteúdo do SELECT
        Cursor c = bd.rawQuery("SELECT * FROM codigo",new String[]{});

        //Testar para verificar se o SELECT retornou alguma coisa.
        if(c.moveToFirst()){
            do{
                Chaves chave = new Chaves(c.getInt(0),c.getString(1),c.getString(2),c.getDouble(3),c.getInt(4));

                listaChave.add(chave);
            }while(c.moveToNext()); //Executar até achar a última linha
        }
        bd.close(); //Fecha a conexão
        return listaChave; //Retorna a lista
    }

    public boolean validarChave(String chave){
        SQLiteDatabase bd = getReadableDatabase(); //Abre a conexão
        Cursor c = bd.rawQuery("SELECT * FROM codigo WHERE chave=?", new String[]{chave});

        //Verificar se o select retornou alguma coisa
        if(c.moveToFirst()){
            bd.close();
            return true;
        }
        bd.close();
        return false;
    }



    public boolean alterarStatus(String chave){
        SQLiteDatabase bd = getReadableDatabase(); //Abre a conexão
        Cursor c = bd.rawQuery("SELECT * FROM codigo WHERE chave=? and status=?;",new String[]{chave, "0"});

        //Verificar se o select retornou alguma coisa
        if(c.moveToFirst()){
            ContentValues valores = new ContentValues();
            valores.put("status", 1);
            bd.update("codigo", valores, "chave=?", new String[]{chave}); //UPDATE codigos SET status=1 WHERE chave=?
            return true;
        }
        bd.close();
        return false;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(codigos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
