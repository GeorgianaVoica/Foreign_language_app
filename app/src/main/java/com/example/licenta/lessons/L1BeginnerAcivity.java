package com.example.licenta.lessons;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.licenta.R;
import com.example.licenta.activities.ResultsActivity;
import com.example.licenta.classes.Lesson;
import com.example.licenta.classes.Quiz;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class L1BeginnerAcivity extends AppCompatActivity {


    Toolbar toolbar;
    private TextView tvQ1L1B, tvQ2L1B, tvQ3L1B, tvQ4L1B, tvQ5L1B;
    private EditText raspunsQ2L1B;
    private Button bword1L1B, bword2L1B, bword3L1B, bword4L1B, bFinnishL1B;
    private RadioGroup rgQ1L1B, rgChkL1B, rg4L1B, rg5L1B;
    private ImageButton imgbReplayL1B;
    private String text="";
    private int points=0;
    private TextToSpeech textToSpeech;
    private FirebaseFirestore db;
    private DocumentReference userRef;
    private FirebaseAuth auth;

    private RadioButton  rb2, rb1Q4, rb3Q5;
    private CheckBox  cb2;
    private List<Quiz> quizzes = new ArrayList<>();
    private AlertDialog.Builder builder;
    User user;

    private int CONTOR = 1;
    private String RASP = "";
    //contor, punctaj, raspunsuri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l1_beginner_acivity);

        Drawable image = getResources().getDrawable(R.drawable.medal1);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(image);


        bFinnishL1B = findViewById(R.id.bFinnishL1B);

        getComponents();
        builder = new AlertDialog.Builder(this);
        builder.setView(imageView);

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);

                Query query = db.collection("lessons").whereEqualTo("level", user.getLevel()).whereEqualTo("noLesson", 1);
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        if (!documents.isEmpty()) {
                            DocumentReference docRef = queryDocumentSnapshots.getDocuments().get(0).getReference();
                            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Lesson lesson = documentSnapshot.toObject(Lesson.class);
                                    quizzes = lesson.getQuiz();

                                    tvQ1L1B.setText(quizzes.get(0).getQuestion());
                                    tvQ2L1B.setText(quizzes.get(1).getQuestion());
                                    tvQ3L1B.setText(quizzes.get(2).getQuestion());
                                    tvQ4L1B.setText(quizzes.get(3).getQuestion());
                                    tvQ5L1B.setText(quizzes.get(4).getQuestion());


                                }
                            });
                        } else {
                            System.out.println("Nu functioneaza");
                        }
                    }
                });
            }
        });

        bFinnishL1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb2.isChecked()){
                    points += quizzes.get(0).getPoints();
                    userRef.update("noPoints", user.getNoPoints() + points);
                    Log.d(TAG, "points1: "+ points);
                }else{
                    points +=0;
                }

                if(raspunsQ2L1B.getText().toString().trim().equals(quizzes.get(1).getAnswer().trim())){
                    points += quizzes.get(1).getPoints();
                    userRef.update("noPoints", user.getNoPoints() + points);
                    Log.d(TAG, "points2: "+ points);
                }else {
                    points+=0;
                }

                if(cb2.isChecked()){
                    points += quizzes.get(2).getPoints();
                    userRef.update("noPoints", user.getNoPoints() + points);
                    Log.d(TAG, "points2: "+ points);
                }else{
                    points += 0;
                }

                if(rb1Q4.isChecked()){
                    points += quizzes.get(3).getPoints();
                    userRef.update("noPoints", user.getNoPoints() + points);
                    Log.d(TAG, "points1: "+ points);
                }else{
                    points +=0;
                }

                if(rb3Q5.isChecked()){
                    points += quizzes.get(4).getPoints();
                    userRef.update("noPoints", user.getNoPoints() + points);
                    Log.d(TAG, "points1: "+ points);
                }else{
                    points +=0;
                }
                //userRef.update("noPoints", user.getNoPoints() + points);

                int pctTot=user.getNoPoints();
                System.out.println(pctTot);

                RASP = RASP.concat("Răspunsul corect la întrebarea 1 este: " + quizzes.get(0).getAnswer() + "\n");
                RASP = RASP.concat("Răspunsul corect la întrebarea 2 este: " + quizzes.get(1).getAnswer() + "\n");
                RASP = RASP.concat("Răspunsul corect la întrebarea 3 este: " + quizzes.get(2).getAnswer() + "\n");
                RASP = RASP.concat("Răspunsul corect la întrebarea 4 este: " + quizzes.get(3).getAnswer() + "\n");
                RASP = RASP.concat("Răspunsul corect la întrebarea 5 este: " + quizzes.get(4).getAnswer() + "\n");

                builder.setTitle("Felicitari! Ai finalizat lectia")
                        .setMessage("Ai finalizat lectia")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), ResultsActivity.class);
                                intent.putExtra("CONTOR", CONTOR);
                                intent.putExtra("RASP", RASP);
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.GERMAN);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        bword1L1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text += bword1L1B.getText() + " ";
                raspunsQ2L1B.setText(text);
                textToSpeech.speak(bword1L1B.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                // text += etRaspuns.getText();
            }
        });

        bword2L1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text += bword2L1B.getText() + " ";
                raspunsQ2L1B.setText(text);
                textToSpeech.speak(bword2L1B.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        bword3L1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text += bword3L1B.getText()+ " ";
                raspunsQ2L1B.setText(text);
                textToSpeech.speak(bword3L1B.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        bword4L1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text += bword4L1B.getText()+ " ";
                raspunsQ2L1B.setText(text);
                textToSpeech.speak(bword4L1B.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        imgbReplayL1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raspunsQ2L1B.setText("");
                text = "";
            }
        });


    }

    private void getComponents() {
        toolbar = findViewById(R.id.toolbarShowLesson);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvQ1L1B = findViewById(R.id.tvQ1L1B);
        tvQ2L1B = findViewById(R.id.tvQ2L1B);
        tvQ3L1B = findViewById(R.id.tvQ3L1B);
        tvQ4L1B = findViewById(R.id.textViewQ4L1B);
        tvQ5L1B = findViewById(R.id.textViewQ5L1B);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userRef = db.collection("users").document(auth.getCurrentUser().getUid());

        bword1L1B = findViewById(R.id.bword1L1B);
        bword2L1B = findViewById(R.id.bword2L1B);
        bword3L1B = findViewById(R.id.bword3L1B);
        bword4L1B = findViewById(R.id.bword4L1B);

        raspunsQ2L1B = findViewById(R.id.raspunsQ2L1B);

        imgbReplayL1B = findViewById(R.id.imageButtonReplayL1B);

        rgQ1L1B = findViewById(R.id.rgQ1L1B);
        rgChkL1B = findViewById(R.id. rgChkL1B);
        rg4L1B = findViewById(R.id.rg4L1B);
        rg5L1B = findViewById(R.id.rg5L1B);

        rb2 = findViewById(R.id.rb2Q1L1B);
        rb1Q4 = findViewById(R.id.rb1Q4L1B);
        rb3Q5 = findViewById(R.id.rb3Q5L1B);

        cb2 = findViewById(R.id.checkBox2L1B);
    }

    private void resetPoints() {
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()) {
                        userRef.update("noPoints", 60);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}