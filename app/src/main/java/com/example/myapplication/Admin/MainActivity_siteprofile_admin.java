package com.example.myapplication.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity_siteprofile_admin extends AppCompatActivity {


    TextView nameTextViewSite;
    TextView addressDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_siteprofile_admin);

        ListElementSite element = (ListElementSite) getIntent().getSerializableExtra("ListElementSite");
        nameTextViewSite = findViewById(R.id.nameTextViewSite);
        addressDescriptionTextView = findViewById(R.id.addressTextViewSite);

        nameTextViewSite.setText(element.getName());
        String fullDirection = element.getDepartment() + " " + element.getProvince() + " " + element.getDistrict();
        addressDescriptionTextView.setText(fullDirection);


        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfil);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showConfirmationDialog(View view) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de inhabilitar este sitio?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Aquí puedes realizar la acción de inhabilitar el sitio
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); // Cierra el diálogo sin hacer nada
            }
        });
        builder.show();
    }

}