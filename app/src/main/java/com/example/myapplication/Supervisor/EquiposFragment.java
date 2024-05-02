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
                Intent intent = new Intent(getContext(), CrearEquipo.class);
                startActivity(intent);
            }
        });

        return view;

    }

    public void init(View view) {
        elements = new ArrayList<>();
        elements.add(new ListElementDevices("SW145XS", "SWI103", "GILLAT", "T-GAP", "2024-04-18", "Switch con capacidad para soportar varios equipos.", "Switch Gillat FP-435"));
        elements.add(new ListElementDevices("RO82DAS", "SWI102", "CISCO", "C-SWS", "2024-04-19", "Router preparado para WIFI7", "Router CISCO WIFIMAX"));
        elements.add(new ListElementDevices("RE123FD", "RTI209", "HUAWEI", "HGW-004", "2024-04-20", "Gateway de Huawei para redes de fibra óptica.", "Huawei Fiber Gateway HGW-004"));
        elements.add(new ListElementDevices("SW301SK", "SWI405", "ZYXEL", "Z-SWT", "2024-04-21", "Switch avanzado para redes empresariales.", "ZYXEL Enterprise Switch Z-SWT"));
        elements.add(new ListElementDevices("RT456QA", "RTI502", "JUNIPER", "JN-RTR", "2024-04-22", "Router de alta capacidad para redes de gran tamaño.", "Juniper High-Performance Router JN-RTR"));
        elements.add(new ListElementDevices("RW907LS", "RTI109", "NETGEAR", "N-RTR", "2024-04-23", "Router inalámbrico de alto rendimiento.", "NETGEAR Wireless Router N-RTR"));
        elements.add(new ListElementDevices("GW234DE", "GW501", "CISCO", "C-GW", "2024-04-24", "Gateway Cisco para redes empresariales.", "Cisco Enterprise Gateway C-GW"));
        elements.add(new ListElementDevices("SW509YH", "SWI806", "HPE", "H-SWT", "2024-04-25", "Switch de alto rendimiento para centros de datos.", "HPE Datacenter Switch H-SWT"));
        elements.add(new ListElementDevices("RT123XE", "RTI102", "TP-LINK", "TPL-RTR", "2024-04-26", "Router TP-LINK con múltiples puertos Ethernet.", "TP-LINK Multi-Port Router TPL-RTR"));
        elements.add(new ListElementDevices("GW890PR", "GWI209", "D-LINK", "D-GW", "2024-04-27", "Gateway D-LINK para redes domésticas.", "D-LINK Home Gateway D-GW"));
        elements.add(new ListElementDevices("SW124HT", "SWI305", "CISCO", "C-SWT", "2024-04-28", "Switch Cisco con administración avanzada.", "Cisco Managed Switch C-SWT"));
        elements.add(new ListElementDevices("RO432MK", "RTI305", "NETGEAR", "N-ROU", "2024-04-29", "Router NETGEAR para redes de oficina.", "NETGEAR Office Router N-ROU"));
        elements.add(new ListElementDevices("RT764PE", "RTI702", "HUAWEI", "H-RTR", "2024-04-30", "Router Huawei con capacidad de firewall integrado.", "Huawei Firewall Router H-RTR"));
        elements.add(new ListElementDevices("GW903KT", "GWI402", "ZYXEL", "Z-GW", "2024-05-01", "Gateway ZYXEL con funciones avanzadas de seguridad.", "ZYXEL Secure Gateway Z-GW"));
        elements.add(new ListElementDevices("RT234YE", "RTI609", "JUNIPER", "JN-GW", "2024-05-02", "Gateway Juniper con alto rendimiento y escalabilidad.", "Juniper High-Performance Gateway JN-GW"));


        // Añadir más elementos según sea necesario

        // Obtener referencia al RecyclerView
        recyclerView = view.findViewById(R.id.listElementsDevice);

        // Crear y configurar el adaptador y el LayoutManager
        ListAdapterDevices listAdapter = new ListAdapterDevices(elements, getContext(), item -> moveToDescriptionDevice(item));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescriptionDevice(ListElementDevices item){
        Intent intent = new Intent(getContext(), MasDetallesEquipos.class);
        intent.putExtra("ListElementDevices", item);
        startActivity(intent);
    }

}