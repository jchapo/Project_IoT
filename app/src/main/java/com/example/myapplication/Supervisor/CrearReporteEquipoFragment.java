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
    private ArrayAdapter<String> EquipoAdapter;
    private String[] EquipoOptions = {"Switch Gillat FP-435", "Router CISCO WIFIMAX", "Huawei Fiber Gateway HGW-004", "ZYXEL Enterprise Switch Z-SWT"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.supervisor_fragment_crear_reporte_equipo, container, false);

        selectEquipo = view.findViewById(R.id.selectEquipo);
        EquipoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, EquipoOptions);
        selectEquipo.setAdapter(EquipoAdapter);

        // Verificar si se pasó el nombre del equipo seleccionado en el Bundle
        Bundle args = getArguments();
        if (args != null) {
            String equipoSeleccionado = args.getString("equipo_seleccionado");
            if (equipoSeleccionado != null) {
                selectEquipo.setText(equipoSeleccionado, false);
                selectEquipo.setEnabled(false); // Deshabilitar para que no se pueda cambiar
            }
        }

        return view;
    }

    private boolean areFieldsEmpty() {
        return selectEquipo.getText().toString().isEmpty();
    }
}



