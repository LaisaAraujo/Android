package com.laisa.formativa;

public class Chaves {

    private int id;
    private String chave;
    private String autenticacao;
    private double dataHota;
    private int status;

    public Chaves(int id,String chave,String autenticacao,double dataHota,int status){
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

    public double getDataHora(){
        return dataHota;
    }

    public int getStatus(){
        return status;
    }
}
