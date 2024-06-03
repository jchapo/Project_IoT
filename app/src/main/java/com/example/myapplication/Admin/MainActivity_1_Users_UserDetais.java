package com.example.myapplication.Admin;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity_1_Users_UserDetais extends AppCompatActivity {

    TextView nameDescriptionTextView;
    TextView userDescriptionTextView;
    TextView dniDescriptionTextView;
    TextView mailDescriptionTextView;
    TextView phoneDescriptionTextView;
    TextView addressDescriptionTextView;
    TextView textoHabilitar;
    FirebaseFirestore db;
    String estado;
    ImageView profileImageView;
    private static final int REQUEST_CODE_ADD_SITE = 1; // Define el código de solicitud
    private ActivityResultLauncher<Intent> addSiteLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_userprofile);

        // Inicializa el ActivityResultLauncher
        addSiteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            ArrayList<String> selectedSites = data.getStringArrayListExtra("selectedSites");
                            // Procesa la lista de sitios seleccionados
                            for (String site : selectedSites) {
                                Log.d("SelectedSite", site);
                            }
                        }
                    }
                }
        );

        ListElementUser element = (ListElementUser) getIntent().getSerializableExtra("ListElement");
        nameDescriptionTextView = findViewById(R.id.fullNameTextView);
        userDescriptionTextView = findViewById(R.id.cargoTextView);
        dniDescriptionTextView = findViewById(R.id.textViewDNI);
        mailDescriptionTextView = findViewById(R.id.textViewMail);
        phoneDescriptionTextView = findViewById(R.id.textViewPhone);
        addressDescriptionTextView = findViewById(R.id.textViewAddress);
        profileImageView = findViewById(R.id.imageViewProfileAdmin);

        nameDescriptionTextView.setText(element.getName());
        mailDescriptionTextView.setText(element.getMail());
        userDescriptionTextView.setText(element.getUser());
        dniDescriptionTextView.setText(element.getDni());
        phoneDescriptionTextView.setText(element.getPhone());
        addressDescriptionTextView.setText(element.getAddress());
        estado = element.getStatus();

        // Cargar la imagen en el ImageView
        if (element.getImageUrl() != null && !element.getImageUrl().isEmpty()) {
            byte[] decodedString = Base64.decode(element.getImageUrl(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImageView.setImageBitmap(decodedByte);
        }

        Toolbar toolbar = findViewById(R.id.topAppBarUserPerfil);
        toolbar.setTitle("Perfil "+element.getName().toUpperCase());
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        // Agregar Listener al botón flotante de editar
        findViewById(R.id.fabEditUserAdmin).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_1_Users_UserDetais.this, MainActivity_1_Users_NewUser.class);
            intent.putExtra("isEditing", true);
            intent.putExtra("ListElement", element);
            startActivity(intent);
        });

        // Obtener referencia al FrameLayout que actuará como botón
        FrameLayout btnAddSiteUserProfile = findViewById(R.id.btnAddSiteUserProfile);

        // Agregar un OnClickListener al FrameLayout
        btnAddSiteUserProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity_2_Sites_AddSite.class);
            addSiteLauncher.launch(intent);
        });

        textoHabilitar = findViewById(R.id.deleteUserPerfil);
        if (estado.equals("Activo")) {
            // Cambiar el texto, color y ícono para "Inhabilitar Usuario"
            textoHabilitar.setText("Inhabilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0); // Icono a la izquierda
        } else {
            // Cambiar el texto, color y ícono para "Habilitar Usuario"
            textoHabilitar.setText("Habilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme())); // Suponiendo que tienes un color para habilitar
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0); // Icono a la izquierda
        }

        findViewById(R.id.deleteUserPerfil).setOnClickListener(v -> showConfirmationDialog(v, estado));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_SITE && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> selectedSites = data.getStringArrayListExtra("selectedSites");
                // Procesa la lista de sitios seleccionados
            }
        }
    }

    public void showConfirmationDialog(View view, String currentState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");

        // Configurar el mensaje y el estado objetivo basado en el estado actual
        final String newState;
        final String message;
        if (currentState.equals("Activo")) {
            message = "¿Está seguro de inhabilitar este usuario?";
            newState = "Inactivo";
        } else {
            message = "¿Está seguro de habilitar este usuario?";
            newState = "Activo";
        }

        builder.setMessage(message);
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> {
            // Suponiendo que tienes una instancia de FirebaseFirestore llamada db
            db = FirebaseFirestore.getInstance();
            // Crear un mapa con el nuevo estado
            Map<String, Object> update = new HashMap<>();
            update.put("status", newState);

            // Obtener el documento del usuario y actualizar el campo 'status'
            db.collection("usuarios")
                    .document(dniDescriptionTextView.getText().toString())
                    .update(update)
                    .addOnSuccessListener(aVoid -> {
                        // Mostrar el Toast indicando el nuevo estado del usuario
                        String toastMessage = newState.equals("Inactivo") ? "El usuario ha sido inhabilitado" : "El usuario ha sido habilitado";
                        Toast.makeText(MainActivity_1_Users_UserDetais.this, toastMessage, Toast.LENGTH_SHORT).show();
                        // Actualizar la interfaz de usuario
                        updateUIBasedOnState(newState);
                        // Terminar la actividad actual y regresar a la actividad anterior
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Manejo de error en caso de fallo
                        Toast.makeText(MainActivity_1_Users_UserDetais.this, "Error al actualizar el estado del usuario", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void updateUIBasedOnState(String state) {
        TextView textoHabilitar = findViewById(R.id.deleteUserPerfil);

        if (state.equals("Activo")) {
            textoHabilitar.setText("Inhabilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0);
        } else {
            textoHabilitar.setText("Habilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0);
        }
    }

    public void showRemoveSiteConfirmationDialog(View view) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage("¿Está seguro de que desea remover este sitio?")
                .setPositiveButton("Aceptar", (dialog, id) -> {
                    // Eliminar el supervisor y mostrar el toast
                    Toast.makeText(MainActivity_1_Users_UserDetais.this, "Sitio removido", Toast.LENGTH_SHORT).show();
                    // Aquí debes agregar el código para eliminar el supervisor
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    // No hacer nada, simplemente cerrar el diálogo
                    dialog.dismiss();
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
