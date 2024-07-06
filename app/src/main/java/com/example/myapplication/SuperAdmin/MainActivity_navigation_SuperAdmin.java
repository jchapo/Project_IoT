package com.example.myapplication.SuperAdmin;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Admin.Fragment_3_Chat;
import com.example.myapplication.Admin.dataHolder.DataHolder;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.SuperAdmin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.list.ListElementSuperAdminUser;
import com.example.myapplication.databinding.SuperadminActivityMainNavigationBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity_navigation_SuperAdmin extends AppCompatActivity {
    String canal1 = "importanteDefault";
    SuperadminActivityMainNavigationBinding binding;
    NavigationActivityViewModel navigationActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SuperadminActivityMainNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadUsersFromFirestore();
        navigationActivityViewModel = new ViewModelProvider(this).get(NavigationActivityViewModel.class);
        replaceFragmentSuperAdmin(new UsersFragmentSuperAdmin());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_SuperAdmin);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.usuarios_menu_superadmin) {
                replaceFragmentSuperAdmin(new UsersFragmentSuperAdmin());
                return true;
            } else if (item.getItemId() == R.id.log_menu_superadmin) {
                replaceFragmentSuperAdmin(new LogFragment());
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                replaceFragmentSuperAdmin(new Fragment_3_Chat());
                return true;
            }
            return true;
        });
        /*Toolbar toolbar = binding.topAppBarUserFragment;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBarUserFragment);

        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity_navigation_SuperAdmin.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
*/


    }
    @Override
    protected void onResume() {
        super.onResume();
        loadUsersFromFirestore();
    }
    private void loadUsersFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<ListElementSuperAdminUser> adminUser = new ArrayList<>();
                        ArrayList<ListElementSuperAdminUser> supervisorUser = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ListElementSuperAdminUser listElementSuperAdminUser = document.toObject(ListElementSuperAdminUser.class);
                            Log.d("msg-test", "Active users: " + listElementSuperAdminUser.getName());
                            if ("Administrador".equals(listElementSuperAdminUser.getUser())) {
                                adminUser.add(listElementSuperAdminUser);
                            } else if ("Supervisor".equals(listElementSuperAdminUser.getUser())) {
                                supervisorUser.add(listElementSuperAdminUser);
                            }
                        }

                        Collections.sort(adminUser, Comparator.comparing(ListElementSuperAdminUser::getName));
                        Collections.sort(supervisorUser, Comparator.comparing(ListElementSuperAdminUser::getName));

                        navigationActivityViewModel.getAdminUser().setValue(adminUser);
                        navigationActivityViewModel.getSupervisorUser().setValue(supervisorUser);
                    } else {
                        Log.d("msg-test", "Error getting user documents: ", task.getException());
                    }
                });
    }

    private void replaceFragmentSuperAdmin(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_superadmin_Superadmin,fragment);
        fragmentTransaction.commit();

    }
}