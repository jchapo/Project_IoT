package com.example.myapplication.Admin;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.textfield.TextInputLayout;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Arrays;
import java.util.Locale;

public class MainActivity_new_site_admin extends AppCompatActivity {

    private EditText editDepartment, editProvince, editDistrict, editAddress, editUbigeo, editZoneType, editSiteType, editSiteCoordenadas;
    private static final int PICK_LOCATION_REQUEST = 1;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    double latitude, longitude;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_new_site);
        TextInputLayout textInputLayout = findViewById(R.id.textInputLayoutLongitud);

        Places.initialize(getApplicationContext(), "AIzaSyAExTORfkCED6S7JMtHAnYKhJVJ5J9inaw");
        PlacesClient placesClient = Places.createClient(this);

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

                    String location = "NombreGeneradoAutomáticamente";
                    String latitud = "NombreGeneradoAutomáticamente";
                    String longitud = "NombreGeneradoAutomáticamente";
                    String name = "NombreGeneradoAutomáticamente";
                    //double latitud = latitude;

                    String name2 = "NombreGeneradoAutomáticamente";
                    //double longitud = longitude;
                    String status = "Activo";
                    //String coordenadas = String.format(Locale.getDefault(), "%.6f ; %.6f", longitud, latitud);

                    ListElementSite listElement = new ListElementSite(department, name2, status, province, district, address, location ,ubigeo, zonetype, sitetype, latitud, longitud);

                    
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
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
                        .setCountry("PE") // Cambia esto al código de país que desees
                        .build(MainActivity_new_site_admin.this);

                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
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
        } else if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();

                // Intercambiar los valores de latitud y longitud
                latitude = latLng.longitude; // Aquí se guarda la longitud
                longitude = latLng.latitude; // Aquí se guarda la latitud

                // Formatear las coordenadas en el formato "longitud ; latitud"
                String formattedCoordinates = String.format(Locale.getDefault(), "%.6f ; %.6f", longitude, latitude);

                // Colocar las coordenadas en el campo de texto
                editSiteCoordenadas.setText(formattedCoordinates);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // El usuario canceló la selección
            }
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