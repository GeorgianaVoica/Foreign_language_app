package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.licenta.R;
import com.example.licenta.classes.TutorForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TeachingFormActivity extends AppCompatActivity {

    private EditText etDescriere, etDurata, etPret, etContact;
    private Button btTrimite;
    private Button signUpButton;
    private TextView loginRedirectText;
    private FirebaseAuth auth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_form);

        etDescriere = findViewById(R.id.etDescriere);
        etDurata = findViewById(R.id.etDurata);
        etPret = findViewById(R.id.etPret);
        etContact = findViewById(R.id.etContact);
        btTrimite = findViewById(R.id.btnTrimite);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btTrimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descriere = etDescriere.getText().toString().trim();
                String durata = etDurata.getText().toString().trim();
                String pret = etPret.getText().toString().trim();
                String contact = etContact.getText().toString().trim();

                if(descriere.isEmpty()) {
                    etDescriere.setError("Trebuie sa introduceti o descriere a planului de lectii");
                }

                if(durata.isEmpty()) {
                    etDurata.setError("Trebuie sa introduceti o durata a lectiei");
                }

                if(pret.isEmpty()) {
                    etPret.setError("Trebuie sa introduceti un pret al sedintei");
                }

                if(contact.isEmpty()) {
                    etContact.setError("Trebuie sa introduceti datele de contact");
                }

                TutorForm form = new TutorForm(descriere, durata, pret, contact);
                db.collection("forms").add(form);
                startActivity(new Intent(TeachingFormActivity.this, SeeOffersActivity.class));
            }
        });
    }
}