package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListAdapterAddSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_2_Sites_AddSite extends AppCompatActivity {
    private NavigationActivityViewModel viewModel;
    private ListAdapterAddSite listAdapter;
    private RecyclerView recyclerView;
    private List<ListElementSite> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main_add_site_admin);

        // Utiliza el mismo ViewModel
        viewModel = new ViewModelProvider(this).get(NavigationActivityViewModel.class);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBarAddSiteUser);
        topAppBar.inflateMenu(R.menu.top_app_bar_select);

        init();

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.chooseSuper) {
                Toast.makeText(this, "Sitio asignado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                return false;
            }
            return true;
        });

        topAppBar.setNavigationOnClickListener(v -> finish());
    }

    private void init() {
        elements = new ArrayList<>();

        recyclerView = findViewById(R.id.listElementsSites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ListAdapterAddSite(this, elements);
        recyclerView.setAdapter(listAdapter);

        // Observar cambios en activeSites para actualizar automáticamente la lista
        viewModel.getActiveSites().observe(this, new Observer<ArrayList<ListElementSite>>() {
            @Override
            public void onChanged(ArrayList<ListElementSite> sites) {
                if (sites != null) {
                    elements.clear();
                    elements.addAll(sites);
                    listAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity_2_Sites_AddSite.this, "No active sites found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void moveToDescription(ListElementSite item) {
        Intent intent = new Intent(this, MainActivity_2_Sites_SiteDetails.class);
        intent.putExtra("ListElementSite", item);
        startActivity(intent);
    }
}
