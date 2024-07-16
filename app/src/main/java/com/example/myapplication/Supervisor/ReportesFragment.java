package com.example.myapplication.Supervisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListAdapterReportesSupervisor;
import com.example.myapplication.Supervisor.objetos.ListElementReportes;
import com.example.myapplication.Admin.viewModels.NavigationActivityViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportesFragment extends Fragment {

    private List<ListElementReportes> pendingReports = new ArrayList<>();
    private List<ListElementReportes> resolvedReports = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListAdapterReportesSupervisor listAdapter;
    private NavigationActivityViewModel navigationActivityViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.supervisor_fragment_reportes, container, false);
        Toolbar toolbar = view.findViewById(R.id.topAppBarSupervisorReportes);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        navigationActivityViewModel = new ViewModelProvider(requireActivity()).get(NavigationActivityViewModel.class);
        init(view);

        FloatingActionButton fab = view.findViewById(R.id.agregarReportefloatingActionButton);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CrearReporte_previo.class);
            startActivity(intent);
        });

        observeViewModel();
        setupTabs(view);
        return view;
    }

    private void observeViewModel() {
        if (navigationActivityViewModel != null) {
            navigationActivityViewModel.getActiveReports().observe(getViewLifecycleOwner(), activeReports -> {
                pendingReports.clear();
                pendingReports.addAll(activeReports);
                listAdapter.notifyDataSetChanged();
            });
            navigationActivityViewModel.getResolvedReports().observe(getViewLifecycleOwner(), resolvedReportsList -> {
                resolvedReports.clear();
                resolvedReports.addAll(resolvedReportsList);
                listAdapter.notifyDataSetChanged();
            });
        }
    }

    public void init(View view) {
        recyclerView = view.findViewById(R.id.listReportes);
        listAdapter = new ListAdapterReportesSupervisor(pendingReports, getContext(), this::moveToDescription);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
    }

    private void setupTabs(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutReportes);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // Pendientes
                        listAdapter.setItems(pendingReports);
                        listAdapter.notifyDataSetChanged();
                        break;
                    case 1: // Resueltos
                        listAdapter.setItems(resolvedReports);
                        listAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void moveToDescription(ListElementReportes item) {
        Intent intent = new Intent(getContext(), MasDetallesReportes.class);
        intent.putExtra("ListElementReporte", item);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_supervisor_reportes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.masreporte) {
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
            builder.setTitleText("Selecciona un rango de fechas");
            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
            builder.setCalendarConstraints(constraintsBuilder.build());
            MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
            datePicker.show(getParentFragmentManager(), "DATE_PICKER");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                Pair<Long, Long> dateRange = selection;
                filterReportsByDateRange(dateRange.first, dateRange.second);
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterReportsByDateRange(Long startDate, Long endDate) {
        List<ListElementReportes> filteredReports = new ArrayList<>();
        for (ListElementReportes report : pendingReports) {
            Long reportStartDate = convertDateToMillis(report.getFecha_creacion());
            Long reportEndDate = convertDateToMillis(report.getFecha_final());
            if ((reportStartDate >= startDate && reportStartDate <= endDate) ||
                    (reportEndDate >= startDate && reportEndDate <= endDate)) {
                filteredReports.add(report);
            }
        }
        ListAdapterReportesSupervisor listAdapter = new ListAdapterReportesSupervisor(filteredReports, getContext(), item -> moveToDescription(item));
        recyclerView.setAdapter(listAdapter);
    }

    private Long convertDateToMillis(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        try {
            Date parsedDate = sdf.parse(date);
            return parsedDate != null ? parsedDate.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
