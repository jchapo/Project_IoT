package com.example.myapplication.Admin;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.NotificationChannel;


import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.dataHolder.DataHolder;
import com.example.myapplication.Admin.items.ListElementChat;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Sistem.LoginActivity;
import com.example.myapplication.databinding.AdminActivityMainNavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity_0_NavigationAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String canal1 = "importanteDefault";
    AdminActivityMainNavigationBinding binding;
    private DrawerLayout drawerLayout;
    NavigationActivityViewModel navigationActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_navigation);
        binding = AdminActivityMainNavigationBinding.inflate(getLayoutInflater());

        // Obtener los datos del usuario del Intent
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");
        String userLastName = intent.getStringExtra("USER_LASTNAME");
        String userEmail = intent.getStringExtra("USER_EMAIL");
        String userPhone = intent.getStringExtra("USER_PHONE");

        // Configurar el NavigationView con los datos del usuario
        NavigationView navigationView = findViewById(R.id.navView);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_profile).setTitle(userName + ' ' + userLastName);
        menu.findItem(R.id.nav_email).setTitle(userEmail);
        menu.findItem(R.id.nav_phone).setTitle(userPhone);

        // El resto del código de tu onCreate
        //setSupportActionBar(binding.);
        loadUsersFromFirestore();
        loadSitesFromFirestore();
        loadUsersChatFromFirestore();
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

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);




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
        loadUsersChatFromFirestore();
    }

    private void loadUsersFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .whereEqualTo("user", "Supervisor")
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


    private void loadUsersChatFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("msg-test", "Task is successful");
                        ArrayList<ListElementChat> chatUserAdmin = new ArrayList<>();
                        ArrayList<ListElementChat> chatUserSupervisor = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ListElementChat listElementChat = document.toObject(ListElementChat.class);
                            Log.d("msg-test", "Processing user: " + listElementChat.getUsuarioChatName());
                            if ("Administrador".equals(listElementChat.getRole())) {
                                chatUserAdmin.add(listElementChat);
                            } else if ("Supervisor".equals(listElementChat.getRole())) {
                                chatUserSupervisor.add(listElementChat);
                            }
                        }

                        Collections.sort(chatUserAdmin, Comparator.comparing(ListElementChat::getUsuarioChatName));
                        Collections.sort(chatUserSupervisor, Comparator.comparing(ListElementChat::getUsuarioChatName));

                        Log.d("msg-test", "Admin users count: " + chatUserAdmin.size());
                        Log.d("msg-test", "Supervisor users count: " + chatUserSupervisor.size());

                        navigationActivityViewModel.getUsuarioChatAdmin().setValue(chatUserAdmin);
                        navigationActivityViewModel.getUsuarioChatSupervisor().setValue(chatUserSupervisor);
                        DataHolder.getInstance().setUsuarioChatAdmin(chatUserAdmin);
                        DataHolder.getInstance().setUsuarioChatSupervisor(chatUserSupervisor);
                    } else {
                        Log.d("msg-test", "Error getting user documents: ", task.getException());
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_change_password) {
            showChangePasswordDialog();
        } else if (id == R.id.nav_logout) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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


    private void openChatActivity() {
        Intent intent = new Intent(MainActivity_0_NavigationAdmin.this, MainActivity_3_Chat_ChatAdmin.class);
        // Obtener los datos del Intent que inició esta actividad
        Intent currentIntent = getIntent();
        String userEmail = currentIntent.getStringExtra("USER_EMAIL");
        String userName = currentIntent.getStringExtra("USER_NAME");
        String userLastName = currentIntent.getStringExtra("USER_LASTNAME");

        // Verificar si los datos están presentes
        if (userEmail != null && userName != null && userLastName != null) {
            // Pasar los datos al siguiente Activity
            intent.putExtra("USER_EMAIL", userEmail);
            intent.putExtra("USER_NAME", userName);
            intent.putExtra("USER_LASTNAME", userLastName);
        } else {
            Log.d("MainActivity_0_NavigationAdmin", "Error: Datos del usuario no recibidos en MainActivity_0_NavigationAdmin.");
        }

        startActivity(intent);
    }


}
