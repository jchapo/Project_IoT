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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Supervisor.objetos.ListAdapterReportesSupervisor;
import com.example.myapplication.Supervisor.objetos.ListElementReportes;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportesFragment extends Fragment {

    List<ListElementReportes> elementsReportes;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.supervisor_fragment_reportes, container, false);
        Toolbar toolbar = view.findViewById(R.id.topAppBarSupervisorReportes);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        // Inicializar RecyclerView y cargar datos
        init(view);

        FloatingActionButton fab = view.findViewById(R.id.agregarReportefloatingActionButton);

        // Agregar OnClickListener al botón flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad Crear Reporte
                Intent intent = new Intent(getContext(), CrearReporte.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void init(View view) {
        elementsReportes = new ArrayList<>();
        elementsReportes.add(new ListElementReportes("RPT-101", "ARP-101", "13/04/24", "17/04/24", "Pablo", "Router SW1", "Malfuncionamiento del equipo"));
        elementsReportes.add(new ListElementReportes("RPT-102", "ARP-102", "12/04/24", "17/04/24", "Pablo", "Router SW2", "Malfuncionamiento del equipo"));
        elementsReportes.add(new ListElementReportes("RPT-103", "ARP-103", "11/04/24", "13/04/24", "Sergio", "Router SW3", "Malfuncionamiento del equipo"));
        elementsReportes.add(new ListElementReportes("RPT-104", "CAJ-101", "10/04/24", "11/04/24", "Juan", "Switch DW3", "Malfuncionamiento del equipo"));
        elementsReportes.add(new ListElementReportes("RPT-105", "CAJ-102", "09/04/24", "12/04/24", "Juan", "Switch DW4", "Malfuncionamiento del equipo"));
        elementsReportes.add(new ListElementReportes("RPT-106", "CAJ-103", "06/04/24", "11/04/24", "Juan", "Switch DW5", "Malfuncionamiento del equipo"));
        elementsReportes.add(new ListElementReportes("RPT-107", "LIM-101", "03/04/24", "10/04/24", "Diego", "Repetidor RE1", "Malfuncionamiento del equipo"));
        elementsReportes.add(new ListElementReportes("RPT-108", "LIM-102", "12/04/24", "14/04/24", "Diego", "Repetidor RE2", "Malfuncionamiento del equipo"));
        elementsReportes.add(new ListElementReportes("RPT-109", "LIM-103", "16/04/24", "17/04/24", "Sergio", "Repetidor RE3", "Malfuncionamiento del equipo"));

        // Añadir más elementos según sea necesario

        // Obtener referencia al RecyclerView
        recyclerView = view.findViewById(R.id.listReportes);

        // Crear y configurar el adaptador y el LayoutManager
        ListAdapterReportesSupervisor listAdapter = new ListAdapterReportesSupervisor(elementsReportes, getContext(), item -> moveToDescription(item));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);
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
            // Crear y mostrar el Date Range Picker
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
            builder.setTitleText("Selecciona un rango de fechas");

            // No agregar restricciones de fecha
            CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

            builder.setCalendarConstraints(constraintsBuilder.build());

            MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();

            datePicker.show(getParentFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                // Manejar el rango de fechas seleccionado
                Pair<Long, Long> dateRange = selection;
                Long startDate = dateRange.first;
                Long endDate = dateRange.second;

                // Filtrar tu lista de reportes aquí
                filterReportsByDateRange(startDate, endDate);
            });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void filterReportsByDateRange(Long startDate, Long endDate) {
        // Crear una nueva lista para los reportes filtrados
        List<ListElementReportes> filteredReports = new ArrayList<>();

        for (ListElementReportes report : elementsReportes) {
            // Convierte las fechas del reporte a milisegundos para la comparación
            Long reportStartDate = convertDateToMillis(report.getFecha_creacion());
            Long reportEndDate = convertDateToMillis(report.getFecha_final());

            if ((reportStartDate >= startDate && reportStartDate <= endDate) ||
                    (reportEndDate >= startDate && reportEndDate <= endDate)) {
                filteredReports.add(report);
            }
        }

        // Actualiza el RecyclerView con la lista filtrada
        ListAdapterReportesSupervisor listAdapter = new ListAdapterReportesSupervisor(filteredReports, getContext(), item -> moveToDescription(item));
        recyclerView.setAdapter(listAdapter);
    }

    private Long convertDateToMillis(String date) {
        // Implementa tu lógica para convertir una fecha en formato String a milisegundos
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
