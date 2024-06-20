package com.example.myapplication.SuperAdmin;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Admin.Fragment_3_Chat;
import com.example.myapplication.R;
import com.example.myapplication.databinding.SuperadminActivityMainNavigationBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity_navigation_SuperAdmin extends AppCompatActivity {

    SuperadminActivityMainNavigationBinding binding;

    private DrawerLayout drawerLayout;
    private boolean isSearchViewActive = false;
    private BottomNavigationView bottomNavigationView;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SuperadminActivityMainNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = binding.bottomNavigation;
        binding.topAppBarUserFragment.setTitle("Lista de usuarios");
        replaceFragmentSuperAdmin(new UsersFragmentSuperAdmin());

        Toolbar toolbar = binding.topAppBarUserFragment;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBarUserFragment);

        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity_navigation_SuperAdmin.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.usuarios_menu_superadmin) {
                binding.topAppBarUserFragment.setTitle("Usuarios");
                replaceFragmentSuperAdmin(new UsersFragmentSuperAdmin());
                return true;
            } else if (item.getItemId() == R.id.log_menu_superadmin) {
                binding.topAppBarUserFragment.setTitle("Registro de eventos");
                replaceFragmentSuperAdmin(new LogFragment());
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                binding.topAppBarUserFragment.setTitle("Chats");
                replaceFragmentSuperAdmin(new Fragment_3_Chat());
                return true;
            }
            return true;
        });

    }

    private void replaceFragmentSuperAdmin(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_superadmin,fragment);
        fragmentTransaction.commit();

    }
}