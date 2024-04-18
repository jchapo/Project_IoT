package com.example.myapplication.Admin;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Locale;

public class MainActivity_new_site_admin extends AppCompatActivity {

    private EditText editDepartment, editProvince, editDistrict, editAddress, editUbigeo, editZoneType, editSiteType, editSiteLatitud, editSiteLongitud, editSiteCoordenadas;
    private static final int PICK_LOCATION_REQUEST = 1;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_site_admin);
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayoutLongitud);


        imageView = findViewById(R.id.imageViewNewSite);
        imageView.setOnClickListener(v -> openFileChooser());

        editDepartment = findViewById(R.id.editDepartment);
        editProvince = findViewById(R.id.editProvince);
        editDistrict = findViewById(R.id.editDistrict);
        editAddress = findViewById(R.id.editAddress);
        editUbigeo = findViewById(R.id.editUbigeo);
        editZoneType = findViewById(R.id.editZoneType);
        editSiteType = findViewById(R.id.editSiteType);
        editSiteCoordenadas = findViewById(R.id.editCoordenadas);


        MaterialToolbar topAppBar = findViewById(R.id.topAppBarNewSite);
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createNewSite) {
                if (areFieldsEmpty()) {
                    Toast.makeText(MainActivity_new_site_admin.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    String department = editDepartment.getText().toString();
                    String province = editProvince.getText().toString();
                    String district = editDistrict.getText().toString();
                    String address = editAddress.getText().toString();
                    String ubigeo = editUbigeo.getText().toString();
                    String zonetype = editZoneType.getText().toString();
                    String sitetype = editSiteType.getText().toString();
                    String name = "NombreGeneradoAutomáticamente";
                    String latitud = "NombreGeneradoAutomáticamente";
                    String name2 = "NombreGeneradoAutomáticamente";
                    String longitud = "Activo";
                    String status = "Activo";

                    ListElementSite listElement = new ListElementSite(department,name2, status, province, district, address,  ubigeo, zonetype, sitetype, latitud, longitud);
                    
                    Intent intent = new Intent(MainActivity_new_site_admin.this, MainActivity_siteprofile_admin.class);
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
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir Google Maps para seleccionar la ubicación
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent, PICK_LOCATION_REQUEST);
            }
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
        } else if (requestCode == PICK_LOCATION_REQUEST && resultCode == RESULT_OK) {
            // Obtener las coordenadas del punto seleccionado
            double latitude = data.getDoubleExtra("latitude", 0.0);
            double longitude = data.getDoubleExtra("longitude", 0.0);

            // Formatear las coordenadas en el formato "longitud ; latitud"
            String formattedCoordinates = String.format(Locale.getDefault(), "%.6f ; %.6f", longitude, latitude);

            // Colocar las coordenadas en el campo de texto
            editSiteCoordenadas.setText(formattedCoordinates);
        }
    }

    private boolean areFieldsEmpty() {
        return editDepartment.getText().toString().isEmpty() ||
                editProvince.getText().toString().isEmpty() ||
                editDistrict.getText().toString().isEmpty() ||
                editAddress.getText().toString().isEmpty() ||
                editUbigeo.getText().toString().isEmpty() ||
                editZoneType.getText().toString().isEmpty() ||
                editSiteType.getText().toString().isEmpty();
    }

}