package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListAdapterUser;
import com.example.myapplication.Admin.items.ListElementUser;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Fragment_1_Users extends Fragment {

    private ArrayList<ListElementUser> activeUsers = new ArrayList<>();
    private ArrayList<ListElementUser> inactiveUsers = new ArrayList<>();

    private ListAdapterUser listAdapterUsers;
    private RecyclerView recyclerViewUsers;
    private NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment_users, container, false);
        setHasOptionsMenu(true);
        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavigationActivityViewModel.class);
        initializeViews(view);
        observeViewModel();
        return view;
    }

    private void observeViewModel() {
        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getActiveUsers().observe(getViewLifecycleOwner(), usuarioActivos -> {
                activeUsers.clear();
                listAdapterUsers.notifyDataSetChanged();
                activeUsers.addAll(usuarioActivos);

            });
            navigationActivityViewModel.getInactiveUsers().observe(getViewLifecycleOwner(), usuarioInactivos -> {
                inactiveUsers.clear();
                listAdapterUsers.notifyDataSetChanged();
                inactiveUsers.addAll(usuarioInactivos);

            });
        }
    }

    private void initializeViews(View view) {
        listAdapterUsers = new ListAdapterUser(activeUsers, getContext(), this::moveToDescription);
        recyclerViewUsers = view.findViewById(R.id.listElementsUsers);
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewUsers.setAdapter(listAdapterUsers);
        FloatingActionButton agregarUsuarioButton = view.findViewById(R.id.agregarUsuariofloatingActionButton);
        agregarUsuarioButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity_1_Users_NewUser.class);
            startActivity(intent);
        });
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutUsers);
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

