package com.laisa.formativa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    BroadcastSMS sms = new BroadcastSMS();
    BancoDeDados bd = new BancoDeDados(this,"bd",1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd.InserirCodigo();


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!=
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
