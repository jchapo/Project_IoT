package com.example.myapplication.SuperAdmin;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.Admin.MainActivity_1_Users_UserDetails;
import com.example.myapplication.Admin.MainActivity_2_Sites_AddSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.list.ListElementSuperAdminUser;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDetais extends AppCompatActivity {

    TextView nameDescriptionTextView;
    TextView userDescriptionTextView;
    TextView dniDescriptionTextView;
    TextView mailDescriptionTextView;
    TextView phoneDescriptionTextView;
    TextView addressDescriptionTextView;
    FirebaseFirestore db;
    TextView textoHabilitar,textViewDevider2;
    String estado;

    ImageView profileImageView;

    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.superadmin_activity_main_userprofile);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ListElementSuperAdminUser element = (ListElementSuperAdminUser) getIntent().getSerializableExtra("ListElement");

        nameDescriptionTextView = findViewById(R.id.fullNameTextView);
        userDescriptionTextView = findViewById(R.id.cargoTextView);
        dniDescriptionTextView = findViewById(R.id.textViewDNI);
        mailDescriptionTextView = findViewById(R.id.textViewMail);
        phoneDescriptionTextView = findViewById(R.id.textViewPhone);
        addressDescriptionTextView = findViewById(R.id.textViewAddress);
        profileImageView = findViewById(R.id.imageViewProfile);

        nameDescriptionTextView.setText(element.getName());
        mailDescriptionTextView.setText(element.getMail());
        userDescriptionTextView.setText(element.getUser());
        dniDescriptionTextView.setText(element.getDni());
        phoneDescriptionTextView.setText(element.getPhone());
        addressDescriptionTextView.setText(element.getAddress());
        estado = element.getStatus();

        if (element.getImageUrl() != null && !element.getImageUrl().isEmpty()) {
            Log.d("UserDetails", "Image URL is not null or empty. URL: " + element.getImageUrl());

            // Usar FirebaseImageLoader para cargar la imagen desde Firebase Storage
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

        Toolbar toolbar = findViewById(R.id.topAppBarUserPerfil);
        toolbar.setTitle("Perfil de " + element.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Agregar Listener al botón flotante de editar
        findViewById(R.id.fabEditUserSuperAdmin).setOnClickListener(v -> {
            Intent intent = new Intent(UserDetais.this, NewAdmin.class);
            intent.putExtra("isEditing", true);
            intent.putExtra("ListElement", element);
            startActivity(intent);
        });



        textoHabilitar = findViewById(R.id.deleteUserPerfil);
        if (estado.equals("Activo")) {
            textoHabilitar.setText("Inhabilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_error, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_delete_outline, 0, 0, 0);
        } else {
            textoHabilitar.setText("Habilitar Usuario");
            textoHabilitar.setTextColor(getResources().getColor(R.color.md_theme_primary, getTheme()));
            textoHabilitar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_outline_green, 0, 0, 0);
        }

        textoHabilitar.setOnClickListener(v -> showConfirmationDialog(v, estado));

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

    }

    public void showConfirmationDialog(View view, String currentState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");
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
                        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
                        // Actualizar la interfaz de usuario
                        updateUIBasedOnState(newState);
                        // Terminar la actividad actual y regresar a la actividad anterior
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Manejo de error en caso de fallo
                        Toast.makeText(this, "Error al actualizar el estado del usuario", Toast.LENGTH_SHORT).show();
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
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Eliminar el supervisor y mostrar el toast
                        Toast.makeText(UserDetais.this, "Sitio removido", Toast.LENGTH_SHORT).show();
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
