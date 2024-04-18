package com.example.myapplication.SuperAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainNavigationAdminBinding;

public class MainActivitySuperAdmin extends AppCompatActivity {

    ActivityMainNavigationAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainNavigationAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ListaUsuario());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.usuarios_menu_sa) {
                replaceFragment(new ListaUsuario());
                return true;
            } else if (item.getItemId() == R.id.logs_menu_sa) {
                //replaceFragment(new SitesFragment());
                return true;
            } else if (item.getItemId() == R.id.chat_menu_sa) {
                //replaceFragment(new ChatFragment());
                return true;
            } else if (item.getItemId() == R.id.notificaciones_menu_sa) {
                //replaceFragment(new NotificationsFragment());
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
}