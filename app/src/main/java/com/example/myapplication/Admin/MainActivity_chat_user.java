package com.example.myapplication.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

public class MainActivity_chat_user extends AppCompatActivity {

    TextView nameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_main_chat_users);

        //ListElementUser element = (ListElementUser) getIntent().getSerializableExtra("ListElement");
        //nameTextView = findViewById(R.id.topAppBarNameChat);


        //nameTextView.setText(element.getName());


        Toolbar toolbar = findViewById(R.id.topAppBarNameChat);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }





}