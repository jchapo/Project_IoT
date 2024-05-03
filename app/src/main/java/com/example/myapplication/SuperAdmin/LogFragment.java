package com.example.myapplication.SuperAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.MainActivity_new_site_admin;
import com.example.myapplication.Admin.MainActivity_siteprofile_admin;
import com.example.myapplication.Admin.items.ListAdapterSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LogFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.superadmin_fragment_log, container, false);
        Toolbar toolbar = view.findViewById(R.id.topAppBarLog);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        init(view);

        return view;
    }

    public void init(View view) {
        elements = new ArrayList<>();

        elements.add(new ListElementSite("Lima","LIM-01","Activo","Lima","Carabayllo","Av. San Felipe","location","31501","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Lima","LIM-02","Activo","Lima","Carabayllo","Av. San Carlos","location","31502","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Lima","LIM-03","Activo","Lima","Carabayllo","Av. Los Incas","location","31503","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-01","Activo","Cajamarca","Asunción","Calle Los Mirlos","location","31504","Rural","Móvil","-34.3213","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-02","Activo","Cajamarca","Asunción","Calle Los Piris","location","31505","Rural","Móvil","-34.3213","56.3123"));
        elements.add(new ListElementSite("Cajamarca","CAJ-03","Activo","Cajamarca","Asunción","Calle Los Mirpus","location","31506","Rural","Móvil","-34.3213","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-01","Activo","Arequipa","Yanahuara","Av. Unión","location","31507","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-02","Activo","Caylloma","Caylloma","Av. Bayoneta","location","31508","Urbano","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Arequipa","ARQ-03","Activo","Arequipa","Cayma","Av. La Paz","location","31509","Urbano","Móvil","-34.3213","56.3123"));
        elements.add(new ListElementSite("Tumbes","TUM-01","Activo","Tumbes","Tumbes","Av. Lobitos","location","31510","Rural","Fijo","-34.3213","56.3123"));
        elements.add(new ListElementSite("Lima","LIM-04","Activo","Lima","Miraflores","Av. Larco","location","31509","Urbano","Fijo","-12.1212","-76.1234"));
        elements.add(new ListElementSite("Lima","LIM-05","Activo","Lima","San Borja","Av. Aviación","location","31510","Urbano","Fijo","-12.1212","-76.1234"));
        elements.add(new ListElementSite("Lima","LIM-06","Activo","Lima","Surco","Av. Primavera","location","31511","Urbano","Fijo","-12.1212","-76.1234"));
        elements.add(new ListElementSite("Cusco","CUS-01","Activo","Cusco","Wanchaq","Av. de la Cultura","location","31512","Urbano","Fijo","-13.5252","-71.9723"));
        elements.add(new ListElementSite("Cusco","CUS-02","Activo","Cusco","San Sebastián","Av. Tomasa Tito Condemayta","location","31513","Urbano","Fijo","-13.5252","-71.9723"));
        elements.add(new ListElementSite("Cusco","CUS-03","Activo","Cusco","San Jerónimo","Av. Los Incas","location","31514","Urbano","Fijo","-13.5252","-71.9723"));
        elements.add(new ListElementSite("Trujillo","TRJ-01","Activo","La Libertad","Trujillo","Av. España","location","31515","Urbano","Fijo","-8.1152","-79.0299"));
        elements.add(new ListElementSite("Trujillo","TRJ-02","Activo","La Libertad","Huanchaco","Av. La Ribera","location","31516","Urbano","Fijo","-8.1152","-79.0299"));
        elements.add(new ListElementSite("Trujillo","TRJ-03","Activo","La Libertad","Moche","Av. Huaca del Sol","location","31517","Urbano","Fijo","-8.1152","-79.0299"));
        elements.add(new ListElementSite("Piura","PIU-01","Activo","Piura","Piura","Av. Grau","location","31518","Urbano","Fijo","-5.1945","-80.6328"));




        ListAdapterSite listAdapter = new ListAdapterSite(elements, getContext(), item -> moveToDescription(item));
        RecyclerView recyclerView = view.findViewById(R.id.listElements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescription(ListElementSite item){
        Intent intent = new Intent(getContext(), MainActivity_siteprofile_admin.class);
        intent.putExtra("ListElementSite", item);
        startActivity(intent);
    }
}
