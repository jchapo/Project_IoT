package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListAdapterDevices;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class EquiposDeSitios extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ListElementDevices> elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_activity_equipos_de_sitios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });
        String titulo_equipo = (String) getIntent().getSerializableExtra("sitio_seleccionado");

        MaterialToolbar toolbar = findViewById(R.id.topAppBarSiteSuper);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Esto regresa a la actividad anterior
            }
        });

        // Obtén una referencia al TextView y establece el nuevo texto
        TextView equipoDeSitioTextView = findViewById(R.id.EquipodeSitio);
        String nuevoTexto = titulo_equipo;
        equipoDeSitioTextView.setText(nuevoTexto);

        initRecyclerView();


    }

    private void initRecyclerView() {
        elements = new ArrayList<>();
        elements.add(new ListElementDevices("SW145XS", "SWI103", "GILLAT", "T-GAP", "2024-04-18", "Switch con capacidad para soportar varios equipos.", "Switch Gillat FP-435"));
        elements.add(new ListElementDevices("RO82DAS", "SWI102", "CISCO", "C-SWS", "2024-04-19", "Router preparado para WIFI7", "Router CISCO WIFIMAX"));
        elements.add(new ListElementDevices("RE123FD", "RTI209", "HUAWEI", "HGW-004", "2024-04-20", "Gateway de Huawei para redes de fibra óptica.", "Huawei Fiber Gateway HGW-004"));


        // Añadir más elementos según sea necesario

        recyclerView = findViewById(R.id.listElementsEquiposdeSitio);
        ListAdapterDevices listAdapter = new ListAdapterDevices(elements, this, item -> moveToDescriptionDevice(item));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescriptionDevice(ListElementDevices item){
        Intent intent = new Intent(EquiposDeSitios.this, MasDetallesEquipos.class);
        intent.putExtra("ListElementDevices", item);
        startActivity(intent);
    }
}