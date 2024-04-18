package com.example.myapplication.Sistem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.MainActivity_navigation_admin;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.NavegacionSupervisor;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_users);

        Button buttonLogin = findViewById(R.id.buttonLogin); // Reemplaza "buttonLogin" con el ID correcto de tu botón

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, NavegacionSupervisor.class);
                startActivity(intent);
            }
        });

        TextView textForgotPassword = findViewById(R.id.textForgotPassword); // Reemplaza "textForgotPassword" con el ID correcto de tu TextView
        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showForgotPasswordPopup();
            }
        });
    }

    private void showForgotPasswordPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Correo de recuperación:");

        final EditText editTextEmail = new EditText(this);
        editTextEmail.setHint("Correo electrónico");
        builder.setView(editTextEmail);

        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userEmail = editTextEmail.getText().toString();
                Toast.makeText(LoginActivity.this, "Correo electrónico enviado", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}