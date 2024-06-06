package com.example.myapplication.Sistem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.NavegacionInicial;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivityFirstTime extends AppCompatActivity {

    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_main_login_user_first_time);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                Log.d("LoginActivityFirstTime", "Email: " + email);
                Log.d("LoginActivityFirstTime", "Password: " + password);

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivityFirstTime.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registro exitoso
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {
                                        // Enviar correo de verificación
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(LoginActivityFirstTime.this, "Correo de verificación enviado", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(LoginActivityFirstTime.this, "Error al enviar correo de verificación", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                        // Pasar el correo electrónico a la siguiente actividad
                                        Intent intent = new Intent(LoginActivityFirstTime.this, MainActivityCambiarPrimeraContra.class);
                                        intent.putExtra("USER_EMAIL", email);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    // Error al registrarse, muestra un mensaje al usuario
                                    Toast.makeText(LoginActivityFirstTime.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        TextView textForgotPassword = findViewById(R.id.textForgotPasswordFirstTime);
        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordPopup();
            }
        });
    }

    private void showForgotPasswordPopup() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Correo de recuperación:");

        final TextInputLayout textInputLayout = new TextInputLayout(this);
        textInputLayout.setHint("Correo electrónico");
        int padding = (int) getResources().getDimension(R.dimen.padding_16dp);
        textInputLayout.setPadding(padding, padding, padding, padding);

        final TextInputEditText textInputEditText = new TextInputEditText(this);
        textInputLayout.addView(textInputEditText);

        builder.setView(textInputLayout);

        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userEmail = textInputEditText.getText().toString();
                Toast.makeText(getBaseContext(), "Correo electrónico enviado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
