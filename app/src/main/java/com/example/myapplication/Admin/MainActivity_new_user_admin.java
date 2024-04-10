package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class MainActivity_new_user_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_user_admin);

        Toolbar toolbar = findViewById(R.id.topAppBarUsers);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí defines la nueva actividad a la que quieres navegar
                Intent intent = new Intent(MainActivity_new_user_admin.this, MainActivity_navigation_admin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_admin_crear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejar los clics en los elementos del menú
        if (item.getItemId() == R.id.createUser) {
            startActivity(new Intent(this, MainActivity_userprofile_admin.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}