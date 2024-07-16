package com.example.myapplication.SuperAdmin;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.example.myapplication.SuperAdmin.list.ListElementLog;

import java.text.SimpleDateFormat;

public class Log_Description extends AppCompatActivity {

    TextView timestampTextView;
    TextView userTextView;
    TextView userRolTextView;
    TextView logTypeTextView;
    TextView messageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.superadmin_activity_log_description);

        ListElementLog logElement = (ListElementLog) getIntent().getSerializableExtra("ListElementLog");

        timestampTextView = findViewById(R.id.timestampTextView);
        userTextView = findViewById(R.id.userTextView);
        userRolTextView = findViewById(R.id.userRolTextView);
        logTypeTextView = findViewById(R.id.logTypeTextView);
        messageTextView = findViewById(R.id.messageTextView);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String formattedDate = formatter.format(logElement.getTimestamp());
        timestampTextView.setText(formattedDate);
        userTextView.setText(logElement.getUser());
        userRolTextView.setText(logElement.getUserRol());
        logTypeTextView.setText(logElement.getLogType().toString());
        messageTextView.setText(logElement.getMessage());

        Toolbar toolbar = findViewById(R.id.topAppBarLogDescription);
        toolbar.setTitle("Detalle del Log");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        findViewById(R.id.fabDeleteLog).setOnClickListener(v -> {
            Toast.makeText(Log_Description.this, "Log eliminado", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
