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

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListAdapterSiteSupervisor;

import java.util.ArrayList;
import java.util.List;

public class SitiosFragment extends Fragment {

    List<ListElementSite> elements;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.supervisor_fragment_sitios, container, false);
        Toolbar toolbar = view.findViewById(R.id.topAppBarSiteSuper);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // Inicializar RecyclerView y cargar datos
        init(view);

        return view;
    }

    public void init(View view) {
        elements = new ArrayList<>();
        elements.add(new ListElementSite("Lima","LIM-01","Activo","Lima","Carabayllo","Av. San Felipe","31501","Urbano","Fijo","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Lima","LIM-02","Activo","Lima","Carabayllo","Av. San Carlos","31502","Urbano","Fijo","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Lima","LIM-03","Activo","Lima","Carabayllo","Av. Los Incas","31503","Urbano","Fijo","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Cajamarca","CAJ-01","Activo","Cajamarca","Asunción","Calle Los Mirlos","31504","Rural","Móvil","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Cajamarca","CAJ-02","Activo","Cajamarca","Asunción","Calle Los Piris","31505","Rural","Móvil","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Cajamarca","CAJ-03","Activo","Cajamarca","Asunción","Calle Los Mirpus","31506","Rural","Móvil","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Arequipa","ARQ-01","Activo","Arequipa","Yanahuara","Av. Unión","31507","Urbano","Fijo","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Arequipa","ARQ-02","Activo","Caylloma","Caylloma","Av. Bayoneta","31508","Urbano","Fijo","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Arequipa","ARQ-03","Activo","Arequipa","Cayma","Av. La Paz","31509","Urbano","Móvil","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Tumbes","TUM-01","Activo","Tumbes","Tumbes","Av. Lobitos","31510","Rural","Fijo","-34.3213",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Lima", "LIM-03", "Activo", "Lima", "San Borja", "Av. Javier Prado Este", "15021", "Urbano", "Fijo", "-12.0897",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Arequipa", "ARQ-07", "Activo", "Arequipa", "Cerro Colorado", "Av. Ejército", "04001", "Urbano", "Móvil", "-16.4017",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Trujillo", "TRU-01", "Activo", "La Libertad", "Trujillo", "Av. España", "13001", "Urbano", "Fijo", "-8.1079",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Cusco", "CUS-01", "Activo", "Cusco", "Wanchaq", "Av. De La Cultura", "08001", "Urbano", "Móvil", "-13.5250",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Chiclayo", "CHI-01", "Activo", "Lambayeque", "Chiclayo", "Av. Balta", "14001", "Urbano", "Fijo", "-6.7712",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));
        elements.add(new ListElementSite("Puno", "PUN-01", "Activo", "Puno", "Puno", "Jr. Ayacucho", "21001", "Urbano", "Móvil", "-15.8439",-34.3213,56.3123, "-34.3213 ; 56.3123", "1/1/2022",""));

        // Añadir más elementos según sea necesario

        // Obtener referencia al RecyclerView
        recyclerView = view.findViewById(R.id.listElements);

        // Crear y configurar el adaptador y el LayoutManager
        ListAdapterSiteSupervisor listAdapter = new ListAdapterSiteSupervisor(elements, getContext(), item -> moveToDescription(item));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescription(ListElementSite item){
        Intent intent = new Intent(getContext(), MasDetallesSitioSupervisor.class);
        intent.putExtra("ListElementSite", item);
        startActivity(intent);
    }
}