package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.dataHolder.DataHolder;
import com.example.myapplication.Admin.items.ListAdapterAddSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_2_Sites_AddSite extends AppCompatActivity {
    private ListAdapterAddSite listAdapter;
    private RecyclerView recyclerView;
    private List<ListElementSite> elements;
    private static final int REQUEST_CODE_ADD_SITE = 1; // Define el código de solicitud


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_add_site_admin);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarAddSiteUser);
        topAppBar.inflateMenu(R.menu.top_app_bar_select);

        init();

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.chooseSite) {
                ArrayList<String> selectedSitesNames = getSelectedSitesNames();
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("selectedSites", selectedSitesNames);
                setResult(RESULT_OK, resultIntent);
                Toast.makeText(this, "Sitio asignado", Toast.LENGTH_SHORT).show();
                finish();
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

    private void init() {
        elements = new ArrayList<>();

        recyclerView = findViewById(R.id.listElementsSites);
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
