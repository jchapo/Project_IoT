package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;

public class MainActivity_chat_user extends AppCompatActivity {

    TextView nameDescriptionTextView;
    TextView userDescriptionTextView;
    TextView dniDescriptionTextView;
    TextView mailDescriptionTextView;
    TextView phoneDescriptionTextView;
    TextView addressDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_users);

        /**ListElementUser element = (ListElementUser) getIntent().getSerializableExtra("ListElement");
        nameDescriptionTextView = findViewById(R.id.fullNameTextView);
        userDescriptionTextView = findViewById(R.id.cargoTextView);
        dniDescriptionTextView = findViewById(R.id.textViewDNI);
        mailDescriptionTextView = findViewById(R.id.textViewMail);
        phoneDescriptionTextView = findViewById(R.id.textViewPhone);
        addressDescriptionTextView = findViewById(R.id.textViewAddress);

        nameDescriptionTextView.setText(element.getName());
        mailDescriptionTextView.setText(element.getMail());
        userDescriptionTextView.setText(element.getUser());
        dniDescriptionTextView.setText(element.getDni());
        phoneDescriptionTextView.setText(element.getPhone());
        addressDescriptionTextView.setText(element.getAddress());

        Toolbar toolbar = findViewById(R.id.topAppBarUsersPerfil);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí defines la nueva actividad a la que quieres navegar
                Intent intent = new Intent(MainActivity_chat_user.this, MainActivity_chat_user.class);
                startActivity(intent);
            }
        });**/
    }

}