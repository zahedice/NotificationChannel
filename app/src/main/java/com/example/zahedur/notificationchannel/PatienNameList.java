package com.example.zahedur.notificationchannel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.example.zahedur.tuntuninews.dataUploading.StartMenu;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatienNameList extends AppCompatActivity {

    ListView lv;
    public SessionManager session;
    public static final String doctorNameArray = "name";
    public static final String JSON_ARRAY = "result";
    // private JSONArray result;
    private JSONArray result1;
    //Spinner spinner1,spinner2;
    //private ArrayList<String> arrayList;
    private ArrayList<String> arrayList1;
public  static  String usernane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patien_name_list);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle(LoginActivity.email); // set the top title

        lv = (ListView) findViewById(R.id.my_list_id);
        arrayList1 = new ArrayList<String>();

        fetchingData();

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
     */

    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(PatienNameList.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



    void fetchingData(){


        String myURL = "https://zahedice14.000webhostapp.com/patient_name_selection/gettingnews.php";
        //JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>()
        //StringRequest stringRequest = new StringRequest(Request.Method.POST,myURL, new Response.Listener<String>(){
            /*
            @Override
            public void onResponse(String response) {

                final String[]   name = new String[response.length()];
               // final String[]     news_details = new String[response.length()];
                //final String[]    news_time = new String[response.length()];


                for(int i=0; i<response.length(); i++) {
                    try {


                        JSONObject jsonObject = (JSONObject) response.get(i);
                        name[i] = jsonObject.getString("name");
                       // news_details[i] = jsonObject.getString("Temperature");
                        //news_time[i] = jsonObject.getString("HeartRate");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                */
        StringRequest stringRequest = new StringRequest(Request.Method.POST,myURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result1= j.getJSONArray(JSON_ARRAY);
                    doctorname(result1);
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
                params.put("email", LoginActivity.email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void doctorname(JSONArray j) {
        arrayList1.clear();
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList1.add(json.getString(doctorNameArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // arrayList.add(0,"Select Employee");
        //spinner2.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList1));



        lv.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.mylistview,R.id.textviewforlist,arrayList1));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               usernane= arrayList1.get(i);
                Intent intent = new Intent(PatienNameList.this, MainActivity.class);
                 //intent.putExtra("username",nane);
                //intent.putExtra("MyDETAILS",news_details[i]);
                // intent.putExtra("MyTIME",news_time[i]);
                startActivity(intent);
            }

         });
    }
}
