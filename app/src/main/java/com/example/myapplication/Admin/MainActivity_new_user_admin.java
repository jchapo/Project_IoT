package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity_new_user_admin extends AppCompatActivity {
    private EditText editFirstName, editLastName, editDNI, editMail, editAddress, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_user_admin);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastttName);
        editDNI = findViewById(R.id.editDNI);
        editMail = findViewById(R.id.editMail);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarUsers);
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createUser) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_admin_crear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Manejar el clic en el botón de navegación (en este caso, la flecha hacia atrás)
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
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