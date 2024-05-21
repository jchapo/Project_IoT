package com.example.myapplication.Supervisor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.R.id;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.Calendar;

public class CrearEquipo_2 extends AppCompatActivity {
    private MaterialAutoCompleteTextView selectTypeDevice;
    ArrayAdapter<String> typeDeviceAdapter;
    String[] typeOptions = {"Type A", "Type B", "Type C"};

    private EditText editTypeDevice, editBrand, editSerialNumber, editDescription, editSKU, editRegistrationDate;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private boolean isEditing = false; // Indicador para editar o crear nuevo usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_activity_crear_equipo_2);
        selectTypeDevice = findViewById(R.id.selectTypeDevice);
        typeDeviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, typeOptions);
        selectTypeDevice.setAdapter(typeDeviceAdapter);


        // Obtener el indicador de si se está editando desde el Intent
        isEditing = getIntent().getBooleanExtra("isEditing", false);

        imageView = findViewById(R.id.imageViewDevice);
        imageView.setOnClickListener(v -> openFileChooser());

        editBrand= findViewById(R.id.editBrand);
        editSerialNumber= findViewById(R.id.editSerialNumber);
        editDescription = findViewById(id.editDescription);
        editSKU = findViewById(R.id.editSKU);
        editRegistrationDate = findViewById(R.id.editRegistrationDate);

        editRegistrationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

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
            ListElementDevices element = (ListElementDevices) intent.getSerializableExtra("ListElement");
            isEditing = true; // Indicar que se está editando un usuario existente
            fillFields(element); // Llenar campos con los datos del usuario existente
            topAppBar.setTitle("Editar Usuario"); // Cambiar título de la actividad
        } else {
            topAppBar.setTitle("Nuevo Usuario"); // Cambiar título de la actividad
        }

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.createNewTopAppBar) {
                if (areFieldsEmpty()) {
                    Toast.makeText(CrearEquipo_2.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    String typeDevice = editTypeDevice.getText().toString();
                    String brand = editBrand.getText().toString();
                    String serie = editSerialNumber.getText().toString();
                    String description = editDescription.getText().toString();
                    String sku = editSKU.getText().toString();
                    String registrationDate = editRegistrationDate.getText().toString();
                    String nameDevice = "Supervisor";
                    String status = "Activo";

                    ListElementDevices listElement = new ListElementDevices(sku, serie, brand, typeDevice, registrationDate, description, nameDevice, status);

                    Intent intent2 = new Intent(CrearEquipo_2.this, MasDetallesEquipos_2.class);
                    intent2.putExtra("ListElement", listElement);
                    startActivity(intent2);
                }
                return true;
            } else if (item.getItemId() == R.id.saveOldTopAppBar) {
                if (areFieldsEmpty()) {
                    Toast.makeText(CrearEquipo_2.this, "Debe completar todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    String typeDevice = editTypeDevice.getText().toString();
                    String brand = editBrand.getText().toString();
                    String serie = editSerialNumber.getText().toString();
                    String description = editDescription.getText().toString();
                    String sku = editSKU.getText().toString();
                    String registrationDate = editRegistrationDate.getText().toString();
                    String nameDevice = "Supervisor";
                    String status = "Activo";

                    ListElementDevices listElement = new ListElementDevices(sku, serie, brand, typeDevice, registrationDate, description, nameDevice, status);

                    Intent intent3 = new Intent(CrearEquipo_2.this, MasDetallesEquipos_2.class);
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
        return selectTypeDevice.getText().toString().isEmpty() ||
                editBrand.getText().toString().isEmpty() ||
                editSerialNumber.getText().toString().isEmpty() ||
                editDescription.getText().toString().isEmpty() ||
                editSKU.getText().toString().isEmpty() ||
                editRegistrationDate.getText().toString().isEmpty();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione Imagen"), PICK_IMAGE_REQUEST);
    }

    private void fillFields(ListElementDevices element) {
        editTypeDevice.setText(element.getName());
        editBrand.setText(element.getMarca());
        editSerialNumber.setText(element.getSerie());
        editDescription.setText(element.getDescripcion());
        editSKU.setText(element.getSku());
        editRegistrationDate.setText(element.getFechaIngreso());
    }

    private void updateExistingUser() {
        // Implementa la lógica para actualizar los datos del usuario existente
    }

    private void createNewUser() {
        // Implementa la lógica para crear un nuevo usuario
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Se establece la fecha seleccionada en el EditText
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        editRegistrationDate.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
}