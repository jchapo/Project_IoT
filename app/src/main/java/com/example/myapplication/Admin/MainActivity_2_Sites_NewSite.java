package com.example.myapplication.Admin;

import android.os.Bundle;
import android.content.Intent;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MainActivity_2_Sites_NewSite extends AppCompatActivity {

    ListElementSite element;
    private EditText editAddress, editUbigeo, editSiteCoordenadas;
    double latitude, longitude;
    String nombredesitio;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private boolean isEditing = false; // Indicador para editar o crear nuevo sitio
    private MaterialAutoCompleteTextView selectDepartment, selectProvince, selectDistrict, selectZoneType, selectSiteType;
    ArrayAdapter<String> departmentAdapter,provinceAdapter,districtAdapter,zoneTypeAdapter, siteTypeAdapter;

    String[] departmentOptions = {
            "Amazonas", "Áncash","Apurímac","Arequipa","Ayacucho","Cajamarca","Callao","Cusco","Huancavelica","Huánuco","Ica","Junín","La Libertad","Lambayeque","Lima","Loreto","Madre de Dios","Moquegua","Pasco","Piura",
            "Puno","San Martín","Tacna","Tumbes","Ucayali","Amazonas","Áncash","Apurímac","Arequipa","Ayacucho","Cajamarca","Callao",
            "Cusco","Huancavelica","Huánuco","Ica","Junín","La Libertad","Lambayeque","Lima","Loreto","Madre de Dios","Moquegua",
            "Pasco","Piura","Puno","San Martín","Tacna","Tumbes","Ucayali"};
    // Cambia a tus opciones reales
    String[] provinceOptions = {"Province A", "Province B", "Province C"}; // Cambia a tus opciones reales
    String[] districtOptions = {"District X", "District Y", "District Z"}; // Cambia a tus opciones reales
    String[] zoneTypeOptions = {"Zone Type 1", "Zone Type 2", "Zone Type 3"}; // Cambia a tus opciones reales
    String[] siteTypeOptions = {"Site Type 1", "Site Type 2", "Site Type 3"}; // Cambia a tus opciones reales
    FirebaseFirestore db;
    int resultSize;

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

        db = FirebaseFirestore.getInstance();

        TextInputLayout textInputLayout = findViewById(R.id.textInputLayoutLongitud);

        // Obtener el indicador de si se está editando desde el Intent
        isEditing = getIntent().getBooleanExtra("isEditing", false);


        imageView = findViewById(R.id.imageViewNewSite);
        imageView.setOnClickListener(v -> openFileChooser());
        editAddress = findViewById(R.id.editAddress);
        editUbigeo = findViewById(R.id.editUbigeo);
        editSiteCoordenadas = findViewById(R.id.editCoordenadas);

        db.collection("sitios")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        resultSize = 0;
                        if (task.getResult() != null) {
                            resultSize = task.getResult().size();
                        }
                        Log.d("msg-test", "Cantidad de documentos en la colección 'sitios': " + resultSize);
                    } else {
                        Log.d("msg-test", "Error getting documents: ", task.getException());
                    }
                });


        MaterialToolbar topAppBar = findViewById(R.id.topAppBarNewSite);
        if (isEditing) {
            // Si se está editando, inflar el menú de editar sitio
            topAppBar.inflateMenu(R.menu.top_app_bar_edit);
        } else {
            // Si se está creando, inflar el menú de crear sitio
            topAppBar.inflateMenu(R.menu.top_app_bar_new);
        }

        // Verificar si se está editando un sitio existente o creando uno nuevo
        Intent intent = getIntent();
        if (intent.hasExtra("ListElementSite")) {
            element = (ListElementSite) intent.getSerializableExtra("ListElementSite");
            isEditing = true; // Indicar que se está editando un sitio existente
            fillFields(element); // Llenar campos con los datos del sitio existente
            topAppBar.setTitle("Editar Sitio"); // Cambiar título de la actividad
        } else {
            topAppBar.setTitle("Nuevo Sitio"); // Cambiar título de la actividad
        }


        topAppBar.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.createNewTopAppBar) {
                        if (areFieldsEmpty()) {
                            Toast.makeText(MainActivity_2_Sites_NewSite.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
                        } else {
                            String department = selectDepartment.getText().toString();
                            String province = selectProvince.getText().toString();
                            String district = selectDistrict.getText().toString();
                            String zonetype = selectZoneType.getText().toString();
                            String sitetype = selectSiteType.getText().toString();
                            String location = editSiteCoordenadas.getText().toString();
                            Double latitud = latitude;
                            Double longitud = longitude;
                            String coordenadas = String.valueOf(latitud) + " ; " + String.valueOf(longitud);
                            String name = department.substring(0, 3) + "-" + String.valueOf(resultSize+1);
                            String address = editAddress.getText().toString();
                            String status = "Activo";
                            String ubigeo = editUbigeo.getText().toString();
                            LocalDate fechaActual = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String fechaCreacion = fechaActual.format(formatter);

                            ListElementSite listElement = new ListElementSite(department, name, status, province, district, address, location, ubigeo, zonetype, sitetype, latitud, longitud, coordenadas, fechaCreacion);
                            db.collection("sitios")
                                    .document(name)
                                    .set(listElement)
                                    .addOnSuccessListener(unused -> {
                                        Log. d("msg-test","Data guardada exitosamente");
                                    })
                                    .addOnFailureListener(e -> e.printStackTrace()) ;

                            // Iniciar la actividad de perfil de sitio y pasar los datos
                            Intent intent2 = new Intent(MainActivity_2_Sites_NewSite.this, MainActivity_2_Sites_SiteDetails.class);
                            intent2.putExtra("ListElementSite", listElement);
                            startActivity(intent2);
                            finish();
                        }
                        return true;
                    }else if (item.getItemId() == R.id.saveOldTopAppBar) {
                        if (areFieldsEmpty()) {
                            Toast.makeText(MainActivity_2_Sites_NewSite.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
                        } else {
                            String department = selectDepartment.getText().toString();
                            String province = selectProvince.getText().toString();
                            String district = selectDistrict.getText().toString();
                            String zonetype = selectZoneType.getText().toString();
                            String sitetype = selectSiteType.getText().toString();
                            String location = editSiteCoordenadas.getText().toString();
                            Double latitud = latitude;
                            Double longitud = longitude;
                            String coordenadas = String.valueOf(latitud) + " ; " + String.valueOf(longitud);
                            String name = department.substring(0, 3) + "-" + String.valueOf(resultSize+1);
                            String address = editAddress.getText().toString();
                            String status = "Activo";
                            String ubigeo = editUbigeo.getText().toString();
                            LocalDate fechaActual = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String fechaCreacion = fechaActual.format(formatter);

                            ListElementSite listElement = new ListElementSite(department, name, status, province, district, address, location, ubigeo, zonetype, sitetype, latitud, longitud, coordenadas, fechaCreacion);

                            db.collection("sitios")
                                    .document(name)
                                    .set(listElement)
                                    .addOnSuccessListener(unused -> {
                                        Log. d("msg-test","Data guardada exitosamente");
                                    })
                                    .addOnFailureListener(e -> e.printStackTrace()) ;

                            Intent intent3 = new Intent(MainActivity_2_Sites_NewSite.this, MainActivity_2_Sites_SiteDetails.class);
                            intent3.putExtra("ListElement", listElement);
                            startActivity(intent3);
                        }
                        return true;
                    } else{
                        return false;
                    }
        });
/*
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
                    Toast.makeText(MainActivity_2_Sites_NewSite.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
                    return false; // No continuar si falta algún dato
                }


                // Si todos los datos están completos, crear el elemento de sitio
                String location = editSiteCoordenadas.getText().toString();
                Double latitud = latitude;
                Double longitud = longitude;
                String coordenadas = String.valueOf(latitud) + " ; " + String.valueOf(longitud);
                String name = (item.getItemId() == R.id.createNewTopAppBar) ? department.substring(0, 3) + "-" + String.valueOf(resultSize+1) : element.getName();
                String address = editAddress.getText().toString();
                String status = "Activo";
                String ubigeo = editUbigeo.getText().toString();
                LocalDate fechaActual = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaCreacion = fechaActual.format(formatter);

                // Crear el objeto ListElementSite con los valores seleccionados
                ListElementSite listElement = new ListElementSite(department, name, status, province, district, address, location, ubigeo, zonetype, sitetype, latitud, longitud, coordenadas, fechaCreacion);
                db.collection("sitios")
                        .document(name)
                        .set(listElement)
                        .addOnSuccessListener(unused -> {
                            Log. d("msg-test","Data guardada exitosamente");
                        })
                        .addOnFailureListener(e -> e.printStackTrace()) ;

                // Iniciar la actividad de perfil de sitio y pasar los datos
                Intent intent2 = new Intent(MainActivity_2_Sites_NewSite.this, MainActivity_2_Sites_SiteDetails.class);
                intent2.putExtra("ListElementSite", listElement);
                startActivity(intent2);

                return true;
            } else {
                return false;
            }
        });
*/
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
        // Primero, registra un ActivityResultLauncher en tu actividad
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Aquí maneja el resultado de la actividad MapActivity si es necesario
                        if (result.getData() != null) {
                            // Aquí obtén los datos de ubicación seleccionados si los hay
                            latitude = result.getData().getDoubleExtra("latitude", 0.0);
                            longitude = result.getData().getDoubleExtra("longitude", 0.0);
                            // Luego puedes hacer algo con la ubicación seleccionada
                            // Por ejemplo, mostrarla en un TextView o almacenarla en una base de datos
                            editSiteCoordenadas.setText(String.format(Locale.getDefault(), "%.6f ; %.6f", latitude, longitude));

                        }
                    }
                });

// Luego, en tu onClickListener, usa el ActivityResultLauncher para iniciar la actividad
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí iniciamos la actividad MapActivity para que el usuario pueda seleccionar la ubicación
                Intent intent = new Intent(MainActivity_2_Sites_NewSite.this, MainActivity_2_Sites_MappChooser.class);
                launcher.launch(intent);
            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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