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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListAdapterNotificaciones;
import com.example.myapplication.Admin.items.ListElementNotificaciones;
import com.example.myapplication.R;
import com.example.myapplication.Sistem.MainActivity_notificacion_user;

import java.util.ArrayList;
import java.util.List;

public class Fragment_4_Notifications extends Fragment {
    List<ListElementNotificaciones> elementsForms;
    List<ListElementNotificaciones> elementsUsers;

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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.admin_fragment_notifications, container, false);
        setHasOptionsMenu(true);
        init(view);
        


        return view;
    }

    public void init(View view) {
        elementsUsers = new ArrayList<>();
        elementsUsers.add(new ListElementNotificaciones("Supervisor asignado", "Has asignado a Pablo flores a un nuevo trabajo", "11:11 a.m."));
        elementsUsers.add(new ListElementNotificaciones("Supervisro asignado", "Has asignado a Sergio Maldonado a un nuevo proyecto", "12:59 p.m."));

        elementsForms = new ArrayList<>();
        elementsForms.add(new ListElementNotificaciones("Formulario Completado", "Pablo Flores ha llenado un formulario", "10:20 a.m."));
        elementsForms.add(new ListElementNotificaciones("Formulario Completado", "Sergio Maldonado ha completado un formulario", "9:10 a.m."));



        List<ListElementNotificaciones> combinedElements = new ArrayList<>();
        combinedElements.addAll(elementsForms);
        combinedElements.addAll(elementsUsers);

        ListAdapterNotificaciones listAdapter = new ListAdapterNotificaciones(combinedElements, getContext(), item -> moveToDescription(item));
        RecyclerView recyclerView = view.findViewById(R.id.listElementsNotiForms);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);



    }

    public void moveToDescription(ListElementNotificaciones item){
        Intent intent = new Intent(getContext(), MainActivity_notificacion_user.class);
        intent.putExtra("ListElement",item);
        startActivity(intent);
    }
}
