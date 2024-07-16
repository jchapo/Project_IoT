package com.example.myapplication.Supervisor;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CrearReporte_2_Sitio extends AppCompatActivity {

    private MaterialAutoCompleteTextView selectTypeSite;

    ArrayAdapter<String> typeSiteAdapter;

    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_activity_crear_reporte2_sitio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        boolean isEditing = false;

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarNewReport);
        if (isEditing) {
            topAppBar.inflateMenu(R.menu.top_app_bar_edit);
        } else {
            topAppBar.inflateMenu(R.menu.top_app_bar_new);
            topAppBar.setTitle("Nuevo Reporte de Sitio");
        }

        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance();

        // Obtener referencia al MaterialAutoCompleteTextView
        selectTypeSite = findViewById(R.id.selectTypeSite);

        // Cargar nombres de equipos desde Firestore
        loadSitiosFromFirestore();
    }

    private void loadSitiosFromFirestore() {
        db.collection("sitios").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> sitiosList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String nombreSitio = document.getString("name");
                    if (nombreSitio != null) {
                        sitiosList.add(nombreSitio);
                    }
                }
                typeSiteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sitiosList);
                selectTypeSite.setAdapter(typeSiteAdapter);
            } else {
                // Manejar el error
            }
        });
    }
}