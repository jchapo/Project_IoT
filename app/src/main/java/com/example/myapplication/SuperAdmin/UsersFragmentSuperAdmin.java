package com.example.myapplication.SuperAdmin;

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

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.example.myapplication.SuperAdmin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.list.ListAdapterSuperAdminUser;
import com.example.myapplication.SuperAdmin.list.ListElementSuperAdminUser;
import com.example.myapplication.databinding.SuperadminFragmentUsersBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsersFragmentSuperAdmin extends Fragment {
    private Client client;
    private Index userIndex;
    private ArrayList<ListElementSuperAdminUser> adminUser = new ArrayList<>();
    private ArrayList<ListElementSuperAdminUser> supervisorUser = new ArrayList<>();
    private ArrayList<ListElementSuperAdminUser> allUsers = new ArrayList<>();
    private ListAdapterSuperAdminUser listAdapterSuperAdminUser;
    private RecyclerView recyclerViewUsers;
    private Toolbar toolbar;
    private SearchView searchView;
    private MenuItem menuItem;
    private TabLayout tabLayout;

    SuperadminFragmentUsersBinding binding;
    private NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        client = new Client("3125VRFCOL", "017ff2e5dc660a066578d999fff272fb");
        userIndex = client.getIndex("users_index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SuperadminFragmentUsersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.topAppBarUserFragmentSuperAdmin.setTitle("Usuarios");
        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavigationActivityViewModel.class);
        initializeViews(view);
        observeViewModel();
        selectInitialTabs();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_superadmin_users, menu);

        menuItem = menu.findItem(R.id.searchSuperAdminUser);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    resetUserList();
                } else {
                    searchUsers(newText);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            resetUserList();
            return false;
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void resetUserList() {
        if (binding.tabLayoutSuperAdmin.getSelectedTabPosition() == 0) {
            listAdapterSuperAdminUser.setItems(adminUser);
        } else if (binding.tabLayoutSuperAdmin.getSelectedTabPosition() == 1) {
            listAdapterSuperAdminUser.setItems(supervisorUser);
        }
    }


    private void searchUsers(String query) {
        userIndex.searchAsync(new com.algolia.search.saas.Query(query), (content, error) -> {
            if (error == null) {
                try {
                    JSONArray hits = content.getJSONArray("hits");
                    ArrayList<ListElementSuperAdminUser> searchResults = new ArrayList<>();
                    for (int i = 0; i < hits.length(); i++) {
                        JSONObject hit = hits.getJSONObject(i);
                        ListElementSuperAdminUser user = new ListElementSuperAdminUser();
                        user.setName(hit.getString("name"));
                        user.setLastname(hit.getString("lastname"));
                        user.setUser(hit.getString("user"));
                        user.setStatus(hit.getString("status"));
                        user.setMail(hit.getString("mail"));
                        searchResults.add(user);
                    }
                    // Actualiza el adaptador con los resultados de la búsqueda
                    listAdapterSuperAdminUser.setItems(searchResults);
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
            navigationActivityViewModel.getAdminUser().observe(getViewLifecycleOwner(), adminUserList -> {
                adminUser.clear();
                adminUser.addAll(adminUserList);
                if (binding.tabLayoutSuperAdmin.getSelectedTabPosition() == 0) {
                    updateUsersList(adminUser);
                }
            });
            navigationActivityViewModel.getSupervisorUser().observe(getViewLifecycleOwner(), supervisorUserList -> {
                supervisorUser.clear();
                supervisorUser.addAll(supervisorUserList);
                if (binding.tabLayoutSuperAdmin.getSelectedTabPosition() == 1) {
                    updateUsersList(supervisorUser);
                }
            });
        }
    }

    private void updateUsersList(List<ListElementSuperAdminUser> users) {
        listAdapterSuperAdminUser.setItems(users);
    }


    public void initializeViews(View view) {
        toolbar = binding.topAppBarUserFragmentSuperAdmin;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Usuarios");
        FloatingActionButton agregarUsuarioButton = binding.agregarAdminfloatingActionButton;
        agregarUsuarioButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewAdmin.class);
            startActivity(intent);
        });
        listAdapterSuperAdminUser = new ListAdapterSuperAdminUser(adminUser, getContext(), item -> moveToDescription(item));
        recyclerViewUsers = view.findViewById(R.id.listElementsSuperAdmin);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsers.setAdapter(listAdapterSuperAdminUser);

        tabLayout = binding.tabLayoutSuperAdmin;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterSuperAdminUser.setItems(adminUser);
                        break;
                    case 1:
                        listAdapterSuperAdminUser.setItems(supervisorUser);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }


    private void selectInitialTabs() {
        tabLayout.post(() -> {
            TabLayout.Tab tabSupervisores = tabLayout.getTabAt(1);
            if (tabSupervisores != null) {
                tabSupervisores.select();
                tabLayout.post(() -> {
                    TabLayout.Tab tabAdministradores = tabLayout.getTabAt(0);
                    if (tabAdministradores != null) {
                        tabAdministradores.select();
                    }
                });
            }
        });
    }

    public void moveToDescription(ListElementSuperAdminUser item){
        Intent intent = new Intent(getContext(), UserDetais.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
}
