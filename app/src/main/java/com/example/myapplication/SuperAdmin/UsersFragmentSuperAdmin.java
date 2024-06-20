package com.example.myapplication.SuperAdmin;

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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.MainActivity_1_Users_NewUser;
import com.example.myapplication.SuperAdmin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.list.ListAdapterSuperAdminUser;
import com.example.myapplication.SuperAdmin.list.ListElementSuperAdminUser;
import com.example.myapplication.databinding.AdminFragmentUsersBinding;
import com.example.myapplication.databinding.SuperadminFragmentUsersBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersFragmentSuperAdmin extends Fragment {
    private ArrayList<ListElementSuperAdminUser> adminUser = new ArrayList<>();
    private ArrayList<ListElementSuperAdminUser> supervisorUser = new ArrayList<>();
    private ListAdapterSuperAdminUser listAdapterSuperAdminUser;
    private RecyclerView recyclerViewUsers;
    FirebaseFirestore db;
    SuperadminFragmentUsersBinding binding;
    private NavigationActivityViewModel navigationActivityViewModel;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SuperadminFragmentUsersBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.topAppBarUserFragment.setTitle("Usuarios");
        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavigationActivityViewModel.class);
        initializeViews(view);
        observeViewModel();



        TabLayout tabLayout = view.findViewById(R.id.tabLayoutSuperAdmin);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterSuperAdminUser.setItems(adminUser);
                        listAdapterSuperAdminUser.notifyDataSetChanged();
                        break;
                    case 1:
                        listAdapterSuperAdminUser.setItems(supervisorUser);
                        listAdapterSuperAdminUser.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_superadmin_users, menu);

        MenuItem searchItem = menu.findItem(R.id.searchUser);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SearchView", "Query text changed to: " + newText); // Añadir este log para depuración
                System.out.println("hola: " + newText);
                listAdapterSuperAdminUser.filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void observeViewModel() {
        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getAdminUser().observe(getViewLifecycleOwner(), adminUserList -> {
                adminUser.clear();
                listAdapterSuperAdminUser.notifyDataSetChanged();
                adminUser.addAll(adminUserList);
            });
            navigationActivityViewModel.getSupervisorUser().observe(getViewLifecycleOwner(), supervisorUserList -> {
                supervisorUser.clear();
                listAdapterSuperAdminUser.notifyDataSetChanged();
                supervisorUser.addAll(supervisorUserList);
            });
        }
    }

    public void initializeViews(View view) {
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

        // Select the active users tab initially
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutSuperAdmin);
        tabLayout.post(() -> {
            TabLayout.Tab tab = tabLayout.getTabAt(0); // Assuming the first tab is for active users
            if (tab != null) {
                tab.select();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterSuperAdminUser.setItems(adminUser);
                        listAdapterSuperAdminUser.notifyDataSetChanged();
                        break;
                    case 1:
                        listAdapterSuperAdminUser.setItems(supervisorUser);
                        listAdapterSuperAdminUser.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }

    public void moveToDescription(ListElementSuperAdminUser item){
        Intent intent = new Intent(getContext(), UserDetais.class);
        intent.putExtra("ListElement", item);
        startActivity(intent);
    }
}
