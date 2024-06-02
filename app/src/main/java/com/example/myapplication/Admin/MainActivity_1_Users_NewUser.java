package com.example.myapplication.Admin;

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
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.R;
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

public class MainActivity_1_Users_NewUser extends AppCompatActivity {
    String canal1 = "importanteDefault2";
    private MaterialAutoCompleteTextView selectTypeUser;
    ArrayAdapter<String> typeUserAdapter;
    String[] typeOptions = {"Supervisor"};
    private EditText editFirstName, editLastName, editDNI, editMail, editAddress, editPhone, editFechaCreacion, editPrimerInicio;
    private ImageView imageView;
    private boolean isEditing = false; // Indicador para editar o crear nuevo usuario
    private boolean isImageAdded = false; // Variable para indicar si se ha agregado una imagen

    FirebaseFirestore db;
    private Uri imageUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_new_user);

        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageView = findViewById(R.id.imageViewProfile);
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
        selectTypeUser = findViewById(R.id.selectTypeUser);
        typeUserAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, typeOptions);
        selectTypeUser.setAdapter(typeUserAdapter);
        // Establecer la opción preseleccionada en "Supervisor"
        selectTypeUser.setText("Supervisor", false);

        db = FirebaseFirestore.getInstance();

        // Obtener el indicador de si se está editando desde el Intent
        isEditing = getIntent().getBooleanExtra("isEditing", false);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastttName);
        editDNI = findViewById(R.id.editDNI);
        editMail = findViewById(R.id.editMail);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        editFechaCreacion = findViewById(R.id.editFechaCreacion);
        editPrimerInicio = findViewById(R.id.editPrimerInicio);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarNewUser);
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
            ListElementUser element = (ListElementUser) intent.getSerializableExtra("ListElement");
            isEditing = true; // Indicar que se está editando un usuario existente
            fillFields(element); // Llenar campos con los datos del usuario existente
            topAppBar.setTitle("Editar Usuario"); // Cambiar título de la actividad
        } else {
            topAppBar.setTitle("Nuevo Usuario"); // Cambiar título de la actividad
        }

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createNewTopAppBar) {
                createNewUser();
                return true;
            } else if (item.getItemId() == R.id.saveOldTopAppBar) {
                updateExistingUser();
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

    private void uploadImageAndSaveUser(ListElementUser listElement, boolean isEditing) {
        // Obtener el bitmap desde el ImageView
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        // Convertir el bitmap a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        // Convertir byte array a base64
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        listElement.setImageUrl(encodedImage);

        saveUserToFirestore(listElement, isEditing);
    }



    private void saveUserToFirestore(ListElementUser listElement, boolean isEditing) {
        Log.d("msg-test", "entro a saveUserToFirestore");
        db.collection("usuarios")
                .document(listElement.getDni())
                .set(listElement, SetOptions.merge())
                .addOnSuccessListener(unused -> {
                    Log.d("msg-test", isEditing ? "Datos actualizados exitosamente" : "Data guardada exitosamente");
                })
                .addOnFailureListener(e -> e.printStackTrace());
        Intent intent3 = new Intent(MainActivity_1_Users_NewUser.this, MainActivity_1_Users_UserDetais.class);
        intent3.putExtra("ListElement", listElement);
        startActivity(intent3);
    }

    private void createNewUser() {
        if (areFieldsEmpty() || !isImageAdded) {
            Toast.makeText(MainActivity_1_Users_NewUser.this, "Debe completar todos los datos y seleccionar una imagen", Toast.LENGTH_SHORT).show();
        } else {
            String typeUser = selectTypeUser.getText().toString();
            String firstName = editFirstName.getText().toString();
            String lastName = editLastName.getText().toString();
            String dni = editDNI.getText().toString();
            String mail = editMail.getText().toString();
            String address = editAddress.getText().toString();
            String phone = editPhone.getText().toString();
            String status = "Activo";
            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaCreacion = fechaActual.format(formatter);
            Integer primerInicio = 0;
            String imagen = "";

            ListElementUser listElement = new ListElementUser(firstName, lastName, typeUser, status, dni, mail, phone, address, primerInicio, fechaCreacion, imagen);
            uploadImageAndSaveUser(listElement, false);
        }
    }

    private void updateExistingUser() {
        if (areFieldsEmpty()) {
            Toast.makeText(MainActivity_1_Users_NewUser.this, "Debe completar todos los datos y seleccionar una imagen", Toast.LENGTH_SHORT).show();
        } else {
            String typeUser = selectTypeUser.getText().toString();
            String firstName = editFirstName.getText().toString();
            String lastName = editLastName.getText().toString();
            String dni = editDNI.getText().toString();
            String mail = editMail.getText().toString();
            String address = editAddress.getText().toString();
            String phone = editPhone.getText().toString();
            String status = "Activo";
            String fechaCreacion = editFechaCreacion.getText().toString();
            Integer primerInicio = Integer.parseInt(editPrimerInicio.getText().toString());
            String imagen = "";

            ListElementUser listElement = new ListElementUser(firstName, lastName, typeUser, status, dni, mail, phone, address, primerInicio, fechaCreacion, imagen);
            uploadImageAndSaveUser(listElement, true);
        }
    }

    private boolean areFieldsEmpty() {
        return editFirstName.getText().toString().isEmpty() ||
                editLastName.getText().toString().isEmpty() ||
                editDNI.getText().toString().isEmpty() ||
                editMail.getText().toString().isEmpty() ||
                editAddress.getText().toString().isEmpty() ||
                editPhone.getText().toString().isEmpty();
    }

    private void fillFields(ListElementUser element) {
        editFirstName.setText(element.getName());
        editLastName.setText(element.getLastname());
        editDNI.setText(element.getDni());
        editMail.setText(element.getMail());
        editAddress.setText(element.getAddress());
        editPhone.setText(element.getPhone());
        editFechaCreacion.setText(element.getFechaCreacion());
        editPrimerInicio.setText(String.valueOf(element.getPrimerInicio()));

        // Rellenar el campo del tipo de usuario
        selectTypeUser.setText(element.getUser(), false);

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
}
