package com.example.exerciciofinal;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ActivityManager activityManager =(ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
            if(activityManager != null) {
                List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();
                // Maneira Nova de fazer a Activity Manager, retorna a activity que esta rodando no momento
                String name;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //Busca a activity esta no topo
                    name = tasks.get(0).getTaskInfo().topActivity.getClassName();
                }else{ // Maneira Antiga de verificar a Activity Manager ( Não se usa mais)
                    name = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
                }

                Toast.makeText(this, "Nome da Tela: "+name, Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SSF", "Service on Destroy");
    }
}