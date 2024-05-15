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
        elements.add(new ListElementSite("Lima","LIM-01","Activo","Lima","Carabayllo","Av. San Felipe","Near","31501","Urbano","Fijo","2.1234","56.3123"));
        elements.add(new ListElementSite("Lima","LIM-02","Activo","Lima","Carabayllo","Av. San Carlos","Far","31502","Urbano","Fijo","19.2414","56.3123"));
        elements.add(new ListElementSite("Lima","LIM-03","Activo","Lima","Carabayllo","Av. Los Incas","Near","31503","Urbano","Fijo","21.1453","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-01","Activo","Cajamarca","Asunción","Calle Los Mirlos","Near","31504","Rural","Móvil","34.2313","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-02","Activo","Cajamarca","Asunción","Calle Los Piris","Far","31505","Rural","Móvil","29.3124","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-03","Activo","Cajamarca","Asunción","Calle Los Mirpus","Near","31506","Rural","Móvil","-31.2421","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-01","Activo","Arequipa","Yanahuara","Av. Unión","Far","31507","Urbano","Fijo","-32.1231","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-02","Activo","Caylloma","Caylloma","Av. Bayoneta","Near","31508","Urbano","Fijo","214.232","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-03","Activo","Arequipa","Cayma","Av. La Paz","Far","31509","Urbano","Móvil","-32.231","56.3123"));
        elements.add(new ListElementSite("Tumbes","TUM-01","Activo","Tumbes","Tumbes","Av. Lobitos","Far","31510","Rural","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Lima", "LIM-03", "Activo", "Lima", "San Borja", "Av. Javier Prado Este","Near", "15021", "Urbano", "Fijo", "-12.0897", "-77.0282"));
        elements.add(new ListElementSite("Arequipa", "ARQ-07", "Activo", "Arequipa", "Cerro Colorado", "Av. Ejército","Far", "04001", "Urbano", "Móvil", "-16.4017", "-71.5361"));
        elements.add(new ListElementSite("Trujillo", "TRU-01", "Activo", "La Libertad", "Trujillo", "Av. España","Near", "13001", "Urbano", "Fijo", "-8.1079", "-79.0225"));
        elements.add(new ListElementSite("Cusco", "CUS-01", "Activo", "Cusco", "Wanchaq", "Av. De La Cultura", "Far","08001", "Urbano", "Móvil", "-13.5250", "-71.9722"));
        elements.add(new ListElementSite("Chiclayo", "CHI-01", "Activo", "Lambayeque", "Chiclayo", "Av. Balta", "Far","14001", "Urbano", "Fijo", "-6.7712", "-79.8443"));
        elements.add(new ListElementSite("Puno", "PUN-01", "Activo", "Puno", "Puno", "Jr. Ayacucho","Near" ,"21001", "Urbano", "Móvil", "-15.8439", "-70.0190"));
        elements.add(new ListElementSite("Tacna", "TAC-01", "Activo", "Tacna", "Tacna", "Av. Bolognesi","Far", "23001", "Urbano", "Fijo", "-18.0097", "-70.2500"));
        elements.add(new ListElementSite("Iquitos", "IQT-01", "Activo", "Loreto", "Iquitos", "Av. Abelardo Quiñones","Far", "16001", "Urbano", "Móvil", "-3.7437", "-73.2519"));
        elements.add(new ListElementSite("Huancayo", "HUA-01", "Activo", "Junín", "Huancayo", "Jr. Real","Far", "12001", "Urbano", "Fijo", "-12.0650", "-75.2049"));
        elements.add(new ListElementSite("Ayacucho", "AYA-01", "Activo", "Ayacucho", "Ayacucho", "Av. Mariscal Sucre","Near", "05001", "Urbano", "Móvil", "-13.1587", "-74.2234"));

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