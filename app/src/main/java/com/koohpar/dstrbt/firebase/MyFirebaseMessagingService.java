package com.koohpar.dstrbt.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.ui.main.MainActivity;
import com.koohpar.dstrbt.utils.AppConstants;

import java.util.Date;


/**
 * Created by Behnaz on 06/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService implements AppConstants {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("type"));
    }

    private void showNotification(String message, String type) {
        Context context = getBaseContext();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (!pm.isScreenOn()) {
            PowerManager.WakeLock w1 = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MyLock");
            w1.acquire(10000);
            PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyCpuLock");
            wl_cpu.acquire(10000);
        }

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int notificationId = Integer.valueOf(last4Str);
//        int notificationId = Integer.valueOf(1);
        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.putExtra(KEY_NOTIFICATION_TYPE, type);
        notifyIntent.putExtra(KEY_NOTIFICATION_MESSAGE, message);
        notifyIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notifyIntent.setAction(Intent.ACTION_MAIN);

        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        this,
                        notificationId,
                        notifyIntent,
                        PendingIntent.FLAG_ONE_SHOT
                );
        long pattern[] = {0, 100, 200, 300};
        Uri notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notifSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.khanedaran);//Here is FILE_NAME is the name of file that you want to play

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)

                .setContentIntent(notifyPendingIntent)

                .setSmallIcon(getNotificationIcon()).setColor(Color.parseColor("#c42626"))

                .setContentTitle(context.getString(R.string.app_name))

                .setVibrate(pattern)

                .setSound(notifSound)

                .setAutoCancel(true)

                .setContentText(message);
        if (type.equalsIgnoreCase("1")) {
            Intent resultIntent = new Intent(context, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
        }
//        if (type.equalsIgnoreCase("2")) {
//            Intent resultIntent = new Intent(context, MyBuildingListActivity.class);
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//            stackBuilder.addParentStack(MyBuildingListActivity.class);
//            stackBuilder.addNextIntent(resultIntent);
//            PendingIntent resultPendingIntent =
//                    stackBuilder.getPendingIntent(
//                            0,
//                            PendingIntent.FLAG_UPDATE_CURRENT
//                    );
//            mBuilder.setContentIntent(resultPendingIntent);
//        }


        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, mBuilder.build());

    }

    private int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.drawable.logo : R.drawable.logo;
    }
}
