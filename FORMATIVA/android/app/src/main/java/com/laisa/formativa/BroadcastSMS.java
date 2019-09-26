package com.laisa.formativa;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class BroadcastSMS extends BroadcastReceiver {
    public String autenticacao;

    @Override
    public void onReceive(Context context, Intent intent) {
        String conteudoSMS = "";
        String conteudoMensagem = "";
        String chave = "";
        BancoDeDados bd = new BancoDeDados(context, "bd", 1);
        boolean permissao = false;

        //Utilizar a classe Bundle para recuperar todos os dados extras da intent
        Bundle extras = intent.getExtras();


        //PDU (SMS) - Protocol Description Unit
        //Filtar o "extras" para listar somente o tipo "PDU"

        Object[] pdus = (Object[]) extras.get("pdus");//quando não se sabe o tamanho do sms que a pessoa irá mandar, por isso Object está com colchetes vazios

        //Utilizar a classe SmsSessage do Android para poder manipular os dados com os métodos da classe

        SmsMessage[] sms = new SmsMessage[pdus.length];


        //Laço para percorer cada SMS/PDUs
        for (int i = 0; i < sms.length; i++) {
            //Criando uma mensagem SMS à parit do PDU
            sms[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            //Concatenando o conteúdo do SMS na variável conteudoSMS
            conteudoSMS += sms[i].getMessageBody();
            //Recuperando o número de quem enviou o SMS
            conteudoMensagem += sms[i].getMessageBody();
        }//fim do laço for
        //validar a chave
        if (bd.validarChave(conteudoSMS) == true) {
            Toast.makeText(context, "Utilizada", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(context, "Não utilizada", Toast.LENGTH_SHORT).show();

            bd.alterarStatus(conteudoSMS);
            autenticacao = bd.buscarAutenticação(conteudoSMS);


            try {
                //Notificação
                Intent it = new Intent(context, MainActivity.class);
                PendingIntent pending = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_CANCEL_CURRENT);
                Notification.Builder notificacao = new Notification.Builder(context).setContentTitle("Novas chave autenticada").setSmallIcon(R.drawable.icone_notificacao).setContentText("Chave: " + conteudoSMS + "Autenticação: " + autenticacao).setContentIntent(pending).setAutoCancel(true);
                NotificationManager servico = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                servico.notify(230, notificacao.build());

            } catch (Exception ex) {
                Toast.makeText(context, "Erro!" + ex, Toast.LENGTH_LONG).show();
            }
        }
    }
}


