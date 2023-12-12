package com.example.projek3.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projek3.R;
import com.example.projek3.user_reminde.userReminder;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    private SharedPreferences sh_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Switch swReminder = findViewById(R.id.remind_switch);
        sh_pre = this.getSharedPreferences("daily_notification", Context.MODE_PRIVATE);
        boolean isChecked = sh_pre.getBoolean("notification", false);
        swReminder.setChecked(isChecked);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.reminder_setting));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        swReminder.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            SharedPreferences.Editor editor = sh_pre.edit();
            if (isChecked1) {
                editor.putBoolean("notification", true);
                editor.apply();
                Toast.makeText(SettingActivity.this, "actived", Toast.LENGTH_SHORT).show();
                setReminder(getApplicationContext());
            } else {
                editor.putBoolean("notification", false);
                editor.apply();
                Toast.makeText(SettingActivity.this, "not active", Toast.LENGTH_SHORT).show();
                cancelReminder(SettingActivity.this);
            }
        });
    }

    private void setReminder(Context context) {
        AlarmManager reminderManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent reminderIntent = new Intent(context, userReminder.class);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, reminderIntent, 0);
        if (reminderManager != null) {
            reminderManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void cancelReminder(Context context) {
        AlarmManager reminderManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, userReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0);
        pendingIntent.cancel();

        if (reminderManager != null) {
            reminderManager.cancel(pendingIntent);
        }
    }
}
