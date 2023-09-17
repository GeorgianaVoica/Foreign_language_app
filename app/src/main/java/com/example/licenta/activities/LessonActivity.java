package com.example.licenta.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.licenta.R;
import com.example.licenta.classes.User;
import com.example.licenta.lessons.L1AdvancedActivity;
import com.example.licenta.lessons.L1BeginnerAcivity;
import com.example.licenta.lessons.L1IntermediateActivity;
import com.example.licenta.lessons.L2AdvancedActivity;
import com.example.licenta.lessons.L2BeginnerActivity;
import com.example.licenta.lessons.L2IntermediateActivity;
import com.example.licenta.lessons.L3AdvancedActivity;
import com.example.licenta.lessons.L3BeginnerActivity;
import com.example.licenta.lessons.L3IntermediateActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class LessonActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText etL1, etL2, etL3, etVocabular;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userRef = db.collection("users").document( FirebaseAuth.getInstance().getUid()) ;
    private FirebaseAuth auth;
    private String level;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_acivity);

        toolbar = findViewById(R.id.toolbarLessonType);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LevelActivity.class));
            }
        });

        etL1 = findViewById(R.id.etL1);
        etL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        Query query = db.collection("lessons").whereEqualTo("level", user.getLevel())
                                .whereEqualTo("noLesson", 1);
                        level = user.getLevel();
                        System.out.println(level);

                        if(level.equals("incepator")) {
                            startActivity(new Intent(getApplicationContext(), L1BeginnerAcivity.class));
                        } else if(level.equals("intermediar")) {
                            startActivity(new Intent(getApplicationContext(), L1IntermediateActivity.class));
                        } else if(level.equals("avansat")) {
                            startActivity(new Intent(getApplicationContext(), L1AdvancedActivity.class));
                        }
                    }
                });


            }
        });

        etL2 = findViewById(R.id.etL2);
        etL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        Query query = db.collection("lessons").whereEqualTo("level", user.getLevel())
                                .whereEqualTo("noLesson", 2);
                        level = user.getLevel();
                        System.out.println(level);

                        if(level.equals("incepator")) {
                            startActivity(new Intent(getApplicationContext(), L2BeginnerActivity.class));
                        } else if(level.equals("intermediar")) {
                            startActivity(new Intent(getApplicationContext(), L1IntermediateActivity.class));
                        } else if(level.equals("avansat")) {
                            startActivity(new Intent(getApplicationContext(), L2AdvancedActivity.class));
                        }
                    }
                });


            }
        });

        etL3 = findViewById(R.id.etL3);
        etL3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        Query query = db.collection("lessons").whereEqualTo("level", user.getLevel())
                                .whereEqualTo("noLesson", 3);
                        level = user.getLevel();
                        System.out.println(level);

                        if(level.equals("incepator")) {
                            startActivity(new Intent(getApplicationContext(), L2BeginnerActivity.class));
                        } else if(level.equals("intermediar")) {
                            startActivity(new Intent(getApplicationContext(), L1IntermediateActivity.class));
                        } else if(level.equals("avansat")) {
                            startActivity(new Intent(getApplicationContext(), L2AdvancedActivity .class));
                        }
                    }
                });


            }
        });

        etVocabular = findViewById(R.id.vocabular);
        etVocabular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), VocabularyActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.home2_item) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        if(id == R.id.settings2_item) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        }

        if(id == R.id.logout2_item) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        return true;
    }
}