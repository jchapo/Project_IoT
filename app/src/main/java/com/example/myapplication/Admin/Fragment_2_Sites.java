package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Fragment_2_Sites extends Fragment {

    private List<ListElementSite> activeSites;
    private List<ListElementSite> inactiveSites;
    private ListAdapterSite listAdapterSites;
    private RecyclerView recyclerViewSites;

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
        View view = inflater.inflate(R.layout.admin_fragment_sites, container, false);
        setHasOptionsMenu(true);
        init(view);
        FloatingActionButton agregarSitioButton = view.findViewById(R.id.agregarSitiofloatingActionButton);
        agregarSitioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí cambia "NuevaActividad" por la clase de la actividad a la que deseas cambiar
                Intent intent = new Intent(getActivity(), MainActivity_2_Sites_NewSite.class);
                intent.putExtra("isEditing", false);
                startActivity(intent);
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutSites);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterSites.setItems(activeSites);
                        listAdapterSites.notifyDataSetChanged();
                        break;
                    case 1:
                        listAdapterSites.setItems(inactiveSites);
                        listAdapterSites.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    public void init(View view) {
        activeSites = new ArrayList<>();
        inactiveSites = new ArrayList<>();

        activeSites.add(new ListElementSite("Lima","LIM-01","Activo","Lima","Carabayllo","Av. San Felipe","location","31501","Urbano","Fijo","-34.3213","56.3123"));
        activeSites.add(new ListElementSite("Lima","LIM-02","Activo","Lima","Carabayllo","Av. San Carlos","location","31502","Urbano","Fijo","-34.3213","56.3123"));
        activeSites.add(new ListElementSite("Lima","LIM-03","Activo","Lima","Carabayllo","Av. Los Incas","location","31503","Urbano","Fijo","-34.3213","56.3123"));
        activeSites.add(new ListElementSite("Cajamarca","CAJ-01","Activo","Cajamarca","Asunción","Calle Los Mirlos","location","31504","Rural","Móvil","-34.3213","56.3123"));
        activeSites.add(new ListElementSite("Cajamarca","CAJ-02","Activo","Cajamarca","Asunción","Calle Los Piris","location","31505","Rural","Móvil","-34.3213","56.3123"));


        inactiveSites.add(new ListElementSite("Cajamarca","CAJ-03","Activo","Cajamarca","Asunción","Calle Los Mirpus","location","31506","Rural","Móvil","-34.3213","56.3123"));
        inactiveSites.add(new ListElementSite("Arequipa","ARQ-01","Activo","Arequipa","Yanahuara","Av. Unión","location","31507","Urbano","Fijo","-34.3213","56.3123"));
        inactiveSites.add(new ListElementSite("Arequipa","ARQ-02","Activo","Caylloma","Caylloma","Av. Bayoneta","location","31508","Urbano","Fijo","-34.3213","56.3123"));

        // Initialize the adapter and RecyclerView
        listAdapterSites = new ListAdapterSite(activeSites, getContext(), item -> moveToDescription(item));
        recyclerViewSites = view.findViewById(R.id.listElementsSites);
        recyclerViewSites.setHasFixedSize(true);
        recyclerViewSites.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSites.setAdapter(listAdapterSites);
    }

    public void moveToDescription(ListElementSite item){
        Intent intent = new Intent(getContext(), MainActivity_2_Sites_SiteDetails.class);
        intent.putExtra("ListElementSite", item);
        startActivity(intent);
    }
}
