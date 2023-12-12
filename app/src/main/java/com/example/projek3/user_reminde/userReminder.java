package com.example.projek3.user_reminde;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.projek3.MainActivity;
import com.example.projek3.R;

import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

public class userReminder extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {
        String CHANNEL_ID = "Chanel_1";
        String CHANNEL_NAME = "Notification";

        Intent reminderIn = new Intent(context, MainActivity.class);
        PendingIntent pendingIn = PendingIntent.getActivity(context, 101, reminderIn, 0);

        NotificationManager reminderManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder build = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIn)
                .setSmallIcon(R.drawable.ic_baseline_notifications_none_24)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.reminder_txt))
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            build.setChannelId(CHANNEL_ID);

            if (reminderManager != null) {
                reminderManager.createNotificationChannel(channel);
            }
        }

        Notification notifi = build.build();

        if (reminderManager != null) {
            reminderManager.notify(101, notifi);
        }
    }
}
