package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.Admin.MainActivity_2_Sites_SiteDetails;
import com.example.myapplication.Admin.MainActivity_2_Sites_SiteImages;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListAdapterDevices;
import com.example.myapplication.Supervisor.objetos.ListElementDevices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MasDetallesSitioSupervisor extends AppCompatActivity {
    private static final int REQUEST_CODE_UPDATE_IMAGES = 2;
    TextView nameTextViewSite;
    TextView addressDescriptionTextView;
    TextView tipoZonatextView;
    TextView tipoSitiotextView;
    ImageView profileImageView;

    TextView latitud;
    TextView longitud;
    TextView ubigeo;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    ListElementSite element;
    TextView departmentDescriptionTextView, provinceDescriptionTextView, districtDescriptionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supervisor_masdetalles_sitio);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        element = (ListElementSite) getIntent().getSerializableExtra("ListElementSite");
        departmentDescriptionTextView = findViewById(R.id.departmentTextViewSiteSuper);
        nameTextViewSite = findViewById(R.id.nameTextViewSiteSiteProfile);
        provinceDescriptionTextView = findViewById(R.id.provinceTextViewSiteSuper);
        districtDescriptionTextView = findViewById(R.id.districtTextViewSiteSuper);
        tipoZonatextView = findViewById(R.id.textViewTipoZona);
        tipoSitiotextView = findViewById(R.id.textViewTipoSitio1);
        profileImageView = findViewById(R.id.imageViewProfileSuper2);
        latitud = findViewById(R.id.textViewLatitud);
        longitud = findViewById(R.id.textViewLongitud);
        ubigeo = findViewById(R.id.textViewUbigeo);


        nameTextViewSite.setText(element.getName());
        departmentDescriptionTextView.setText(element.getDepartment());
        provinceDescriptionTextView.setText(element.getProvince());
        districtDescriptionTextView.setText(element.getDistrict());
        tipoZonatextView.setText(element.getZonetype());
        tipoSitiotextView.setText(element.getSitetype());
        latitud.setText(element.getLatitud().toString());
        longitud.setText(element.getLongitud().toString());
        ubigeo.setText(element.getUbigeo());

        String assignedImageJson = element.getImageUrl();
        ArrayList<String> assignedImage = new ArrayList<>();
        String imagenUrl = "";

        if (assignedImageJson != null && !assignedImageJson.isEmpty()) {
            assignedImage = new Gson().fromJson(assignedImageJson, new TypeToken<ArrayList<String>>() {}.getType());
            if (assignedImage != null && !assignedImage.isEmpty()) {
                imagenUrl = assignedImage.get(0);
                Log.d("msg2", imagenUrl);
            }
        }

        if (imagenUrl != null && !imagenUrl.isEmpty()) {
            StorageReference imageRef = storageReference.child(imagenUrl);

            Log.d("UserDetails", "StorageReference path: " + imageRef.getPath());
            ImageView imageView = findViewById(R.id.imageViewProfileSuper2);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setLayoutParams(layoutParams);

            Glide.with(this /* context */)
                    .load(imageRef)
                    .skipMemoryCache(true) // Desactivar la caché de memoria
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Desactivar la caché en disco
                    .into(imageView);

            Log.d("UserDetails", "Image loading initiated with Glide.");
        } else {
            Log.d("UserDetails", "Image URL is null or empty.");
        }

        Toolbar toolbar = findViewById(R.id.topAppBarSitePerfilSuper);
        toolbar.setTitle("Detalles "+element.getName().toUpperCase());
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ImageButton buttonImagesSiteAdmin = findViewById(R.id.buttonImagesSiteSuper);

        buttonImagesSiteAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesSitioSupervisor.this, ImagenesSitio.class);
            intent.putExtra("imagenesSitio", element.getImageUrl());
            intent.putExtra("siteName", element.getName());  // Pasar el nombre del sitio
            startActivityForResult(intent, REQUEST_CODE_UPDATE_IMAGES);
        });


        ImageButton buttonEquipmentSiteAdmin = findViewById(R.id.buttonEquipmentSiteSuper);
        buttonEquipmentSiteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear Intent para iniciar la actividad ImagenesSitio
                Intent intent = new Intent(MasDetallesSitioSupervisor.this, EquiposDeSitios.class);
                String sitio_escogido = "Equipos de " + element.getName();
                intent.putExtra("sitio_seleccionado", sitio_escogido);
                startActivity(intent);
            }
        });

        FloatingActionButton fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MasDetallesSitioSupervisor.this, CrearReporte.class);
            intent.putExtra("tipo_reporte", "Sitio");
            intent.putExtra("sitio_seleccionado",element.getName());
            startActivity(intent);
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_UPDATE_IMAGES:
                    String updatedImagesJson = data.getStringExtra("updatedImages");
                    element.setImageUrl(updatedImagesJson);
                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }


}