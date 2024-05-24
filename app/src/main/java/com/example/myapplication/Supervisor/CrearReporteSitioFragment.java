package com.example.myapplication.Supervisor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.myapplication.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;


public class CrearReporteSitioFragment extends Fragment {


    private MaterialAutoCompleteTextView selectSitio;
    private ArrayAdapter<String> sitioAdapter;

    private String[] sitioOptions = {"LIM-01","CAJ-02","ARQ-03","TRJ-01","PIU-01"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.supervisor_fragment_crear_reporte_sitio, container, false);

        selectSitio = view.findViewById(R.id.selectSitio);
        sitioAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, sitioOptions);
        selectSitio.setAdapter(sitioAdapter);

        Bundle args = getArguments();
        if (args != null) {
            String sitioSeleccionado = args.getString("sitio_seleccionado");
            if (sitioSeleccionado != null) {
                selectSitio.setText(sitioSeleccionado, false);
                selectSitio.setEnabled(false); // Deshabilitar para que no se pueda cambiar
            }
        }

        return view;
    }

    private boolean areFieldsEmpty() {
        return selectSitio.getText().toString().isEmpty();
    }

}