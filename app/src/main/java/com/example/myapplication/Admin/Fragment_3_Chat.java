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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_3_Chat extends Fragment {
    List<ListElementChat> elements;
    private DrawerLayout drawerLayout;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        db = FirebaseFirestore.getInstance();  // Inicializar Firestore
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

        // Referencia a la colección "usuarios"
        CollectionReference usuariosRef = db.collection("usuarios");

        // Consulta para los usuarios con role "Administrador"
        Query queryAdmin = usuariosRef.whereEqualTo("user", "Administrador");

        // Consulta para los usuarios con role "Supervisor"
        Query querySupervisor = usuariosRef.whereEqualTo("user", "Supervisor");

        // Escuchar cambios en la colección para "Administrador"
        queryAdmin.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Manejar errores
                    return;
                }

                for (QueryDocumentSnapshot doc : value) {
                    String name = doc.getString("name");
                    String lastName = doc.getString("lastname");
                    String role = doc.getString("user");
                    String id = doc.getId(); // Obtener el ID del documento

                    elements.add(new ListElementChat(name + " " + lastName, role, id));
                }

                actualizarRecyclerView(view);
            }
        });

        // Escuchar cambios en la colección para "Supervisor"
        querySupervisor.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Manejar errores
                    return;
                }

                for (QueryDocumentSnapshot doc : value) {
                    String name = doc.getString("name");
                    String lastName = doc.getString("lastname");
                    String role = doc.getString("user");
                    String id = doc.getId(); // Obtener el ID del documento

                    elements.add(new ListElementChat(name + " " + lastName, role, id));
                }

                actualizarRecyclerView(view);
            }
        });
    }


    private void actualizarRecyclerView(View view) {
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
