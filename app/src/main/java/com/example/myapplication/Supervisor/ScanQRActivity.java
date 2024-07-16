package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;


public class ScanQRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qractivity);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan a QR code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            } else {
                handleScannedData(result.getContents());
            }
        }
    }

    private void handleScannedData(String scannedData) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("equipos").document(scannedData).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                ListElementEquiposNuevo equipo = documentSnapshot.toObject(ListElementEquiposNuevo.class);
                Intent intent = new Intent(ScanQRActivity.this, MasDetallesEquipos_2.class);
                intent.putExtra("ListElementDevices", equipo);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Equipo no encontrado", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error al buscar el equipo", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
