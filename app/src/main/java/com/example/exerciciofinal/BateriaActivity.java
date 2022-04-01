package com.example.exerciciofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class BateriaActivity extends AppCompatActivity {

    private TextView porcentagem;
    private TextView carregamento;
    private TextView saudeBateria;

    private MyBatInfoReceiver receiver;
    public long timeRemaning = 0L;

    private static int pluged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bateria);

        porcentagem = findViewById(R.id.porcentagem);
        carregamento = findViewById(R.id.carregamento);
        saudeBateria = findViewById(R.id.saudeBateria);

        Intent intent = new Intent(this,MyIntentService.class);
        intent.putExtra("TELA","tela 1");
        startService(intent);



    }


    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyBatInfoReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        receiver = new MyBatInfoReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        receiver = new MyBatInfoReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
    }


    class MyBatInfoReceiver extends BroadcastReceiver {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int deviceHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            pluged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);

            checkBatteryChargeTime();

            float battery_percent = level * 100 / (float)scale;
            porcentagem.setText(battery_percent + "%");

            if (deviceHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                saudeBateria.setText("Fria");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                saudeBateria.setText("Morta");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                saudeBateria.setText("Boa");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                saudeBateria.setText("Superaquecida");
            }
            if (deviceHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                saudeBateria.setText("Sobretensão");
            }
        }
    }

    public void checkBatteryChargeTime() {
        BatteryManager bateryM = (BatteryManager) getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            timeRemaning = bateryM.computeChargeTimeRemaining()/600000; //TODO: Valor de 600000 para conseguir vizualziar nos celulares xiaomi
            if (pluged == 0) {                                          //TODO: Valor de 60000 para os demais aparelhos
                carregamento.setText("Dispositivo não está carregando" );
            } else {
                carregamento.setText("Faltam " + timeRemaning + " minutos para o carregamento completo" );
            }
        }
    }
}