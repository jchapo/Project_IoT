package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class CrearReporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_activity_crear_reporte);

        // Verificar si se pasó el tipo de reporte en el Intent
        String tipoReporte = getIntent().getStringExtra("tipo_reporte");


        Fragment fragment;
        if (tipoReporte != null && tipoReporte.equals("Equipo")) {
            fragment = new CrearReporteEquipoFragment();
        } else {
            fragment = new CrearReporteSitioFragment();
        }


        loadFragment(fragment);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment;
                switch (position) {
                    case 0:
                        fragment = new CrearReporteSitioFragment();
                        break;
                    case 1:
                        fragment = new CrearReporteEquipoFragment();
                        break;
                    default:
                        fragment = new CrearReporteSitioFragment(); // Fragment por defecto
                }
                loadFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (tipoReporte != null) {
            if (tipoReporte.equals("Equipo")) {
                tabLayout.getTabAt(1).select();
            } else {
                tabLayout.getTabAt(0).select();
            }
        }

        Toolbar toolbar = findViewById(R.id.topAppBarCrearReporte);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createNewReporte:
                Toast.makeText(this, "Reporte generado", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_supervisor_crear_reporte, menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

