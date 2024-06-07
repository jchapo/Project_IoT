package com.example.myapplication.Sistem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.NavegacionInicial;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_main_login_users);

        mAuth = FirebaseAuth.getInstance();

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        TextInputLayout textInputLayoutEmail = findViewById(R.id.textInputEmail);
        TextInputLayout textInputLayoutPassword = findViewById(R.id.textInputPassword);

        TextInputEditText editTextEmail = (TextInputEditText) textInputLayoutEmail.getEditText();
        TextInputEditText editTextPassword = (TextInputEditText) textInputLayoutPassword.getEditText();


        if (editTextEmail != null && editTextPassword != null) {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(LoginActivity.this, NavegacionInicial.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // Handle the case where editTextEmail or editTextPassword is null
            Toast.makeText(LoginActivity.this, "EditText is null.",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
