package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.dataHolder.DataHolder;
import com.example.myapplication.Admin.items.ListAdapterAddSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.items.ListAdapterAddSuper;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity_2_Sites_AddSupervisor extends AppCompatActivity {
    private ListAdapterAddSuper listAdapter;
    private RecyclerView recyclerView;
    private String siteName;
    List<ListElementUser> elements;
    private CircularProgressIndicator progressIndicator;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_add_supervisor);
        siteName = getIntent().getStringExtra("siteName");

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarAddSuperSite);
        topAppBar.inflateMenu(R.menu.top_app_bar_select);

        db = FirebaseFirestore.getInstance();
        progressIndicator = findViewById(R.id.progressIndicatorSuper);

        init();

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.chooseElement) {
                SelectedSupervisors selectedSupervisors = getSelectedSupervisors();
                saveSuperToFirebase(selectedSupervisors);
            } else {
                return false;
            }
            return false;
        });
        topAppBar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private class SelectedSupervisors {
        ArrayList<String> names;
        ArrayList<String> dnis;

        SelectedSupervisors(ArrayList<String> names, ArrayList<String> dnis) {
            this.names = names;
            this.dnis = dnis;
        }
    }

    private SelectedSupervisors getSelectedSupervisors() {
        ArrayList<String> selectedNames = new ArrayList<>();
        ArrayList<String> selectedDNIs = new ArrayList<>();
        for (ListElementUser supervisor : listAdapter.getSelectedItems()) {
            selectedNames.add(supervisor.getName() + " " + supervisor.getLastname());
            selectedDNIs.add(supervisor.getDni());
        }
        return new SelectedSupervisors(selectedNames, selectedDNIs);
    }

    private void saveSuperToFirebase(SelectedSupervisors selectedSupervisors) {
        progressIndicator.setVisibility(View.VISIBLE);
        DocumentReference siteRef = db.collection("sitios").document(siteName);

        siteRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String existingSuperJson = documentSnapshot.getString("superAsignados");

                Set<String> updatedSuper = new HashSet<>(selectedSupervisors.names);

                if (existingSuperJson != null && !existingSuperJson.isEmpty()) {
                    try {
                        JSONArray existingSuperArray = new JSONArray(existingSuperJson);
                        for (int i = 0; i < existingSuperArray.length(); i++) {
                            updatedSuper.add(existingSuperArray.getString(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle invalid JSON
                    }
                }

                String updatedSuperJson = new JSONArray(updatedSuper).toString();

                siteRef.update("superAsignados", updatedSuperJson)
                        .addOnSuccessListener(aVoid -> {
                            // Update each user document with the site name
                            for (String supervisorDNI : selectedSupervisors.dnis) {
                                updateUserWithSite(supervisorDNI, siteName);
                            }
                            sendUserBackToUserDetails();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity_2_Sites_AddSupervisor.this, "Error guardando supervisores", Toast.LENGTH_SHORT).show();
                            Log.w("MainActivity_2_Sites_AddSupervisor", "Error updating document", e);
                            progressIndicator.setVisibility(View.GONE);
                        });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(MainActivity_2_Sites_AddSupervisor.this, "Error obteniendo sitio", Toast.LENGTH_SHORT).show();
            Log.w("MainActivity_2_Sites_AddSupervisor", "Error getting document", e);
            progressIndicator.setVisibility(View.GONE);
        });
    }

    private void updateUserWithSite(String userDNI, String siteName) {
        DocumentReference userRef = db.collection("usuarios").document(userDNI);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String existingSitesJson = documentSnapshot.getString("sitiosAsignados");

                Set<String> updatedSites = new HashSet<>();
                if (existingSitesJson != null && !existingSitesJson.isEmpty()) {
                    try {
                        JSONArray existingSitesArray = new JSONArray(existingSitesJson);
                        for (int i = 0; i < existingSitesArray.length(); i++) {
                            updatedSites.add(existingSitesArray.getString(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle invalid JSON
                    }
                }

                updatedSites.add(siteName);

                String updatedSitesJson = new JSONArray(updatedSites).toString();

                userRef.update("sitiosAsignados", updatedSitesJson)
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity_2_Sites_AddSupervisor.this, "Error actualizando usuario", Toast.LENGTH_SHORT).show();
                            Log.w("MainActivity_2_Sites_AddSupervisor", "Error updating user document", e);
                        });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(MainActivity_2_Sites_AddSupervisor.this, "Error obteniendo usuario", Toast.LENGTH_SHORT).show();
            Log.w("MainActivity_2_Sites_AddSupervisor", "Error getting user document", e);
        });
    }

    private void sendUserBackToUserDetails() {
        db.collection("sitios").document(siteName).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        ListElementSite site = documentSnapshot.toObject(ListElementSite.class);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("updatedSite", site);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity_2_Sites_AddSupervisor.this, "Sitio no encontrado", Toast.LENGTH_SHORT).show();
                    }
                    progressIndicator.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity_2_Sites_AddSupervisor.this, "Error obteniendo sitio", Toast.LENGTH_SHORT).show();
                    Log.w("MainActivity_2_Sites_AddSupervisor", "Error getting document", e);
                    progressIndicator.setVisibility(View.GONE);
                });
    }

    private void init() {
        elements = new ArrayList<>();

        recyclerView = findViewById(R.id.listElementsUsersAddSuper);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ListAdapterAddSuper(this, elements);
        recyclerView.setAdapter(listAdapter);

        ArrayList<ListElementUser> activeSuperHolder = DataHolder.getInstance().getActiveUsers();
        if (activeSuperHolder != null) {
            elements.clear();
            elements.addAll(activeSuperHolder);
            listAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(MainActivity_2_Sites_AddSupervisor.this, "No active super found", Toast.LENGTH_SHORT).show();
        }
    }
}
