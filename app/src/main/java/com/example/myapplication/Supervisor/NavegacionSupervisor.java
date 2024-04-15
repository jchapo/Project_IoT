package com.example.myapplication.Supervisor;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.myapplication.Admin.ChatFragment;
import com.example.myapplication.Admin.NotificationsFragment;
import com.example.myapplication.Admin.SitesFragment;
import com.example.myapplication.Admin.UsersFragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainNavigationAdminBinding;
import com.example.myapplication.databinding.ActivityNavegacionSupervisorBinding;

public class NavegacionSupervisor extends AppCompatActivity {

    ActivityNavegacionSupervisorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavegacionSupervisorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }



}