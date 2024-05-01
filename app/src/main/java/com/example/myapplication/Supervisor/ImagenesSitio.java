package com.example.myapplication.Supervisor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;

public class ImagenesSitio extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private LinearLayout contenedorImagenes;
    private ActivityResultLauncher<String> imagePickerLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_activity_imagenes_sitio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contenedorImagenes = findViewById(R.id.contenedorImagenes);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarimagenmas);
        topAppBar.inflateMenu(R.menu.top_app_bar_imagen_mas);
        topAppBar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        topAppBar.setNavigationOnClickListener(v -> finish());

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onImagePicked);

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
        // Iniciar la actividad para seleccionar una imagen usando el Activity Result API
        imagePickerLauncher.launch("image/*");
    }

    // Método llamado cuando se elige una imagen
    private void onImagePicked(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            mostrarImagen(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarImagen(Bitmap bitmap) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(bitmap);
        contenedorImagenes.addView(imageView);
    }
}