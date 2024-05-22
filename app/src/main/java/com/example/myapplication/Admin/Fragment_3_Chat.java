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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListAdapterChat;
import com.example.myapplication.Admin.items.ListElementChat;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_3_Chat extends Fragment {
    List<ListElementChat> elements;
    private DrawerLayout drawerLayout;
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
        View view = inflater.inflate(R.layout.admin_fragments_chats, container, false);

        setHasOptionsMenu(true);
        init(view);

        return view;
    }

    public void init(View view) {
        elements = new ArrayList<>();
        elements.add(new ListElementChat("Diego Corcuera Llamo", "Tú: Qué fue con el proyecto?", "10:20 a.m."));
        elements.add(new ListElementChat("Pablo Flores Revilla", "Pablo: Mañana no podré ir", "9:10 a.m."));
        elements.add(new ListElementChat("Fernando Maldonado Benites", "Tú: Me puedes decir porqué lo hiciste?", "9:07 a.m."));
        elements.add(new ListElementChat("Carlos Fuentes Martinez", "Tú: Envíame el informe de lo que te pedí, porfa :v/", "Ayer"));




        ListAdapterChat listAdapter = new ListAdapterChat(elements, getContext(), item -> moveToDescription(item));
        RecyclerView recyclerView = view.findViewById(R.id.listElements);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    public void moveToDescription(ListElementChat item){
        Intent intent = new Intent(getContext(), MainActivity_3_Chat_ChatAdmin.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
}
