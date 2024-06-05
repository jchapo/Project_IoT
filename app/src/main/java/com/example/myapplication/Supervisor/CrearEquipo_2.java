package com.example.myapplication.Supervisor;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.example.myapplication.Admin.MainActivity_0_NavigationAdmin;
import com.example.myapplication.Admin.MainActivity_1_Users_NewUser;
import com.example.myapplication.Admin.MainActivity_1_Users_UserDetais;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CrearEquipo_2 extends AppCompatActivity {

    String canal1 = "importanteDefault2";
    private MaterialAutoCompleteTextView selectTypeDevice, selectSite;
    ArrayAdapter<String> typeDeviceAdapter, typeSiteAdapter;
    String[] typeOptionsDevices = {"Router","Switch","Hub","Patch Pannel","Gateway"};
    String[] typeOptionsSites = {"Por jalar"};

    private EditText editBrand, editModelo, editDescription, editSKU;
    private ImageView imageView;
    private boolean isEditing = false; // Indicador para editar o crear nuevo usuario
    private boolean isImageAdded = false; // Variable para indicar si se ha agregado una imagen

    private static final int PICK_IMAGE_REQUEST = 1;
    FirebaseFirestore db;
    private Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_activity_crear_equipo_2);

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageView = findViewById(R.id.imageViewDevice);
        imageView.setOnClickListener(v -> openFileChooser());

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        imageView.setImageURI(imageUri);
                        isImageAdded = true; // Actualizar la variable cuando se selecciona una imagen
                    }
                });

        pedirPermisos();

        crearCanalesNotificacion();
        selectTypeDevice = findViewById(R.id.selectTypeDevice);
        typeDeviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, typeOptionsDevices);
        selectTypeDevice.setAdapter(typeDeviceAdapter);

        selectSite = findViewById(R.id.selectSite);
        typeSiteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, typeOptionsSites);
        selectSite.setAdapter(typeSiteAdapter);

        db = FirebaseFirestore.getInstance();

        // Obtener el indicador de si se está editando desde el Intent
        isEditing = getIntent().getBooleanExtra("isEditing", false);


        editBrand = findViewById(R.id.editBrand);
        editModelo = findViewById(R.id.editModelo);
        editDescription = findViewById(R.id.editDescription);
        editSKU = findViewById(R.id.editSKU);


        MaterialToolbar topAppBar = findViewById(R.id.topAppBarNewDevice);
        if (isEditing) {
            // Si se está editando, inflar el menú de editar usuario
            topAppBar.inflateMenu(R.menu.top_app_bar_edit);
        } else {
            // Si se está creando, inflar el menú de crear usuario
            topAppBar.inflateMenu(R.menu.top_app_bar_new);
        }




        // Verificar si se está editando un usuario existente o creando uno nuevo
        Intent intent = getIntent();
        if (intent.hasExtra("ListElement")) {
            ListElementEquiposNuevo element = (ListElementEquiposNuevo) intent.getSerializableExtra("ListElement");
            isEditing = true; // Indicar que se está editando un usuario existente
            fillFields(element); // Llenar campos con los datos del usuario existente
            topAppBar.setTitle("Editar Equipo"); // Cambiar título de la actividad
        } else {
            topAppBar.setTitle("Nuevo Equipo"); // Cambiar título de la actividad
        }

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createNewTopAppBar) {
                createNewEquipment();
                return true;
            } else if (item.getItemId() == R.id.saveOldTopAppBar) {
                updateExistingEquipment();
                return true;
            } else {
                return false;
            }
        });

        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void uploadImageAndSaveEquipment(ListElementEquiposNuevo listElement, boolean isEditing) {
        // Obtener el bitmap desde el ImageView
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        // Convertir el bitmap a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        // Convertir byte array a base64
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        listElement.setImagenEquipo(encodedImage);

        saveEquipmentToFirestore(listElement, isEditing);
    }



    private void saveEquipmentToFirestore(ListElementEquiposNuevo listElement, boolean isEditing) {
        Log.d("msg-test", "entro a saveEquipmentToFirestore");
        db.collection("equipos")
                .document(listElement.getSku())
                .set(listElement, SetOptions.merge())
                .addOnSuccessListener(unused -> {
                    Log.d("msg-test", isEditing ? "Datos actualizados exitosamente" : "Data guardada exitosamente");
                })
                .addOnFailureListener(e -> e.printStackTrace());
        Intent intent3 = new Intent(CrearEquipo_2.this, MasDetallesEquipos_2.class);
        intent3.putExtra("ListElement", listElement);
        startActivity(intent3);
    }

    private void createNewEquipment() {
        if (areFieldsEmpty() || !isImageAdded) {
            Toast.makeText(CrearEquipo_2.this, "Debe completar todos los datos y seleccionar una imagen", Toast.LENGTH_SHORT).show();
        } else {

            String marca = editBrand.getText().toString();
            String modelo = editModelo.getText().toString();
            String tipoEquipo = selectTypeDevice.getText().toString();
            String descripcionEquipo = editDescription.getText().toString();
            String status = "Activo";
            String idSitio = selectSite.getText().toString();
            String sku = editSKU.getText().toString();

            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fecha_ingreso = fechaActual.format(formatter);

            String imagenEquipo = "";
            String nameEquipo = marca + " " + modelo;

            ListElementEquiposNuevo listElement = new ListElementEquiposNuevo(nameEquipo,marca,modelo,tipoEquipo,descripcionEquipo,status,idSitio,sku,fecha_ingreso,imagenEquipo);
            uploadImageAndSaveEquipment(listElement, false);
        }
    }

    private void updateExistingEquipment() {
        if (areFieldsEmpty()) {
            Toast.makeText(CrearEquipo_2.this, "Debe completar todos los datos y seleccionar una imagen", Toast.LENGTH_SHORT).show();
        } else {
            String marca = editBrand.getText().toString();
            String modelo = editModelo.getText().toString();
            String tipoEquipo = selectTypeDevice.getText().toString();
            String descripcionEquipo = editDescription.getText().toString();
            String status = "Activo";
            String idSitio = selectSite.getText().toString();
            String sku = editSKU.getText().toString();

            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fecha_ingreso = fechaActual.format(formatter);

            String imagenEquipo = "";
            String nameEquipo = marca + " " + modelo;

            ListElementEquiposNuevo listElement = new ListElementEquiposNuevo(nameEquipo,marca,modelo,tipoEquipo,descripcionEquipo,status,idSitio,sku,fecha_ingreso,imagenEquipo);
            uploadImageAndSaveEquipment(listElement, false);
        }
    }

    private boolean areFieldsEmpty(){
        return editBrand.getText().toString().isEmpty() ||
                editModelo.getText().toString().isEmpty() ||
                editDescription.getText().toString().isEmpty() ||
                editSKU.getText().toString().isEmpty();
    }

    private void fillFields(ListElementEquiposNuevo element) {

        editBrand.setText(element.getMarca());
        editModelo.setText(element.getModelo());
        editDescription.setText(element.getDescripcionEquipo());
        editSKU.setText(element.getSku());

        selectTypeDevice.setText(element.getTipoEquipo(),false);
        selectSite.setText(element.getIdSitio(),false);

        if (element.getImagenEquipo() != null && !element.getImagenEquipo().isEmpty()) {
            byte[] decodedString = Base64.decode(element.getImagenEquipo(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
            isImageAdded = true; // Actualizar la variable cuando se rellena una imagen existente
        }
    }


    public void crearCanalesNotificacion() {
        NotificationChannel channel = new NotificationChannel(canal1,
                "Canal Equipment Creation",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Canal para notificaciones de creación de equipos con prioridad default");
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


}