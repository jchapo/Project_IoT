package com.example.myapplication.SuperAdmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.list.ListAdapterLog;
import com.example.myapplication.SuperAdmin.list.ListElementLog;

import com.example.myapplication.SuperAdmin.list.ListElementLog.*;
import com.example.myapplication.SuperAdmin.viewModels.NavigationActivityViewModel;
import com.example.myapplication.databinding.SuperadminFragmentLogBinding;
import com.example.myapplication.databinding.SuperadminFragmentUsersBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogFragment extends Fragment {
    private Client client;
    private Index logIndex;

    private List<ListElementLog> activeLog= new ArrayList<>();
    private List<ListElementLog> inactiveLog= new ArrayList<>();
    private ListAdapterLog listAdapterLog;
    SuperadminFragmentLogBinding binding;
    private RecyclerView recyclerViewLog;
    private Toolbar toolbar;
    private SearchView searchView;
    private MenuItem menuItem;
    private TabLayout tabLayout;
    private NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        client = new Client("3125VRFCOL", "017ff2e5dc660a066578d999fff272fb");
        logIndex = client.getIndex("logs_index");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_superadmin_log, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SuperadminFragmentLogBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.topAppBarUserFragment.setTitle("Registro de eventos");
        init(view);
        FloatingActionButton filtrarLogButton = view.findViewById(R.id.filtrarLogsfloatingActionButton);
        filtrarLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

        return view;
    }
    private void showFilterDialog() {
        FilterLogsDialogFragment dialogFragment = new FilterLogsDialogFragment(new FilterLogsDialogFragment.FilterLogsListener() {
            @Override
            public void onApplyFilters(String fromDate, String toDate, String logType, String userRol) {
                // Aquí aplicas los filtros y actualizas la lista de logs
                // Llama a un método para aplicar los filtros y actualizar la vista principal
                // filterLogs(fromDate, toDate, logType, user); por ejemplo
            }
        });
        dialogFragment.show(getParentFragmentManager(), "FilterLogsDialogFragment");
    }

    public void init(View view) {
        activeLog = new ArrayList<>();
        inactiveLog = new ArrayList<>();

        activeLog.add(new ListElementLog(new Date(), "Carlos", "Administrador", LogType.INFO, "Se ha creado un usuario"));
        activeLog.add(new ListElementLog(new Date(), "Luis", "Supervisor", LogType.ERROR, "Se ha realizado una error"));
        activeLog.add(new ListElementLog(new Date(), "Ana", "Administrador", LogType.ERROR, "Se ha encontrado un error"));
        activeLog.add(new ListElementLog(new Date(), "Juan", "Supervisor", LogType.INFO, "Se ha creado un usuario"));

        activeLog.add(new ListElementLog(new Date(), "Carlos", "Administrador", LogType.INFO, "Se ha creado un usuario"));
        activeLog.add(new ListElementLog(new Date(), "Luis", "Supervisor", LogType.ERROR, "Se ha realizado una error"));
        activeLog.add(new ListElementLog(new Date(), "Ana", "Administrador", LogType.ERROR, "Se ha encontrado un error"));
        activeLog.add(new ListElementLog(new Date(), "Juan", "Supervisor", LogType.INFO, "Se ha creado un usuario"));

        inactiveLog.add(new ListElementLog(new Date(), "Carlos", "Administrador", LogType.ERROR, "El administrador Carlos ha encontrado un error"));
        inactiveLog.add(new ListElementLog(new Date(), "Luis", "Supervisor", LogType.INFO, "El supervisor Luis ha realizado una acción"));
        inactiveLog.add(new ListElementLog(new Date(), "Ana", "Administrador", LogType.ERROR, "El administrador Ana ha emitido una advertencia"));
        inactiveLog.add(new ListElementLog(new Date(), "Juan", "Supervisor", LogType.INFO, "El administrador Juan ha creado un supervisor"));

        inactiveLog.add(new ListElementLog(new Date(), "Carlos", "Administrador", LogType.ERROR, "El administrador Luis ha encontrado un error"));
        inactiveLog.add(new ListElementLog(new Date(), "Luis", "Supervisor", LogType.INFO, "El supervisor Luis ha reportado un problema crítico"));
        inactiveLog.add(new ListElementLog(new Date(), "Ana", "Administrador", LogType.ERROR, "El administrador Ana ha emitido una error"));
        inactiveLog.add(new ListElementLog(new Date(), "Juan", "Supervisor", LogType.INFO, "El administrador Juan ha realizado una acción"));

        // Initialize the adapter and RecyclerView
        listAdapterLog = new ListAdapterLog(activeLog, getContext(), item -> moveToDescription(item));
        recyclerViewLog = view.findViewById(R.id.listElementsLogs);
        recyclerViewLog.setHasFixedSize(true);
        recyclerViewLog.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLog.setAdapter(listAdapterLog);
    }
    public void moveToDescription(ListElementLog item){
        Intent intent = new Intent(getContext(), Log_Description.class);
        intent.putExtra("ListElementLog", item);
        startActivity(intent);
    }
}
