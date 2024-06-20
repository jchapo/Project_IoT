package com.example.myapplication.SuperAdmin;

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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.list.ListAdapterSuperAdminUser;
import com.example.myapplication.SuperAdmin.list.ListElementSuperAdminUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersFragmentSuperAdmin extends Fragment {
    private List<ListElementSuperAdminUser> elements;
    private List<ListElementSuperAdminUser> adminUser;
    private List<ListElementSuperAdminUser> supervisorUser;
    private ListAdapterSuperAdminUser ListAdapterSuperAdminUser;
    private RecyclerView recyclerViewUsers;
    FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_superadmin_users, menu);

        MenuItem searchItem = menu.findItem(R.id.searchUser);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SearchView", "Query text changed to: " + newText); // Añadir este log para depuración
                System.out.println("hola: " + newText);
                ListAdapterSuperAdminUser.filter(newText);
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
        View view = inflater.inflate(R.layout.superadmin_fragment_users, container, false);
        setHasOptionsMenu(true);
        init(view);

        FloatingActionButton agregarUsuarioButton = view.findViewById(R.id.agregarAdminfloatingActionButton);
        agregarUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewAdmin.class);
                startActivity(intent);
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutSuperAdmin);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ListAdapterSuperAdminUser.setItems(adminUser);
                        ListAdapterSuperAdminUser.notifyDataSetChanged();
                        break;
                    case 1:
                        ListAdapterSuperAdminUser.setItems(supervisorUser);
                        ListAdapterSuperAdminUser.notifyDataSetChanged();
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
        elements = new ArrayList<>();
        adminUser = new ArrayList<>();
        supervisorUser = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ListElementSuperAdminUser listElementSuperAdminUser = document.toObject(ListElementSuperAdminUser.class);
                            Log.d("msg-test", "Active users: " + listElementSuperAdminUser.getName());
                            if ("Administrador".equals(listElementSuperAdminUser.getUser())) {
                                adminUser.add(listElementSuperAdminUser);
                            } else if ("Supervisor".equals(listElementSuperAdminUser.getUser())) {
                                supervisorUser.add(listElementSuperAdminUser);
                            }
                        }
                        Log.d("msg-test", "Administrador users: " + adminUser.size());
                        Log.d("msg-test", "Supervisor users: " + supervisorUser.size());

                        // Set initial data to active users
                        ListAdapterSuperAdminUser.setItems(adminUser);
                        ListAdapterSuperAdminUser.notifyDataSetChanged();
                    } else {
                        Log.d("msg-test", "Error getting documents: ", task.getException());
                    }
                });

        ListAdapterSuperAdminUser = new ListAdapterSuperAdminUser(adminUser, getContext(), item -> moveToDescription(item));
        recyclerViewUsers = view.findViewById(R.id.listElementsSuperAdmin);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsers.setAdapter(ListAdapterSuperAdminUser);

        // Select the active users tab initially
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutSuperAdmin);
        tabLayout.post(() -> {
            TabLayout.Tab tab = tabLayout.getTabAt(0); // Assuming the first tab is for active users
            if (tab != null) {
                tab.select();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        ListAdapterSuperAdminUser.setItems(adminUser);
                        ListAdapterSuperAdminUser.notifyDataSetChanged();
                        break;
                    case 1:
                        ListAdapterSuperAdminUser.setItems(supervisorUser);
                        ListAdapterSuperAdminUser.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void moveToDescription(ListElementSuperAdminUser item){
        Intent intent = new Intent(getContext(), UserDetais.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
}
