package com.prime.recipejungle.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.prime.recipejungle.R;
import com.prime.redef.utils.AppUtils;

import java.util.UUID;

public class NotificationHelper {
    private static final String DEFAULT_CHANNEL_ID = "default-channel";

    public static void post(@NonNull Context context, String title, String content) {
        createNotificationChannel(context);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null)
            return;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setColor(AppUtils.getAccentColor())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(content))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // tag-id ikilisinin uygulama içinde unique olması gerekiyor.
        // tag'ı unique yapıp id'yi sabit tuttuk
        manager.notify(UUID.randomUUID().toString(), 1234, builder.build());
    }

    private static void createNotificationChannel(@NonNull Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Varsayılan Bildirim Kanalı";
            NotificationChannel channel = new NotificationChannel(DEFAULT_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.enableVibration(true);

            // Register the channel with the system; we can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        }
    }
}
