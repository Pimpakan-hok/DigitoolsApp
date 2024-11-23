package com.example.digitoolsapp;

import static com.example.digitoolsapp.PostPage.DESC;
import static com.example.digitoolsapp.PostPage.TIME;
import static com.example.digitoolsapp.PostPage.TITLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetailsPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post);
        Intent intent = getIntent();
        String topic = intent.getStringExtra(TITLE);
        String time = intent.getStringExtra(TIME);
        String des = intent.getStringExtra(DESC);
//        Button map = findViewById(R.id.mapButton);
//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        ImageButton back = findViewById(R.id.backpage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(DetailsPostActivity.this,MainActivity.class);
                startActivity(back);            }
        });
        TextView topics = findViewById(R.id.Titlepost);
        TextView timesr = findViewById(R.id.timepost);
        TextView desc = findViewById(R.id.Despost);

        topics.setText(topic);
        timesr.setText(time);
        desc.setText(des);

    }
}