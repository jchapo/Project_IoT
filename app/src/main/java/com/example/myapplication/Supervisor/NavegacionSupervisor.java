package com.example.myapplication.Supervisor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityNavegacionSupervisorBinding;

public class NavegacionSupervisor extends AppCompatActivity {

    ActivityNavegacionSupervisorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavegacionSupervisorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Reemplazar el fragmento con SitiosFragment al iniciar la actividad
        replaceFragment(new SitiosFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            // No es necesario reemplazar el fragmento aquí
            // El fragmento se reemplaza solo una vez al inicio de la actividad
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
