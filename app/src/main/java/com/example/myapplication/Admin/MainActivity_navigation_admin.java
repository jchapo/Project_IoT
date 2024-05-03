package com.example.myapplication.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.LogFragment;
import com.example.myapplication.databinding.AdminActivityMainNavigationBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity_navigation_admin extends AppCompatActivity {

    AdminActivityMainNavigationBinding binding;
    String comeFrom = "userProfile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminActivityMainNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        comeFrom = getIntent().getStringExtra("comeFrom");
        if (comeFrom != null) {
            if (comeFrom.equals("userProfile")) {
                replaceFragment(new UsersFragment());
                MenuItem menuItem = binding.bottomNavigation.getMenu().findItem(R.id.usuarios_menu);
                menuItem.setChecked(true);
            } else if (comeFrom.equals("siteProfile")) {
                replaceFragment(new SitesFragment());
                MenuItem menuItem = binding.bottomNavigation.getMenu().findItem(R.id.sitios_menu);
                menuItem.setChecked(true);
            }
        } else {
            replaceFragment(new UsersFragment());
        }





        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.usuarios_menu) {
                replaceFragment(new UsersFragment());
                return true;
            } else if (item.getItemId() == R.id.sitios_menu) {
                replaceFragment(new LogFragment());
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                replaceFragment(new ChatFragment());
                return true;
            } else if (item.getItemId() == R.id.notificaciones_menu) {
                replaceFragment(new NotificationsFragment());
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