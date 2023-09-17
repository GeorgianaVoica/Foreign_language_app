package com.example.licenta.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licenta.R;
import com.example.licenta.classes.Lesson;
import com.example.licenta.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class LevelActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editText1, editText2, editText3;
    private FirebaseFirestore db;
    private DocumentReference userRef;
    private FirebaseAuth auth;
    private String userId;
    private TextView tvLevel;
    private TextView tvReset;
    private AlertDialog.Builder builder;
    String textNew;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        getComponents();
        displayLevel();
        modifyDb();

        builder = new AlertDialog.Builder(this);
        clickResetTv();

//        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                User user = documentSnapshot.toObject(User.class);
//                Query query = db.collection("lessons").whereEqualTo("level", user.getLevel());
//                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        DocumentReference documentReference = queryDocumentSnapshots.getDocuments().get(0).getReference();
//                        //Toast.makeText(ProfileActivity.this, documentReference.getId(), Toast.LENGTH_SHORT).show();
//                        documentReference.get()
//                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                        Lesson lesson = documentSnapshot.toObject(Lesson.class);
//                                        Toast.makeText(getApplicationContext(), lesson.getQuiz().toString(), Toast.LENGTH_LONG).show();
//                                        Log.d("GetLesson", lesson.getLevel());
//                                    }
//                                });
//                    }
//                });
//            }
//        });

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
            startActivity(new Intent(this, ProfileActivity.class));
        }

        if(id == R.id.settings2_item) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        if(id == R.id.logout2_item) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
        }

        return true;
    }

    private void getComponents() {
        toolbar = findViewById(R.id.toolbarLevel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LessonTypeActivity.class));
            }
        });

        editText1 = findViewById(R.id.etLevel1);
        editText2 = findViewById(R.id.etLevel2);
        editText3 = findViewById(R.id.etLevel3);

        auth = FirebaseAuth.getInstance();
        userId= auth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        userRef = db.collection("users").document(userId);
        tvReset = findViewById(R.id.tvReset);
        tvLevel = findViewById(R.id.tvLevel);
    }

    private void displayLevel() {

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    String text = documentSnapshot.getString("level");
                    tvLevel.setText("Esti la nivelul: " + text);
                }
            }
        });
    }

    private void modifyDb() {
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        int points = documentSnapshot.getLong("noPoints").intValue();
                        boolean hasPoints = documentSnapshot.getBoolean("hasPoints");
                        if( points > 0 && points < 30 && hasPoints) {
                            begginnerLogged();
                        } else if((points >= 30 && points <60) && hasPoints) {
                            intermediateLogged();
                        } else if((points >= 60) && hasPoints) {
                            advancedLogged();
                        } else {
                            firstLogin();
                        }
                    }
                }
            }
        });
    }


    private void firstLogin() {
        firstLoginBeginner();
        firstLoginIntermediate();
        firstLoginAdvanced();
    }

    private void firstLoginBeginner() {
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.update("noPoints", 1);
                userRef.update("hasPoints", true);
                userRef.update("level", "incepator");
                textNew="incepator";
                tvLevel.setText("Esti la nivelul: " + textNew);
                editText2.setEnabled(false);
                editText3.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Ai ales nivelul incepator", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LessonActivity.class));
            }
        });
    }

    private void firstLoginIntermediate() {
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.update("noPoints", 30);
                userRef.update("hasPoints", true);
                userRef.update("level", "intermediar");
               // editText1.setEnabled(false);
                textNew="intermediar";
                tvLevel.setText("Esti la nivelul: " + textNew);
                editText3.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Ai ales nivelul intermediar", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LessonActivity.class));
            }
        });
    }

    private void firstLoginAdvanced() {
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.update("noPoints", 60);
                userRef.update("hasPoints", true);
                userRef.update("level", "avansat");
                //editText1.setEnabled(false);
                //editText2.setEnabled(false);
                textNew="avansat";
                tvLevel.setText("Esti la nivelul: " + textNew);
                Toast.makeText(getApplicationContext(), "Ai ales nivelul avansat", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LessonActivity.class));
            }
        });
    }


    private void begginnerLogged() {
        editText1.setEnabled(true);
        editText2.setEnabled(false);
        editText3.setEnabled(false);
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LessonActivity.class));
            }
        });
    }

    private void intermediateLogged() {
        //editText1.setEnabled(false);
        editText1.setEnabled(true);
        editText2.setEnabled(true);
        editText3.setEnabled(false);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LessonActivity.class));
            }
        });
    }

    private void advancedLogged() {
//        editText1.setEnabled(false);
//        editText2.setEnabled(false);
        editText1.setEnabled(true);
        editText2.setEnabled(true);
        editText3.setEnabled(true);
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LessonActivity.class));
            }
        });
    }

    private void clickResetTv() {
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Resetare nivel")
                        .setMessage("Doriți să resetați nivelul?")
                        .setCancelable(true)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetLevel();
                                tvLevel.setText("Esti la nivelul: " + textNew);
                                Toast.makeText(getApplicationContext(), "Resetați nivelul", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        })
                        .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    private void resetLevel() {
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        int points = documentSnapshot.getLong("noPoints").intValue();
                        boolean hasPoints = documentSnapshot.getBoolean("hasPoints");
                        if( points > 0 && hasPoints) {
                            userRef.update("noPoints", 0);
                            userRef.update("hasPoints", false);
                            userRef.update("level", "incepator");
                            editText1.setEnabled(true);
                            editText2.setEnabled(true);
                            editText3.setEnabled(true);
                            firstLogin();
                            String text = documentSnapshot.getString("level");
                            textNew=text;
                            //tvLevel.setText("Esti la nivelul: " + text);
                        }
                    }
                }
            }
        });
    }

}