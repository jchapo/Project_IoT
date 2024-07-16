package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Supervisor.CrearEquipo_2;
import com.example.myapplication.Supervisor.MasDetallesEquipos_2;
import com.example.myapplication.Supervisor.ScanQRActivity;
import com.example.myapplication.Supervisor.objetos.ListAdapterEquiposNuevo;
import com.example.myapplication.Supervisor.objetos.ListElementEquiposNuevo;
import com.example.myapplication.databinding.SupervisorFragmentEquiposBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class EquiposFragment extends Fragment {

    private ArrayList<ListElementEquiposNuevo> activeEquipments = new ArrayList<>();
    private ArrayList<ListElementEquiposNuevo> inactiveEquipments = new ArrayList<>();
    private ListAdapterEquiposNuevo listAdapterDevices;
    private RecyclerView recyclerViewSites;
    SupervisorFragmentEquiposBinding binding;
    private NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SupervisorFragmentEquiposBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.topAppBarDevices.setTitle("Equipos");
        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavigationActivityViewModel.class);
        initializeViews(view);
        observeViewModel();
        return view;
    }

    private void observeViewModel() {
        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getActiveEquipments().observe(getViewLifecycleOwner(), equiposActivos -> {
                activeEquipments.clear();
                listAdapterDevices.notifyDataSetChanged();
                activeEquipments.addAll(equiposActivos);
            });
            navigationActivityViewModel.getInactiveEquipments().observe(getViewLifecycleOwner(), equiposInactivos -> {
                inactiveEquipments.clear();
                listAdapterDevices.notifyDataSetChanged();
                inactiveEquipments.addAll(equiposInactivos);
            });
        }
    }

    public void initializeViews(View view) {
        FloatingActionButton agregarEquipoButton = view.findViewById(R.id.agregarEquipofloatingActionButton);
        agregarEquipoButton.setOnClickListener(View -> {
            Intent intent = new Intent(getActivity(), CrearEquipo_2.class);
            startActivity(intent);
        });
        listAdapterDevices = new ListAdapterEquiposNuevo(activeEquipments, getContext(), this::moveToDescriptionDevice);
        recyclerViewSites = view.findViewById(R.id.listElementsDevice);
        recyclerViewSites.setHasFixedSize(true);
        recyclerViewSites.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSites.setAdapter(listAdapterDevices);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        listAdapterDevices.setItems(activeEquipments);
                        listAdapterDevices.notifyDataSetChanged();
                        break;
                    case 1:
                        listAdapterDevices.setItems(inactiveEquipments);
                        listAdapterDevices.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        ImageButton scanQRButton = view.findViewById(R.id.btnScanQR);
        scanQRButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ScanQRActivity.class);
            startActivity(intent);
        });
    }

    public void moveToDescriptionDevice(ListElementEquiposNuevo item) {
        Intent intent = new Intent(getContext(), MasDetallesEquipos_2.class);
        intent.putExtra("ListElementDevices", item);
        startActivity(intent);
    }
}
