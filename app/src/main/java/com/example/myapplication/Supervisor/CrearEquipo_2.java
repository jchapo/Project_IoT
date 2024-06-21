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
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.Admin.MainActivity_2_Sites_NewSite;
import com.example.myapplication.Admin.MainActivity_2_Sites_SiteDetails;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
                        try {
                            // Usar Glide para mostrar la imagen seleccionada en el ImageView
                            Glide.with(this)
                                    .load(imageUri)
                                    .into(imageView);
                            isImageAdded = true; // Actualizar la variable cuando se selecciona una imagen
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

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

            // Generar el SKU
            String sku = generateSKU(marca, modelo, idSitio);

            LocalDate fechaActual = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fecha_ingreso = fechaActual.format(formatter);

            String imagenEquipo = "";
            String nameEquipo = marca + " " + modelo;

            ListElementEquiposNuevo listElement = new ListElementEquiposNuevo(nameEquipo, marca, modelo, tipoEquipo, descripcionEquipo, status, idSitio, sku, fecha_ingreso, imagenEquipo, "");

            // Subir la imagen y guardar el equipo
            uploadImageAndSaveEquipment(listElement, false);
        }
    }
    public static String generateSKU(String marca, String modelo, String idSitio) {
        try {
            // Combina las cadenas
            String combined = marca + modelo + idSitio;

            // Crea una instancia del algoritmo de hash SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Calcula el hash en bytes
            byte[] messageDigest = md.digest(combined.getBytes());

            // Convierte el hash en una representación hexadecimal
            BigInteger no = new BigInteger(1, messageDigest);
            String hashText = no.toString(16);

            // Asegura que el hash tenga al menos 32 caracteres (si es necesario, agrega ceros al principio)
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }

            // Obtén los últimos 12 caracteres numéricos del hash
            String last12Digits = hashText.replaceAll("[^0-9]", ""); // Elimina caracteres no numéricos
            if (last12Digits.length() < 12) {
                last12Digits = String.format("%012d", new BigInteger(last12Digits)); // Asegura que tenga 12 dígitos
            } else {
                last12Digits = last12Digits.substring(last12Digits.length() - 12);
            }

            return last12Digits;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
    private Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void uploadImageAndSaveEquipment(ListElementEquiposNuevo listElement, boolean isEditing) {
        if (imageUri != null) {
            try {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Bitmap resizedBitmap = getResizedBitmap(bitmap, 600); // Redimensionar a 600x600 píxeles
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos); // Comprimir con calidad del 80%
                byte[] imageBytes = baos.toByteArray();

                String storagePath = "equipmentimages/" + listElement.getSku() + ".jpg";
                ArrayList<String> selectedImages = new ArrayList<>();
                if (selectedImages.isEmpty()) {
                    selectedImages.add(storagePath);
                } else {
                    selectedImages.add(0, storagePath);
                }
                Set<String> updatedImages = new HashSet<>(selectedImages);
                String updatedSitesJson = new JSONArray(updatedImages).toString();

                StorageReference ref = storageReference.child(storagePath);
                ref.putBytes(imageBytes)
                        .addOnSuccessListener(taskSnapshot -> {
                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                listElement.setImagenEquipo(updatedSitesJson); // Guardar el path en Firestore
                                generateAndUploadQRCode(listElement, isEditing);
                            });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(CrearEquipo_2.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            generateAndUploadQRCode(listElement, isEditing);
        }
    }

    private void generateAndUploadQRCode(ListElementEquiposNuevo listElement, boolean isEditing) {
        Bitmap qrCodeBitmap = generateQRCode(listElement.getSku());
        if (qrCodeBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            qrCodeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            String qrCodePath = "qrcodes/" + listElement.getSku() + ".jpg";
            StorageReference qrCodeRef = storageReference.child(qrCodePath);

            qrCodeRef.putBytes(data)
                    .addOnSuccessListener(taskSnapshot -> {
                        listElement.setImagenQr(qrCodePath); // Guardar solo el path del QR code en Firestore
                        saveEquipmentToFirestore(listElement, isEditing);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(CrearEquipo_2.this, "Failed to upload QR code", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        } else {
            saveEquipmentToFirestore(listElement, isEditing);
        }
    }


    private Bitmap generateQRCode(String sku) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(sku, BarcodeFormat.QR_CODE, 200, 200);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveEquipmentToFirestore(ListElementEquiposNuevo listElement, boolean isEditing) {
        if (listElement == null) {
            return;
        }
        if (listElement.getSku() == null) {
            return;
        }

        db.collection("equipos")
                .document(listElement.getSku())
                .set(listElement, SetOptions.merge())
                .addOnSuccessListener(unused -> {
                    Log.d("msg-test", isEditing ? "Datos actualizados exitosamente" : "Data guardada exitosamente");
                    // Mover la apertura de la nueva actividad aquí, dentro de onSuccessListener
                    openEquipoDetailsActivity(listElement);
                })
                .addOnFailureListener(e -> {
                    Log.e("msg-test", "Error al guardar en Firestore", e);
                    e.printStackTrace();
                });
    }
    private void openEquipoDetailsActivity(ListElementEquiposNuevo listElement) {
        Intent intent = new Intent(CrearEquipo_2.this, MasDetallesEquipos_2.class);
        intent.putExtra("ListElementSite", listElement);
        startActivity(intent);
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

            ListElementEquiposNuevo listElement = new ListElementEquiposNuevo(nameEquipo, marca, modelo, tipoEquipo, descripcionEquipo, status, idSitio, sku, fecha_ingreso, imagenEquipo,"");
            uploadImageAndSaveEquipment(listElement, true);
        }
    }

    private boolean areFieldsEmpty() {
        return editBrand.getText().toString().isEmpty() ||
                editModelo.getText().toString().isEmpty() ||
                editDescription.getText().toString().isEmpty();
    }

    private void fillFields(ListElementEquiposNuevo element) {
        editBrand.setText(element.getMarca());
        editModelo.setText(element.getModelo());
        editDescription.setText(element.getDescripcionEquipo());
        editSKU.setText(element.getSku());

        selectTypeDevice.setText(element.getTipoEquipo(), false);
        selectSite.setText(element.getIdSitio(), false);


        if (element.getImagenEquipo() != null && !element.getImagenEquipo().isEmpty()) {
            // Usar Glide para cargar la imagen desde Firebase Storage utilizando la ruta de acceso
            StorageReference imageRef = storageReference.child(element.getImagenEquipo());
            Glide.with(this)
                    .load(imageRef)
                    .skipMemoryCache(true) // Desactivar la caché de memoria
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Desactivar la caché en disco
                    .into(imageView);
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
        Intent intent = new Intent(this, CrearEquipo_2.class);
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
