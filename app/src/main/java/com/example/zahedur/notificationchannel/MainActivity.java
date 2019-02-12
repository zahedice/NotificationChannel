package com.example.zahedur.notificationchannel;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

     public TextView et1,et2;
    String  message=" ";
    String  date;

    String result=" ";
    String result1=" ";
    int check =0,ck=0;

    Button b;
    Button see_msg;
    Button seePreviousMessage;
    int  loginCheck=1;
    public SessionManager session;
    FirebaseMessagingService fmc;
    //public static String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle(LoginActivity.email); // set the top title

        //fetchingData();
        b = (Button)findViewById(R.id.addingnewnews);
       // see_msg = (Button) findViewById(R.id.button);

        et1= (TextView) findViewById(R.id.et1);
        et2= (TextView) findViewById(R.id.et2);
        //et2= (EditText)findViewById(R.id.et2);
        //b = (Button)findViewById(R.id.button);

        see_msg = (Button)findViewById(R.id.button);
        seePreviousMessage = (Button) findViewById(R.id.button3);


       /// Intent intent = getIntent();
       // username = intent.getExtras().getString("username");

        //Toast.makeText(getApplicationContext(),username, Toast.LENGTH_LONG).show();
        seePreviousMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Show_Patient_Previous_Status.class));

            }
        });


         see_msg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 startActivity(new Intent(getApplicationContext(),Previous_Message_Show_Acitivity.class));
             }
         });


        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        //fmc = new  FirebaseMessagingService();
       //String message =  fmc.sendMessage();

        if ( getIntent().getExtras()!=null) {

            message = getIntent().getExtras().getString("message");

           if(message == null)
            {
               message = "No new message";
           }

           if(message != null){

               try {


                  fetchingData1(message);

               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
           if(message != null){

               try {

                  fetchingData(message);

               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
        }


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),AddingNews.class));
                //Intent intent = new Intent(MainActivity.this,AddingNews.class);
                //startActivity(intent);
                //finish();
            }
        });

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manubar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */

    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    void fetchingData(String message) throws JSONException {

        String  mes=" ", date=" ";

        String in;
        in = message;

        JSONArray contacts = new JSONArray(message);

        JSONObject jsonObject = contacts.getJSONObject(0);

        //name = jsonObject.getString("id");
        mes = jsonObject.getString("message");
        date = jsonObject.getString("upload_date");

        result = result + " (" + date + ") \n" + mes;


        et1.setText(result);


    }

    void fetchingData1(String message) throws JSONException {

        String name=" ", temp= " ",heart_rate=" ",date=" ";


        JSONArray contacts = new JSONArray(message);

        JSONObject jsonObject = contacts.getJSONObject(0);

        name = jsonObject.getString("title");
        temp = jsonObject.getString("Temperature");
        heart_rate = jsonObject.getString("HeartRate");
        date = jsonObject.getString("time");

        result1 = result1 + "Name: "+name + " (" + date + ") \n" + "Temperature: "+temp + "\nHeart Rate: "+heart_rate+"\n\n";


        et2.setText(result1);

    }





}
