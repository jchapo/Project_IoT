package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.Admin.MainActivity_2_Sites_SiteImages;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ImagenesSitio extends AppCompatActivity {

    private LinearLayout contenedorImagenes;
    private ActivityResultLauncher<String> imagePickerLauncher;
    private ArrayList<String> imageUrls;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private String siteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_imagenes_sitio);

        // Configuración de EdgeToEdge y Layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contenedorImagenes = findViewById(R.id.contenedorImagenes);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarimagenmas);
        topAppBar.inflateMenu(R.menu.top_app_bar_imagen_mas);
        topAppBar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        topAppBar.setNavigationOnClickListener(v -> finish());

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onImagePicked);

        // Obtener las URLs de las imágenes y el nombre del sitio desde el Intent
        String assignedImageJson = getIntent().getStringExtra("imagenesSitio");
        siteName = getIntent().getStringExtra("siteName");

        if (assignedImageJson != null && !assignedImageJson.isEmpty()) {
            imageUrls = new Gson().fromJson(assignedImageJson, new TypeToken<ArrayList<String>>() {}.getType());
        } else {
            imageUrls = new ArrayList<>();
        }

        // Cargar las imágenes guardadas
        loadSavedImages();
    }

    private void loadSavedImages() {
        for (int i = 0; i < imageUrls.size(); i++) {
            String imageUrl = imageUrls.get(i);
            StorageReference imageRef = storageReference.child(imageUrl);
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            Glide.with(this)
                    .load(imageRef)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
            contenedorImagenes.addView(imageView);

            final int index = i; // Necesario para acceder al índice dentro del listener
            imageView.setOnLongClickListener(v -> {
                showDeleteConfirmationDialog(imageUrl, index);
                return true;
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addPhotoSite) {
            openFileChooser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openFileChooser() {
        imagePickerLauncher.launch("image/*");
    }

    private void onImagePicked(Uri imageUri) {
        if (imageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                Bitmap resizedBitmap = getResizedBitmap(bitmap, 600); // Redimensionar a que el lado más largo sea 600 píxeles

                // Mostrar la imagen redimensionada en la interfaz de usuario
                mostrarImagen(resizedBitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos); // Comprimir con calidad del 80%
                byte[] imageBytes = baos.toByteArray();

                String storagePath = "siteimages/" + siteName + "_" + System.currentTimeMillis() + ".jpg";
                imageUrls.add(storagePath);

                Set<String> updatedImages = new HashSet<>(imageUrls);
                String updatedSitesJson = new JSONArray(updatedImages).toString();

                StorageReference ref = storageReference.child(storagePath);
                ref.putBytes(imageBytes)
                        .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            updateImageUrlsInFirestore();
                            // Si necesitas actualizar algo más después de subir la imagen, puedes hacerlo aquí.
                        }))
                        .addOnFailureListener(e -> {
                            Toast.makeText(ImagenesSitio.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Manejo de caso cuando imageUri es nulo, si es necesario
        }
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void mostrarImagen(Bitmap bitmap) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(bitmap);
        contenedorImagenes.addView(imageView);

        final int index = imageUrls.size() - 1; // Index of the newly added image
        imageView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(imageUrls.get(index), index);
            return true;
        });
    }

    private void showDeleteConfirmationDialog(String imageUrl, int index) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Deseas eliminar esta imagen?")
                .setPositiveButton("Sí", (dialog, which) -> deleteImage(imageUrl, index))
                .setNegativeButton("No", null)
                .show();
    }

    private void deleteImage(String imageUrl, int index) {
        StorageReference imageRef = storageReference.child(imageUrl);
        imageRef.delete().addOnSuccessListener(aVoid -> {
            // Eliminar la URL de la lista y actualizar Firestore
            imageUrls.remove(index);
            updateImageUrlsInFirestore();
            // Eliminar la imagen de la vista
            contenedorImagenes.removeViewAt(index);
            Toast.makeText(ImagenesSitio.this, "Imagen eliminada", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(ImagenesSitio.this, "Error al eliminar la imagen", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        });
    }

    private void updateImageUrlsInFirestore() {
        // Asegurarse de que siteName no sea nulo
        if (siteName != null) {
            Set<String> updatedImages = new HashSet<>(imageUrls);
            String updatedSitesJson = new JSONArray(updatedImages).toString();
            db.collection("sitios").document(siteName).update("imageUrl", updatedSitesJson)
                    .addOnSuccessListener(aVoid -> Log.d("Firestore", "DocumentSnapshot successfully updated!"))
                    .addOnFailureListener(e -> Log.w("Firestore", "Error updating document", e));
        } else {
            Log.e("Firestore", "siteName is null, cannot update Firestore.");
        }
    }

    @Override
    public void finish() {
        // Devolver las URLs de las imágenes actualizadas a la actividad anterior
        Intent data = new Intent();
        String updatedImagesJson = new JSONArray(imageUrls).toString();
        data.putExtra("updatedImages", updatedImagesJson);
        setResult(RESULT_OK, data);
        super.finish();
    }
}