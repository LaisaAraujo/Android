package com.example.notificao;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastNotificacao extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Recuperando qual Action foi disparada, ou seja, qual botão
        //foi escolhido abaixo da notificação
        String actionEscolhida = intent.getAction();

        switch (actionEscolhida){
            case "CLICOU_BOTAO_SIM":
                Toast.makeText(context, "Clicou na Action SIM", Toast.LENGTH_LONG).show();
                break;
            case "CLICOU_BOTAO_NAO":
                Toast.makeText(context, "Clicou na Action NÃO", Toast.LENGTH_LONG).show();
                break;
            case "CLICOU_BOTAO_TALVEZ":
                Toast.makeText(context, "Clicou na Action TALVEZ", Toast.LENGTH_LONG).show();
                break;
            case "ACTION_RESPONDER":
                //Recuperar o texto escrito dentro do RemoteInput
                String texto = RemoteInput.getResultsFromIntent(intent).getCharSequence("TEXTO_ESCRITO_RESPONDER").toString();
                Toast.makeText(context, "Resposta escrita: " + texto, Toast.LENGTH_LONG).show();
                break;
        }

        //Aciona o serviço de notificação do Android para que depois seja possível remover uma notificação
        NotificationManager servico = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Como não foi clicado na notificação e sim em uma das Actions abaixo, a notificação
        //não irá "sumir" automaticamente. Portanto é necessário recuperar o valor da notificação,
        //passar para o serviço de notificação do Android e utilizar o método "cancel"
        int codigoNotificacao = intent.getIntExtra("CODIGO_NOTIFICACAO", 0);

        //Teste para verificar se conseguiu recuperar o valor do código da notificação
        if(codigoNotificacao != 0){
            //Acessa a notificação de acordo com o código recuperado e utiliza-se o método "cancel" para removê-la
            servico.cancel(codigoNotificacao);
        }

        //Recuperar o código da notificação com Resposta
        int codNotifResposta = intent.getIntExtra("CODIGO_NOTIFICACAO_RESPOSTA", 0);
        //Fazer o mesmo procedimento para cancelar
        if(codNotifResposta != 0){
            servico.cancel(codNotifResposta);
        }

    }
}