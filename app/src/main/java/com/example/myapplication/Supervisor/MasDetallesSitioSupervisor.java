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

import com.example.myapplication.Admin.MainActivity_navigation_admin;
import com.example.myapplication.Admin.MainActivity_siteprofile_admin;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MasDetallesSitioSupervisor extends AppCompatActivity {
    TextView nameTextViewSite;
    TextView addressDescriptionTextView;
    TextView tipoZonatextView;
    TextView tipoSitiotextView;
    TextView latitud;
    TextView longitud;
    TextView ubigeo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masdetalles_sitio_supervisor);

        ListElementSite element = (ListElementSite) getIntent().getSerializableExtra("ListElementSite");
        nameTextViewSite = findViewById(R.id.nameTextViewSite);
        addressDescriptionTextView = findViewById(R.id.addressTextViewSite);
        tipoZonatextView = findViewById(R.id.textViewTipoZona);
        tipoSitiotextView = findViewById(R.id.textViewTipoSitio1);
        latitud = findViewById(R.id.textViewLatitud);
        longitud = findViewById(R.id.textViewLongitud);
        ubigeo = findViewById(R.id.textViewUbigeo);


        nameTextViewSite.setText(element.getName());
        String fullDirection = element.getDepartment() + " " + element.getProvince() + " " + element.getDistrict();
        addressDescriptionTextView.setText(fullDirection);
        tipoZonatextView.setText(element.getZonetype());
        tipoSitiotextView.setText(element.getSitetype());
        //latitud.setText(element.getLatitud());
        //longitud.setText(element.getLongitud());
        ubigeo.setText(element.getUbigeo());


        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfilSuper);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtener referencia al botón de imágenes
        ImageButton buttonImagesSiteAdmin = findViewById(R.id.buttonImagesSiteSuper);

        // Agregar un OnClickListener al botón de imágenes
        buttonImagesSiteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear Intent para iniciar la actividad ImagenesSitio
                Intent intent = new Intent(MasDetallesSitioSupervisor.this, ImagenesSitio.class);
                // Iniciar la actividad ImagenesSitio con el Intent
                startActivity(intent);
            }
        });

    }

}