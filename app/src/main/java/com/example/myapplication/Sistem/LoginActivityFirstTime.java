package com.example.myapplication.Sistem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.NavegacionInicial;
import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivityFirstTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_main_login_user_first_time);

        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivityFirstTime.this, NavegacionInicial.class);
                startActivity(intent);
            }
        });

        TextView textForgotPassword = findViewById(R.id.textForgotPasswordFirstTime); // Reemplaza "textForgotPassword" con el ID correcto de tu TextView
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