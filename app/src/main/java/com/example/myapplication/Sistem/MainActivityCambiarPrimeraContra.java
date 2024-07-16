package com.example.myapplication.Sistem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class MainActivityCambiarPrimeraContra extends AppCompatActivity {

    private TextInputEditText editTextEmail;
    private TextInputEditText editTextNewPassword;
    private TextInputEditText editTextConfirmPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_cambiar_contrasena_first_time);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        Button buttonAccept = findViewById(R.id.buttonAccept);

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (newPassword.equals(confirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(email, newPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivityCambiarPrimeraContra.this, "Se ha registrado con éxito", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivityCambiarPrimeraContra.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivityCambiarPrimeraContra.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(MainActivityCambiarPrimeraContra.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.topAppBarNewSite);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
    }
}
