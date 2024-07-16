package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListElementReportes;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CrearReporte_2_Equipo extends AppCompatActivity {

    private MaterialAutoCompleteTextView selectTypeEquipo;
    private EditText descripcionEditText;

    ArrayAdapter<String> typeDeviceAdapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_activity_crear_reporte2_equipo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarNewReport);
        topAppBar.inflateMenu(R.menu.top_app_bar_new);
        topAppBar.setTitle("Nuevo Reporte de Equipo");
        topAppBar.setNavigationOnClickListener(v -> finish());

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createNewTopAppBar) {
                crearNuevoReporte();
                return true;
            }
            return false;
        });

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Obtener referencia al MaterialAutoCompleteTextView y EditText
        selectTypeEquipo = findViewById(R.id.selectTypeEquipo);
        descripcionEditText = findViewById(R.id.TextArea1);

        // Cargar nombres de equipos desde Firestore
        loadEquiposFromFirestore();
    }

    private void loadEquiposFromFirestore() {
        db.collection("equipos").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> equiposList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String nombreEquipo = document.getString("nameEquipo");
                    if (nombreEquipo != null) {
                        equiposList.add(nombreEquipo);
                    }
                }
                typeDeviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, equiposList);
                selectTypeEquipo.setAdapter(typeDeviceAdapter);
            } else {
                Toast.makeText(this, "Error al cargar equipos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearNuevoReporte() {
        String equipoObjetivo = selectTypeEquipo.getText().toString();
        String descripcion = descripcionEditText.getText().toString();

        if (equipoObjetivo.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generar nombre de reporte aleatorio
        String nombreReporte = "RPT-" + (new Random().nextInt(900) + 100);
        // Obtener la fecha actual
        String fechaCreacion = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ListElementReportes nuevoReporte = new ListElementReportes(
                nombreReporte,
                "",  // Asigna un valor adecuado para 'sitio'
                fechaCreacion,
                "",
                "Pablo",
                equipoObjetivo,
                descripcion
        );

        db.collection("reportes").document(nombreReporte).set(nuevoReporte).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Reporte creado con éxito", Toast.LENGTH_SHORT).show();
            // Navegar a la actividad NavegacionSupervisor
            Intent intent = new Intent(CrearReporte_2_Equipo.this, NavegacionSupervisor.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error al crear el reporte", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_new, menu);
        return true;
    }
}

