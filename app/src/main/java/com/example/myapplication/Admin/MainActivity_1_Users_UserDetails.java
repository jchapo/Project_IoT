package com.example.myapplication.Admin;

import static android.view.View.GONE;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import com.firebase.ui.storage.images.FirebaseImageLoader;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;



public class MainActivity_1_Users_UserDetails extends AppCompatActivity {

    TextView nameDescriptionTextView;
    TextView userDescriptionTextView;
    TextView dniDescriptionTextView;
    TextView mailDescriptionTextView;
    TextView phoneDescriptionTextView;
    TextView addressDescriptionTextView;
    TextView textoHabilitar,textViewDevider2User;
    FirebaseFirestore db;
    String estado;
    ImageView profileImageView;
    LinearLayout assignedSitesLayout;
    View deviderSitiosAsignados;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_userprofile);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ListElementUser element = (ListElementUser) getIntent().getSerializableExtra("ListElement");

        nameDescriptionTextView = findViewById(R.id.fullNameTextView);
        userDescriptionTextView = findViewById(R.id.cargoTextView);
        dniDescriptionTextView = findViewById(R.id.textViewDNI);
        mailDescriptionTextView = findViewById(R.id.textViewMail);
        phoneDescriptionTextView = findViewById(R.id.textViewPhone);
        addressDescriptionTextView = findViewById(R.id.textViewAddress);
        profileImageView = findViewById(R.id.imageViewProfileAdmin);
        textViewDevider2User = findViewById(R.id.textViewDevider2User);
        deviderSitiosAsignados = findViewById(R.id.deviderSitiosAsignados);
        assignedSitesLayout = findViewById(R.id.assignedSitesLayout);
        FrameLayout btnAddSiteUserProfile = findViewById(R.id.btnAddSiteUserProfile);

        nameDescriptionTextView.setText(element.getName());
        mailDescriptionTextView.setText(element.getMail());
        userDescriptionTextView.setText(element.getUser());
        dniDescriptionTextView.setText(element.getDni());
        phoneDescriptionTextView.setText(element.getPhone());
        addressDescriptionTextView.setText(element.getAddress());
        estado = element.getStatus();

        if (element.getImageUrl() != null && !element.getImageUrl().isEmpty()) {
            Log.d("UserDetails", "Image URL is not null or empty. URL: " + element.getImageUrl());

            StorageReference imageRef = storageReference.child(element.getImageUrl());

            Log.d("UserDetails", "StorageReference path: " + imageRef.getPath());

            Glide.with(this /* context */)
                    .load(imageRef)
                    .skipMemoryCache(true) // Desactivar la caché de memoria
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Desactivar la caché en disco
                    .into(profileImageView);

            Log.d("UserDetails", "Image loading initiated with Glide.");
        } else {
            Log.d("UserDetails", "Image URL is null or empty.");
        }

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.topAppBarUserPerfil);
        toolbar.setTitle("Perfil " + element.getName().toUpperCase());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        findViewById(R.id.fabEditUserAdmin).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_1_Users_UserDetails.this, MainActivity_1_Users_NewUser.class);
            intent.putExtra("isEditing", true);
            intent.putExtra("ListElement", element);
            startActivity(intent);
        });

        if (btnAddSiteUserProfile.getParent() == null) {
            assignedSitesLayout.addView(btnAddSiteUserProfile, 0);
        }

        btnAddSiteUserProfile.setOnClickListener(v -> {
            Intent intent7 = new Intent(this, MainActivity_2_Sites_AddSite.class);
            intent7.putExtra("idDNI", element.getDni());
            intent7.putExtra("idDNIName", element.getName());
            startActivityForResult(intent7, 1); // UPDATED
        });

        textoHabilitar = findViewById(R.id.deleteUserPerfil);
        if (estado.equals("Activo")) {
            textoHabilitar.setText("Inhabilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0);
        } else {
            textViewDevider2User.setVisibility(View.GONE);
            deviderSitiosAsignados.setVisibility(View.GONE);
            btnAddSiteUserProfile.setVisibility(View.GONE);
            textoHabilitar.setText("Habilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0);
            FloatingActionButton fabEditSiteAdmin = findViewById(R.id.fabEditUserAdmin);
            fabEditSiteAdmin.setVisibility(GONE);
        }

        textoHabilitar.setOnClickListener(v -> showConfirmationDialog(v, estado));
        showAssignedSites(element);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Recuperar el usuario actualizado
            ListElementUser updatedUser = (ListElementUser) data.getSerializableExtra("updatedUser");
            // Actualizar la interfaz de usuario con los nuevos datos
            updateUserDetails(updatedUser);
        }
    }


    private void updateUserDetails(ListElementUser updatedUser) {
        // Actualizar los campos de texto con la información actualizada del usuario
        nameDescriptionTextView.setText(updatedUser.getName());
        mailDescriptionTextView.setText(updatedUser.getMail());
        userDescriptionTextView.setText(updatedUser.getUser());
        dniDescriptionTextView.setText(updatedUser.getDni());
        phoneDescriptionTextView.setText(updatedUser.getPhone());
        addressDescriptionTextView.setText(updatedUser.getAddress());
        estado = updatedUser.getStatus();

        // Rellenar el campo del tipo de usuario

        if (updatedUser.getImageUrl() != null && !updatedUser.getImageUrl().isEmpty()) {
            // Usar Glide para cargar la imagen desde Firebase Storage utilizando la ruta de acceso
            StorageReference imageRef = storageReference.child(updatedUser.getImageUrl());
            Glide.with(this)
                    .load(imageRef)
                    .skipMemoryCache(true) // Desactivar la caché de memoria
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Desactivar la caché en disco
                    .into(profileImageView);
        }

        // Actualizar la imagen de perfil
        /*if (updatedUser.getImageUrl() != null && !updatedUser.getImageUrl().isEmpty()) {
            byte[] decodedString = Base64.decode(updatedUser.getImageUrl(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImageView.setImageBitmap(decodedByte);
        }*/

        // Actualizar los sitios asignados
        showAssignedSites(updatedUser);
    }
    public void showConfirmationDialog(View view, String currentState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");
        final String newState;
        final String message;
        if (currentState.equals("Activo")) {
            message = "¿Está seguro de inhabilitar este usuario?\nSe eliminaran sus sitios asignados.";
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
            update.put("sitiosAsignados", "[]");

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
                        // Registrar el error en el log
                        Log.e("UserDetails", "Error al actualizar el estado del usuario: " + e.getMessage(), e);
                    });
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void showAssignedSites(ListElementUser element) {
        String assignedSitesJson = element.getSitiosAsignados();
        ArrayList<String> assignedSites;

        if (assignedSitesJson == null || assignedSitesJson.isEmpty()) {
            assignedSites = new ArrayList<>();
        } else {
            assignedSites = new Gson().fromJson(assignedSitesJson, new TypeToken<ArrayList<String>>() {}.getType());
        }

        int childCount = assignedSitesLayout.getChildCount();
        assignedSitesLayout.removeAllViews();

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
                    // Actualizar la UI con los sitios asignados actualizados
                    showAssignedSites(element); // ADD
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
