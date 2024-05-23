package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListAdapterUser;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.Dto.UsuarioDto;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment_1_Users extends Fragment {

    private ArrayList<ListElementUser> activeUsers, inactiveUsers;
    private ListAdapterUser listAdapterUsers;
    private RecyclerView recyclerViewUsers;
    NavigationActivityViewModel navigationActivityViewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_admin_users, menu);

        MenuItem searchItem = menu.findItem(R.id.searchUser);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapterUsers.filter(newText);
                return true;
            }
        });
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
        View view = inflater.inflate(R.layout.admin_fragment_users, container, false);

        setHasOptionsMenu(true);

        navigationActivityViewModel = new ViewModelProvider(requireActivity()) .get(NavigationActivityViewModel. class);
        init(view);

        FloatingActionButton agregarUsuarioButton = view.findViewById(R.id.agregarUsuariofloatingActionButton);
        agregarUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí cambia "NuevaActividad" por la clase de la actividad a la que deseas cambiar
                Intent intent = new Intent(getActivity(), MainActivity_1_Users_NewUser.class);
                startActivity(intent);
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutUsers);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterUsers.setItems(activeUsers);
                        listAdapterUsers.notifyDataSetChanged();
                        break;
                    case 1:
                        listAdapterUsers.setItems(inactiveUsers);
                        listAdapterUsers.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

    public void init(View view) {
        activeUsers = new ArrayList<>();
        inactiveUsers = new ArrayList<>();

        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getActiveUsers().observe(getViewLifecycleOwner(), usuarioActivos -> {
                for (ListElementUser p : usuarioActivos) {
                    activeUsers.add(p);
                    Log.d("msg-test", "Name3: " + activeUsers.size());
                }
            });
        navigationActivityViewModel.getInactiveUsers().observe(getViewLifecycleOwner(), usuarioInactivos -> {
            for (ListElementUser p : usuarioInactivos) {
                inactiveUsers.add(p);
                Log.d("msg-test", "Name3: " + inactiveUsers.size());
            }
        });
        } else {
            // Manejar el caso en el que navigationActivityViewModel es nulo
        }
/*
        activeUsers.add(new ListElementUser("74567890", "Pedro", "Suares","Administrador", "Activo", "pedro_correo@gmail.com","978675678", "Calle Manzana", 0,"11/11/2022"));
        activeUsers.add(new ListElementUser("72903456", "Ana", "Suares","Supervisor", "Activo", "ana_correo@gmail.com","934567890", "Avenida Central", 0,"11/11/2022"));
        activeUsers.add(new ListElementUser("70234567", "Juan","Suares", "Administrador", "Activo", "juan_correo@gmail.com","956783421", "Calle Primavera", 0,"11/11/2022"));
        activeUsers.add(new ListElementUser("71234567", "María","Suares", "Supervisor", "Activo", "maria_correo@gmail.com","911234567", "Calle San José", 0,"11/11/2022"));
        activeUsers.add(new ListElementUser("79908765", "Carlos", "Suares","Administrador", "Activo", "carlos_correo@gmail.com","978564321", "Avenida Libertad", 0,"11/11/2022"));


        inactiveUsers.add(new ListElementUser("79908765", "Marta", "Suares","Administrador", "Activo", "marta_correo@gmail.com","978564321", "Avenida de la Costa", 0,"11/11/2022"));
        inactiveUsers.add(new ListElementUser("74895673", "Raquel", "Suares","Supervisor", "Activo", "raquel_correo@gmail.com","923456789", "Calle del Mar", 0,"11/11/2022"));

        allUsers.addAll(activeUsers);
        allUsers.addAll(inactiveUsers);
       */


        listAdapterUsers = new ListAdapterUser(activeUsers, getContext(), item -> moveToDescription(item));
        recyclerViewUsers = view.findViewById(R.id.listElementsUsers);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsers.setAdapter(listAdapterUsers);
    }

    public void moveToDescription(ListElementUser item) {
        Intent intent = new Intent(getContext(), MainActivity_1_Users_UserDetais.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
}
