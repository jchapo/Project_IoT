package com.example.myapplication.Admin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

import com.algolia.search.saas.Client;
import com.example.myapplication.Admin.items.ListAdapterSite;
import com.example.myapplication.Admin.items.ListAdapterSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AdminFragmentSitesBinding;
import com.example.myapplication.databinding.AdminFragmentSitesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_2_Sites extends Fragment {
    private Client client;
    private Index siteIndex;
    Toolbar toolbar;
    private SearchView searchView;
    private MenuItem menuItem;

    private List<ListElementSite> activeSites = new ArrayList<>();
    private List<ListElementSite> inactiveSites = new ArrayList<>();
    private List<ListElementSite> allSites = new ArrayList<>();
    private ListAdapterSite listAdapterSites;
    private RecyclerView recyclerViewSites;
    AdminFragmentSitesBinding binding;
    NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        client = new Client("3125VRFCOL", "017ff2e5dc660a066578d999fff272fb");
        siteIndex = client.getIndex("site_index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = AdminFragmentSitesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        binding.topAppBarSiteFragment.setTitle("Sitios");
        navigationActivityViewModel = new ViewModelProvider(requireActivity()) .get(NavigationActivityViewModel. class);
        initializeViews(view);
        observeViewModel();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_admin_users, menu);
        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Fragment_2_Sites", "onQueryTextSubmit: " + query);
                searchSites(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Fragment_2_Sites", "onQueryTextChange: " + newText);
                searchSites(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchSites(String query) {
        siteIndex.searchAsync(new com.algolia.search.saas.Query(query), (content, error) -> {
            if (error == null) {
                try {
                    JSONArray hits = content.getJSONArray("hits");
                    ArrayList<ListElementSite> searchResults = new ArrayList<>();
                    for (int i = 0; i < hits.length(); i++) {
                        JSONObject hit = hits.getJSONObject(i);
                        ListElementSite listElementSite = new ListElementSite();
                        listElementSite.setDepartment(hit.optString("department", ""));
                        listElementSite.setProvince(hit.optString("province", ""));
                        listElementSite.setDistrict(hit.optString("district", ""));
                        listElementSite.setZonetype(hit.optString("zonetype", ""));
                        listElementSite.setSitetype(hit.optString("sitetype", ""));
                        listElementSite.setLocation(hit.optString("location", ""));
                        listElementSite.setLatitud(hit.optDouble("latitud", 0.0));
                        listElementSite.setLongitud(hit.optDouble("longitud", 0.0));
                        listElementSite.setCoordenadas(hit.optString("coordenadas", ""));
                        listElementSite.setName(hit.optString("name", ""));
                        listElementSite.setAddress(hit.optString("address", ""));
                        listElementSite.setStatus(hit.optString("status", ""));
                        listElementSite.setUbigeo(hit.optString("ubigeo", ""));
                        listElementSite.setFechaCreacion(hit.optString("fechaCreacion", ""));
                        listElementSite.setImageUrl(hit.optString("imageUrl", ""));
                        listElementSite.setSuperAsignados(hit.optString("superAsignados", ""));

                        searchResults.add(listElementSite);
                    }
                    // Actualiza el adaptador con los resultados de la búsqueda
                    listAdapterSites.setItems(searchResults);
                } catch (JSONException e) {
                    Log.e("AlgoliaSearch", "Error parsing search results", e);
                }
            } else {
                Log.e("AlgoliaSearch", "Search error: ", error);
            }
        });
    }

    private void observeViewModel() {
        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getActiveSites().observe(getViewLifecycleOwner(), sitiosActivos -> {
                activeSites.clear();
                listAdapterSites.notifyDataSetChanged();
                activeSites.addAll(sitiosActivos);
                updateSitesList();
            });
            navigationActivityViewModel.getInactiveSites().observe(getViewLifecycleOwner(), sitiosInactivos -> {
                inactiveSites.clear();
                listAdapterSites.notifyDataSetChanged();
                inactiveSites.addAll(sitiosInactivos);
                updateSitesList();
            });
        }
    }
    private void updateSitesList() {
        Log.d("Fragment_1_Users", "updateUsersList called");
        allSites.clear();
        allSites.addAll(activeSites);
        allSites.addAll(inactiveSites);
        Log.d("Fragment_1_Users", "Total users: " + allSites.size());
        listAdapterSites.setItems(allSites); // Actualiza la lista en el adaptador
    }
    public void initializeViews(View view) {
        toolbar = binding.topAppBarSiteFragment;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        FloatingActionButton agregarSitioButton = view.findViewById(R.id.agregarSitiofloatingActionButton);
        agregarSitioButton.setOnClickListener( View -> {
            Intent intent = new Intent(getActivity(), MainActivity_2_Sites_NewSite.class);
            startActivity(intent);
        });
        listAdapterSites = new ListAdapterSite(activeSites, getContext(), this :: moveToDescription);
        recyclerViewSites = view.findViewById(R.id.listElementsSites);
        recyclerViewSites.setHasFixedSize(true);
        recyclerViewSites.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSites.setAdapter(listAdapterSites);

        TabLayout tabLayout = binding.tabLayoutSites;
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


