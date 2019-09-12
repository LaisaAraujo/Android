package com.example.broadcastsms;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


//classe necessária para podermos vincular os componentes que estão no
//layout do cardView com um ID equivalente a ele para um objeto do mesm
//tipo, no cao o TextView, ou seja, faz a ligação do ID (XML) com a parte
//da programação em Java (Classe TextView)

public class ViewHolderCoordenadas extends RecyclerView.ViewHolder {

    TextView cardLatitude, cardLongitude, cardDataHora;
    ImageButton btRemover;

    public ViewHolderCoordenadas(@NonNull View itemView) {
        super(itemView);
            cardLatitude = itemView.findViewById(R.id.cardLatitude);
            cardLongitude = itemView.findViewById(R.id.cardLongitude);
            cardDataHora = itemView.findViewById(R.id.cardDataHora);
            btRemover = itemView.findViewById(R.id.btRemover);
    }
}
