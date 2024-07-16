package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.items.ListAdapterMensaje;
import com.example.myapplication.Admin.items.ListElementChat;
import com.example.myapplication.Admin.items.ListElementMensaje;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_3_Chat_ChatAdmin extends AppCompatActivity {

    private EditText editTextMessage;
    private ImageButton buttonSendMessage;
    private RecyclerView recyclerView;
    private ListAdapterMensaje messageAdapter;
    private List<ListElementMensaje> messageList;
    private CollectionReference messagesCollection;

    private FirebaseAuth mAuth;
    private String receiverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_activity_main_chat_users);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("USER_EMAIL");
        String userName = intent.getStringExtra("USER_NAME");
        String userLastName = intent.getStringExtra("USER_LASTNAME");

        if (userEmail != null && userName != null && userLastName != null) {
            // Aquí puedes utilizar los datos como desees
            Log.d("MainActivity_3_Chat_ChatAdmin", "Email: " + userEmail);
            Log.d("MainActivity_3_Chat_ChatAdmin", "Name: " + userName);
            Log.d("MainActivity_3_Chat_ChatAdmin", "LastName: " + userLastName);

            // Mostrar datos en el layout (si tienes TextViews para mostrar esta información)
            //TextView emailTextView = findViewById(R.id.emailTextView);
            //TextView nameTextView = findViewById(R.id.nameTextView);
            //TextView lastNameTextView = findViewById(R.id.lastNameTextView);

            //emailTextView.setText(userEmail);
            //nameTextView.setText(userName);
            //lastNameTextView.setText(userLastName);
        } else {
            Log.d("MainActivity_3_Chat_ChatAdmin", "NO HA RECIBIDO LOS PARAMETRSO");
            Toast.makeText(this, "Error: Datos del usuario no recibidos.", Toast.LENGTH_SHORT).show();
        }



        // Obtener el usuario desde el Intent
        ListElementChat user = (ListElementChat) getIntent().getSerializableExtra("ListElement");
        String username = user != null ? user.getUsuarioChatName() : "Usuario Desconocido";
        receiverId = user != null ? user.getId() : "unknown_receiver"; // Obtener el ID del receptor

        // Verificar si el nombre del usuario se obtiene correctamente
        Log.d("MainActivity_3_Chat", "Nombre del usuario: " + username);
        Log.d("MainActivity_3_Chat", "ID del receptor: " + receiverId);

        // Configurar la barra de herramientas
        Toolbar toolbar = findViewById(R.id.topAppBarNameChat);
        toolbar.setTitle(username); // Establecer el nombre en la barra de herramientas
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Inicializar Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        messagesCollection = db.collection("messages");

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        messageList = new ArrayList<>();
        messageAdapter = new ListAdapterMensaje(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        // Configurar EditText y botón de envío
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Escuchar cambios en Firestore
        messagesCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @NonNull FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("MainActivity_3_Chat", "Listen failed.", e);
                    return;
                }

                messageList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    ListElementMensaje message = doc.toObject(ListElementMensaje.class);
                    messageList.add(message);
                }
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messageList.size() - 1);
            }
        });
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String senderId = currentUser != null ? currentUser.getUid() : "unknown_user";

            long timestamp = System.currentTimeMillis();
            ListElementMensaje message = new ListElementMensaje(messageText, senderId, receiverId, timestamp);
            messagesCollection.add(message);

            editTextMessage.setText("");
        }
    }
}