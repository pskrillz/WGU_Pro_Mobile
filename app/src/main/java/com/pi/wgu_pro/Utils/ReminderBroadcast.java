package com.pi.wgu_pro.Utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.pi.wgu_pro.R;

public class ReminderBroadcast extends BroadcastReceiver {
    private static int orderNum = 0;


// set notification
    public static void setAlert(Context context, String id, int channelIdNum, long time, String title, String text) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        orderNum += 1;

        Intent intent = new Intent(context, ReminderBroadcast.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        intent.putExtra("channelIdNum", channelIdNum);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, orderNum, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);



    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String alertTitle = intent.getStringExtra("title");
        String alertText = intent.getStringExtra("text");
        String channelId = intent.getStringExtra("id");
        int channelIdNumber = intent.getIntExtra("channelIdNum", -1);

        // create channel
        createNotificationChannel(context, channelId);

        // create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
                .setContentTitle(alertTitle)
                .setContentText(alertText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(channelIdNumber, builder.build());
    }




    public static void createNotificationChannel(Context context, String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "desc";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




}
