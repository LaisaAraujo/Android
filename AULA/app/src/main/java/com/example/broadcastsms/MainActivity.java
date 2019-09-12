package com.example.broadcastsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Button btAgendar;
    EditText campoHora, campoMinuto, campoSegundo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView  = findViewById(R.id.recyclerView);
        BancoDeDados bd = new BancoDeDados(this, 1);
        List<Coordenadas> lista = bd.buscaPontos();
        recyclerView.setAdapter(new AdapterCoordenadas(lista, getApplicationContext()));

        //atribuindo um layout
        RecyclerView.LayoutManager layout = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layout);


        btAgendar = findViewById(R.id.btAgendar);
        campoHora = findViewById(R.id.campoHora);
        campoMinuto = findViewById(R.id.campoMinuto);
        campoSegundo = findViewById(R.id.campoSegundo);

        btAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hora = Integer.parseInt(campoHora.getText().toString());
                int minuto = Integer.parseInt(campoMinuto.getText().toString());
                int segundo = Integer.parseInt(campoSegundo.getText().toString());

                //Utilização da classe Calendar para criar um objeto da data e hora
                Calendar dataHora = Calendar.getInstance();
                //Montando a data com valores específicos
                dataHora.set(dataHora.get(Calendar.YEAR),
                        dataHora.get(Calendar.MONTH),
                        dataHora.get(Calendar.DAY_OF_MONTH),
                        hora, minuto, segundo);


                //Criação da intent (o que será executado - mensagem)
                Intent intent = new Intent("AGENDAR_ALARME");
                //transformar a intent em PendingIntent (pois será executada futuramente)
                PendingIntent pending = PendingIntent.getBroadcast(
                        MainActivity.this, //contexto
                        0, //zero por padrão do método
                        intent, //Intent que será transformada em PendingIntent
                        0 //zero por padão do método (sim!!!! outro zero)
                );


                //criação de um objeto para o serviço de alarme do Android
                AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

                //configuração do alarme
                alarme.set(
                        AlarmManager.RTC_WAKEUP, //tipo de alarme, ativo mesmo com a tela desligada
                        dataHora.getTimeInMillis(), //horário que o evento será disparado
                        pending// o que será executado no jorário indicado acima

                );
            }
        });



        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.RECEIVE_SMS}, 100);
        }

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.READ_SMS}, 101);
        }

    }
}
