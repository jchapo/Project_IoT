package com.example.myapplication.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainNavigationAdminBinding;

public class MainActivity_navigation_admin extends AppCompatActivity {

    ActivityMainNavigationAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainNavigationAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new UsersFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.usuarios_menu) {
                replaceFragment(new UsersFragment());
                return true;
            } else if (item.getItemId() == R.id.sitios_menu) {
                replaceFragment(new SitesFragment());
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
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.addUser) {
            Intent intent = new Intent(MainActivity_navigation_admin.this, MainActivity_new_user_admin.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}