package com.example.myapplication.Admin;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AdminActivityMainNavigationBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity_0_NavigationAdmin extends AppCompatActivity {

    AdminActivityMainNavigationBinding binding;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminActivityMainNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.topAppBarUserFragment.setTitle("Lista de usuarios");
        replaceFragment(new Fragment_1_Users());

        Toolbar toolbar = binding.topAppBarUserFragment;
        MaterialToolbar topAppBar = findViewById(R.id.topAppBarUserFragment);

        topAppBar.inflateMenu(R.menu.top_app_bar_admin_users);
        topAppBar.setOnMenuItemClickListener(item -> {
            binding.bottomNavigation.setVisibility(View.GONE);
            return false;
        });

        drawerLayout = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity_0_NavigationAdmin.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.usuarios_menu) {
                binding.topAppBarUserFragment.setTitle("Usuarios");
                replaceFragment(new Fragment_1_Users());
                return true;
            } else if (item.getItemId() == R.id.sitios_menu) {
                binding.topAppBarUserFragment.setTitle("Sitios");
                replaceFragment(new Fragment_2_Sites());
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                binding.topAppBarUserFragment.setTitle("Chats");
                replaceFragment(new Fragment_3_Chat());
                return true;
            } else if (item.getItemId() == R.id.notificaciones_menu) {
                binding.topAppBarUserFragment.setTitle("Notificaciones");
                replaceFragment(new Fragment_4_Notifications());
                return true;
            }
            return true;
        });


    }

    /*@Override
    public void onBackPressed() {
        // Obtener el MenuItem de búsqueda
        MenuItem searchMenuItem = menu.findItem(R.id.searchUser);

        // Verificar si el SearchView está expandido
        if (searchMenuItem != null) {
            SearchView searchView = (SearchView) searchMenuItem.getActionView();
            if (!searchView.isIconified()) {
                // Mostrar Toast
                Toast.makeText(this, "Presionaste atrás con el cuadro de búsqueda abierto", Toast.LENGTH_SHORT).show();
                // Colapsar el SearchView
                searchMenuItem.collapseActionView();
                return;
            }
        }

        // Si el SearchView no está expandido, realizar el comportamiento predeterminado
        super.onBackPressed();
    }*/


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_Admin, fragment);
        fragmentTransaction.commit();
    }


}
