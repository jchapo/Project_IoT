package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = inflater.inflate(R.layout.admin_fragment_equipos, container, false);
        Toolbar toolbar = view.findViewById(R.id.topAppBarDevices);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // Inicializar RecyclerView y cargar datos
        init(view);

        return view;
    }

    public void init(View view) {
        elements = new ArrayList<>();
        elements.add(new ListElementDevices("Lima", "LIM-01", "Marca 1", "Modelo 1", "2024-04-18", "Descripción 1", "Nombre 1"));
        elements.add(new ListElementDevices("Lima", "LIM-02", "Marca 2", "Modelo 2", "2024-04-19", "Descripción 2", "Nombre 2"));
        elements.add(new ListElementDevices("Cusco", "CUS-01", "Marca 3", "Modelo 3", "2024-04-20", "Descripción 3", "Nombre 3"));
        elements.add(new ListElementDevices("Arequipa", "ARE-01", "Marca 4", "Modelo 4", "2024-04-21", "Descripción 4", "Nombre 4"));
        elements.add(new ListElementDevices("Trujillo", "TRU-01", "Marca 5", "Modelo 5", "2024-04-22", "Descripción 5", "Nombre 5"));
        elements.add(new ListElementDevices("Puno", "PUN-01", "Marca 6", "Modelo 6", "2024-04-23", "Descripción 6", "Nombre 6"));
        elements.add(new ListElementDevices("Iquitos", "IQU-01", "Marca 7", "Modelo 7", "2024-04-24", "Descripción 7", "Nombre 7"));
        elements.add(new ListElementDevices("Piura", "PIU-01", "Marca 8", "Modelo 8", "2024-04-25", "Descripción 8", "Nombre 8"));
        elements.add(new ListElementDevices("Huancayo", "HUA-01", "Marca 9", "Modelo 9", "2024-04-26", "Descripción 9", "Nombre 9"));
        elements.add(new ListElementDevices("Chiclayo", "CHI-01", "Marca 10", "Modelo 10", "2024-04-27", "Descripción 10", "Nombre 10"));

        // Añadir más elementos según sea necesario

        // Obtener referencia al RecyclerView
        recyclerView = view.findViewById(R.id.listElementsDevice);

        // Crear y configurar el adaptador y el LayoutManager
        ListAdapterDevices listAdapter = new ListAdapterDevices(elements, getContext(), item -> moveToDescriptionDevice(item));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescriptionDevice(ListElementDevices item){
        Intent intent = new Intent(getContext(), MasDetallesSitioSupervisor.class);
        intent.putExtra("ListElementDevices", item);
        startActivity(intent);
    }
}