package com.example.myapplication.Admin;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Dto.UsuarioDto;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity_1_Users_NewUser extends AppCompatActivity {
    String canal1 = "importanteDefault2";
    private MaterialAutoCompleteTextView selectTypeUser;
    ArrayAdapter<String> typeUserAdapter;
    String[] typeOptions = {"Supervisor"};
    private EditText editFirstName, editLastName, editDNI, editMail, editAddress, editPhone, editFechaCreacion, editPrimerInicio;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private boolean isEditing = false; // Indicador para editar o crear nuevo usuario
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_new_user);
        crearCanalesNotificacion();
        selectTypeUser = findViewById(R.id.selectTypeUser);
        typeUserAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, typeOptions);
        selectTypeUser.setAdapter(typeUserAdapter);

        db = FirebaseFirestore.getInstance();

        // Obtener el indicador de si se está editando desde el Intent
        isEditing = getIntent().getBooleanExtra("isEditing", false);

        imageView = findViewById(R.id.imageViewProfile);
        imageView.setOnClickListener(v -> openFileChooser());

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
                if (areFieldsEmpty()) {
                    Toast.makeText(MainActivity_1_Users_NewUser.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
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
                    String fullName = firstName + " " + lastName;

                    ListElementUser listElement = new ListElementUser(dni, firstName, lastName, typeUser, status, mail, phone, address, primerInicio, fechaCreacion);
                    db.collection("usuarios")
                            .document(dni)
                            .set(listElement)
                            .addOnSuccessListener(unused -> {
                                Log. d("msg-test","Data guardada exitosamente");
                            })
                            .addOnFailureListener(e -> e.printStackTrace()) ;

                    Intent intent2 = new Intent(MainActivity_1_Users_NewUser.this, MainActivity_1_Users_UserDetais.class);
                    intent2.putExtra("ListElement", listElement);
                    notificarImportanceDefault(fullName, fechaCreacion);
                    startActivity(intent2);
                }
                return true;
            } else if (item.getItemId() == R.id.saveOldTopAppBar) {
                if (areFieldsEmpty()) {
                    Toast.makeText(MainActivity_1_Users_NewUser.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
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

                    ListElementUser listElement = new ListElementUser(dni, firstName, lastName, typeUser, status, mail, phone, address, primerInicio, fechaCreacion);

                    // Actualizamos el documento existente
                    db.collection("usuarios")
                            .document(dni) // Utilizamos el identificador del documento
                            .set(listElement) // Sobreescribimos los datos con los nuevos valores
                            .addOnSuccessListener(unused -> {
                                Log.d("msg-test", "Datos actualizados exitosamente");
                            })
                            .addOnFailureListener(e -> e.printStackTrace());

                    Intent intent3 = new Intent(MainActivity_1_Users_NewUser.this, MainActivity_1_Users_UserDetais.class);
                    intent3.putExtra("ListElement", listElement);
                    startActivity(intent3);
                }
                return true;
            } else{
                return false;
            }
        });
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_new, menu);
        return true;
    }

    private boolean areFieldsEmpty() {
        return editFirstName.getText().toString().isEmpty() ||
                editLastName.getText().toString().isEmpty() ||
                editDNI.getText().toString().isEmpty() ||
                editMail.getText().toString().isEmpty() ||
                editAddress.getText().toString().isEmpty() ||
                editPhone.getText().toString().isEmpty();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
        // Implementa la lógica para mostrar la imagen de perfil
    }

    private void updateExistingUser() {
        // Implementa la lógica para actualizar los datos del usuario existente
    }

    private void createNewUser() {
        // Implementa la lógica para crear un nuevo usuario
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
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, 101);
        }
    }
    public void notificarImportanceDefault(String fullname, String fechaCreacion){

        //Crear notificación
        //Agregar información a la notificación que luego sea enviada a la actividad que se abre
        Intent intent = new Intent(this, MainActivity_0_NavigationAdmin.class);
        intent.putExtra("pid",4616);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        //
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, canal1)
                .setSmallIcon(R.drawable.ic_addperson_filled_black)
                .setContentTitle("Nuevo registrado")
                .setContentText("Usuario : " + fullname + "\nFecha: "+ fechaCreacion)
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