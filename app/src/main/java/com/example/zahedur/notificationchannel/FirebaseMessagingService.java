package com.example.zahedur.notificationchannel;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by zahedur on 2/20/18.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private  NotificationHelper mNotificationHelper;


     public  String message=" ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        mNotificationHelper = new NotificationHelper(this);

        //showNotification(remoteMessage.getData().get("message"));

         //sendMessage(remoteMessage.getData().get("message"));

        message = remoteMessage.getData().get("message");


        showNotification(message);



    }



    public void sendMessage(String message) {

        // return  message;

        //sn.Display(message);

       // Intent intent = new Intent(this, ShowNotification.class);
        //intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);

       // Intent intent = new Intent(this,MainActivity.class);
        //if ( message.size()>0){
            //String  message = remoteMessage.getData().get("message");

       // }



    }


    private void showNotification(String message) {

        String title = "Message from patient";
        sendOnChannel(title,message);

    }


    private void sendOnChannel(String title, String message) {


        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(title,message);


        mNotificationHelper.getManager().notify(1,nb.build());
    }



}
