package com.laisa.formativa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;


public class BroadcastSMS extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Utilizar a classe Bundle para recuperar todos os dados extras da intent

        Bundle extras = intent.getExtras();

        //PDU (SMS) - Protocol Description Unit
        //Filtar o "extras" para listar somente o tipo "PDU"

        Object[] pdus = (Object[]) extras.get("pdus");//quando não se sabe o tamanho do sms que a pessoa irá mandar, por isso Object está com colchetes vazios

        //Utilizar a classe SmsSessage do Android para poder manipular os dados com os métodos da classe

        SmsMessage[] sms = new SmsMessage[pdus.length];
        String conteudoSMS = "";
        String conteudoMensagem = "";
        String chave = "";

        //Laço para percorer cada SMS/PDUs
        for (int i = 0; i < sms.length; i++) {
            //Criando uma mensagem SMS à parit do PDU
            sms[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            //Concatenando o conteúdo do SMS na variável conteudoSMS
            conteudoSMS += sms[i].getMessageBody();
            //Recuperando o número de quem enviou o SMS
            conteudoMensagem += sms[i].getMessageBody();
        }//fim do laço for
        BancoDeDados bd = new BancoDeDados(context, "bd", 1);

        int ind = conteudoSMS.indexOf(':');
        if (ind != -1) {
            chave = conteudoSMS.substring(ind + 1, ind + 7);
        }
        else {
            chave = conteudoSMS;
        }

            try {
                if (!bd.validarChave(chave.trim())) {
                    Toast.makeText(context, "Não existe", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Existe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(context, "Erro " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
    }
}