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

public class MainActivity_2_Sites_AddSite extends AppCompatActivity {
    private ListAdapterAddSite listAdapter;
    private RecyclerView recyclerView;
    private String idDNI;
    private String idDNIName;
    private List<ListElementSite> elements;
    private CircularProgressIndicator progressIndicator;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_add_site_admin);
        idDNI = getIntent().getStringExtra("idDNI");
        idDNIName = getIntent().getStringExtra("idDNIName");

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarAddSiteUser);
        topAppBar.inflateMenu(R.menu.top_app_bar_select);

        db = FirebaseFirestore.getInstance();
        progressIndicator = findViewById(R.id.progressIndicator);

        init();

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.chooseElement) {
                ArrayList<String> selectedSitesNames = getSelectedSitesNames();
                saveSitesToFirebase(selectedSitesNames);
            } else {
                return false;
            }
            return true;
        });

        topAppBar.setNavigationOnClickListener(v -> finish());
    }

    private ArrayList<String> getSelectedSitesNames() {
        ArrayList<String> selectedNames = new ArrayList<>();
        for (ListElementSite site : listAdapter.getSelectedItems()) {
            selectedNames.add(site.getName());
        }
        return selectedNames;
    }


    private void saveSitesToFirebase(ArrayList<String> selectedSitesNames) {
        progressIndicator.setVisibility(View.VISIBLE);
        DocumentReference userRef = db.collection("usuarios").document(idDNI);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String existingSitesJson = documentSnapshot.getString("sitiosAsignados");

                Set<String> updatedSites = new HashSet<>(selectedSitesNames);

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

                String updatedSitesJson = new JSONArray(updatedSites).toString();

                userRef.update("sitiosAsignados", updatedSitesJson)
                        .addOnSuccessListener(aVoid -> {
                            // Update each site document with the user's ID
                            for (String siteName : updatedSites) {
                                updateSiteWithUser(siteName, idDNIName);
                            }
                            sendUserBackToUserDetails();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity_2_Sites_AddSite.this, "Error guardando sitios", Toast.LENGTH_SHORT).show();
                            Log.w("MainActivity_2_Sites_AddSite", "Error updating document", e);
                            progressIndicator.setVisibility(View.GONE);
                        });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(MainActivity_2_Sites_AddSite.this, "Error obteniendo usuario", Toast.LENGTH_SHORT).show();
            Log.w("MainActivity_2_Sites_AddSite", "Error getting document", e);
            progressIndicator.setVisibility(View.GONE);
        });
    }


    private void updateSiteWithUser(String siteName, String userId) {
        DocumentReference siteRef = db.collection("sitios").document(siteName);

        siteRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String existingUsersJson = documentSnapshot.getString("superAsignados");

                Set<String> updatedUsers = new HashSet<>();
                if (existingUsersJson != null && !existingUsersJson.isEmpty()) {
                    try {
                        JSONArray existingUsersArray = new JSONArray(existingUsersJson);
                        for (int i = 0; i < existingUsersArray.length(); i++) {
                            updatedUsers.add(existingUsersArray.getString(i));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle invalid JSON
                    }
                }

                updatedUsers.add(userId);

                String updatedUsersJson = new JSONArray(updatedUsers).toString();

                siteRef.update("superAsignados", updatedUsersJson)
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity_2_Sites_AddSite.this, "Error actualizando sitio", Toast.LENGTH_SHORT).show();
                            Log.w("MainActivity_2_Sites_AddSite", "Error updating site document", e);
                        });
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(MainActivity_2_Sites_AddSite.this, "Error obteniendo sitio", Toast.LENGTH_SHORT).show();
            Log.w("MainActivity_2_Sites_AddSite", "Error getting site document", e);
        });
    }

    private void sendUserBackToUserDetails() {
        db.collection("usuarios").document(idDNI).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        ListElementUser user = documentSnapshot.toObject(ListElementUser.class);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("updatedUser", user);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity_2_Sites_AddSite.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }
                    progressIndicator.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity_2_Sites_AddSite.this, "Error obteniendo usuario", Toast.LENGTH_SHORT).show();
                    Log.w("MainActivity_2_Sites_AddSite", "Error getting document", e);
                    progressIndicator.setVisibility(View.GONE);
                });
    }

    private void init() {
        elements = new ArrayList<>();

        recyclerView = findViewById(R.id.listElementsAddSites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ListAdapterAddSite(this, elements);
        recyclerView.setAdapter(listAdapter);

        ArrayList<ListElementSite> activeSitesHolder = DataHolder.getInstance().getActiveSites();
        if (activeSitesHolder != null) {
            elements.clear();
            elements.addAll(activeSitesHolder);
            listAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(MainActivity_2_Sites_AddSite.this, "No active sites found", Toast.LENGTH_SHORT).show();
        }
    }
}
