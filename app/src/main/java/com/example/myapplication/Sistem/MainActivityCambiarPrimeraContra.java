package com.example.myapplication.Sistem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.MainActivity_navigation_admin;
import com.example.myapplication.NavegacionInicial;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.NavegacionSupervisor;

public class MainActivityCambiarPrimeraContra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena_first_time);

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