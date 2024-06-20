package com.example.myapplication.Admin;

import static android.view.View.GONE;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.ImagenesSitio;
import com.example.myapplication.Supervisor.MasDetallesSitioSupervisor;
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

public class MainActivity_2_Sites_SiteDetails extends AppCompatActivity {
    private static final int REQUEST_CODE_UPDATE_SITE = 1;
    private static final int REQUEST_CODE_UPDATE_IMAGES = 2;

    TextView nameTextViewSite;
    TextView departmentDescriptionTextView, provinceDescriptionTextView, districtDescriptionTextView;
    TextView textoHabilitar,textViewDevider2Site;
    FirebaseFirestore db;
    String estado;
    ImageView profileImageView;
    View deviderSuperAsignados;
    LinearLayout assignedSuperLayout;
    FirebaseStorage storage;
    StorageReference storageReference;
    String coordenadas;
    ListElementSite element;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_siteprofile);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        element = (ListElementSite) getIntent().getSerializableExtra("ListElementSite");

        nameTextViewSite = findViewById(R.id.nameTextViewSiteSiteProfile);
        departmentDescriptionTextView = findViewById(R.id.departmentTextViewSite);
        provinceDescriptionTextView = findViewById(R.id.provinceTextViewSite);
        districtDescriptionTextView = findViewById(R.id.districtTextViewSite);
        profileImageView = findViewById(R.id.imageViewProfileSite);
        textViewDevider2Site = findViewById(R.id.textViewDevider2Site);
        deviderSuperAsignados = findViewById(R.id.deviderSuperAsignados);
        assignedSuperLayout = findViewById(R.id.assignedSuperLayout);
        FrameLayout btnAddSuperSiteProfile = findViewById(R.id.btnAddSuperSiteProfile);

        nameTextViewSite.setText(element.getName());
        departmentDescriptionTextView.setText(element.getDepartment());
        provinceDescriptionTextView.setText(element.getProvince());
        districtDescriptionTextView.setText(element.getDistrict());
        estado = element.getStatus();
        coordenadas = element.getCoordenadas();

        String assignedImageJson = element.getImageUrl();
        ArrayList<String> assignedImage = new ArrayList<>();
        String imagenUrl = "";

        if (assignedImageJson != null && !assignedImageJson.isEmpty()) {
            assignedImage = new Gson().fromJson(assignedImageJson, new TypeToken<ArrayList<String>>() {}.getType());
            if (assignedImage != null && !assignedImage.isEmpty()) {
                imagenUrl = assignedImage.get(0);
                Log.d("msg2", imagenUrl);
            }
        }

        if (imagenUrl != null && !imagenUrl.isEmpty()) {
            StorageReference imageRef = storageReference.child(imagenUrl);

            Log.d("UserDetails", "StorageReference path: " + imageRef.getPath());
            ImageView imageView = findViewById(R.id.imageViewProfileSite);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(layoutParams);

            Glide.with(this /* context */)
                    .load(imageRef)
                    .skipMemoryCache(true) // Desactivar la caché de memoria
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Desactivar la caché en disco
                    .into(imageView);

            Log.d("UserDetails", "Image loading initiated with Glide.");
        } else {
            Log.d("UserDetails", "Image URL is null or empty.");
        }


        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfilAdmin);
        toolbar.setTitle("Detalles "+element.getName().toUpperCase());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        findViewById(R.id.fabEditSiteAdmin).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_2_Sites_SiteDetails.this, MainActivity_2_Sites_NewSite.class);
            intent.putExtra("isEditing", true);
            intent.putExtra("ListElement", element);
            startActivity(intent);
        });

        if (btnAddSuperSiteProfile.getParent() == null) {
            assignedSuperLayout.addView(btnAddSuperSiteProfile, 0);
        }

        btnAddSuperSiteProfile.setOnClickListener(v -> {
            Intent intent7 = new Intent(this, MainActivity_2_Sites_AddSupervisor.class);
            intent7.putExtra("siteName", element.getName());
            startActivityForResult(intent7, REQUEST_CODE_UPDATE_SITE); // UPDATED
        });

        textoHabilitar = findViewById(R.id.deleteSitePerfil);
        if (estado.equals("Activo")) {
            // Cambiar el texto, color y ícono para "Inhabilitar Sitio"
            textoHabilitar.setText("Inhabilitar Sitio");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0); // Icono a la izquierda
        } else {
            textViewDevider2Site.setVisibility(GONE);
            deviderSuperAsignados.setVisibility(GONE);
            btnAddSuperSiteProfile.setVisibility(GONE);            textoHabilitar.setText("Habilitar Sitio");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme())); // Suponiendo que tienes un color para habilitar
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0); // Icono a la izquierda
            FloatingActionButton fabEditSiteAdmin = findViewById(R.id.fabEditSiteAdmin);
            fabEditSiteAdmin.setVisibility(GONE);        }

        textoHabilitar.setOnClickListener(v -> showConfirmationDialog(v, estado));

        ImageButton buttonImagesSiteAdmin = findViewById(R.id.buttonImagesSiteAdmin);
        buttonImagesSiteAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity_2_Sites_SiteDetails.this, MainActivity_2_Sites_SiteImages.class);
            intent.putExtra("imagenesSitio", element.getImageUrl());
            intent.putExtra("siteName", element.getName());  // Pasar el nombre del sitio
            startActivityForResult(intent, REQUEST_CODE_UPDATE_IMAGES);
        });

        ImageButton buttonMapSite = findViewById(R.id.buttonMapSite);
        buttonMapSite.setOnClickListener(v -> openMapActivity(element));
        showAssignedSuper(element);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_UPDATE_SITE:
                    ListElementSite updatedSite = (ListElementSite) data.getSerializableExtra("updatedSite");
                    updateSiteDetails(updatedSite);
                    break;

                case REQUEST_CODE_UPDATE_IMAGES:
                    String updatedImagesJson = data.getStringExtra("updatedImages");
                    element.setImageUrl(updatedImagesJson);
                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }


    private void updateSiteDetails(ListElementSite updatedSite) {
        nameTextViewSite.setText(updatedSite.getName());
        departmentDescriptionTextView.setText(updatedSite.getDepartment());
        provinceDescriptionTextView.setText(updatedSite.getProvince());
        districtDescriptionTextView.setText(updatedSite.getDistrict());
        estado = updatedSite.getStatus();
        coordenadas = updatedSite.getCoordenadas();

        // Rellenar el campo del tipo de usuario

        if (updatedSite.getImageUrl() != null && !updatedSite.getImageUrl().isEmpty()) {
            // Usar Glide para cargar la imagen desde Firebase Storage utilizando la ruta de acceso
            StorageReference imageRef = storageReference.child(updatedSite.getImageUrl());
            Glide.with(this)
                    .load(imageRef)
                    .skipMemoryCache(true) // Desactivar la caché de memoria
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Desactivar la caché en disco
                    .into(profileImageView);
        }

        showAssignedSuper(updatedSite);
    }
    private void showAssignedSuper(ListElementSite element) {
        String assignedSuperJson = element.getSuperAsignados();
        ArrayList<String> assignedSuper;

        if (assignedSuperJson == null || assignedSuperJson.isEmpty()) {
            assignedSuper = new ArrayList<>();
        } else {
            assignedSuper = new Gson().fromJson(assignedSuperJson, new TypeToken<ArrayList<String>>() {}.getType());
        }

        int childCount = assignedSuperLayout.getChildCount();
        assignedSuperLayout.removeAllViews();

        for (String supervisor : assignedSuper) {
            View siteCard = getLayoutInflater().inflate(R.layout.admin_super_asignado_card, assignedSuperLayout, false);
            TextView siteTextView = siteCard.findViewById(R.id.superAsignedTextView);
            siteTextView.setText(supervisor);

            ImageView removeButton = siteCard.findViewById(R.id.botonEliminarSuperAsignado);
            removeButton.setOnClickListener(v -> showRemoveSuperConfirmationDialog(element, supervisor));

            assignedSuperLayout.addView(siteCard);
        }
    }
    private void showRemoveSuperConfirmationDialog(ListElementSite element, String supervisor) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Está seguro de que desea remover este supervisor?");
        builder.setPositiveButton("Aceptar", (dialog, which) -> removeSuper(element, supervisor));
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
    private void removeSuper(ListElementSite element, String supervisor) {
        String assignedSuperJson = element.getSuperAsignados();
        ArrayList<String> assignedSuper = new Gson().fromJson(assignedSuperJson, new TypeToken<ArrayList<String>>(){}.getType());
        assignedSuper.remove(supervisor);

        element.setSuperAsignados(new Gson().toJson(assignedSuper));
        // Actualizar en Firestore
        db.collection("sitios")
                .document(element.getName())
                .update("superAsignados", new Gson().toJson(assignedSuper))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Supervisor removido", Toast.LENGTH_SHORT).show();
                    // Actualizar la UI con los sitios asignados actualizados
                    showAssignedSuper(element); // Asegurarse de ejecutar en el hilo de UI
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al remover el supervisor", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
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
                update.put("superAsignados", "[]");

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
        TextView textoHabilitar = findViewById(R.id.deleteSitePerfil);
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
    private void openMapActivity(ListElementSite element) {
        String[] parts = element.getCoordenadas().split(";");
        if (parts.length == 2) {
            try {
                double latitude = Double.parseDouble(parts[0].trim());
                double longitude = Double.parseDouble(parts[1].trim());
                Intent intent = new Intent(MainActivity_2_Sites_SiteDetails.this, MainActivity_2_SiteLocation.class);
                intent.putExtra("siteName", element.getName());
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
            } catch (NumberFormatException e) {
                Log.e("MainActivity_2_Sites_SiteDetails", "Error al parsear coordenadas", e);
            }
        } else {
            Log.e("MainActivity_2_Sites_SiteDetails", "Formato de coordenadas incorrecto");
        }
    }

}