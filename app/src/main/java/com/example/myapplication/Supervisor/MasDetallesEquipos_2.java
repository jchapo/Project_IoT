package com.example.myapplication.Supervisor;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Admin.MainActivity_0_NavigationAdmin;
import com.example.myapplication.Admin.MainActivity_1_Users_NewUser;
import com.example.myapplication.Admin.MasDetallesEquipos_2;
import com.example.myapplication.Admin.MainActivity_2_Sites_AddSite;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MasDetallesEquipos_2 extends AppCompatActivity {


    TextView nombreEquipogrande, nombreEquipo, marca, serie, descripcion, SKU, fechaingreso, datosequipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.supervisor_activity_mas_detalles_equipos_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListElementDevices element = (ListElementDevices) getIntent().getSerializableExtra("ListElementDevices");
        nombreEquipo = findViewById(R.id.textViewNombreEquipo);
        marca = findViewById(R.id.textViewMarca);
        serie = findViewById(R.id.textViewSerie);
        descripcion = findViewById(R.id.textViewDescripcion);
        SKU = findViewById(R.id.textViewSku);
        fechaingreso = findViewById(R.id.textViewFechaIngreso);
        nombreEquipogrande = findViewById(R.id.nameTextViewDevice);
        datosequipo = findViewById(R.id.Datosdevice);


        String datosequipo1 = element.getMarca() + " / " + element.getModelo() + " / " + element.getSku();
        datosequipo.setText(datosequipo1);
        nombreEquipogrande.setText(element.getName());
        nombreEquipo.setText(element.getName());
        marca.setText(element.getMarca());
        serie.setText(element.getSerie());
        descripcion.setText(element.getDescripcion());
        SKU.setText(element.getSku());
        fechaingreso.setText(element.getFechaIngreso());

        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfilSuper);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasDetallesEquipos_2.this, MainActivity_0_NavigationAdmin.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fabEdit = findViewById(R.id.fabGenerarReporte);
        fabEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesEquipos_2.this, CrearReporte.class);
            startActivity(intent);
        });
        // Agregar Listener al botón flotante de editar
        findViewById(R.id.fabEditUserAdmin).setOnClickListener(new View.OnClickListener() {
            // Código para abrir MainActivity_new_user_admin desde la actividad del perfil de usuario
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasDetallesEquipos_2.this, MainActivity_1_Users_NewUser.class);
                intent.putExtra("isEditing", true);
                intent.putExtra("ListElement", element);
                startActivity(intent);
            }
        });
        // Obtener referencia al FrameLayout que actuará como botón
        FrameLayout btnAddSiteUserProfile = findViewById(R.id.btnAddSiteUserProfile);

        // Agregar un OnClickListener al FrameLayout
        btnAddSiteUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear Intent para iniciar la actividad MainActivity_addSupervisor_admin
                Intent intent = new Intent(MasDetallesEquipos_2.this, MainActivity_2_Sites_AddSite.class);
                // Iniciar la actividad MainActivity_addSupervisor_admin con el Intent
                startActivity(intent);
            }
        });
    }

    public void showConfirmationDialog(View view) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de inhabilitar este usuario?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Aquí puedes realizar la acción de inhabilitar el usuario
                // Muestra el Toast indicando que el usuario ha sido inhabilitado
                Toast.makeText(MasDetallesEquipos_2.this, "El usuario ha sido inhabilitado", Toast.LENGTH_SHORT).show();
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

    public void showRemoveSiteConfirmationDialog(View view) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage("¿Está seguro de que desea remover este sitio?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Eliminar el supervisor y mostrar el toast
                        Toast.makeText(MasDetallesEquipos_2.this, "Sitio removido", Toast.LENGTH_SHORT).show();
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

    // Método para abrir la aplicación de correo electrónico al hacer clic en el icono de correo
    public void icono2Click(View view) {
        String email = mailDescriptionTextView.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar correo electrónico"));
        } catch (ActivityNotFoundException e) {
            // Manejar la excepción si no hay aplicaciones de correo electrónico instaladas
            Toast.makeText(this, "No hay aplicaciones de correo electrónico instaladas", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para abrir la aplicación de teléfono al hacer clic en el icono de teléfono
    public void icono3Click(View view) {
        String phoneNumber = phoneDescriptionTextView.getText().toString();
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
        phoneIntent.setData(Uri.parse("tel:" + phoneNumber));

        try {
            startActivity(phoneIntent);
        } catch (ActivityNotFoundException e) {
            // Manejar la excepción si no se puede abrir la aplicación de teléfono
            Toast.makeText(this, "No se puede realizar la llamada telefónica", Toast.LENGTH_SHORT).show();
        }
    }

}