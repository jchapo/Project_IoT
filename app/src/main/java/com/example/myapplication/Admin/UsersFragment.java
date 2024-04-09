package com.example.myapplication.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Admin.items.ListAdapter;
import com.example.myapplication.Admin.items.ListElement;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {
    List<ListElement> elements;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
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
        RecyclerView recyclerView = view.findViewById(R.id.listElements); // Aquí se cambia a view.findViewById()
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

}
