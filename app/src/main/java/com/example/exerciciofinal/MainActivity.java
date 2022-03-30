package com.example.exerciciofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button botaoBateria;
    private Button botaoAplicacoesInstaladas;

    private  MyBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoBateria = findViewById(R.id.botaoBateria);
        botaoAplicacoesInstaladas = findViewById(R.id.botaoAplicacoesInstaladas);

        botaoBateria.setOnClickListener(v->{
            Intent in = new Intent(this, BateriaActivity.class);
            startActivity(in);
        });

        botaoAplicacoesInstaladas.setOnClickListener(v->{
            Intent in = new Intent(this, AplicacoesInstaladasActivity.class);
            startActivity(in);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            AlertDialog.Builder mudaWifi = new AlertDialog.Builder(MainActivity.this);
            mudaWifi.setTitle("Status do Wi-Fi !");
            mudaWifi.setMessage("Mudou .....  " + WifiManager.WIFI_STATE_CHANGED_ACTION.getClass());
            mudaWifi.create().show();
            Log.d("SSF", "O Status do wi fi mudou");
        }
    }




}