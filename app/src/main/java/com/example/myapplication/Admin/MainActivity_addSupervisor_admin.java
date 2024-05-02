package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.items.ListAdapterAddSuper;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_addSupervisor_admin extends AppCompatActivity {

    List<ListElementUser> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_add_supervisor);
        MaterialToolbar topAppBar = findViewById(R.id.topAppBarAddSuperSite);
        topAppBar.inflateMenu(R.menu.top_app_bar_admin_select);
        View view = getWindow().getDecorView().getRootView();
        init(view);

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.chooseSuper) {
                Toast.makeText(MainActivity_addSupervisor_admin.this, "Supervisor asignado", Toast.LENGTH_SHORT).show();
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
        elements.add(new ListElementUser("74567890", "Pedro", "Suares","Administrador", "Activo", "pedro_correo@gmail.com","978675678", "Calle Manzana"));
        elements.add(new ListElementUser("72903456", "Ana", "Suares","Supervisor", "Activo", "ana_correo@gmail.com","934567890", "Avenida Central"));
        elements.add(new ListElementUser("70234567", "Juan","Suares", "Administrador", "Activo", "juan_correo@gmail.com","956783421", "Calle Primavera"));
        elements.add(new ListElementUser("71234567", "María","Suares", "Supervisor", "Activo", "maria_correo@gmail.com","911234567", "Calle San José"));
        elements.add(new ListElementUser("79908765", "Carlos", "Suares","Administrador", "Activo", "carlos_correo@gmail.com","978564321", "Avenida Libertad"));
        elements.add(new ListElementUser("74895673", "Laura", "Suares","Supervisor", "Activo", "laura_correo@gmail.com","923456789", "Calle del Sol"));
        elements.add(new ListElementUser("71234567", "Roberto","Suares", "Administrador", "Activo", "roberto_correo@gmail.com","989765432", "Avenida del Parque"));
        elements.add(new ListElementUser("75789234", "Sofía", "Suares","Supervisor", "Activo", "sofia_correo@gmail.com","957684321", "Calle de la Luna"));
        elements.add(new ListElementUser("72543019", "Fernando","Suares", "Administrador", "Activo", "fernando_correo@gmail.com","978932156", "Avenida Central"));
        elements.add(new ListElementUser("70987654", "Ana","Suares", "Supervisor", "Activo", "ana_correo@gmail.com","976543218", "Calle de las Flores"));
        elements.add(new ListElementUser("74891230", "Elena","Suares", "Administrador", "Activo", "elena_correo@gmail.com","923456789", "Avenida de la Paz"));
        elements.add(new ListElementUser("71234567", "David","Suares", "Supervisor", "Activo", "david_correo@gmail.com","934567890", "Avenida del Bosque"));
        elements.add(new ListElementUser("75678901", "Lucía", "Suares","Administrador", "Activo", "lucia_correo@gmail.com","956783421", "Calle del Río"));
        elements.add(new ListElementUser("70234567", "Andrés", "Suares","Supervisor", "Activo", "andres_correo@gmail.com","911234567", "Calle de la Montaña"));
        elements.add(new ListElementUser("79908765", "Marta", "Suares","Administrador", "Activo", "marta_correo@gmail.com","978564321", "Avenida de la Costa"));
        elements.add(new ListElementUser("74895673", "Raquel", "Suares","Supervisor", "Activo", "raquel_correo@gmail.com","923456789", "Calle del Mar"));
        elements.add(new ListElementUser("71234567", "José","Suares", "Administrador", "Activo", "jose_correo@gmail.com","989765432", "Avenida del Lago"));
        elements.add(new ListElementUser("75789234", "Laura", "Suares","Supervisor", "Activo", "laura_correo@gmail.com","957684321", "Calle de la Playa"));
        elements.add(new ListElementUser("72543019", "Isabel", "Suares","Administrador", "Activo", "isabel_correo@gmail.com","978932156", "Avenida del Sol"));
        elements.add(new ListElementUser("70987654", "Roberto", "Suares","Supervisor", "Activo", "roberto_correo@gmail.com","976543218", "Calle de la Arena"));


        ListAdapterAddSuper simpleListAdapter = new ListAdapterAddSuper( this, elements);
        RecyclerView recyclerView = view.findViewById(R.id.listElementsUsersAdd);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(simpleListAdapter);
    }

    public void moveToDescription(ListElementUser item){
        Intent intent = new Intent(this,MainActivity_userprofile_admin.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }

}