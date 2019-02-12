package com.example.zahedur.notificationchannel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Show_Patient_Previous_Status extends AppCompatActivity {

   // TextView mTextView;

     Button temp_button, heart_button;
     ListView lv;

    RequestQueue requestQueue;

    TextView patient_status;
    public SessionManager session;
    String  url = "https://zahedice14.000webhostapp.com/api/gettingnews.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__patient__previous__status);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle(LoginActivity.email); // set the top title

        //patient_status = (TextView)findViewById(R.id.textView);

        //patient_status.setText("");
       // patient_status.setMovementMethod(new ScrollingMovementMethod());

        temp_button =(Button)findViewById(R.id.tempBtn);
        heart_button= (Button)findViewById(R.id.heartBtn);
        lv = (ListView)findViewById(R.id.messageList);




        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());



       /*
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Do something with response
                //mTextView.setText(response.toString());

                // Process the JSON
                try{
                    // Loop through the array elements
                    for(int i=0;i<response.length();i++){
                        // Get current json object
                        JSONObject student = response.getJSONObject(i);

                        // Get the current student (json object) data
                        String Name = student.getString("title");
                        String temp = student.getString("Temperature");
                        String heartRate = student.getString("HeartRate");
                        String date = student.getString("time");

                        // Display the formatted json data in text view
                        patient_status.append("Name: "+ Name +"("+date+")" + "\nTemperature: "+temp +"\nHeartRate : " + heartRate);
                        patient_status.append("\n\n");
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        VolleyLog.d("Volley Log",error);
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);


        */



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
        Intent intent = new Intent(Show_Patient_Previous_Status.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
