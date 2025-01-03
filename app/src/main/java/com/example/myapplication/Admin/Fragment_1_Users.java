package com.example.myapplication.Admin;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algolia.search.saas.android.BuildConfig;
import com.example.myapplication.Admin.items.ListAdapterUser;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AdminFragmentUsersBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_1_Users extends Fragment {
    private Client client;
    private Index userIndex;
    Toolbar toolbar;
    private SearchView searchView;
    private MenuItem menuItem;
    private ArrayList<ListElementUser> activeUsers = new ArrayList<>();
    private ArrayList<ListElementUser> inactiveUsers = new ArrayList<>();
    private ArrayList<ListElementUser> allUsers = new ArrayList<>();
    private ListAdapterUser listAdapterUsers;
    private RecyclerView recyclerViewUsers;
    AdminFragmentUsersBinding binding;
    private NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        client = new Client("3125VRFCOL", "017ff2e5dc660a066578d999fff272fb");
        userIndex = client.getIndex("users_index");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AdminFragmentUsersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        binding.topAppBarUserFragment.setTitle("Usuarios");
        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavigationActivityViewModel.class);
        initializeViews(view);
        observeViewModel();
        listAdapterUsers.setItems(activeUsers);
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
                Log.d("Fragment_1_Users", "onQueryTextSubmit: " + query);
                searchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Fragment_1_Users", "onQueryTextChange: " + newText);
                searchUsers(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchUsers(String query) {
        userIndex.searchAsync(new com.algolia.search.saas.Query(query), (content, error) -> {
            if (error == null) {
                try {
                    JSONArray hits = content.getJSONArray("hits");
                    ArrayList<ListElementUser> searchResults = new ArrayList<>();
                    for (int i = 0; i < hits.length(); i++) {
                        JSONObject hit = hits.getJSONObject(i);
                        ListElementUser listElementUser = new ListElementUser();
                        listElementUser.setUser(hit.optString("user", ""));
                        listElementUser.setName(hit.optString("name", ""));
                        listElementUser.setLastname(hit.optString("lastname", ""));
                        listElementUser.setDni(hit.optString("dni", ""));
                        listElementUser.setMail(hit.optString("mail", ""));
                        listElementUser.setAddress(hit.optString("address", ""));
                        listElementUser.setPhone(hit.optString("phone", ""));
                        listElementUser.setStatus(hit.optString("status", "Activo")); // Default value as "Activo"
                        listElementUser.setFechaCreacion(hit.optString("fechaCreacion", ""));
                        listElementUser.setSitiosAsignados(hit.optString("sitiosAsignados", ""));
                        listElementUser.setImageUrl(hit.optString("imageUrl", ""));
                        searchResults.add(listElementUser);
                    }
                    // Actualiza el adaptador con los resultados de la búsqueda
                    Log.e("AlgoliaSearch", "Se actualizo la lista");
                    listAdapterUsers.setItems(searchResults);
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
            navigationActivityViewModel.getActiveUsers().observe(getViewLifecycleOwner(), usuarioActivos -> {
                Log.d("Fragment_1_Users", "Active users updated: " + usuarioActivos.size());
                activeUsers.clear();
                activeUsers.addAll(usuarioActivos);
                listAdapterUsers.notifyDataSetChanged();
                //updateUsersList();
            });
            navigationActivityViewModel.getInactiveUsers().observe(getViewLifecycleOwner(), usuarioInactivos -> {
                Log.d("Fragment_1_Users", "Inactive users updated: " + usuarioInactivos.size());
                inactiveUsers.clear();
                inactiveUsers.addAll(usuarioInactivos);
                listAdapterUsers.notifyDataSetChanged();
                //updateUsersList();
            });
        }
    }
    private void updateUsersList() {
        Log.d("Fragment_1_Users", "updateUsersList called");
        allUsers.clear();
        allUsers.addAll(activeUsers);
        allUsers.addAll(inactiveUsers);
        Log.d("Fragment_1_Users", "Total users: " + allUsers.size());
        //listAdapterUsers.setItems(allUsers); // Actualiza la lista en el adaptador
    }
    private void initializeViews(View view) {
        toolbar = binding.topAppBarUserFragment;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        FloatingActionButton agregarUsuarioButton = view.findViewById(R.id.agregarUsuariofloatingActionButton);
        agregarUsuarioButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity_1_Users_NewUser.class);
            startActivity(intent);
        });
        listAdapterUsers = new ListAdapterUser(allUsers, getContext(), this::moveToDescription);
        recyclerViewUsers = binding.listElementsUsers;
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsers.setAdapter(listAdapterUsers);

        TabLayout tabLayout = binding.tabLayoutUsers;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterUsers.setItems(activeUsers);
                        listAdapterUsers.notifyDataSetChanged();
                        break;
                    case 1:
                        listAdapterUsers.setItems(inactiveUsers);
                        listAdapterUsers.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
    public void moveToDescription(ListElementUser item) {
        Intent intent = new Intent(getContext(), MainActivity_1_Users_UserDetails.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
}