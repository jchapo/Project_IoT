package com.example.myapplication.Supervisor;

import static android.view.View.GONE;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.Admin.MainActivity_0_NavigationAdmin;
import com.example.myapplication.Admin.MainActivity_1_Users_NewUser;
import com.example.myapplication.Admin.MainActivity_2_Sites_AddSite;
import com.example.myapplication.Admin.MainActivity_2_Sites_NewSite;
import com.example.myapplication.Admin.MainActivity_2_Sites_SiteDetails;
import com.example.myapplication.Admin.MainActivity_2_Sites_SiteImages;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class MasDetallesEquipos_2 extends AppCompatActivity {

    private static final int REQUEST_CODE_UPDATE_IMAGES = 2;
    StorageReference storageReference;
    ListElementEquiposNuevo element;
    View deviderSuperEquipos;

    TextView nameEquipo,nameEquipo2, marca,sku2, modelo, marca2,modelo2,tipoEquipo,descripcionEquipo, status, idSitio, sku, fecha_ingreso,datos_juntos;
    FirebaseStorage storage;
    TextView textoHabilitar,textViewDeviderEquipo;
    String estado;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_activity_mas_detalles_equipos_2);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        element = (ListElementEquiposNuevo) getIntent().getSerializableExtra("ListElementDevices");

        nameEquipo = findViewById(R.id.nameTextViewDevice);
        marca = findViewById(R.id.marcaTextViewEquipo);
        modelo = findViewById(R.id.modeloTextViewEquipo);
        marca2 = findViewById(R.id.textViewMarca);
        modelo2 = findViewById(R.id.textViewModelo);
        descripcionEquipo = findViewById(R.id.textViewDescripcion);
        sku = findViewById(R.id.textViewSku);
        sku2 = findViewById(R.id.skuTextViewEquipo);
        fecha_ingreso = findViewById(R.id.textViewFechaIngreso);
        textViewDeviderEquipo = findViewById(R.id.textViewEliminarEquipo);
        deviderSuperEquipos = findViewById(R.id.deviderSuperEquipos);
        nameEquipo2 = findViewById(R.id.nameTextViewDevice);

        nameEquipo.setText(element.getNameEquipo());
        nameEquipo2.setText(element.getNameEquipo());
        marca2.setText(element.getMarca());
        modelo2.setText(element.getModelo());
        descripcionEquipo.setText(element.getDescripcionEquipo());
        sku.setText(element.getSku());
        estado = element.getStatus();
        fecha_ingreso.setText(element.getFecha_ingreso());

        String assignedImageJson = element.getImagenEquipo();
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
            ImageView imageView = findViewById(R.id.imageViewProfileEquipo);
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

        Toolbar toolbar = findViewById(R.id.topAppBarEquipoPerfilSuper);
        toolbar.setTitle("Detalles "+element.getNameEquipo().toUpperCase()+"-"+element.getModelo().toUpperCase());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        findViewById(R.id.textViewEditarEquipo).setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesEquipos_2.this,CrearEquipo_2.class);
            intent.putExtra("isEditing", true);
            intent.putExtra("ListElement", element);
            startActivity(intent);
        });

        textoHabilitar = findViewById(R.id.textViewEliminarEquipo);
        if (estado.equals("Activo")) {
            // Cambiar el texto, color y ícono para "Inhabilitar equipo"
            textoHabilitar.setText("Inhabilitar Equipo");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0); // Icono a la izquierda
        } else {
            textoHabilitar.setText("Habilitar Equipo");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme())); // Suponiendo que tienes un color para habilitar
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0); // Icono a la izquierda
            FloatingActionButton fabEdit = findViewById(R.id.fabGenerarReporte);
            fabEdit.setVisibility(GONE);        }

        textoHabilitar.setOnClickListener(v -> showConfirmationDialogDevice(v, estado));

        ImageButton buttonImagesSiteAdmin = findViewById(R.id.buttonImagesEquipoSuper);
        buttonImagesSiteAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesEquipos_2.this, ImagenesEquipo.class);
            intent.putExtra("imagenesEquipo", element.getImagenEquipo());
            intent.putExtra("sku", element.getSku());
            startActivityForResult(intent, REQUEST_CODE_UPDATE_IMAGES);
        });

        ImageButton buttonQR = findViewById(R.id.buttonQR);
        buttonQR.setOnClickListener(v -> {
            String imagePath = element.getImagenQr();
            showImageDialog(imagePath);
        });

        FloatingActionButton fabEdit = findViewById(R.id.fabGenerarReporte);
        fabEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesEquipos_2.this, CrearReporte.class);
            startActivity(intent);
        });
    }

    private void showImageDialog(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            Toast.makeText(this, "No hay una imagen QR disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageRef = storage.getReference().child(imagePath);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView imageView = new ImageView(MasDetallesEquipos_2.this);
                Glide.with(MasDetallesEquipos_2.this)
                        .load(uri)
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                imageView.setImageDrawable(resource);
                                new MaterialAlertDialogBuilder(MasDetallesEquipos_2.this)
                                        .setTitle("Imagen QR")
                                        .setView(imageView)
                                        .setPositiveButton("Cerrar", null)
                                        .show();
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                // This can be left empty
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                Toast.makeText(MasDetallesEquipos_2.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(MasDetallesEquipos_2.this, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void showConfirmationDialogDevice(View view, String currentState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");

        final String newState;
        final String message;
        if (currentState.equals("Activo")) {
            message = "¿Está seguro de inhabilitar este equipo?";
            newState = "Inactivo";
        } else {
            message = "¿Está seguro de habilitar este equipo?";
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

                db.collection("equipos")
                        .document(element.getSku())
                        .update(update)
                        .addOnSuccessListener(aVoid -> {
                            String toastMessage = newState.equals("Inactivo") ? "El equipo ha sido inhabilitado" : "El equipo ha sido habilitado";
                            Toast.makeText(MasDetallesEquipos_2.this, toastMessage, Toast.LENGTH_SHORT).show();
                            updateUIBasedOnState(newState);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MasDetallesEquipos_2.this, "Error al actualizar el estado del equipo", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        });
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void updateUIBasedOnState(String state) {
        TextView textoHabilitar = findViewById(R.id.textViewEliminarEquipo);
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

}