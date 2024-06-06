package com.example.myapplication.SuperAdmin;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

import java.util.Calendar;

public class FilterLogsDialogFragment extends DialogFragment {

    private TextView fromDateTextView, toDateTextView;
    private Spinner logTypeSpinner, userRolSpinner;
    private FilterLogsListener listener;

    public interface FilterLogsListener {
        void onApplyFilters(String fromDate, String toDate, String logType, String user);
    }

    public FilterLogsDialogFragment(FilterLogsListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.superadmin_filter_logs, container, false);

        fromDateTextView = view.findViewById(R.id.textViewFromDate);
        toDateTextView = view.findViewById(R.id.textViewToDate);
        logTypeSpinner = view.findViewById(R.id.spinnerLogType);

        Button cancelButton = view.findViewById(R.id.buttonCancel);
        Button applyButton = view.findViewById(R.id.buttonApply);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDate = fromDateTextView.getText().toString();
                String toDate = toDateTextView.getText().toString();
                String logType = logTypeSpinner.getSelectedItem().toString();
                String userRol = userRolSpinner.getSelectedItem().toString();

                if (listener != null) {
                    listener.onApplyFilters(fromDate, toDate, logType, userRol);
                }
                dismiss();
            }
        });

        fromDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(fromDateTextView);
            }
        });

        toDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(toDateTextView);
            }
        });

        return view;
    }

    private void showDatePickerDialog(final TextView dateTextView) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dateTextView.setText(selectedDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
