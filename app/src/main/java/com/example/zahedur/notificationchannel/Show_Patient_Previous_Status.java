package com.example.zahedur.notificationchannel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Show_Patient_Previous_Status extends AppCompatActivity {

   // TextView mTextView;

     Button temp_button, heart_button;
     ListView lv, lv2;
   // RequestQueue requestQueue;
    public static final String tempData = "name";
    public static final String date = "date";
    public static final String JSON_ARRAY = "result";
    RequestQueue requestQueue;

    private JSONArray result1;
    private JSONArray result2;
    //Spinner spinner1,spinner2;
    //private ArrayList<String> arrayList;
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;

    public SessionManager session;
    String  url = "https://zahedice14.000webhostapp.com/PatientDataStore/TemperatureDataRetrieval.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__patient__previous__status);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle(LoginActivity.email); // set the top title
        arrayList1 = new ArrayList<String>();
        arrayList2 = new ArrayList<String>();
        //patient_status = (TextView)findViewById(R.id.textView);

        //patient_status.setText("");
       // patient_status.setMovementMethod(new ScrollingMovementMethod());

        temp_button =(Button)findViewById(R.id.tempBtn);
        heart_button= (Button)findViewById(R.id.heartBtn);
        lv = (ListView)findViewById(R.id.messageList2);
        lv2 = (ListView)findViewById(R.id.messageList3);

       // requestQueue = Volley.newRequestQueue(this);
        temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              //receiveTempData();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result1= j.getJSONArray(JSON_ARRAY);
                            DisplayData(result1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        })
                {

                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to register url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", PatienNameList.usernane);
                        return params;
                    }
                };
                requestQueue = Volley.newRequestQueue(Show_Patient_Previous_Status.this);
                requestQueue.add(stringRequest);



            }
        });


        // requestQueue = Volley.newRequestQueue(this);
        heart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String url1 ="https://zahedice14.000webhostapp.com/PatientDataStore/HeartbeatDataRetrieval.php";
                //receiveTempData();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result2= j.getJSONArray(JSON_ARRAY);
                            DisplayData1(result2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        })
                {

                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to register url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", PatienNameList.usernane);
                        return params;
                    }
                };
                requestQueue = Volley.newRequestQueue(Show_Patient_Previous_Status.this);
                requestQueue.add(stringRequest);



            }
        });



        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();

        }



    }

    String temp1,temp2;
    private void DisplayData(JSONArray j) {
        arrayList1.clear();
       // for (int i = 0; i < j.length(); i++) {
        for (int i = j.length()-1; i >=0 ; i--){
            try {
                JSONObject json = j.getJSONObject(i);
                temp1=json.getString(tempData);
                temp2=json.getString(date);
                arrayList1.add("Date: "+temp2+"\n"+"Temperatuer(celcius): "+temp1);
                //arrayList1.add(json.getString(message));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        lv.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.text_view_for_temperatuer_layout,R.id.textviewforlist3,arrayList1));
    }
    private void DisplayData1(JSONArray j) {
        arrayList2.clear();
        //for (int i = 0; i < j.length(); i++) {
        for (int i = j.length()-1; i >=0 ; i--){
            try {
                JSONObject json = j.getJSONObject(i);
                temp1=json.getString(tempData);
                temp2=json.getString(date);
                arrayList2.add("Date: "+temp2+"\n"+"HeartBeat(bpm): "+temp1);
                //arrayList1.add(json.getString(message));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        lv2.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.text_view_for_heartbeat_layout,R.id.textviewforlist4,arrayList2));
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



/*
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());




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
