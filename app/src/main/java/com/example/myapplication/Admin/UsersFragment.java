package com.example.myapplication.Admin;

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

import com.example.myapplication.Admin.items.ListAdapter;
import com.example.myapplication.Admin.items.ListElement;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    List<ListElement> elements;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_admin_users, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addUser) {
            Intent intent = new Intent(requireContext(), MainActivity_new_user_admin.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        Toolbar toolbar = view.findViewById(R.id.topAppBarUsers);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        init(view);
        return view;
    }

    public void init(View view) {
        elements = new ArrayList<>();
        elements.add(new ListElement("74567890", "Pedro", "Administrador", "Activo", "pedro_correo@gmail.com","978675678", "Calle Manzana"));
        elements.add(new ListElement("72903456", "Ana", "Supervisor", "Activo", "ana_correo@gmail.com","934567890", "Avenida Central"));
        elements.add(new ListElement("70234567", "Juan", "Administrador", "Activo", "juan_correo@gmail.com","956783421", "Calle Primavera"));
        elements.add(new ListElement("71234567", "María", "Supervisor", "Activo", "maria_correo@gmail.com","911234567", "Calle San José"));
        elements.add(new ListElement("79908765", "Carlos", "Administrador", "Activo", "carlos_correo@gmail.com","978564321", "Avenida Libertad"));
        elements.add(new ListElement("74895673", "Laura", "Supervisor", "Activo", "laura_correo@gmail.com","923456789", "Calle del Sol"));
        elements.add(new ListElement("71234567", "Roberto", "Administrador", "Activo", "roberto_correo@gmail.com","989765432", "Avenida del Parque"));
        elements.add(new ListElement("75789234", "Sofía", "Supervisor", "Activo", "sofia_correo@gmail.com","957684321", "Calle de la Luna"));
        elements.add(new ListElement("72543019", "Fernando", "Administrador", "Activo", "fernando_correo@gmail.com","978932156", "Avenida Central"));
        elements.add(new ListElement("70987654", "Ana", "Supervisor", "Activo", "ana_correo@gmail.com","976543218", "Calle de las Flores"));
        elements.add(new ListElement("74891230", "Elena", "Administrador", "Activo", "elena_correo@gmail.com","923456789", "Avenida de la Paz"));
        elements.add(new ListElement("71234567", "David", "Supervisor", "Activo", "david_correo@gmail.com","934567890", "Avenida del Bosque"));
        elements.add(new ListElement("75678901", "Lucía", "Administrador", "Activo", "lucia_correo@gmail.com","956783421", "Calle del Río"));
        elements.add(new ListElement("70234567", "Andrés", "Supervisor", "Activo", "andres_correo@gmail.com","911234567", "Calle de la Montaña"));
        elements.add(new ListElement("79908765", "Marta", "Administrador", "Activo", "marta_correo@gmail.com","978564321", "Avenida de la Costa"));
        elements.add(new ListElement("74895673", "Raquel", "Supervisor", "Activo", "raquel_correo@gmail.com","923456789", "Calle del Mar"));
        elements.add(new ListElement("71234567", "José", "Administrador", "Activo", "jose_correo@gmail.com","989765432", "Avenida del Lago"));
        elements.add(new ListElement("75789234", "Laura", "Supervisor", "Activo", "laura_correo@gmail.com","957684321", "Calle de la Playa"));
        elements.add(new ListElement("72543019", "Isabel", "Administrador", "Activo", "isabel_correo@gmail.com","978932156", "Avenida del Sol"));
        elements.add(new ListElement("70987654", "Roberto", "Supervisor", "Activo", "roberto_correo@gmail.com","976543218", "Calle de la Arena"));


        ListAdapter listAdapter = new ListAdapter(elements, getContext(), new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListElement item) {
                moveToDescription(item);
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.listElements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescription(ListElement item){
        Intent intent = new Intent(getContext(),MainActivity_userprofile_admin.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
}
