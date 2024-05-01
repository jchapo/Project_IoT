package com.example.myapplication.Sistem;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

public class MainActivity_notificacion_user extends AppCompatActivity {

    TextView nameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_notificacion_user);

        //ListElementUser element = (ListElementUser) getIntent().getSerializableExtra("ListElement");
        //nameTextView = findViewById(R.id.topAppBarNameChat);


        //nameTextView.setText(element.getName());


        Toolbar toolbar = findViewById(R.id.topAppBarNameNotification);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}