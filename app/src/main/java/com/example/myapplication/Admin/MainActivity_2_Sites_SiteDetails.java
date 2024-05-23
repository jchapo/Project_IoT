package com.example.myapplication.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.ImagenesSitio;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity_2_Sites_SiteDetails extends AppCompatActivity {


    TextView nameTextViewSite;
    TextView departmentDescriptionTextView,provinceDescriptionTextView,districtDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_siteprofile);

        ListElementSite element = (ListElementSite) getIntent().getSerializableExtra("ListElementSite");
        nameTextViewSite = findViewById(R.id.nameTextViewSiteSiteProfile);
        departmentDescriptionTextView = findViewById(R.id.departmentTextViewSite);
        provinceDescriptionTextView = findViewById(R.id.provinceTextViewSite);
        districtDescriptionTextView = findViewById(R.id.districtTextViewSite);

        nameTextViewSite.setText(element.getName());
        departmentDescriptionTextView.setText(element.getDepartment());
        provinceDescriptionTextView.setText(element.getProvince());
        districtDescriptionTextView.setText(element.getDistrict());


        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfilAdmin);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Agregar Listener al botón flotante de editar
        findViewById(R.id.fabEditSiteAdmin).setOnClickListener(new View.OnClickListener() {
            // Código para abrir MainActivity_new_user_admin desde la actividad del perfil de usuario
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_2_Sites_SiteDetails.this, MainActivity_2_Sites_NewSite.class);
                intent.putExtra("isEditing", true);
                intent.putExtra("ListElementSite", element);
                startActivity(intent);
            }
        });

        // Obtener referencia al botón de imágenes
        ImageButton buttonImagesSiteAdmin = findViewById(R.id.buttonImagesSiteAdmin);

        // Agregar un OnClickListener al botón de imágenes
        buttonImagesSiteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Obtener referencia al FrameLayout que actuará como botón
        FrameLayout btnAddSupervisor = findViewById(R.id.btnAddSupervisor);

        // Agregar un OnClickListener al FrameLayout
        btnAddSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear Intent para iniciar la actividad MainActivity_addSupervisor_admin
                Intent intent = new Intent(MainActivity_2_Sites_SiteDetails.this, MainActivity_2_Sites_AddSupervisor.class);
                // Iniciar la actividad MainActivity_addSupervisor_admin con el Intent
                startActivity(intent);
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
                // Muestra un toast después de inhabilitar el sitio
                Toast.makeText(MainActivity_2_Sites_SiteDetails.this, "El sitio ha sido inhabilitado", Toast.LENGTH_LONG).show();
                // Termina la actividad actual y regresa a la actividad anterior
                finish();
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

    public void showRemoveSupervisorConfirmationDialog(View view) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage("¿Está seguro de que desea eliminar este supervisor?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Eliminar el supervisor y mostrar el toast
                        Toast.makeText(MainActivity_2_Sites_SiteDetails.this, "Supervisor removido", Toast.LENGTH_SHORT).show();
                        // Aquí debes agregar el código para eliminar el supervisor
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // No hacer nada, simplemente cerrar el diálogo
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

}