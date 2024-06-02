package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Admin.MainActivity_0_NavigationAdmin;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.SuperAdmin.MainActivity_navigation_SuperAdmin;
import com.example.myapplication.Supervisor.NavegacionSupervisor;

public class NavegacionInicial extends AppCompatActivity {
    NavigationActivityViewModel navigationActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_navegacion_inicial);
        navigationActivityViewModel = new ViewModelProvider(NavegacionInicial.this) .get(NavigationActivityViewModel. class);

        Button supervButton = findViewById(R.id.Supervisor);
        Button adminButton = findViewById(R.id.Admin);
        Button superaButton = findViewById(R.id.SuperAdmin);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Código para iniciar la actividad Admin
                String inicio = "1";
                navigationActivityViewModel.getInicio().setValue(inicio);
                Intent intent = new Intent(NavegacionInicial.this, MainActivity_0_NavigationAdmin.class);
                intent.putExtra("inicio", inicio);
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