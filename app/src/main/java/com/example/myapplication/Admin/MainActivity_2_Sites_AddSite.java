package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListAdapterAddSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_2_Sites_AddSite extends AppCompatActivity {
    List<ListElementSite> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_add_site_admin);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBarAddSiteUser);
        topAppBar.inflateMenu(R.menu.top_app_bar_select);
        View view = getWindow().getDecorView().getRootView();
        init(view);

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.chooseSuper) {
                Toast.makeText(this, "Sitio asignado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                return false;
            }
            return false;
        });
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
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




        ListAdapterAddSite listAdapter = new ListAdapterAddSite(this,elements);
        RecyclerView recyclerView = view.findViewById(R.id.listElementsSites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescription(ListElementSite item){
        Intent intent = new Intent(this, MainActivity_2_Sites_SiteDetails.class);
        intent.putExtra("ListElementSite", item);
        startActivity(intent);
    }
}