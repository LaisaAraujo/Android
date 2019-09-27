package com.laisa.formativa;

import android.widget.ImageView;

public class Chaves {

    private int id;
    private String chave;
    private String autenticacao;
    private long dataHota;
    private int status;


    public Chaves(int id,String chave,String autenticacao,long dataHota,int status){
        this.id = id;
        this.chave = chave;
        this.autenticacao = autenticacao;
        this.dataHota = dataHota;
        this.status = status;
    }

    public int getId(){
        return id;
    }

    public String getChave(){
        return chave;
    }

    public String getAutenticacao(){
        return autenticacao;
    }

    public long getDataHora(){
        return dataHota;
    }

    public int getStatus(){
        return status;
    }
}
