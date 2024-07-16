package com.example.myapplication.Sistem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.MainActivity_0_NavigationAdmin;
import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.MainActivity_navigation_SuperAdmin;
import com.example.myapplication.Supervisor.NavegacionSupervisor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_main_login_users);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        TextView textFirstTimeLogin = findViewById(R.id.textFirstTimeLogin);
        textFirstTimeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivityCambiarPrimeraContra.class);
                startActivity(intent);
            }
        });

        TextView textForgotPassword = findViewById(R.id.textForgotPassword);
        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarContraActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        TextInputLayout textInputLayoutEmail = findViewById(R.id.textInputEmail);
        TextInputLayout textInputLayoutPassword = findViewById(R.id.textInputPassword);

        TextInputEditText editTextEmail = (TextInputEditText) textInputLayoutEmail.getEditText();
        TextInputEditText editTextPassword = (TextInputEditText) textInputLayoutPassword.getEditText();

        if (editTextEmail != null && editTextPassword != null) {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty()) {
                textInputLayoutEmail.setError("Email is required.");
                return;
            } else {
                textInputLayoutEmail.setError(null);
            }

            if (password.isEmpty()) {
                textInputLayoutPassword.setError("Password is required.");
                return;
            } else {
                textInputLayoutPassword.setError(null);
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    checkUserRole(user.getEmail());
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "EditText is null.", Toast.LENGTH_SHORT).show();
        }
    }


    private void checkUserRole(String email) {
        db.collection("usuarios")
                .whereEqualTo("mail", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.firestore.QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.firestore.QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                String userRole = document.getString("user");
                                String userName = document.getString("name");
                                String userLastName = document.getString("lastname");
                                String userPhone = document.getString("phone");
                                navigateBasedOnRole(userRole, userName, userLastName, email, userPhone);
                            } else {
                                Toast.makeText(LoginActivity.this, "No user found with this email.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to retrieve user role.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void navigateBasedOnRole(String role, String name, String lastname, String email, String phone) {
        Intent intent;
        switch (role) {
            case "Supervisor":
                intent = new Intent(LoginActivity.this, NavegacionSupervisor.class);
                break;
            case "Administrador":
                intent = new Intent(LoginActivity.this, MainActivity_0_NavigationAdmin.class);
                break;
            case "Superadministrador":
                intent = new Intent(LoginActivity.this, MainActivity_navigation_SuperAdmin.class);
                break;
            default:
                Toast.makeText(this, "Unknown role.", Toast.LENGTH_SHORT).show();
                return;
        }

        // Pasar los datos del usuario al siguiente Activity
        intent.putExtra("USER_NAME", name);
        intent.putExtra("USER_LASTNAME", lastname);
        intent.putExtra("USER_EMAIL", email);
        intent.putExtra("USER_PHONE", phone);

        startActivity(intent);
        finish();
    }
}
