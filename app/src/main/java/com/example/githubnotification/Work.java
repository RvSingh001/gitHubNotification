package com.example.githubnotification;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Work extends Worker {

    static String upadte="Rajveer";

    public Work(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            final GitHubUserEndPoints apiService =
                    APIClient.getClient().create(GitHubUserEndPoints.class);

            Call<Model> call = apiService.getUser();
            call.enqueue(new Callback<Model>() {
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {

                   if(upadte.equals(response.body().getPushed_at())) {
//                       getApplicationContext().getMainExecutor()
//                               .execute(() ->
//                                       Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_LONG).show());
                   }
                   else {
                       upadte=response.body().getPushed_at();
                       displayNotification(response);
                       getApplicationContext().getMainExecutor()
                               .execute(() ->
                                       Toast.makeText(getApplicationContext(), "Upadted", Toast.LENGTH_LONG).show());


                   }
                }
                @Override
                public void onFailure(Call<Model> call, Throwable t) {

                }
            });
            return Result.success();
        }
        catch (Throwable throwable)
        {
            getApplicationContext().getMainExecutor()
                    .execute(()->
                            Toast.makeText(getApplicationContext(),"failure work1",Toast.LENGTH_LONG).show());
            return Result.failure();
        }



    }

    private void displayNotification(Response<Model> response) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(response.body().getName(), response.body().getFull_name(), NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), response.body().getName())
                .setContentTitle(response.body().getFull_name())
                .setContentText("Updated On"+" "+response.body().getPushed_at())
                .setSmallIcon(R.mipmap.ic_launcher_round);

        notificationManager.notify(1, notification.build());
    }
}




