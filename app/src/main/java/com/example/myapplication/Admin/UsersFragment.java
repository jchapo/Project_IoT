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
            System.out.println("entra a iniciar la actividad");
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
        elements.add(new ListElement("#6744d7", "Pedro", "Administrador", "Activo"));
        elements.add(new ListElement("#6744d7", "Pedro", "Administrador", "Activo"));
        elements.add(new ListElement("#445447", "Pedro", "Administrador", "Activo"));
        elements.add(new ListElement("#772647", "Pedro", "Administrador", "Activo"));
        elements.add(new ListElement("#793447", "Pedro", "Administrador", "Activo"));
        elements.add(new ListElement("#275447", "Pedro", "Administrador", "Activo"));
        elements.add(new ListElement("#771446", "Pedro", "Administrador", "Activo"));
        elements.add(new ListElement("#175445", "Pedro", "Administrador", "Activo"));

        ListAdapter listAdapter = new ListAdapter(elements, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.listElements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }
}
