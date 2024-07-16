package com.example.myapplication.Sistem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContraActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistem_activity_recuperar_contra);

        mAuth = FirebaseAuth.getInstance();

        TextInputEditText editTextEmail = findViewById(R.id.editTextEmail);
        Button buttonResetPassword = findViewById(R.id.buttonResetPassword);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                if (!email.isEmpty()) {
                    resetPassword(email);
                } else {
                    Toast.makeText(RecuperarContraActivity.this, "Por favor, ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RecuperarContraActivity.this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                            Toast.makeText(RecuperarContraActivity.this, "Error al enviar el correo de recuperación: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
