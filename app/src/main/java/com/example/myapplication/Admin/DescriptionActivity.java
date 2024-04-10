package com.example.myapplication.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Admin.items.ListElement;
import com.example.myapplication.R;

import org.w3c.dom.Text;

public class DescriptionActivity extends AppCompatActivity {

    TextView titleDescriptionTextView;
    TextView userDescriptionTextView;
    TextView statusDescriptionTextView;
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_userprofile_admin);

        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        titleDescriptionTextView = findViewById(R.id.fullNameTextView);
        userDescriptionTextView = findViewById(R.id.cargoTextView);
        //statusDescriptionTextView = findViewById(R.id.statusDesciptionTextView);

        titleDescriptionTextView.setText(element.getName());
        titleDescriptionTextView.setTextColor(Color.parseColor(element.getColor()));

        userDescriptionTextView.setText(element.getUser());
        //statusDescriptionTextView.setText(element.getStatus());
        //statusDescriptionTextView.setTextColor(Color.GREEN);

    }
}