package com.example.myapplication.Supervisor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Admin.Fragment_3_Chat;
import com.example.myapplication.R;
import com.example.myapplication.databinding.SupervisorActivityNavegacionBinding;

public class NavegacionSupervisor extends AppCompatActivity {

    SupervisorActivityNavegacionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SupervisorActivityNavegacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Reemplazar el fragmento con SitiosFragment al iniciar la actividad
        replaceFragment(new SitiosFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.sitios_asignados_menu) {
                replaceFragment(new SitiosFragment());
                return true;
            } else if (item.getItemId() == R.id.equipos_menu) {
                replaceFragment(new EquiposFragment());
                return true;
            } else if (item.getItemId() == R.id.chat_menu) {
                replaceFragment(new Fragment_3_Chat());
                return true;
            } else if (item.getItemId() == R.id.reportes_menu) {
                replaceFragment(new ReportesFragment());
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
