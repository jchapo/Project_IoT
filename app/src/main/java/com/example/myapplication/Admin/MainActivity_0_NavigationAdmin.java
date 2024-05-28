package com.example.myapplication.Admin;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.NotificationChannel;


import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AdminActivityMainNavigationBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_0_NavigationAdmin extends AppCompatActivity {
    String canal1 = "importanteDefault";
    AdminActivityMainNavigationBinding binding;
    private DrawerLayout drawerLayout;
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
        crearCanalesNotificacion();
        bottomNavigationView = binding.bottomNavigation;
        navigationActivityViewModel = new ViewModelProvider(this).get(NavigationActivityViewModel.class);

        // Recuperar el valor de inicio desde el Intent
        String inicio = getIntent().getStringExtra("inicio");
        if (inicio != null) {
            navigationActivityViewModel.getInicio().setValue(inicio);
        }

        binding.topAppBarUserFragment.setTitle("Usuarios");
        activeUsers = new ArrayList<>();
        inactiveUsers = new ArrayList<>();
        activeSites = new ArrayList<>();
        inactiveSites = new ArrayList<>();

        loadData();

        Toolbar toolbar = binding.topAppBarUserFragment;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBarUserFragment);

        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity_0_NavigationAdmin.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.topAppBarUserFragment.setTitle("Usuarios");
                replaceFragment(new Fragment_1_Users());
            }
        }, 1000); // 1000 milisegundos = 1 segundo




        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.usuarios_menu) {
                binding.topAppBarUserFragment.setTitle("Usuarios");
                replaceFragment(new Fragment_1_Users());
                return true;
            } else if (item.getItemId() == R.id.sitios_menu) {
                binding.topAppBarUserFragment.setTitle("Sitios");
                replaceFragment(new Fragment_2_Sites());
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
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void loadData() {
        // Limpiar las listas antes de cargar los datos
        activeUsers.clear();
        inactiveUsers.clear();
        activeSites.clear();
        inactiveSites.clear();
        db = FirebaseFirestore.getInstance();

        // Cargar usuarios desde Firestore
        loadUsersFromFirestore();
    }

    private void loadUsersFromFirestore() {
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
                        // Una vez que se cargan los usuarios, cargar sitios desde Firestore
                        db = FirebaseFirestore.getInstance();
                        loadSitesFromFirestore();
                    } else {
                        Log.d("msg-test", "Error getting user documents: ", task.getException());
                    }
                });
    }

    private void loadSitesFromFirestore() {
        db.collection("sitios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document2 : task.getResult()) {
                            ListElementSite listElementSite = document2.toObject(ListElementSite.class);
                            Log.d("msg-test", "Active sites: " + listElementSite.getName());
                            if ("Activo".equals(listElementSite.getStatus())) {
                                activeSites.add(listElementSite);
                            } else if ("Inactivo".equals(listElementSite.getStatus())) {
                                inactiveSites.add(listElementSite);
                            }
                        }
                        // Una vez que se cargan los sitios, actualizar el ViewModel con los datos
                        navigationActivityViewModel.getActiveUsers().setValue(activeUsers);
                        navigationActivityViewModel.getInactiveUsers().setValue(inactiveUsers);
                        navigationActivityViewModel.getActiveSites().setValue(activeSites);
                        navigationActivityViewModel.getInactiveSites().setValue(inactiveSites);
                    } else {
                        Log.d("msg-test", "Error getting site documents: ", task.getException());
                    }
                });
    }



    public void crearCanalesNotificacion() {

        NotificationChannel channel = new NotificationChannel(canal1,
                "Canal notificaciones default",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Canal para notificaciones con prioridad default");
        channel.enableVibration(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        pedirPermisos();
    }

    public void pedirPermisos() {
        // TIRAMISU = 33
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(MainActivity_0_NavigationAdmin.this, new String[]{POST_NOTIFICATIONS}, 101);
        }
    }

}
