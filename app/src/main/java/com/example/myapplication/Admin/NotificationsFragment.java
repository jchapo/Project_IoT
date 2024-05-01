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

import com.example.myapplication.Admin.items.ListAdapterNotificaciones;
import com.example.myapplication.Admin.items.ListElementNotificaciones;
import com.example.myapplication.R;
import com.example.myapplication.Sistem.MainActivity_notificacion_user;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    List<ListElementNotificaciones> elements;

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
        Toolbar toolbar = view.findViewById(R.id.topAppBarUsers);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        init(view);
        


        return view;
    }

    public void init(View view) {
        elements = new ArrayList<>();
        elements.add(new ListElementNotificaciones("Formulario Completado", "Pablo Flores ha llenado un formulario", "10:20 a.m."));
        elements.add(new ListElementNotificaciones("Formulario Completado", "Sergio Maldonado ha completado un formulario", "9:10 a.m."));



        ListAdapterNotificaciones listAdapter = new ListAdapterNotificaciones(elements, getContext(), item -> moveToDescription(item));
        RecyclerView recyclerView = view.findViewById(R.id.listElementsAlerts);
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
