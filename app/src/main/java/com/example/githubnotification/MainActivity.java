package com.example.githubnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaddata();
            }
        });
    }
   public void loaddata()
   {

               final PeriodicWorkRequest periodicWorkRequest =
                       new PeriodicWorkRequest.Builder(Work.class, 15, TimeUnit.MINUTES)
                               .setInitialDelay(6000,TimeUnit.MILLISECONDS)
                               .build();

               WorkManager workManager = WorkManager.getInstance(getApplicationContext());
               workManager.enqueue(periodicWorkRequest);

   }
}