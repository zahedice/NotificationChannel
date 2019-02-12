package com.example.zahedur.notificationchannel;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;


/**
 * Created by zahedur on 2/20/18.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        onTokenRefresh(token);

        Log.e("MYTAG", token);
    }

    //private void registerToken(String token) {
    private void onTokenRefresh(String token) {

        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);


        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("token",token)
                .build();

        Request request = new Request.Builder()
                .url("https://zahedice14.000webhostapp.com/fcm/register.php")
                .post(body)
                .build();


         try{
             client.newCall(request).execute();
         }
         catch (IOException e)
         {
             e.printStackTrace();
         }

    }
}
