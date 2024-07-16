package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Admin.MainActivity_0_NavigationAdmin;
import com.example.myapplication.NavegacionInicial;
import com.example.myapplication.R;

public class CrearReporte_previo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_activity_crear_reporte_previo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button reporte_sitio = findViewById(R.id.Crear_Reporte_Sitio);
        Button reporte_equipo = findViewById(R.id.Crear_Reporte_Equipo);


        reporte_sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CrearReporte_previo.this, CrearReporte_2_Sitio.class);
                startActivity(intent);
            }
        });

        reporte_equipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CrearReporte_previo.this, CrearReporte_2_Equipo.class);
                startActivity(intent);
            }
        });
    }
}