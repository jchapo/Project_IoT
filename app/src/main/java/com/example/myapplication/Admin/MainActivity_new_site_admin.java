package com.example.myapplication.Admin;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity_new_site_admin extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_site_admin);

        imageView = findViewById(R.id.imageViewProfile);

        // Abre el navegador de archivos cuando se hace clic en la imagen
        imageView.setOnClickListener(v -> openFileChooser());

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarSites);
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createSite) {
                if (areFieldsEmpty()) {
                    Toast.makeText(MainActivity_new_user_admin.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    String firstName = editFirstName.getText().toString();
                    String lastName = editLastName.getText().toString();
                    String dni = editDNI.getText().toString();
                    String mail = editMail.getText().toString();
                    String address = editAddress.getText().toString();
                    String phone = editPhone.getText().toString();
                    String user = "Supervisor";
                    String status = "Activo";

                    ListElementUser listElement = new ListElementUser(dni, firstName, lastName, user,status, mail, phone, address);

                    Intent intent = new Intent(MainActivity_new_user_admin.this, MainActivity_userprofile_admin.class);
                    intent.putExtra("ListElement", listElement);
                    startActivity(intent);
                }
                return true;
            } else {
                return false;
            }
        });
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private boolean areFieldsEmpty() {
        return editFirstName.getText().toString().isEmpty() ||
                editLastName.getText().toString().isEmpty() ||
                editDNI.getText().toString().isEmpty() ||
                editMail.getText().toString().isEmpty() ||
                editAddress.getText().toString().isEmpty() ||
                editPhone.getText().toString().isEmpty();
    }
}