package com.ivy.workmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE_STATUS = "message_status";
    public static final String DATE_FORMAT_NOW = "yyyy/MM/dd HH:mm:ss";

    Button btn_one, btn_two,btn_clear;
    OneTimeWorkRequest mRequest;
    WorkManager mWorkManager;
    Constraints mConstraints;
    TextView tvStatus,tvStatus_date;
    Context context;
    Sharedata sharedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        sharedata = new Sharedata(context);
        mWorkManager = WorkManager.getInstance();
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvStatus_date = (TextView) findViewById(R.id.tvStatus_date);
        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStatus.setText("");
                scheduleWork(MESSAGE_STATUS);
                sharedata.setWorkData("\n"+getCurrentDate() +"PERIODIC WORK STARTS" );


            }
        });
        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStatus.setText("");
                oneTimeRequest();
                sharedata.setWorkData("\n"+getCurrentDate() +"ONE TIME WORK STARTS" );

            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkManager.cancelAllWork();
                sharedata.clearSharedData();
                tvStatus.setText("");

            }
        });
        test();

        Log.v("DEVICEID",getDeviceId(context));


    }


    public static String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            deviceId = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);

        }else {

            final TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;

    }



    private void oneTimeRequest() {
        mWorkManager.cancelAllWork();
        mConstraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED).build();
        /**
         * OneTimeWorkRequest with requiresBatteryNotLow Constraints
         */
        mRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class).setConstraints(mConstraints).build();
        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {
                    WorkInfo.State state = workInfo.getState();
                    tvStatus.append(state.toString() + "\n");

                }
            }
        });
        /**
         * Enqueue the WorkRequest
         */
        mWorkManager.enqueue(mRequest);

    }

    private void scheduleWork(String tag) {
        mWorkManager.cancelAllWork();
        PeriodicWorkRequest.Builder photoCheckBuilder =
                new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.MINUTES);
        mConstraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED).build();
        PeriodicWorkRequest request = photoCheckBuilder.setConstraints(mConstraints).build();
      //  WorkManager.getInstance().enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.KEEP, request);


        mWorkManager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null) {
                    WorkInfo.State state = workInfo.getState();
                    tvStatus.append(state.toString() + "\n");

                }
            }
        });
        /**
         * Enqueue the WorkRequest
         */
        mWorkManager.enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.KEEP, request);


    }

    public void test()
    {
        tvStatus_date.setText(sharedata.getWorkData());


    }
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW);
        return df.format(c.getTime());
    }
}