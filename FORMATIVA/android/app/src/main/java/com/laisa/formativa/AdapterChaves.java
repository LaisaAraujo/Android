package com.laisa.formativa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureSession;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterChaves extends RecyclerView.Adapter {
    public Context context;
    public List<Chaves> chaves;


    public AdapterChaves(List<Chaves> chaves, Context applicationContext){
        this.context = applicationContext;
        this.chaves = chaves;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        com.laisa.formativa.ViewHolder vhChaves = new com.laisa.formativa.ViewHolder(view);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        com.laisa.formativa.ViewHolder vhChaves = (com.laisa.formativa.ViewHolder) holder;
        vhChaves.chave.setText(chaves.get(position).getChave());
        vhChaves.autenticacao.setText(chaves.get(position).getChave());
        vhChaves.chave.setText(chaves.get(position).getChave());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
