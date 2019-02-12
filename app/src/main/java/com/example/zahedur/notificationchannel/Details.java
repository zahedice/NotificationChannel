package com.example.zahedur.notificationchannel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    TextView title;
    TextView time;
    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = (TextView)findViewById(R.id.mytitle);
       // time = (TextView)findViewById(R.id.mytime);
        //details = (TextView)findViewById(R.id.mydetails);

        String  _title = getIntent().getStringExtra("MyTITLE");
        //String  _news = getIntent().getStringExtra("MyDETAILS");
        //String  _time = getIntent().getStringExtra("MyTIME");


        title.setText(_title);
      ///  time.setText(_time+"\n\n");
        //details.setText(_news);

    }
}
