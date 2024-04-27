package com.example.myapplication.Sistem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MainActivityCambiarPrimeraContra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_cambiar_contrasena_first_time);

        Button buttonLogin = findViewById(R.id.buttonAccept);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityCambiarPrimeraContra.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


}