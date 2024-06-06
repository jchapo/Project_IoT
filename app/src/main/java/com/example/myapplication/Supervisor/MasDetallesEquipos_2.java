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
import com.example.myapplication.Admin.MainActivity_2_Sites_AddSite;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MasDetallesEquipos_2 extends AppCompatActivity {


    TextView nameEquipo,nameEquipo2, marca,modelo,tipoEquipo,descripcionEquipo, status, idSitio, sku, fecha_ingreso,datos_juntos;

    //POR CORREGIR
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

        ListElementEquiposNuevo element = (ListElementEquiposNuevo) getIntent().getSerializableExtra("ListElementDevices");
        nameEquipo = findViewById(R.id.textViewNombreEquipo);
        marca = findViewById(R.id.textViewMarca);
        modelo = findViewById(R.id.textViewModelo);
        descripcionEquipo = findViewById(R.id.textViewDescripcion);
        sku = findViewById(R.id.textViewSku);
        fecha_ingreso = findViewById(R.id.textViewFechaIngreso);
        nameEquipo2 = findViewById(R.id.nameTextViewDevice);
        datos_juntos = findViewById(R.id.Datosdevice);


        String datosequipo1 = element.getMarca() + " / " + element.getModelo();
        datos_juntos.setText(datosequipo1);
        nameEquipo.setText(element.getNameEquipo());
        nameEquipo2.setText(element.getNameEquipo());
        marca.setText(element.getMarca());
        modelo.setText(element.getModelo());
        descripcionEquipo.setText(element.getDescripcionEquipo());
        sku.setText(element.getSku());
        fecha_ingreso.setText(element.getFecha_ingreso());

        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfilSuper);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fabEdit = findViewById(R.id.fabGenerarReporte);
        fabEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesEquipos_2.this, CrearReporte.class);
            startActivity(intent);
        });
        // Agregar Listener al botón flotante de editar
        findViewById(R.id.textViewEditarEquipo).setOnClickListener(new View.OnClickListener() {
            // Código para abrir MainActivity_new_user_admin desde la actividad del perfil de dispositivo
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasDetallesEquipos_2.this, CrearEquipo_2.class);
                intent.putExtra("isEditing", true);
                intent.putExtra("ListElement", element);
                startActivity(intent);
            }
        });
    }

    public void showConfirmationDialogDevice(View view) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de inhabilitar este dispositivo?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Aquí puedes realizar la acción de inhabilitar el dispositivo
                // Muestra el Toast indicando que el dispositivo ha sido inhabilitado
                Toast.makeText(MasDetallesEquipos_2.this, "El dispositivo ha sido inhabilitado", Toast.LENGTH_SHORT).show();
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

}