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

public class LoginActivityFirstTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_user_first_time);

        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivityFirstTime.this, MainActivityCambiarPrimeraContra.class);
                startActivity(intent);
            }
        });

    }


}