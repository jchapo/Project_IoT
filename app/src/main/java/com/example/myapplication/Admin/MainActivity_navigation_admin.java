package com.example.myapplication.Admin;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.databinding.AdminActivityMainNavigationBinding;

public class MainActivity_navigation_admin extends AppCompatActivity {

    AdminActivityMainNavigationBinding binding;
    private DrawerLayout drawerLayout;

    String comeFrom = "userProfile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminActivityMainNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.topAppBarUserFragment.setTitle("Lista de usuarios");
        replaceFragment(new FragmentUsers());

        Toolbar toolbar = binding.topAppBarUserFragment;

        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity_navigation_admin.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();





        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.usuarios_menu) {
                binding.topAppBarUserFragment.setTitle("Usuarios");

                replaceFragment(new FragmentUsers());
                return true;
            } else if (item.getItemId() == R.id.sitios_menu) {
                binding.topAppBarUserFragment.setTitle("Sitios");

                replaceFragment(new FragmentSites());
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                binding.topAppBarUserFragment.setTitle("Chats");

                replaceFragment(new FragmentChat());
                return true;
            } else if (item.getItemId() == R.id.notificaciones_menu) {
                binding.topAppBarUserFragment.setTitle("Notificaciones");

                replaceFragment(new FragmentNotifications());
                return true;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_Admin,fragment);
        fragmentTransaction.commit();

    }
}