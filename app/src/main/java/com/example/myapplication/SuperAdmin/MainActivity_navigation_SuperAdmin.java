package com.example.myapplication.SuperAdmin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Admin.ChatFragment;
import com.example.myapplication.Admin.SitesFragment;
import com.example.myapplication.R;

public class MainActivity_navigation_SuperAdmin extends AppCompatActivity {

    com.example.myapplication.databinding.SuperadminActivityMainNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.myapplication.databinding.SuperadminActivityMainNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragmentSuperAdmin(new UsersFragmentSuperAdmin());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.usuarios_menu_superadmin) {
                replaceFragmentSuperAdmin(new UsersFragmentSuperAdmin());
                return true;
            } else if (item.getItemId() == R.id.log_menu_superadmin) {
                replaceFragmentSuperAdmin(new LogFragment());
                return true;
            } else if (item.getItemId() == R.id.chat_menu_superadmin) {
                replaceFragmentSuperAdmin(new ChatFragment());
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