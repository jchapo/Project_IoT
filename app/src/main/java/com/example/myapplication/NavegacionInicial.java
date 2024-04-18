package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Admin.MainActivity_navigation_admin;
import com.example.myapplication.SuperAdmin.MainActivity_navigation_SuperAdmin;
import com.example.myapplication.Supervisor.NavegacionSupervisor;

public class NavegacionInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion_inicial);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button supervButton = findViewById(R.id.Supervisor);
        Button adminButton = findViewById(R.id.Admin);
        Button superaButton = findViewById(R.id.SuperAdmin);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para iniciar la actividad Admin
                Intent intent = new Intent(NavegacionInicial.this, MainActivity_navigation_admin.class);
                startActivity(intent);
            }
        });

        supervButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NavegacionInicial.this, NavegacionSupervisor.class);
                startActivity(intent);
            }
        });


        superaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para iniciar la actividad Admin
                Intent intent = new Intent(NavegacionInicial.this, MainActivity_navigation_SuperAdmin.class);
                startActivity(intent);
            }
        });

    }


}