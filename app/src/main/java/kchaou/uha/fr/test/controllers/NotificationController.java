package kchaou.uha.fr.test.controllers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import kchaou.uha.fr.test.MainActivity;
import kchaou.uha.fr.test.R;

public class NotificationController {

    //proprietes
    private Intent intent;
    private String NOTIFICATION_CHANNEL_ID;
    private String titre;
    private String contenu;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    private Context context;


    //constructeurs
    public NotificationController(String titre, String contenu, Context context) {
        this.titre = titre;
        this.contenu = contenu;
        this.context = context;
    }


    //methodes
    public void parametageNotification(Context context) {
        intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NOTIFICATION_CHANNEL_ID = "0";
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


    }


    public void setNotification(Context conrext) {
        parametageNotification(conrext);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(conrext, NOTIFICATION_CHANNEL_ID);
        android.app.Notification mNotification = builder
                .setContentTitle(titre)
                .setContentText(contenu)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(conrext.getResources(), R.mipmap.ic_launcher))
                .build();

        notificationManager.notify(0, mNotification);

    }
}
