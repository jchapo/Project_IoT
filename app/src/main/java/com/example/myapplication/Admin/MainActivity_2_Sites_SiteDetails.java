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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_2_Sites_SiteDetails extends AppCompatActivity {


    TextView nameTextViewSite;
    TextView departmentDescriptionTextView, provinceDescriptionTextView, districtDescriptionTextView;
    TextView textoHabilitar;
    FirebaseFirestore db;
    String estado;

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
        estado = element.getStatus();


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

        textoHabilitar = findViewById(R.id.deleteUserSite);
        if (estado.equals("Activo")) {
            // Cambiar el texto, color y ícono para "Inhabilitar Sitio"
            textoHabilitar.setText("Inhabilitar Sitio");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0); // Icono a la izquierda
        } else {
            // Cambiar el texto, color y ícono para "Habilitar Sitio"
            textoHabilitar.setText("Habilitar Sitio");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme())); // Suponiendo que tienes un color para habilitar
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0); // Icono a la izquierda
        }

        findViewById(R.id.deleteUserSite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(v, estado);
            }
        });
    }

    public void showConfirmationDialog(View view, String currentState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");

        final String newState;
        final String message;
        if (currentState.equals("Activo")) {
            message = "¿Está seguro de inhabilitar este sitio?";
            newState = "Inactivo";
        } else {
            message = "¿Está seguro de habilitar este sitio?";
            newState = "Activo";
        }

        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = FirebaseFirestore.getInstance();
                Map<String, Object> update = new HashMap<>();
                update.put("status", newState);

                db.collection("sitios")
                        .document(nameTextViewSite.getText().toString())
                        .update(update)
                        .addOnSuccessListener(aVoid -> {
                            String toastMessage = newState.equals("Inactivo") ? "El sitio ha sido inhabilitado" : "El sitio ha sido habilitado";
                            Toast.makeText(MainActivity_2_Sites_SiteDetails.this, toastMessage, Toast.LENGTH_SHORT).show();
                            updateUIBasedOnState(newState);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity_2_Sites_SiteDetails.this, "Error al actualizar el estado del sitio", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        });
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
    private void updateUIBasedOnState(String state) {
        TextView textoHabilitar = findViewById(R.id.deleteUserSite);

        if (state.equals("Activo")) {
            textoHabilitar.setText("Inhabilitar Sitio");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0);
        } else {
            textoHabilitar.setText("Habilitar Sitio");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0);
        }
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