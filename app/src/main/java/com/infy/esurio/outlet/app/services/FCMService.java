package com.infy.esurio.outlet.app.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.infy.esurio.middleware.DTO.OrdersDTO;
import com.infy.esurio.outlet.app.This;

import java.util.Map;

public class FCMService extends FirebaseMessagingService {

    private static final String TAG = "FCMService";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        This.FCM_TOKEN.wrap(token);

        FirebaseMessaging.getInstance().subscribeToTopic("esurio");
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            if("order".equals(data.get("title"))){
                OrdersDTO dto = new OrdersDTO();
                dto.setIdentifier(data.get("message"));
                This.ORDERS.add(0, dto);
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
