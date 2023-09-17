package com.example.licenta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licenta.R;
import com.example.licenta.classes.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultsActivity extends AppCompatActivity {
    User user;
    FirebaseFirestore db;
    FirebaseAuth auth;
    DocumentReference userRef;
    int points=0;
    TextView tvResults;
    Button btInapoi;
    String rasp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvResults = findViewById(R.id.tvResults);
        btInapoi = findViewById(R.id.btInapoi);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userRef = db.collection("users").document(auth.getCurrentUser().getUid());



        int contor = getIntent().getIntExtra("CONTOR", 0);
        rasp = getIntent().getStringExtra("RASP");

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                System.out.println(user.getNoPoints());
                points += user.getNoPoints();
                rasp = rasp.concat("Punctaj: " + points);

                if(contor==1) {
                    tvResults.setText(rasp);
                   // Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                }else if(contor==2) {
                    tvResults.setText(rasp);
                    //Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                }else if(contor==3) {
                    tvResults.setText(rasp);
                    //Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                }else if(contor==4) {
                    tvResults.setText(rasp);
                   // Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                } else if(contor==5) {
                    tvResults.setText(rasp);
                    //Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                }else if(contor==7) {
                    tvResults.setText(rasp);
                    //Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                } else if(contor==6) {
                    tvResults.setText(rasp);
                    //Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                } else if(contor==7) {
                    tvResults.setText(rasp);
                    //Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                }else if(contor==8) {
                    tvResults.setText(rasp);
                    //Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                } else if(contor==9) {
                    tvResults.setText(rasp);
                   // Toast.makeText(getApplicationContext(), rasp, Toast.LENGTH_SHORT).show();
                }

                btInapoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), LessonActivity.class));
                    }
                });
            }
        });



    }
}