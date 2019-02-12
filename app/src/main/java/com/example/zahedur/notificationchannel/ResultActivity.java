package com.example.zahedur.notificationchannel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {


    String message="zahed message ";
    TextView et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


         et1 = (TextView)findViewById(R.id.tv);

        if ( getIntent().getExtras()!=null){

            message = getIntent().getExtras().getString("message");

            if(message == null)
            {
                message = "No new message";
            }

        }

        et1.setText(message);

    }
}
