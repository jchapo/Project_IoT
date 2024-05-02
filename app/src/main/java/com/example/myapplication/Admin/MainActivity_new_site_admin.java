package com.example.myapplication.Admin;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

    private EditText editAddress, editUbigeo, editSiteCoordenadas;
    private static final int PICK_LOCATION_REQUEST = 1;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    double latitude, longitude;
    String nombredesitio;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private boolean isEditing = false; // Indicador para editar o crear nuevo sitio

    // Declarar los elementos MaterialAutoCompleteTextView para los campos de autocompletado
    private MaterialAutoCompleteTextView selectDepartment;
    private MaterialAutoCompleteTextView selectProvince;
    private MaterialAutoCompleteTextView selectDistrict;
    private MaterialAutoCompleteTextView selectZoneType;
    private MaterialAutoCompleteTextView selectSiteType;

    // Declarar los adaptadores para los campos de autocompletado
    ArrayAdapter<String> departmentAdapter;
    ArrayAdapter<String> provinceAdapter;
    ArrayAdapter<String> districtAdapter;
    ArrayAdapter<String> zoneTypeAdapter;
    ArrayAdapter<String> siteTypeAdapter;

    // Declarar los arrays de opciones para los campos de autocompletado
    String[] departmentOptions = {
            "Amazonas",
            "Áncash",
            "Apurímac",
            "Arequipa",
            "Ayacucho",
            "Cajamarca",
            "Callao",
            "Cusco",
            "Huancavelica",
            "Huánuco",
            "Ica",
            "Junín",
            "La Libertad",
            "Lambayeque",
            "Lima",
            "Loreto",
            "Madre de Dios",
            "Moquegua",
            "Pasco",
            "Piura",
            "Puno",
            "San Martín",
            "Tacna",
            "Tumbes",
            "Ucayali",
            "Amazonas",
            "Áncash",
            "Apurímac",
            "Arequipa",
            "Ayacucho",
            "Cajamarca",
            "Callao",
            "Cusco",
            "Huancavelica",
            "Huánuco",
            "Ica",
            "Junín",
            "La Libertad",
            "Lambayeque",
            "Lima",
            "Loreto",
            "Madre de Dios",
            "Moquegua",
            "Pasco",
            "Piura",
            "Puno",
            "San Martín",
            "Tacna",
            "Tumbes",
            "Ucayali"
    };
    // Cambia a tus opciones reales
    String[] provinceOptions = {"Province A", "Province B", "Province C"}; // Cambia a tus opciones reales
    String[] districtOptions = {"District X", "District Y", "District Z"}; // Cambia a tus opciones reales
    String[] zoneTypeOptions = {"Zone Type 1", "Zone Type 2", "Zone Type 3"}; // Cambia a tus opciones reales
    String[] siteTypeOptions = {"Site Type 1", "Site Type 2", "Site Type 3"}; // Cambia a tus opciones reales


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_new_site);

        // Encuentra las referencias a los campos de autocompletado
        selectDepartment = findViewById(R.id.selectDepartment);
        selectProvince = findViewById(R.id.selectProvince);
        selectDistrict = findViewById(R.id.selectDistrict);
        selectZoneType = findViewById(R.id.selectZoneType);
        selectSiteType = findViewById(R.id.selectSiteType);

        // Establece el adaptador con las opciones en el AutoCompleteTextView
        // Inicializa los adaptadores con las opciones
        departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, departmentOptions);
        provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, provinceOptions);
        districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, districtOptions);
        zoneTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, zoneTypeOptions);
        siteTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, siteTypeOptions);

        // Asigna los adaptadores a los campos de autocompletado
        selectDepartment.setAdapter(departmentAdapter);
        selectProvince.setAdapter(provinceAdapter);
        selectDistrict.setAdapter(districtAdapter);
        selectZoneType.setAdapter(zoneTypeAdapter);
        selectSiteType.setAdapter(siteTypeAdapter);

        TextInputLayout textInputLayout = findViewById(R.id.textInputLayoutLongitud);

        Places.initialize(getApplicationContext(), "AIzaSyAExTORfkCED6S7JMtHAnYKhJVJ5J9inaw");
        PlacesClient placesClient = Places.createClient(this);

        // Obtener el indicador de si se está editando desde el Intent
        isEditing = getIntent().getBooleanExtra("isEditing", false);


        imageView = findViewById(R.id.imageViewNewSite);
        imageView.setOnClickListener(v -> openFileChooser());
        editAddress = findViewById(R.id.editAddress);
        editUbigeo = findViewById(R.id.editUbigeo);
        editSiteCoordenadas = findViewById(R.id.editCoordenadas);



        MaterialToolbar topAppBar = findViewById(R.id.topAppBarNewSite);
        if (isEditing) {
            // Si se está editando, inflar el menú de editar sitio
            topAppBar.inflateMenu(R.menu.top_app_bar_admin_edit);
        } else {
            // Si se está creando, inflar el menú de crear sitio
            topAppBar.inflateMenu(R.menu.top_app_bar_admin_new);
        }

        // Verificar si se está editando un sitio existente o creando uno nuevo
        Intent intent = getIntent();
        if (intent.hasExtra("ListElementSite")) {
            ListElementSite element = (ListElementSite) intent.getSerializableExtra("ListElementSite");
            isEditing = true; // Indicar que se está editando un sitio existente
            fillFields(element); // Llenar campos con los datos del sitio existente
            topAppBar.setTitle("Editar Sitio"); // Cambiar título de la actividad
        } else {
            topAppBar.setTitle("Nuevo Sitio"); // Cambiar título de la actividad
        }

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createNewTopAppBar || item.getItemId() == R.id.saveOldTopAppBar) {
                // Obtener los valores seleccionados de los menús desplegables
                String department = selectDepartment.getText().toString();
                String province = selectProvince.getText().toString();
                String district = selectDistrict.getText().toString();
                String zonetype = selectZoneType.getText().toString();
                String sitetype = selectSiteType.getText().toString();

                // Verificar si los campos están vacíos
                if (TextUtils.isEmpty(department) || TextUtils.isEmpty(province) || TextUtils.isEmpty(district) ||
                        TextUtils.isEmpty(zonetype) || TextUtils.isEmpty(sitetype)) {
                    Toast.makeText(MainActivity_new_site_admin.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
                    return false; // No continuar si falta algún dato
                }

                // Si todos los datos están completos, crear el elemento de sitio
                String location = "NombreGeneradoAutomáticamente";
                String latitud = "NombreGeneradoAutomáticamente";
                String longitud = "NombreGeneradoAutomáticamente";
                String name = "NombreGeneradoAutomáticamente";
                String status = "Activo";

                // Crear el objeto ListElementSite con los valores seleccionados
                ListElementSite listElement = new ListElementSite(department, name, status, province, district, "", location, "", zonetype, sitetype, latitud, longitud);

                // Iniciar la actividad de perfil de sitio y pasar los datos
                Intent intent2 = new Intent(MainActivity_new_site_admin.this, MainActivity_siteprofile_admin.class);
                intent2.putExtra("ListElementSite", listElement);
                startActivity(intent2);

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
                // El sitio canceló la selección
            }
        }
    }

    private boolean areFieldsEmpty() {
        return selectDepartment.getText().toString().isEmpty() ||
                selectProvince.getText().toString().isEmpty() ||
                selectDistrict.getText().toString().isEmpty() ||
                selectZoneType.getText().toString().isEmpty() ||
                selectSiteType.getText().toString().isEmpty() ||
                editAddress.getText().toString().isEmpty() ||
                editUbigeo.getText().toString().isEmpty();
    }

    private void fillFields(ListElementSite element) {
        selectDepartment.setText(element.getDepartment());
        selectProvince.setText(element.getProvince());
        selectDistrict.setText(element.getDistrict());
        selectZoneType.setText(element.getZonetype());
        selectSiteType.setText(element.getSitetype());
        editSiteCoordenadas.setText(element.getCoordenadas());
        nombredesitio = element.getName();
        editAddress.setText(element.getAddress());
        editUbigeo.setText(element.getUbigeo());
        editSiteCoordenadas.setText(element.getCoordenadas());
        nombredesitio = element.getName();
    }

}