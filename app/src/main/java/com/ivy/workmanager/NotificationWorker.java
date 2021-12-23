package com.ivy.workmanager;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created on : Mar 26, 2019
 * Author     : AndroidWave
 */
public class NotificationWorker extends Worker {
    private static final String WORK_RESULT = "work_result";
    Context context;
    Sharedata sharedata;
    public static final String DATE_FORMAT_NOW = "yyyy/MM/dd HH:mm:ss";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context=context;
        sharedata = new Sharedata(context);

    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        Data taskData = getInputData();
        String taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS);

        showNotification("WorkManager", "Message has been Sent");

        Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();
        Log.v("WORKER","DONE");
        sharedata.setWorkData("\n"+getCurrentDate() +" WORK DONE" );

       //((MainActivity)context).test();

        return Result.success(outputData);

    }

    private void showNotification(String task, String desc) {

        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        String channelId = "task_channel";
        String channelName = "task_name";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1, builder.build());

    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW);
        return df.format(c.getTime());
    }

}
