package com.laisa.formativa;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView chave, autenticacao, dataHora;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        chave = itemView.findViewById(R.id.chaveee);
        autenticacao = itemView.findViewById(R.id.autenticacaoo);
        dataHora = itemView.findViewById(R.id.dataeHora);

    }
}
