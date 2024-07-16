package com.example.myapplication.Supervisor;

import android.os.Bundle;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import com.example.myapplication.Admin.Fragment_1_Users;
import com.example.myapplication.Admin.Fragment_2_Sites;
import com.example.myapplication.Admin.Fragment_4_Notifications;
import com.example.myapplication.Admin.MainActivity_0_NavigationAdmin;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.Sistem.LoginActivity;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;
import com.example.myapplication.databinding.AdminActivityMainNavigationBinding;
import com.example.myapplication.databinding.SupervisorActivityNavegacionBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Admin.Fragment_3_Chat;
import com.example.myapplication.R;

public class NavegacionSupervisor extends AppCompatActivity {

    String canal1 = "importanteDefault";
    SupervisorActivityNavegacionBinding binding;
    private DrawerLayout drawerLayout;
    NavigationActivityViewModel navigationActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_activity_navegacion);
        binding = SupervisorActivityNavegacionBinding.inflate(getLayoutInflater());
        loadSitesFromFirestore();
        loadEquipmentsFromFirestore();
        crearCanalesNotificacion();
        navigationActivityViewModel = new ViewModelProvider(this).get(NavigationActivityViewModel.class);
        replaceFragment(new SitiosFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_supervisor);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.sitios_asignados_menu) {
                replaceFragment(new SitiosFragment());
                return true;
            } else if (item.getItemId() == R.id.equipos_menu) {
                replaceFragment(new EquiposFragment());
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                replaceFragment(new Fragment_3_Chat());
                return true;
            } else if (item.getItemId() == R.id.reportes_menu) {
                replaceFragment(new ReportesFragment());
                return true;
            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSitesFromFirestore();
        loadEquipmentsFromFirestore();
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
                        navigationActivityViewModel.getActiveSites().setValue(activeSites);
                        navigationActivityViewModel.getInactiveSites().setValue(inactiveSites);

                    } else {
                        Log.d("msg-test", "Error getting site documents: ", task.getException());
                    }
                });
    }

    private void loadEquipmentsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("equipos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<ListElementEquiposNuevo> activeEquipments = new ArrayList<>();
                        ArrayList<ListElementEquiposNuevo> inactiveEquipments = new ArrayList<>();
                        for (QueryDocumentSnapshot document2 : task.getResult()) {
                            ListElementEquiposNuevo listElementEquiposNuevo = document2.toObject(ListElementEquiposNuevo.class);
                            Log.d("msg-test", "Active equipments: " + listElementEquiposNuevo.getNameEquipo());
                            if ("Activo".equals(listElementEquiposNuevo.getStatus())) {
                                activeEquipments.add(listElementEquiposNuevo);
                            } else if ("Inactivo".equals(listElementEquiposNuevo.getStatus())) {
                                inactiveEquipments.add(listElementEquiposNuevo);
                            }
                        }
                        navigationActivityViewModel.getActiveEquipments().setValue(activeEquipments);
                        navigationActivityViewModel.getInactiveEquipments().setValue(inactiveEquipments);
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

            ActivityCompat.requestPermissions(NavegacionSupervisor.this, new String[]{POST_NOTIFICATIONS}, 101);
        }
    }


    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.sistema_cambio_contra, null);
        builder.setView(view);

        EditText currentPassword = view.findViewById(R.id.et_current_password);
        EditText newPassword = view.findViewById(R.id.et_new_password);
        EditText confirmPassword = view.findViewById(R.id.et_confirm_password);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        Button btnChange = view.findViewById(R.id.btn_change);

        AlertDialog dialog = builder.create();

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnChange.setOnClickListener(v -> {
            String currentPass = currentPassword.getText().toString();
            String newPass = newPassword.getText().toString();
            String confirmPass = confirmPassword.getText().toString();

            if (newPass.equals(confirmPass)) {
                changePassword(currentPass, newPass, dialog);
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void changePassword(String currentPass, String newPass, AlertDialog dialog) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);

        user.reauthenticate(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.updatePassword(newPass).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(this, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

















}
