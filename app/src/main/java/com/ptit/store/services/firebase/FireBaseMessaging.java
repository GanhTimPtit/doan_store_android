package com.ptit.store.services.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ptit.store.R;
import com.ptit.store.common.Constants;
import com.ptit.store.view.history_order.HistoryOrderActivity;
import com.ptit.store.view.manage_order.ManageOrderActivity;
import com.ptit.store.view.shop.clothes_detail.ClothesDetailActivity;

import org.json.JSONObject;

import java.util.Map;

public class FireBaseMessaging extends FirebaseMessagingService {

    public static final String TYPE = "type";
    public static final String CREATED_DATE = "createdDate";
    public static final String LOGO_URL = "logoUrl";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    private static final String TAG = FireBaseMessaging.class.getSimpleName();
    private static final String CUSTOMER_NAME = "customerName";
    private static final String AVATAR_URL = "avatarUrl";
    private static final String ID = "id";
    public static final String STATUS = "status";
    private NotificationManager notifManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        JSONObject objectData = new JSONObject(data);
        Log.i(TAG, "onMessageReceived: " + objectData.toString());
        try {
            String type = objectData.getString(TYPE);
            switch (type) {
                case "confirm_order": {
                    String status= objectData.getString(STATUS);
                    String msg= objectData.getString(MESSAGE);
                    buildNotiComfirmOrder(this, Integer.parseInt(status), msg);
                    break;
                }

            }
        } catch (Exception e) {
            Log.i(TAG, "error: " + e.getCause());
        }

    }


    public void buildNotiAddClothes(String clothesID) {
        String contentText = "Store";
        String contentTitle = "Kidd Store vừa thêm 1 mặt hàng mới";
        Intent intent = new Intent(this, ClothesDetailActivity.class);
        intent.putExtra(Constants.KEY_CLOTHES_ID, clothesID);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logoapp)
                        .setContentTitle(contentText)
                        .setContentText(contentTitle)
                        .setContentIntent(pendingIntent)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(contentTitle))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setAutoCancel(true);
        final Notification notification = mBuilder.build();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification);
    }

    public void buildNotiComfirmOrder(Context context, int status ,String msg) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = context.getString(R.string.default_notification_channel_id); // default_channel_id
        String title = context.getString(R.string.default_notification_channel_title); // Default Channel
        String contentText = msg;
        String contentTitle = "Store H&H thông báo !!!";
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, ManageOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("status",status);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(contentTitle)                            // required
                    .setSmallIcon(R.drawable.logoapp)   // required
                    .setContentText(contentText) // required
                    .setContentInfo(contentText)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(contentTitle)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            switch (status) {
                case 0: {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_delivery_order);
                    builder.setLargeIcon(bitmap);
                    break;
                }
                case 4: {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_complete_order);
                    builder.setLargeIcon(bitmap);
                    break;
                }
                default: {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_delete_order);
                    builder.setLargeIcon(bitmap);
                    break;
                }
            }

        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, ManageOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("status", status);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            builder.setContentTitle(contentTitle)                            // required
                    .setSmallIcon(R.drawable.logoapp)   // required
                    .setContentText(contentText) // required
                    .setContentInfo(contentText)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(contentTitle)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
            switch (status) {
                case 0: {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_delivery_order);
                    builder.setLargeIcon(bitmap);
                    break;
                }
                case 4: {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_complete_order);
                    builder.setLargeIcon(bitmap);
                    break;
                }
                default: {
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_delete_order);
                    builder.setLargeIcon(bitmap);
                    break;
                }
            }
        }
            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);
    }
}
