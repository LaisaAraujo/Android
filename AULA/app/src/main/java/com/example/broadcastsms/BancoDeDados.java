package com.example.broadcastsms;



    import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
    import java.util.List;

public class BancoDeDados extends SQLiteOpenHelper {// ver documentação para uso do REAL - https://pt.stackoverflow.com/questions/112149/sqlitestudio-tipos-de-dados
        private final String criarTabela = "CREATE TABLE coordenadas (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "latitude REAL NOT NULL," +
                "longitude REAL NOT NULL,"+
                "dataHora REAL NOT NULL); ";


        public BancoDeDados(Context context, int version) {
            super(context, "bd_latLong", null, version );
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            //habilita a chave estrangeira
            db.execSQL("PRAGMA foreign_keys = ON;");
        }

        //método para cadastrar um objeto na tabela
        public boolean cadastraPontos (double  latitude, double longitude, long datahora){
            SQLiteDatabase banco = getWritableDatabase(); //abre a conexão com o banco
            //passagem de valor para cada coluna da tabela
            ContentValues valores = new ContentValues();

            valores.put("latitude", latitude);
            valores.put("longitude", longitude);
            valores.put("datahora", datahora);

            if(banco.insert("coordenadas", null, valores) != -1){
                banco.close();//fecha  a conexão com o banco de dados
                return true;//significa que o comando INSERT foi executado com sucesso
            }else{
                banco.close();//fechar a conexão com o banco de dados
                return  false;
            }
        }

        public List<Coordenadas> buscaPontos(){
            List<Coordenadas> listaPontos = new ArrayList<Coordenadas>();
            SQLiteDatabase banco = getWritableDatabase(); //abre a conexão com o banco
            //utilizando a classe Cursor para armazenar o conteúdo do SELECT
            Cursor c = banco.rawQuery("SELECT * FROM coordenadas", new String[]{});

            //teste para verificar se o SELECT tem alguma coisa
            if(c.moveToFirst()){
                do{

                    Coordenadas coord = new Coordenadas((c.getInt(0)),c.getDouble(1), c.getDouble(2), c.getLong(3));

                    listaPontos.add(coord);

                }while (c.moveToNext()); //faz isso para todas as linhas encontradas
            }
            banco.close(); //fecha a conexão
            return  listaPontos; //retorna a lista com todos os pontos ou em branco
        }

//método para chamar a tabela para ver o que vai sendo cadastrado

    //Método para remover um ponto da tabela
    public boolean removerPonto(int id){
            SQLiteDatabase banco = getReadableDatabase(); //Abre a conexão
            //O método "delete" do SQLite retorna a quantidade de linhas removidas
        if(banco.delete("coordenadas", "_id=?",new String[]{id + "" })!= 0){
            banco.close();
            return true;
        }else{
            banco.close();
            return false;
        }
    }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(criarTabela);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


    }


