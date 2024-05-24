package com.example.myapplication.SuperAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.appbar.MaterialToolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Admin.MainActivity_1_Users_UserDetais;
import com.example.myapplication.R;

public class EditAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.superadmin_activity_main_edit_user);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.topAppBarEditUser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.saveOldTopAppBar) {
            // Inicia la actividad UserDetais
            startActivity(new Intent(this, UserDetais.class));
            return true;
        } else if (itemId == android.R.id.home) {
            // Finaliza la actividad actual para volver al fragmento anterior
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
