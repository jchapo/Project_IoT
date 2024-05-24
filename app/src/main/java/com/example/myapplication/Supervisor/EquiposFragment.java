package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListAdapterDevices;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class EquiposFragment extends Fragment {



    List<ListElementDevices> elements;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.supervisor_fragment_equipos, container, false);

        Toolbar toolbar = view.findViewById(R.id.topAppBarDevices);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // Inicializar RecyclerView y cargar datos
        init(view);

        FloatingActionButton fab = view.findViewById(R.id.agregarEquipofloatingActionButton);

        // Agregar OnClickListener al botón flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad Crear Reporte
                Intent intent = new Intent(getContext(), CrearEquipo_2.class);
                startActivity(intent);
            }
        });

        return view;

    }

    public void init(View view) {
        elements = new ArrayList<>();
        elements.add(new ListElementDevices("SW145XS", "SWI103", "GILLAT", "T-GAP", "2024-04-18", "Switch con capacidad para soportar varios equipos.", "Switch Gillat FP-435", "Activo"));
        elements.add(new ListElementDevices("RO82DAS", "SWI102", "CISCO", "C-SWS", "2024-04-19", "Router preparado para WIFI7", "Router CISCO WIFIMAX", "Activo"));
        elements.add(new ListElementDevices("RE123FD", "RTI209", "HUAWEI", "HGW-004", "2024-04-20", "Gateway de Huawei para redes de fibra óptica.", "Huawei Fiber Gateway HGW-004", "Activo"));
        elements.add(new ListElementDevices("SW301SK", "SWI405", "ZYXEL", "Z-SWT", "2024-04-21", "Switch avanzado para redes empresariales.", "ZYXEL Enterprise Switch Z-SWT", "Activo"));
        elements.add(new ListElementDevices("RT456QA", "RTI502", "JUNIPER", "JN-RTR", "2024-04-22", "Router de alta capacidad para redes de gran tamaño.", "Juniper High-Performance Router JN-RTR", "Activo"));
        elements.add(new ListElementDevices("RW907LS", "RTI109", "NETGEAR", "N-RTR", "2024-04-23", "Router inalámbrico de alto rendimiento.", "NETGEAR Wireless Router N-RTR", "Activo"));
        elements.add(new ListElementDevices("GW234DE", "GW501", "CISCO", "C-GW", "2024-04-24", "Gateway Cisco para redes empresariales.", "Cisco Enterprise Gateway C-GW", "Activo"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT", "Activo"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT", "Activo"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT", "Activo"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT", "Activo"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT", "Activo"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT", "Activo"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT", "Activo"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT", "Activo"));


        // Añadir más elementos según sea necesario

        // Obtener referencia al RecyclerView
        recyclerView = view.findViewById(R.id.listElementsDevice);

        // Crear y configurar el adaptador y el LayoutManager
        ListAdapterDevices listAdapter = new ListAdapterDevices(elements, getContext(), item -> moveToDescriptionDevice(item));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescriptionDevice(ListElementDevices item){
        Intent intent = new Intent(getContext(), MasDetallesEquipos_2.class);
        intent.putExtra("ListElementDevices", item);
        startActivity(intent);
    }

}