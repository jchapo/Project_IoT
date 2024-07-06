package com.example.myapplication.Admin;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.example.myapplication.Admin.dataHolder.DataHolder;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity_0_NavigationAdmin extends AppCompatActivity {
    String canal1 = "importanteDefault";
    AdminActivityMainNavigationBinding binding;
    private DrawerLayout drawerLayout;
    NavigationActivityViewModel navigationActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_navigation);
        binding = AdminActivityMainNavigationBinding.inflate(getLayoutInflater());
        loadUsersFromFirestore();
        loadSitesFromFirestore();
        crearCanalesNotificacion();
        navigationActivityViewModel = new ViewModelProvider(this).get(NavigationActivityViewModel.class);
        replaceFragment(new Fragment_1_Users());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.usuarios_menu) {
                replaceFragment(new Fragment_1_Users());
                return true;
            } else if (item.getItemId() == R.id.sitios_menu) {
                replaceFragment(new Fragment_2_Sites());
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                replaceFragment(new Fragment_3_Chat());
                return true;
            } else if (item.getItemId() == R.id.notificaciones_menu) {
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
        loadUsersFromFirestore();
        loadSitesFromFirestore();
    }

    private void loadUsersFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("msg-test", "Task is successful");
                        ArrayList<ListElementUser> activeUsers = new ArrayList<>();
                        ArrayList<ListElementUser> inactiveUsers = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ListElementUser listElementUser = document.toObject(ListElementUser.class);
                            Log.d("msg-test", "Processing user: " + listElementUser.getName());
                            if ("Activo".equals(listElementUser.getStatus())) {
                                activeUsers.add(listElementUser);
                            } else if ("Inactivo".equals(listElementUser.getStatus())) {
                                inactiveUsers.add(listElementUser);
                            }
                        }

                        Collections.sort(activeUsers, Comparator.comparing(ListElementUser::getName));
                        Collections.sort(inactiveUsers, Comparator.comparing(ListElementUser::getName));

                        Log.d("msg-test", "Active users count: " + activeUsers.size());
                        Log.d("msg-test", "Inactive users count: " + inactiveUsers.size());

                        navigationActivityViewModel.getActiveUsers().setValue(activeUsers);
                        navigationActivityViewModel.getInactiveUsers().setValue(inactiveUsers);
                        DataHolder.getInstance().setActiveUsers(activeUsers);
                        DataHolder.getInstance().setInactiveUsers(inactiveUsers);
                    } else {
                        Log.d("msg-test", "Error getting user documents: ", task.getException());
                    }
                });
    }
    private void loadSitesFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sitios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<ListElementSite> activeSites = new ArrayList<>();
                        ArrayList<ListElementSite> inactiveSites = new ArrayList<>();
                        for (QueryDocumentSnapshot document2 : task.getResult()) {
                            ListElementSite listElementSite = document2.toObject(ListElementSite.class);
                            Log.d("msg-test", "Active sites: " + listElementSite.getName());
                            if ("Activo".equals(listElementSite.getStatus())) {
                                activeSites.add(listElementSite);
                            } else if ("Inactivo".equals(listElementSite.getStatus())) {
                                inactiveSites.add(listElementSite);
                            }
                        }

                        // Ordenar las listas alfabéticamente
                        Collections.sort(activeSites, Comparator.comparing(ListElementSite::getName));
                        Collections.sort(inactiveSites, Comparator.comparing(ListElementSite::getName));

                        // Una vez que se cargan los sitios, actualizar el ViewModel con los datos
                        navigationActivityViewModel.getActiveSites().setValue(activeSites);
                        navigationActivityViewModel.getInactiveSites().setValue(inactiveSites);
                        DataHolder.getInstance().setActiveSites(activeSites);
                        DataHolder.getInstance().setInactiveSites(inactiveSites);

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
