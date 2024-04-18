package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Supervisor.objetos.ListAdapterSiteSupervisor;
import com.example.myapplication.Supervisor.objetos.ListElementReportes;
import com.example.myapplication.Supervisor.objetos.ListAdapterReportesSupervisor;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ReportesFragment extends Fragment {


    List<ListElementReportes> elementsReportes;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reportes, container, false);
        Toolbar toolbar = view.findViewById(R.id.topAppBarSupervisorReportes);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // Inicializar RecyclerView y cargar datos
        init(view);

        return view;
    }

    public void init(View view) {
        elementsReportes = new ArrayList<>();
        elementsReportes.add(new ListElementReportes("RPT-101","ARP-101","13/04/24","17/04/24","Pablo"));
        elementsReportes.add(new ListElementReportes("RPT-102","ARP-102","12/04/24","17/04/24","Pablo"));
        elementsReportes.add(new ListElementReportes("RPT-103","ARP-103","11/04/24","13/04/24","Sergio"));
        elementsReportes.add(new ListElementReportes("RPT-104","CAJ-101","10/04/24","11/04/24","Juan"));
        elementsReportes.add(new ListElementReportes("RPT-105","CAJ-102","09/04/24","12/04/24","Juan"));
        elementsReportes.add(new ListElementReportes("RPT-106","CAJ-103","06/04/24","11/04/24","Juan"));
        elementsReportes.add(new ListElementReportes("RPT-107","LIM-101","03/04/24","10/04/24","Diego"));
        elementsReportes.add(new ListElementReportes("RPT-108","LIM-102","12/04/24","14/04/24","Diego"));
        elementsReportes.add(new ListElementReportes("RPT-109","LIM-103","16/04/24","17/04/24","Sergio"));

        // Añadir más elementos según sea necesario

        // Obtener referencia al RecyclerView
        recyclerView = view.findViewById(R.id.listReportes);

        // Crear y configurar el adaptador y el LayoutManager
        ListAdapterReportesSupervisor listAdapter = new ListAdapterReportesSupervisor(elementsReportes, getContext(), item -> moveToDescription(item));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescription(ListElementReportes item){
        Intent intent = new Intent(getContext(), MasDetallesReportes.class);
        intent.putExtra("ListElementReporte", item);
        startActivity(intent);
    }
}