package com.example.myapplication.Admin;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;

import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

public class MainActivity_2_Sites_NewSite extends AppCompatActivity {
    String canal1 = "importanteDefault3";
    ListElementSite element;
    private EditText editAddress, editUbigeo, editSiteCoordenadas, editDistrict;
    double latitude, longitude;
    String nombredesitio;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private boolean isEditing = false; // Indicador para editar o crear nuevo sitio
    private boolean isImageAdded = false; // Variable para indicar si se ha agregado una imagen
    private Uri imageUri;
    private MaterialAutoCompleteTextView selectDepartment, selectProvince, selectZoneType, selectSiteType;
    ArrayAdapter<String> departmentAdapter,provinceAdapter,zoneTypeAdapter, siteTypeAdapter;

    String[] departmentOptions = {
            "Amazonas", "Áncash","Apurímac","Arequipa","Ayacucho","Cajamarca","Callao","Cusco","Huancavelica","Huánuco","Ica","Junín","La Libertad","Lambayeque","Lima","Loreto","Madre de Dios","Moquegua","Pasco","Piura",
            "Puno","San Martín","Tacna","Tumbes","Ucayali","Amazonas","Áncash","Apurímac","Arequipa","Ayacucho","Cajamarca","Callao",
            "Cusco","Huancavelica","Huánuco","Ica","Junín","La Libertad","Lambayeque","Lima","Loreto","Madre de Dios","Moquegua",
            "Pasco","Piura","Puno","San Martín","Tacna","Tumbes","Ucayali"};
    // Cambia a tus opciones reales
    //String[] provinceOptions = {"Province A", "Province B", "Province C"}; // Cambia a tus opciones reales
    //String[] districtOptions = {"District X", "District Y", "District Z"}; // Cambia a tus opciones reales
    String[] zoneTypeOptions = {"Rural", "Urbano"}; // Cambia a tus opciones reales
    String[] siteTypeOptions = {"Fijo", "Móvil"}; // Cambia a tus opciones reales
    FirebaseFirestore db;
    int resultSize;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    Map<String, String[]> provincesMap = new HashMap<>();
    //Map<String, String[]> districtsMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_new_site);
        // Inicializar los Maps
        initializeLocationData();

        // Encuentra las referencias a los campos de autocompletado
        selectDepartment = findViewById(R.id.selectDepartment);
        selectProvince = findViewById(R.id.selectProvince);

        // Inicializa los adaptadores con las opciones
        departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, departmentOptions);
        selectDepartment.setAdapter(departmentAdapter);

        // Listener para el departamento
        selectDepartment.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDepartment = departmentOptions[position];
            String[] provinces = provincesMap.get(selectedDepartment);
            if (provinces != null) {
                provinceAdapter = new ArrayAdapter<>(MainActivity_2_Sites_NewSite.this, android.R.layout.simple_dropdown_item_1line, provinces);
                selectProvince.setAdapter(provinceAdapter);
                selectProvince.setText("", false); // Clear previous selection
            }
        });

        // Encuentra las referencias a los campos de autocompletado
        selectDepartment = findViewById(R.id.selectDepartment);
        selectProvince = findViewById(R.id.selectProvince);
        selectZoneType = findViewById(R.id.selectZoneType);
        selectSiteType = findViewById(R.id.selectSiteType);

        // Inicializa los adaptadores con las opciones
        //departmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, departmentOptions);
        //provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, provinceOptions);
        zoneTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, zoneTypeOptions);
        siteTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, siteTypeOptions);

        // Asigna los adaptadores a los campos de autocompletado
        selectDepartment.setAdapter(departmentAdapter);
        selectProvince.setAdapter(provinceAdapter);
        selectZoneType.setAdapter(zoneTypeAdapter);
        selectSiteType.setAdapter(siteTypeAdapter);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null){
                        imageUri = result.getData().getData();
                        imageView.setImageURI(imageUri);
                        isImageAdded = true; // Actualizar la variable cuando se selecciona una imagen
                    }
                });

        db = FirebaseFirestore.getInstance();

        TextInputLayout textInputLayout = findViewById(R.id.textInputLayoutLongitud);

        // Obtener el indicador de si se está editando desde el Intent
        isEditing = getIntent().getBooleanExtra("isEditing", false);


        imageView = findViewById(R.id.imageViewNewSite);
        imageView.setOnClickListener(v -> openFileChooser());
        editAddress = findViewById(R.id.editAddress);
        editDistrict = findViewById(R.id.editDistrict);
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
                createNewSite();
                return true;
            } else if (item.getItemId() == R.id.saveOldTopAppBar) {
                updateExistingSite();
                return true;
            } else {
                return false;
            }
        });


        /*topAppBar.setOnMenuItemClickListener(item -> {
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

                            ListElementSite listElement = new ListElementSite(department, name, status, province, district, address, location, ubigeo, zonetype, sitetype, latitud, longitud, coordenadas, fechaCreacion,imagen);
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
        }); */

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
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void uploadImageAndSaveSite(ListElementSite listElement, boolean isEditing) {
        // Obtener el bitmap desde el ImageView
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        // Convertir el bitmap a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        // Convertir byte array a base64
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        listElement.setImageUrl(encodedImage);

        saveSiteToFirestore(listElement, isEditing);
    }

    private void saveSiteToFirestore(ListElementSite listElement, boolean isEditing) {
        Log.d("msg-test", "entro a saveSiteToFirestore");

        if (listElement == null) {
            Log.e("msg-test", "listElement es null");
            return;
        }

        if (listElement.getName() == null) {
            Log.e("msg-test", "listElement.getName() es null");
            return;
        }

        Log.d("msg-test", "Guardando en Firestore: " + listElement.getName());

        db.collection("sitios")
                .document(listElement.getName())
                .set(listElement, SetOptions.merge())
                .addOnSuccessListener(unused -> {
                    Log.d("msg-test", isEditing ? "Datos actualizados exitosamente" : "Data guardada exitosamente");
                    // Mover la apertura de la nueva actividad aquí, dentro de onSuccessListener
                    openSiteDetailsActivity(listElement);
                })
                .addOnFailureListener(e -> {
                    Log.e("msg-test", "Error al guardar en Firestore", e);
                    e.printStackTrace();
                });
    }

    private void openSiteDetailsActivity(ListElementSite listElement) {
        Log.d("msg-test", "Abriendo MainActivity_2_Sites_SiteDetails con ListElementSite: " + listElement.getName());
        Intent intent = new Intent(MainActivity_2_Sites_NewSite.this, MainActivity_2_Sites_SiteDetails.class);
        intent.putExtra("ListElementSite", listElement);
        startActivity(intent);
    }


    private void createNewSite() {
        if (areFieldsEmpty() || !isImageAdded) {
            Toast.makeText(MainActivity_2_Sites_NewSite.this, "Debe completar todos los datos y seleccionar una imagen", Toast.LENGTH_SHORT).show();
        } else {
            String department = selectDepartment.getText().toString();
            String province = selectProvince.getText().toString();
            String district = editDistrict.getText().toString();
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
            String imagen = "";

            ListElementSite listElement = new ListElementSite(department, name, status, province, district, address, location, ubigeo, zonetype, sitetype, latitud, longitud, coordenadas, fechaCreacion, imagen);
            uploadImageAndSaveSite(listElement, false);
        }
    }

    private void updateExistingSite() {
        if (areFieldsEmpty()) {
            Toast.makeText(MainActivity_2_Sites_NewSite.this, "Debe completar todos los datos y seleccionar una imagen", Toast.LENGTH_SHORT).show();
        } else {
            String department = selectDepartment.getText().toString();
            String province = selectProvince.getText().toString();
            String district = editDistrict.getText().toString();
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
            String imagen = "";

            ListElementSite listElement = new ListElementSite(department, name, status, province, district, address, location, ubigeo, zonetype, sitetype, latitud, longitud, coordenadas, fechaCreacion, imagen);
            uploadImageAndSaveSite(listElement, true);
        }
    }

    private boolean areFieldsEmpty() {
        return selectDepartment.getText().toString().isEmpty() ||
                selectProvince.getText().toString().isEmpty() ||
                editDistrict.getText().toString().isEmpty() ||
                selectZoneType.getText().toString().isEmpty() ||
                selectSiteType.getText().toString().isEmpty() ||
                editAddress.getText().toString().isEmpty() ||
                editUbigeo.getText().toString().isEmpty();
    }

    private void fillFields(ListElementSite element) {
        selectDepartment.setText(element.getDepartment());
        selectProvince.setText(element.getProvince());
        editDistrict.setText(element.getDistrict());
        selectZoneType.setText(element.getZonetype());
        selectSiteType.setText(element.getSitetype());
        editSiteCoordenadas.setText(element.getCoordenadas());
        nombredesitio = element.getName();
        editAddress.setText(element.getAddress());
        editUbigeo.setText(element.getUbigeo());
        editSiteCoordenadas.setText(element.getCoordenadas());
        nombredesitio = element.getName();

        if (element.getImageUrl() != null && !element.getImageUrl().isEmpty()) {
            byte[] decodedString = Base64.decode(element.getImageUrl(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
            isImageAdded = true; // Actualizar la variable cuando se rellena una imagen existente
        }
    }

    public void crearCanalesNotificacion() {
        NotificationChannel channel = new NotificationChannel(canal1,
                "Canal Users Creation",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Canal para notificaciones de creación de perfiles de usuario con prioridad default");
        channel.enableVibration(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        pedirPermisos();
    }

    public void pedirPermisos() {
        // TIRAMISU = 33
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS, READ_EXTERNAL_STORAGE}, 101);
        }
    }

    public void notificarImportanceDefault(String fullname, String fechaCreacion) {
        //Crear notificación
        //Agregar información a la notificación que luego sea enviada a la actividad que se abre
        Intent intent = new Intent(this, MainActivity_0_NavigationAdmin.class);
        intent.putExtra("pid", 4616);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        //
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal1)
                .setSmallIcon(R.drawable.ic_addperson_filled_black)
                .setContentTitle("Nuevo registrado")
                .setContentText("Usuario : " + fullname + "\nFecha: " + fechaCreacion)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = builder.build();

        //Lanzar notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, notification);
        }
    }

    private void initializeLocationData() {
        // Mapa de provincias por departamento
        provincesMap.put("Amazonas", new String[]{"Chachapoyas", "Bagua", "Bongará", "Condorcanqui", "Luya", "Rodríguez de Mendoza", "Utcubamba"});
        provincesMap.put("Áncash", new String[]{"Huaraz", "Aija", "Antonio Raymondi", "Asunción", "Bolognesi", "Carhuaz", "Carlos Fermín Fitzcarrald", "Casma", "Corongo", "Huari", "Huarmey", "Huaylas", "Mariscal Luzuriaga", "Ocros", "Pallasca", "Pomabamba", "Recuay", "Santa", "Sihuas", "Yungay"});
        provincesMap.put("Apurímac", new String[]{"Abancay", "Andahuaylas", "Antabamba", "Aymaraes", "Cotabambas", "Chincheros", "Grau"});
        provincesMap.put("Arequipa", new String[]{"Arequipa", "Camana", "Caraveli", "Castilla", "Caylloma", "Condesuyos", "Islay", "La Union"});
        provincesMap.put("Ayacucho", new String[]{"Huamanga", "Cangallo", "Huanca Sancos", "Huanta", "La Mar", "Lucanas", "Parinacochas", "Páucar del Sara Sara", "Sucre", "Víctor Fajardo", "Vilcas Huamán"});
        provincesMap.put("Cajamarca", new String[]{"Cajamarca", "Cajabamba", "Celendín", "Chota", "Contumazá", "Cutervo", "Hualgayoc", "Jaén", "San Ignacio", "San Marcos", "San Miguel", "San Pablo", "Santa Cruz"});
        provincesMap.put("Callao", new String[]{"Callao"});
        provincesMap.put("Cusco", new String[]{"Cusco", "Acomayo", "Anta", "Calca", "Canas", "Canchis", "Chumbivilcas", "Espinar", "La Convención", "Paruro", "Paucartambo", "Quispicanchi", "Urubamba"});
        provincesMap.put("Huancavelica", new String[]{"Huancavelica", "Acobamba", "Angaraes", "Castrovirreyna", "Churcampa", "Huaytará", "Tayacaja"});
        provincesMap.put("Huánuco", new String[]{"Huánuco", "Ambo", "Dos de Mayo", "Huacaybamba", "Huamalíes", "Leoncio Prado", "Marañón", "Pachitea", "Puerto Inca", "Lauricocha", "Yarowilca"});
        provincesMap.put("Ica", new String[]{"Ica", "Chincha", "Nazca", "Palpa", "Pisco"});
        provincesMap.put("Junín", new String[]{"Huancayo", "Concepción", "Chanchamayo", "Jauja", "Junín", "Satipo", "Tarma", "Yauli", "Chupaca"});
        provincesMap.put("La Libertad", new String[]{"Trujillo", "Ascope", "Bolívar", "Chepén", "Julcán", "Otuzco", "Pacasmayo", "Pataz", "Sánchez Carrión", "Santiago de Chuco", "Gran Chimú", "Virú"});
        provincesMap.put("Lambayeque", new String[]{"Chiclayo", "Ferreñafe", "Lambayeque"});
        provincesMap.put("Lima", new String[]{"Lima", "Barranca", "Cajatambo", "Cañete", "Canta", "Huaral", "Huarochirí", "Huaura", "Oyón", "Yauyos"});
        provincesMap.put("Loreto", new String[]{"Maynas", "Alto Amazonas", "Datem del Marañón", "Loreto", "Mariscal Ramón Castilla", "Putumayo", "Requena", "Ucayali"});
        provincesMap.put("Madre de Dios", new String[]{"Tambopata", "Manu", "Tahuamanu"});
        provincesMap.put("Moquegua", new String[]{"Mariscal Nieto", "General Sánchez Cerro", "Ilo"});
        provincesMap.put("Pasco", new String[]{"Pasco", "Daniel Alcides Carrión", "Oxapampa"});
        provincesMap.put("Piura", new String[]{"Piura", "Ayabaca", "Huancabamba", "Morropón", "Paita", "Sullana", "Talara", "Sechura"});
        provincesMap.put("Puno", new String[]{"Puno", "Azángaro", "Carabaya", "Chucuito", "El Collao", "Huancané", "Lampa", "Melgar", "Moho", "San Antonio de Putina", "San Román", "Sandia", "Yunguyo"});
        provincesMap.put("San Martín", new String[]{"Moyobamba", "Bellavista", "El Dorado", "Huallaga", "Lamas", "Mariscal Cáceres", "Picota", "Rioja", "San Martín", "Tocache"});
        provincesMap.put("Tacna", new String[]{"Tacna", "Candarave", "Jorge Basadre", "Tarata"});
        provincesMap.put("Tumbes", new String[]{"Tumbes", "Contralmirante Villar", "Zarumilla"});
        provincesMap.put("Ucayali", new String[]{"Coronel Portillo", "Atalaya", "Padre Abad", "Purús"});
    }

}