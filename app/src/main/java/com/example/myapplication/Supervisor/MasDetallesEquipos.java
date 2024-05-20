package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MasDetallesEquipos extends AppCompatActivity {


    TextView nombreEquipogrande;
    TextView nombreEquipo;
    TextView marca;
    TextView serie;
    TextView SKU;
    TextView fechaingreso;
    TextView datosequipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_activity_mas_detalles_equipos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListElementDevices element = (ListElementDevices) getIntent().getSerializableExtra("ListElementDevices");
        nombreEquipo = findViewById(R.id.textViewNombreEquipo);
        marca = findViewById(R.id.textViewMarca);
        serie = findViewById(R.id.textViewSerie);
        SKU = findViewById(R.id.textViewSku);
        fechaingreso = findViewById(R.id.textViewFechaIngreso);
        nombreEquipogrande = findViewById(R.id.nameTextViewDevice);
        datosequipo = findViewById(R.id.Datosdevice);


        String datosequipo1 = element.getMarca() + " / " + element.getModelo() + " / " + element.getSku();
        datosequipo.setText(datosequipo1);
        nombreEquipogrande.setText(element.getName());
        nombreEquipo.setText(element.getName());
        marca.setText(element.getMarca());
        serie.setText(element.getSerie());
        SKU.setText(element.getSku());
        fechaingreso.setText(element.getFechaIngreso());

        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfilSuper);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton buttonImagesSiteAdmin = findViewById(R.id.buttonImagesSiteSuper);
        buttonImagesSiteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear Intent para iniciar la actividad ImagenesSitio
                Intent intent = new Intent(MasDetallesEquipos.this, ImagenesSitio.class);
                // Iniciar la actividad ImagenesSitio con el Intent
                startActivity(intent);
            }
        });

        FloatingActionButton fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesEquipos.this, CrearReporte.class);
            intent.putExtra("tipo_reporte", "Equipo");
            startActivity(intent);
        });

        FloatingActionButton fabEdit2 = findViewById(R.id.fabeditar);
        fabEdit2.setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesEquipos.this, EditarEquipo.class);
            startActivity(intent);
        });

    }
}