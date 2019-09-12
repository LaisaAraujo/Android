package com.example.broadcastsms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterCoordenadas extends RecyclerView.Adapter {

    private List<Coordenadas> listaPontos;
    private Context context;


    //construtor que recebe a lista de pontos de SELECT e o contexto
   public  AdapterCoordenadas(List<Coordenadas> listaPontos, Context context){
       this.listaPontos = listaPontos;
       this.context = context;

   }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_card_view,
               parent, false); //indicando o layout a ser usado
        //passando o layout para a nossa classe do ViewHolderCoordenadas (view);
       ViewHolderCoordenadas vhC = new ViewHolderCoordenadas(view);

        return vhC;
    }

    //método para indicar o que será atribuído para cada campo do layout CardView
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
       //recupera o layout da tela (nosso viewHolder)
        ViewHolderCoordenadas vhC = (ViewHolderCoordenadas) holder;
        //atribuindo o valor de cada TextView do nosso ViewHolder
        vhC.cardLatitude.setText("Latitude: "+ listaPontos.get (position).getLatitude());
        vhC.cardLongitude.setText("Longitude: "+ listaPontos.get (position).getLongitude());

        //vhC.btRemover.setBackground();
        vhC.btRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BancoDeDados banco = new BancoDeDados(context,1);
                //Recuperar o ID (chave primária do item)
                int id = listaPontos.get(position).get_id();
                if(banco.removerPonto(id)){
                    notifyItemChanged(position); //Informar que o item foi removido
                    Toast.makeText(context,"Removido",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"Não removido",Toast.LENGTH_LONG).show();
                }
            }
        });

        //convertendo dataHora para o formato dd/mm
        Date datahora = new Date (listaPontos.get (position).getDatahora());
        //máscara para data
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");
        vhC.cardLatitude.setText("DataHora: "+ sdf.format(datahora));
   }

    @Override
    public int getItemCount() {
        return listaPontos.size();
    }
}
