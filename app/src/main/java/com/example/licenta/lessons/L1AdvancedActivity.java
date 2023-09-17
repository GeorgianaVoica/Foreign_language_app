package com.example.licenta.lessons;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
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
import com.example.licenta.activities.LessonActivity;
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

public class L1AdvancedActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView tvQuestion1, tvAnswer1, tvQuestion2, tvQuestion3, tvQuestion4, tvQuestion5;
    private EditText etRaspuns;
    private Button bword1, bword2, bword3, bFinnish;
    private RadioGroup rgRb, rgChk, rg4, rg5;
    private ImageButton imgbReplay;
    private String text="";
    private int points=0;
    private TextToSpeech textToSpeech;
    private FirebaseFirestore db;
    private DocumentReference userRef;
    private FirebaseAuth auth;

    private RadioButton rb1, rb2, rb3, rb1Q4, rb2Q4, rb3Q4, rb1Q5, rb2Q5, rb3Q5;
    private CheckBox cb1, cb2, cb3;
    private List<Quiz> quizzes = new ArrayList<>();
    private AlertDialog.Builder builder;
    User user;

    private int CONTOR = 7;
    private String RASP = "";
    //contor, punctaj, raspunsuri

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l1_advanced);

        Drawable image = getResources().getDrawable(R.drawable.medal1);
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(image);

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

                                    tvQuestion1.setText(quizzes.get(0).getQuestion());
                                    tvQuestion2.setText(quizzes.get(1).getQuestion());
                                    tvQuestion3.setText(quizzes.get(2).getQuestion());
                                    tvQuestion4.setText(quizzes.get(3).getQuestion());
                                    tvQuestion5.setText(quizzes.get(4).getQuestion());


                                }
                            });
                        } else {
                            System.out.println("Nu functioneaza");
                        }
                    }
                });
            }
        });

        bFinnish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb2.isChecked()){
                    points += quizzes.get(0).getPoints();
                    userRef.update("noPoints", user.getNoPoints() + points);
                    Log.d(TAG, "points1: "+ points);
                }else{
                    points +=0;
                }

                if(etRaspuns.getText().toString().trim().equals(quizzes.get(1).getAnswer().trim())){
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

        bword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text += bword1.getText() + " ";
                etRaspuns.setText(text);
                textToSpeech.speak(bword1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
               // text += etRaspuns.getText();
            }
        });

        bword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text += bword2.getText() + " ";
                etRaspuns.setText(text);
                textToSpeech.speak(bword2.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        bword3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text += bword3.getText()+ " ";
                etRaspuns.setText(text);
                textToSpeech.speak(bword3.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        imgbReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etRaspuns.setText("");
                text = "";
            }
        });


    }

    protected void getComponents() {
        toolbar = findViewById(R.id.toolbarShowLesson);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvQuestion1 = findViewById(R.id.tvQuestion1);
        tvQuestion2 = findViewById(R.id.tvQuestion2);
        tvQuestion3 = findViewById(R.id.tvQuestion3);
        tvQuestion4 = findViewById(R.id.textViewQ4);
        tvQuestion5 = findViewById(R.id.textViewQ5);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userRef = db.collection("users").document(auth.getCurrentUser().getUid());

        bword1 = findViewById(R.id.bword1);
        bword2 = findViewById(R.id.bword2);
        bword3 = findViewById(R.id.bword3);
        etRaspuns = findViewById(R.id.raspuns);
        imgbReplay = findViewById(R.id.imageButtonReplay);
        rgRb = findViewById(R.id.radioGroup);
        rgChk = findViewById(R.id. rgChk);
        rg4 = findViewById(R.id.rg4);
        rg5 = findViewById(R.id.rg5);

        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb1Q4 = findViewById(R.id.rb1Q4);
        rb2Q4 = findViewById(R.id.rb2Q4);
        rb3Q4 = findViewById(R.id.rb3Q4);
        rb1Q5 = findViewById(R.id.rb1Q5);
        rb2Q5 = findViewById(R.id.rb2Q5);
        rb3Q5 = findViewById(R.id.rb3Q5);

        cb1 = findViewById(R.id.checkBox1);
        cb2 = findViewById(R.id.checkBox2);
        cb3 = findViewById(R.id.checkBox3);


        bFinnish = findViewById(R.id.bFinnish);
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