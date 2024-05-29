package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Admin.items.ListAdapterSite;
import com.example.myapplication.Admin.items.ListAdapterUser;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Fragment_2_Sites extends Fragment {

    private List<ListElementSite> activeSites = new ArrayList<>();
    private List<ListElementSite> inactiveSites = new ArrayList<>();
    private ListAdapterSite listAdapterSites;
    private RecyclerView recyclerViewSites;
    NavigationActivityViewModel navigationActivityViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_sites, container, false);
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
                listAdapterSites.notifyDataSetChanged();
                activeSites.addAll(sitiosActivos);
            });
            navigationActivityViewModel.getInactiveSites().observe(getViewLifecycleOwner(), sitiosInactivos -> {
                inactiveSites.clear();
                listAdapterSites.notifyDataSetChanged();
                inactiveSites.addAll(sitiosInactivos);
            });
        }
    }

    public void initializeViews(View view) {
        listAdapterSites = new ListAdapterSite(activeSites, getContext(), this :: moveToDescription);
        recyclerViewSites = view.findViewById(R.id.listElementsSites);
        recyclerViewSites.setHasFixedSize(true);
        recyclerViewSites.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSites.setAdapter(listAdapterSites);
        FloatingActionButton agregarSitioButton = view.findViewById(R.id.agregarSitiofloatingActionButton);
        agregarSitioButton.setOnClickListener( View -> {
            Intent intent = new Intent(getActivity(), MainActivity_2_Sites_NewSite.class);
            startActivity(intent);
        });
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutSites);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterSites.setItems(activeSites);
                        listAdapterSites.notifyDataSetChanged();
                        break;
                    case 1:
                        listAdapterSites.setItems(inactiveSites);
                        listAdapterSites.notifyDataSetChanged();
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
        Intent intent = new Intent(getContext(), MainActivity_2_Sites_SiteDetails.class);
        intent.putExtra("ListElementSite", item);
        startActivity(intent);
    }
}


