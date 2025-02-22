package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Supervisor.objetos.ListElementReportes;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class MasDetallesReportes extends AppCompatActivity {

    TextView textViewdescripcion;
    TextView textViewEquipo1;
    TextView name;
    TextView completo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_activity_mas_detalles_reportes);
        ListElementReportes element = (ListElementReportes) getIntent().getSerializableExtra("ListElementReporte");
        textViewdescripcion = findViewById(R.id.textViewdescripcion);
        textViewEquipo1 = findViewById(R.id.textViewEquipo1);
        name = findViewById(R.id.nameTextViewSite);
        String datosrepor = element.getSitio() + "/" + element.getFecha_creacion() + "/" + element.getSupervisor_creador();

        completo = findViewById(R.id.addressTextViewSite);
        completo.setText(datosrepor);
        textViewdescripcion.setText(element.getDescripcion());
        textViewEquipo1.setText(element.getEquipo_objetivo());
        name.setText(element.getNombre_reporte());
        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfilSuper);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtener referencia al botón de imágenes
        ImageButton buttonImagesSiteAdmin = findViewById(R.id.buttonImagesReporSuper);

        // Agregar un OnClickListener al botón de imágenes
        buttonImagesSiteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear Intent para iniciar la actividad ImagenesSitio
                Intent intent = new Intent(MasDetallesReportes.this, ImagenesSitio.class);
                // Iniciar la actividad ImagenesSitio con el Intent
                startActivity(intent);
            }
        });

        FloatingActionButton fabEdit = findViewById(R.id.fabGenerarReporte1);
        fabEdit.setOnClickListener(v -> {
            actualizarReporteInactivo(element.getNombre_reporte());
        });

    }

    private void actualizarReporteInactivo(String nombreReporte) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reportes").document(nombreReporte)
                .update("status", "Resuelto")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MasDetallesReportes.this, "Reporte actualizado a Resuelto", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MasDetallesReportes.this, "Error al actualizar el reporte", Toast.LENGTH_SHORT).show();
                });
    }

}