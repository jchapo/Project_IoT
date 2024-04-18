package com.example.myapplication.Admin;

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

import com.example.myapplication.Admin.items.ListAdapterSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SitesFragment extends Fragment {

    List<ListElementSite> elements;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_admin_sites, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sites, container, false);
        Toolbar toolbar = view.findViewById(R.id.topAppBarSite);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        init(view);
        FloatingActionButton agregarSitioButton = view.findViewById(R.id.agregarSitiofloatingActionButton);
        agregarSitioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí cambia "NuevaActividad" por la clase de la actividad a la que deseas cambiar
                Intent intent = new Intent(getActivity(), MainActivity_new_site_admin.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void init(View view) {
        elements = new ArrayList<>();
        elements.add(new ListElementSite("Lima","LIM-01","Activo","Lima","Carabayllo","Av. San Felipe","31501","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Lima","LIM-02","Activo","Lima","Carabayllo","Av. San Carlos","31502","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Lima","LIM-03","Activo","Lima","Carabayllo","Av. Los Incas","31503","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-01","Activo","Cajamarca","Asunción","Calle Los Mirlos","31504","Rural","Móvil","-34.3213","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-02","Activo","Cajamarca","Asunción","Calle Los Piris","31505","Rural","Móvil","-34.3213","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-03","Activo","Cajamarca","Asunción","Calle Los Mirpus","31506","Rural","Móvil","-34.3213","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-01","Activo","Arequipa","Yanahuara","Av. Unión","31507","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-02","Activo","Caylloma","Caylloma","Av. Bayoneta","31508","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-03","Activo","Arequipa","Cayma","Av. La Paz","31509","Urbano","Móvil","-34.3213","56.3123"));
        elements.add(new ListElementSite("Tumbes","TUM-01","Activo","Tumbes","Tumbes","Av. Lobitos","31510","Rural","Fijo","-34.3213","56.3123"));


        ListAdapterSite listAdapter = new ListAdapterSite(elements, getContext(), item -> moveToDescription(item));
        RecyclerView recyclerView = view.findViewById(R.id.listElements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescription(ListElementSite item){
        Intent intent = new Intent(getContext(),MainActivity_siteprofile_admin.class);
        intent.putExtra("ListElementSite", item);
        startActivity(intent);
    }
}