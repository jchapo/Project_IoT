package com.example.myapplication.Admin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity_1_Users_UserDetails extends AppCompatActivity {

    TextView nameDescriptionTextView;
    TextView userDescriptionTextView;
    TextView dniDescriptionTextView;
    TextView mailDescriptionTextView;
    TextView phoneDescriptionTextView;
    TextView addressDescriptionTextView;
    TextView textoHabilitar,textViewDevider2;
    FirebaseFirestore db;
    String estado;
    ImageView profileImageView;
    LinearLayout assignedSitesLayout;
    View deviderSitiosAsignados;
    FrameLayout FrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_userprofile);

        db = FirebaseFirestore.getInstance();

        ListElementUser element = (ListElementUser) getIntent().getSerializableExtra("ListElement");
        nameDescriptionTextView = findViewById(R.id.fullNameTextView);
        userDescriptionTextView = findViewById(R.id.cargoTextView);
        dniDescriptionTextView = findViewById(R.id.textViewDNI);
        mailDescriptionTextView = findViewById(R.id.textViewMail);
        phoneDescriptionTextView = findViewById(R.id.textViewPhone);
        addressDescriptionTextView = findViewById(R.id.textViewAddress);
        profileImageView = findViewById(R.id.imageViewProfileAdmin);
        textViewDevider2 = findViewById(R.id.textViewDevider2);
        deviderSitiosAsignados = findViewById(R.id.deviderSitiosAsignados);
        FrameLayout = findViewById(R.id.btnAddSiteUserProfile);

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
        toolbar.setTitle("Perfil " + element.getName().toUpperCase());
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        // Agregar Listener al botón flotante de editar
        findViewById(R.id.fabEditUserAdmin).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_1_Users_UserDetails.this, MainActivity_1_Users_NewUser.class);
            intent.putExtra("isEditing", true);
            intent.putExtra("ListElement", element);
            startActivity(intent);
        });

        // Inicializar el FrameLayout y agregarlo una vez
        assignedSitesLayout = findViewById(R.id.assignedSitesLayout);
        FrameLayout btnAddSiteUserProfile = findViewById(R.id.btnAddSiteUserProfile);
        assignedSitesLayout.addView(btnAddSiteUserProfile, 0); // Agregarlo al principio del layout

        // Agregar un OnClickListener al FrameLayout
        btnAddSiteUserProfile.setOnClickListener(v -> {
            Intent intent7 = new Intent(this, MainActivity_2_Sites_AddSite.class);
            intent7.putExtra("idDNI", element.getDni());
            startActivity(intent7);
        });

        textoHabilitar = findViewById(R.id.deleteUserPerfil);
        if (estado.equals("Activo")) {
            // Cambiar el texto, color y ícono para "Inhabilitar Usuario"
            textoHabilitar.setText("Inhabilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0); // Icono a la izquierda
        } else {
            textViewDevider2.setVisibility(View.GONE);
            deviderSitiosAsignados.setVisibility(View.GONE);
            btnAddSiteUserProfile.setVisibility(View.GONE);
            // Cambiar el texto, color y ícono para "Habilitar Usuario"
            textoHabilitar.setText("Habilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme())); // Suponiendo que tienes un color para habilitar
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0); // Icono a la izquierda
        }

        findViewById(R.id.deleteUserPerfil).setOnClickListener(v -> showConfirmationDialog(v, estado));

        // Obtener los sitios asignados y mostrarlos
        showAssignedSites(element);
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
                        Toast.makeText(MainActivity_1_Users_UserDetails.this, toastMessage, Toast.LENGTH_SHORT).show();
                        // Actualizar la interfaz de usuario
                        updateUIBasedOnState(newState);
                        // Terminar la actividad actual y regresar a la actividad anterior
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Manejo de error en caso de fallo
                        Toast.makeText(MainActivity_1_Users_UserDetails.this, "Error al actualizar el estado del usuario", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void showAssignedSites(ListElementUser element) {
        String assignedSitesJson = element.getSitiosAsignados();
        ArrayList<String> assignedSites;

        if (assignedSitesJson == null || assignedSitesJson.isEmpty()) {
            assignedSites = new ArrayList<>(); // Inicializar como lista vacía si el JSON es nulo o vacío
        } else {
            assignedSites = new Gson().fromJson(assignedSitesJson, new TypeToken<ArrayList<String>>(){}.getType());
        }

        // Limpiar el layout antes de agregar los sitios asignados, excepto el primer hijo (btnAddSiteUserProfile)
        assignedSitesLayout.removeViews(1, assignedSitesLayout.getChildCount() - 1);

        // Crear dinámicamente los CardView para cada sitio asignado
        for (String site : assignedSites) {
            View siteCard = getLayoutInflater().inflate(R.layout.admin_sitio_asignado_card, assignedSitesLayout, false);
            TextView siteTextView = siteCard.findViewById(R.id.siteAsignedTextView);
            siteTextView.setText(site);

            ImageView removeButton = siteCard.findViewById(R.id.botonEliminarSitioAsignado);
            removeButton.setOnClickListener(v -> showRemoveSiteConfirmationDialog(element, site));

            assignedSitesLayout.addView(siteCard);
        }
    }

    private void removeSite(ListElementUser element, String site) {
        String assignedSitesJson = element.getSitiosAsignados();
        ArrayList<String> assignedSites = new Gson().fromJson(assignedSitesJson, new TypeToken<ArrayList<String>>(){}.getType());
        assignedSites.remove(site);

        element.setSitiosAsignados(new Gson().toJson(assignedSites));
        // Actualizar en Firestore
        db.collection("usuarios")
                .document(element.getDni())
                .update("sitiosAsignados", new Gson().toJson(assignedSites))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Sitio removido", Toast.LENGTH_SHORT).show();
                    showAssignedSites(element);  // Actualizar la UI
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al remover el sitio", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                });
    }

    private void showRemoveSiteConfirmationDialog(ListElementUser element, String site) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de que desea remover este sitio?");
        builder.setPositiveButton("Aceptar", (dialog, which) -> removeSite(element, site));
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
                    Toast.makeText(MainActivity_1_Users_UserDetails.this, "Sitio removido", Toast.LENGTH_SHORT).show();
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
