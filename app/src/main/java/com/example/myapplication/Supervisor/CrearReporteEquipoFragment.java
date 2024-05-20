package com.example.myapplication.Supervisor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class CrearReporteEquipoFragment extends Fragment {

    private MaterialAutoCompleteTextView selectEquipo;
    private ArrayAdapter<String> equipoAdapter;

    private String[] equipoOptions = {"Switch Gillat FP-435", "Router CISCO WIFIMAX", "Huawei Fiber Gateway HGW-004", "ZYXEL Enterprise Switch Z-SWT"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.supervisor_fragment_crear_reporte_equipo, container, false);

        selectEquipo = view.findViewById(R.id.selectEquipo);
        equipoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, equipoOptions);

        selectEquipo.setAdapter(equipoAdapter);

        return view;
    }

    private boolean areFieldsEmpty() {
        return selectEquipo.getText().toString().isEmpty();
    }
}



