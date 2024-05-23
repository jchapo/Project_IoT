package com.example.myapplication.Admin;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AdminActivityMainNavigationBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_0_NavigationAdmin extends AppCompatActivity {

    AdminActivityMainNavigationBinding binding;
    private DrawerLayout drawerLayout;
    private boolean isSearchViewActive = false;
    private BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;
    NavigationActivityViewModel navigationActivityViewModel;

    private ArrayList<ListElementUser> activeUsers, inactiveUsers;
    private ArrayList<ListElementSite> activeSites, inactiveSites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminActivityMainNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = binding.bottomNavigation;
        navigationActivityViewModel = new ViewModelProvider(this).get(NavigationActivityViewModel.class);
        binding.topAppBarUserFragment.setTitle("Usuarios");
        activeUsers = new ArrayList<>();
        inactiveUsers = new ArrayList<>();
        db = FirebaseFirestore. getInstance();
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ListElementUser listElementUser = document.toObject(ListElementUser.class);
                            Log.d("msg-test", "Active users: " + listElementUser.getName());
                            if ("Activo".equals(listElementUser.getStatus())) {
                                activeUsers.add(listElementUser);
                            } else if ("Inactivo".equals(listElementUser.getStatus())) {
                                inactiveUsers.add(listElementUser);
                            }
                        }
                        // Aquí puedes hacer algo con las listas activeUsers y inactiveUsers
                        // Por ejemplo, imprimir los tamaños de las listas
                        Log.d("msg-test", "Active users: " + activeUsers.size());
                        Log.d("msg-test", "Inactive users: " + inactiveUsers.size());
                        // Crear el fragmento y pasar los datos
                        Fragment_1_Users fragment = new Fragment_1_Users();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("activeUsers", activeUsers);
                        bundle.putSerializable("inactiveUsers", inactiveUsers);
                        fragment.setArguments(bundle);

                        // Reemplazar el fragmento
                        replaceFragment(fragment);
                    } else {
                        Log.d("msg-test", "Error getting documents: ", task.getException());
                    }
                });
        navigationActivityViewModel.getActiveUsers().setValue(activeUsers);
        navigationActivityViewModel.getInactiveUsers().setValue(inactiveUsers);

        Toolbar toolbar = binding.topAppBarUserFragment;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBarUserFragment);

        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity_0_NavigationAdmin.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.usuarios_menu) {
                binding.topAppBarUserFragment.setTitle("Usuarios");
                activeUsers = new ArrayList<>();
                inactiveUsers = new ArrayList<>();
                db = FirebaseFirestore. getInstance();
                db.collection("usuarios")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ListElementUser listElementUser = document.toObject(ListElementUser.class);
                                    Log.d("msg-test", "Active users: " + listElementUser.getName());
                                    if ("Activo".equals(listElementUser.getStatus())) {
                                        activeUsers.add(listElementUser);
                                    } else if ("Inactivo".equals(listElementUser.getStatus())) {
                                        inactiveUsers.add(listElementUser);
                                    }
                                }
                                // Aquí puedes hacer algo con las listas activeUsers y inactiveUsers
                                // Por ejemplo, imprimir los tamaños de las listas
                                Log.d("msg-test", "Active users: " + activeUsers.size());
                                Log.d("msg-test", "Inactive users: " + inactiveUsers.size());
                                // Crear el fragmento y pasar los datos
                                Fragment_1_Users fragment = new Fragment_1_Users();

                                // Reemplazar el fragmento
                                replaceFragment(fragment);
                            } else {
                                Log.d("msg-test", "Error getting documents: ", task.getException());
                            }
                        });
                return true;
            } else if (item.getItemId() == R.id.sitios_menu) {
                binding.topAppBarUserFragment.setTitle("Sitios");
                activeSites = new ArrayList<>();
                inactiveSites = new ArrayList<>();
                db = FirebaseFirestore. getInstance();
                db.collection("sitios")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ListElementSite listElementSite = document.toObject(ListElementSite.class);
                                    Log.d("msg-test", "Active sites: " + listElementSite.getName());
                                    if ("Activo".equals(listElementSite.getStatus())) {
                                        activeSites.add(listElementSite);
                                    } else if ("Inactivo".equals(listElementSite.getStatus())) {
                                        inactiveSites.add(listElementSite);
                                    }
                                }
                                // Aquí puedes hacer algo con las listas activeUsers y inactiveUsers
                                // Por ejemplo, imprimir los tamaños de las listas
                                Log.d("msg-test", "Active sites: " + activeSites.size());
                                Log.d("msg-test", "Inactive sites: " + inactiveSites.size());
                                // Crear el fragmento y pasar los datos
                                Fragment_2_Sites fragment = new Fragment_2_Sites();
                                Bundle bundle = new Bundle();
                                // Reemplazar el fragmento
                                replaceFragment(fragment);
                            } else {
                                Log.d("msg-test", "Error getting documents: ", task.getException());
                            }
                        });
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                binding.topAppBarUserFragment.setTitle("Chats");
                replaceFragment(new Fragment_3_Chat());
                return true;
            } else if (item.getItemId() == R.id.notificaciones_menu) {
                binding.topAppBarUserFragment.setTitle("Notificaciones");
                replaceFragment(new Fragment_4_Notifications());
                return true;
            }
            return true;
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_Admin, fragment);
        fragmentTransaction.commit();
    }


}
