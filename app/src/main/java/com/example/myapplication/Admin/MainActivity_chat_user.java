package com.example.myapplication.Admin;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity_chat_user extends AppCompatActivity {

    TextView nameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_users);

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