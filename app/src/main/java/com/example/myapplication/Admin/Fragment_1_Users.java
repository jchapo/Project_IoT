package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListAdapterUser;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AdminFragmentUsersBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Fragment_1_Users extends Fragment {

    private ArrayList<ListElementUser> activeUsers = new ArrayList<>();
    private ArrayList<ListElementUser> inactiveUsers = new ArrayList<>();
    private ArrayList<ListElementUser> allUsers = new ArrayList<>();
    private ListAdapterUser listAdapterUsers;
    private RecyclerView recyclerViewUsers;
    AdminFragmentUsersBinding binding;
    private NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AdminFragmentUsersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true); // Para habilitar opciones de menú en el fragmento
        binding.topAppBarUserFragment.setTitle("Usuarios");
        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavigationActivityViewModel.class);
        initializeViews(view);
        observeViewModel();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_admin_users, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listAdapterUsers.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapterUsers.filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void observeViewModel() {
        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getActiveUsers().observe(getViewLifecycleOwner(), usuarioActivos -> {
                activeUsers.clear();
                activeUsers.addAll(usuarioActivos);
                updateUsersList();
            });
            navigationActivityViewModel.getInactiveUsers().observe(getViewLifecycleOwner(), usuarioInactivos -> {
                inactiveUsers.clear();
                inactiveUsers.addAll(usuarioInactivos);
                updateUsersList();
            });
        }
    }

    private void updateUsersList() {
        allUsers.clear();
        allUsers.addAll(activeUsers);
        allUsers.addAll(inactiveUsers);
        listAdapterUsers.setItems(allUsers);
        listAdapterUsers.notifyDataSetChanged();
    }

    private void initializeViews(View view) {
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
