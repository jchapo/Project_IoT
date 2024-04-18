package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity_new_user_admin extends AppCompatActivity {
    private EditText editFirstName, editLastName, editDNI, editMail, editAddress, editPhone;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private boolean isEditing = false; // Indicador para editar o crear nuevo usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_user_admin);

        // Obtener el indicador de si se está editando desde el Intent
        isEditing = getIntent().getBooleanExtra("isEditing", false);

        imageView = findViewById(R.id.imageViewProfile);
        imageView.setOnClickListener(v -> openFileChooser());

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastttName);
        editDNI = findViewById(R.id.editDNI);
        editMail = findViewById(R.id.editMail);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarNewUser);
        if (isEditing) {
            // Si se está editando, inflar el menú de editar usuario
            topAppBar.inflateMenu(R.menu.top_app_bar_admin_edit_user);
        } else {
            // Si se está creando, inflar el menú de crear usuario
            topAppBar.inflateMenu(R.menu.top_app_bar_admin_new_user);
        }

        // Verificar si se está editando un usuario existente o creando uno nuevo
        Intent intent = getIntent();
        if (intent.hasExtra("ListElement")) {
            ListElementUser element = (ListElementUser) intent.getSerializableExtra("ListElement");
            isEditing = true; // Indicar que se está editando un usuario existente
            fillFields(element); // Llenar campos con los datos del usuario existente
            topAppBar.setTitle("Editar Usuario"); // Cambiar título de la actividad
        } else {
            topAppBar.setTitle("Nuevo Usuario"); // Cambiar título de la actividad
        }

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createNewUser) {
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

                    Intent intent2 = new Intent(MainActivity_new_user_admin.this, MainActivity_userprofile_admin.class);
                    intent2.putExtra("ListElement", listElement);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_admin_new_user, menu);
        return true;
    }

    private boolean areFieldsEmpty() {
        return editFirstName.getText().toString().isEmpty() ||
                editLastName.getText().toString().isEmpty() ||
                editDNI.getText().toString().isEmpty() ||
                editMail.getText().toString().isEmpty() ||
                editAddress.getText().toString().isEmpty() ||
                editPhone.getText().toString().isEmpty();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void fillFields(ListElementUser element) {
        editFirstName.setText(element.getName());
        editLastName.setText(element.getLastname());
        editDNI.setText(element.getDni());
        editMail.setText(element.getMail());
        editAddress.setText(element.getAddress());
        editPhone.setText(element.getPhone());
        // Implementa la lógica para mostrar la imagen de perfil
    }

    private void updateExistingUser() {
        // Implementa la lógica para actualizar los datos del usuario existente
    }

    private void createNewUser() {
        // Implementa la lógica para crear un nuevo usuario
    }
}