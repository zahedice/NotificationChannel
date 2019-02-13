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

     public TextView et1;
    String  message=" ";
    String  date;

    String result=" ";
    String result1=" ";
    String result2=" ";
    int check =0,ck=0;

    Button b;
    Button see_msg;
    Button previousHrtBtn;
    Button previousPreviousBtn;
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
        //et2= (EditText)findViewById(R.id.et2);
        //b = (Button)findViewById(R.id.button);

        see_msg = (Button)findViewById(R.id.button);
        previousPreviousBtn = (Button) findViewById(R.id.button3);
       // previousHrtBtn = (Button) findViewById(R.id.button4);


       /// Intent intent = getIntent();
       // username = intent.getExtras().getString("username");

        //Toast.makeText(getApplicationContext(),username, Toast.LENGTH_LONG).show();
        previousPreviousBtn.setOnClickListener(new View.OnClickListener() {
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

                   fetchingData(message);
                  // fetchingData1(message);

               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
            if(message != null){

                try {

                   // fetchingData(message);
                     fetchingData1(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            if(message != null){

                try {

                    // fetchingData(message);
                    fetchingData2(message);

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


        String  mes=" ";


        JSONArray contacts = new JSONArray(message);

        JSONObject jsonObject = contacts.getJSONObject(0);

        //name = jsonObject.getString("id");
        mes = jsonObject.getString("message");
        date = jsonObject.getString("upload_date");

        // name = jsonObject.getString("title");
       // temp = jsonObject.getString("temperature");
        // heart_rate = jsonObject.getString("HeartRate");
        //date = jsonObject.getString("upload_date");

        //result1 = result1 +   date + " \n" + "Temperature: "+temp +"\n\n";


        result = result + " (" + date + ") \n" + mes;


        et1.setText(result);


    }

    void fetchingData1(String message) throws JSONException {

        String name=" ", temp= " ",heart_rate=" ",date=" ";


        JSONArray contacts = new JSONArray(message);

        JSONObject jsonObject = contacts.getJSONObject(0);

       // name = jsonObject.getString("title");
        temp = jsonObject.getString("temperature");
       // heart_rate = jsonObject.getString("HeartRate");
        date = jsonObject.getString("upload_date");

        result1 = result1 +   date + " \n" + "Temperature: "+temp +"\n\n";


        et1.setText(result1);

    }

    void fetchingData2(String message) throws JSONException {

        String name=" ", temp= " ",heart_rate=" ",date=" ";


        JSONArray contacts = new JSONArray(message);

        JSONObject jsonObject = contacts.getJSONObject(0);

        // name = jsonObject.getString("title");
        temp = jsonObject.getString("heartbeat");
        // heart_rate = jsonObject.getString("HeartRate");
        date = jsonObject.getString("upload_date");

        result2 = result2 +   date + " \n" + "Heartbeat: "+temp +"\n\n";


        et1.setText(result2);

    }





}
