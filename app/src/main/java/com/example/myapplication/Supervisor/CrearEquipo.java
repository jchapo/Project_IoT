package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Admin.MainActivity_new_site_admin;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class CrearEquipo extends AppCompatActivity {


    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;

    private MaterialAutoCompleteTextView selectTipoDeEquipo;

    ArrayAdapter<String> tipoEquipoAdapter;

    String[] tipoEquipoOptions = {"Router","Switch","Repetidor","Patch Pannel","Modem","Adaptador de red","Hub"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_activity_crear_equipo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Toolbar toolbar = findViewById(R.id.topAppBarNewEquipo);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView = findViewById(R.id.imageViewNewEquipment);
        imageView.setOnClickListener(v -> openFileChooser());

        selectTipoDeEquipo = findViewById(R.id.selectTipoDeEquipo);
        tipoEquipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,tipoEquipoOptions);

        selectTipoDeEquipo.setAdapter(tipoEquipoAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_supervisor_new_equipo, menu);
        return true;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private boolean areFieldsEmpty() {
        return selectTipoDeEquipo.getText().toString().isEmpty();
    }



}