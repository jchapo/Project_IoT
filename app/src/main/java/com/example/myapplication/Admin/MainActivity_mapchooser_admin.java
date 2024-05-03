package com.example.myapplication.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity_mapchooser_admin extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_mapchooser);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Centrar el mapa en Perú
        LatLng peru = new LatLng(-9.1900, -75.0152);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(peru, 5));

        // Habilitar controles de zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Configurar el listener para el clic en el mapa
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // Limpiar marcadores anteriores
        mMap.clear();

        // Agregar marcador en la ubicación seleccionada
        mMap.addMarker(new MarkerOptions().position(latLng));

        // Mostrar cuadro de diálogo de confirmación
        showConfirmationDialog(latLng);
    }

    private void showConfirmationDialog(final LatLng latLng) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Estás seguro de seleccionar esta ubicación?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Enviar los datos de la ubicación seleccionada de vuelta a la actividad anterior
                Intent returnIntent = new Intent();
                returnIntent.putExtra("latitude", latLng.latitude);
                returnIntent.putExtra("longitude", latLng.longitude);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
