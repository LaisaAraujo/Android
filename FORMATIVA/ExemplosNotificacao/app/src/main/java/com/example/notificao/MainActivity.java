package com.example.notificao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btNotifSimples, btNotifAction, btNotifResposta;
    private NotificationManager servico;
    //Variável inteira para armazenar um código de identificação da notificação
    private int CODIGO_NOTIFICACAO = 613;
    private int CODIGO_NOTIFICACAO_RESPOSTA = 445;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btNotifSimples = findViewById(R.id.btNotifSimples);
        btNotifAction = findViewById(R.id.btNotifAction);
        btNotifResposta = findViewById(R.id.btNotifResposta);

        //Iniciar o serviço de Notificação do Android
        servico = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Criação de uma Intent padrão para direcionar até a NovaTela.java
        //Ao clicar na notificação será aberta essa outra activity
        Intent intent = new Intent(this, NovaTela.class);

        //Transformando a Intent em PendingIntent
        PendingIntent pending = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Criação de uma notificação padrão para ser usada em todos os exemplos
        //Depois será atribuído somente o que é diferente entre uma notificação e outra
        final Notification.Builder notificacao = new Notification.Builder(this)
                .setContentTitle("Notificação padrão")
                .setContentText("Texto interno da notificação padrão")
                .setAutoCancel(true)
                .setContentIntent(pending)
                .setSmallIcon(R.drawable.icone_notificacao);

        //Código para exibição da notificação simples
        btNotifSimples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Exibir a notificação
                servico.notify(CODIGO_NOTIFICACAO, notificacao.build());
            }
        });

        //Código para configuração da notificação com uma (ou mais) ações
        //isto é, botões que podem ser acionados abaixo da notificação
        btNotifAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Para criar os botões abaixo da notificação é necessário adicionar
                //uma Intent, transformá-la em PendingIntent e também acionar um
                //BroadcastReceiver ou Service quando for clicado sobre um dos botões
                //Nesse exemplo, ao clicar sobre um dos três botões abaixo da notificação
                //será acionado um BroadcastReceiver (BroadcastNotificacao.java)

                Intent itBotaoSim = new Intent();
                //Configurar um ação (Action) para que seja possível identificar
                //que o botão "Sim" foi acionado
                itBotaoSim.setAction("CLICOU_BOTAO_SIM");
                //Passagem de um valor extra junto com a Intent para que seja possível acessar essa
                //notificação no BroadcastNotificacao
                itBotaoSim.putExtra("CODIGO_NOTIFICACAO", CODIGO_NOTIFICACAO);

                //Transformar em PendingIntent
                //É necessário usar o FLAG_UPDATE_CURRENT quanto se tem uma ou mais Action
                PendingIntent pendingBotaoSim = PendingIntent.getBroadcast
                        (MainActivity.this, 0, itBotaoSim, PendingIntent.FLAG_UPDATE_CURRENT);

                //Para configurar a Action é necessário passar uma imagem (somente visível até a versão 24 - 7.0),
                //um texto e uma PendingIntent que, nesse caso, chama o BroadcastNotificao com a Action "CLICOU_BOTAO_SIM"
                notificacao.addAction(R.drawable.action_sim, "Sim", pendingBotaoSim);

                //Nova Action - Botão "Não"
                //Fazer o mesmo procedimento para adiconar uma Action bom o botão "Não" e um "Talvez"
                Intent itBotaoNao = new Intent();
                itBotaoNao.setAction("CLICOU_BOTAO_NAO");
                itBotaoNao.putExtra("CODIGO_NOTIFICACAO", CODIGO_NOTIFICACAO);
                PendingIntent pendingBotaoNao = PendingIntent.getBroadcast
                        (MainActivity.this, 0, itBotaoNao, PendingIntent.FLAG_UPDATE_CURRENT);
                notificacao.addAction(R.drawable.action_nao, "Não", pendingBotaoNao);

                //Nova Action - "Talvez"
                Intent itBotaoTalvez = new Intent();
                itBotaoTalvez.setAction("CLICOU_BOTAO_TALVEZ");
                itBotaoTalvez.putExtra("CODIGO_NOTIFICACAO", CODIGO_NOTIFICACAO);
                PendingIntent pendingBotaoTalvez = PendingIntent.getBroadcast
                        (MainActivity.this, 0, itBotaoTalvez, PendingIntent.FLAG_UPDATE_CURRENT);
                notificacao.addAction(R.drawable.action_talvez, "Talvez", pendingBotaoTalvez);

                //Exibir a notificação
                servico.notify(CODIGO_NOTIFICACAO, notificacao.build());
            }
        });

        //Código para configuração da notificação com um campo de Resposta
        //Só é possível utilizar esse recurso em celulares com a versão 7 (Android N) ou acima
        btNotifResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Teste para verificar qual a versão do Android que está em execução no celular
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){ //Se a versão em execução é maior ou igual ao 7 (N)
                    //Criação de uma Intent e depois a PendingIntent
                    Intent itActionResponder = new Intent();
                    itActionResponder.setAction("ACTION_RESPONDER");
                    itActionResponder.putExtra("CODIGO_NOTIFICACAO_RESPOSTA", CODIGO_NOTIFICACAO_RESPOSTA);
                    PendingIntent pendingIntentResponder = PendingIntent.getBroadcast
                            (MainActivity.this, 0, itActionResponder, PendingIntent.FLAG_UPDATE_CURRENT);

                    //Criação de um RemoteInput (campo aonde será escrita a resposta)
                    RemoteInput remoteInput = new RemoteInput.Builder("TEXTO_ESCRITO_RESPONDER")
                            .setLabel("Escreva sua resposta...") //Texto que aparece dentro da caixa do "responder"
                            .build();

                    //Transformar o RemoteInput em uma Action para que seja possível adicionar à notificação
                    NotificationCompat.Action action = new  NotificationCompat.Action.Builder
                            (R.drawable.icone_responder, "Clique aqui para responder", pendingIntentResponder)
                            .addRemoteInput(remoteInput)
                            .build();

                    //Para esse tipo de recurso (RemoteInput) é necessário usar a notificação da classe NotificationCompat
                    NotificationCompat.Builder notifCompat = new NotificationCompat.Builder(MainActivity.this, "ID_NOTIF_RESPOSTA");
                    notifCompat.setContentTitle("Notificação resposta");
                    notifCompat.setContentText("Texto interno da notificação resposta");
                    notifCompat.setAutoCancel(true);
                    notifCompat.setSmallIcon(R.drawable.icone_notificacao);

                    //Adicionando a action com o responder dentro da notificaçãoResposta
                    notifCompat.addAction(action);

                    //Criação de um serviço para ser usado com a classe NotificationCompat
                    NotificationManagerCompat servicoCompat = NotificationManagerCompat.from(MainActivity.this);
                    servicoCompat.notify(CODIGO_NOTIFICACAO_RESPOSTA, notifCompat.build());
                }
            }
        });



    }
}
