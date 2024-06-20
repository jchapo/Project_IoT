package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Admin.MainActivity_2_Sites_NewSite;
import com.example.myapplication.Admin.items.ListAdapterSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListAdapterSiteSupervisor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SitiosFragment extends Fragment {

    private ArrayList<ListElementSite> activeSites = new ArrayList<>();
    private ArrayList<ListElementSite> inactiveSites = new ArrayList<>();
    private ListAdapterSite listAdapterSiteSupervisor;

    private RecyclerView recyclerViewSites;
    private NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.supervisor_fragment_sitios, container, false);
        setHasOptionsMenu(true);
        navigationActivityViewModel = new ViewModelProvider(requireActivity()) .get(NavigationActivityViewModel. class);
        initializeViews(view);
        observeViewModel();
        return view;
    }

    private void observeViewModel() {

        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getActiveSites().observe(getViewLifecycleOwner(), sitiosActivos -> {
                activeSites.clear();
                listAdapterSiteSupervisor.notifyDataSetChanged();
                activeSites.addAll(sitiosActivos);
            });
            navigationActivityViewModel.getInactiveSites().observe(getViewLifecycleOwner(), sitiosInactivos -> {
                inactiveSites.clear();
                listAdapterSiteSupervisor.notifyDataSetChanged();
                inactiveSites.addAll(sitiosInactivos);
            });
        }
    }

    public void initializeViews(View view) {
        listAdapterSiteSupervisor = new ListAdapterSite(activeSites, getContext(), this :: moveToDescription);
        recyclerViewSites = view.findViewById(R.id.listElements);
        recyclerViewSites.setHasFixedSize(true);
        recyclerViewSites.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSites.setAdapter(listAdapterSiteSupervisor);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterSiteSupervisor.setItems(activeSites);
                        listAdapterSiteSupervisor.notifyDataSetChanged();
                        break;
                    case 1:
                        listAdapterSiteSupervisor.setItems(inactiveSites);
                        listAdapterSiteSupervisor.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    public void moveToDescription(ListElementSite item){
        Intent intent = new Intent(getContext(), MasDetallesSitioSupervisor.class);
        intent.putExtra("ListElementSite", item);
        startActivity(intent);
    }
}