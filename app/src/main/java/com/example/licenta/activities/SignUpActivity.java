package com.example.licenta.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licenta.R;
import com.example.licenta.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signUpEmail, signUpPassword, signUpName;
    private Button signUpButton;
    private TextView loginRedirectText;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signUpEmail = findViewById(R.id.signup_email);
        signUpPassword = findViewById(R.id.signup_password);
        signUpName = findViewById(R.id.signup_name);
        signUpButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        db = FirebaseFirestore.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = signUpEmail.getText().toString().trim();
                String pass = signUpPassword.getText().toString().trim();
                String name = signUpName.getText().toString();

                boolean checkValid  = true;

                if(mail.isEmpty()) {
                    signUpEmail.setError("Trebuie să introduceți adresa de email");
                    checkValid = false;
                }

                if(pass.isEmpty()) {
                    signUpPassword.setError("Trebuie să introduceți parola");
                    checkValid = false;
                } else {
                    if(!isValid(signUpPassword, pass)) {
                        checkValid = false;
                    }
                }

                if(name.isEmpty()) {
                    signUpEmail.setError("Trebuie să introduceți numele");
                    checkValid = false;
                }

                if(checkValid) {
                    createUser(mail, pass, name);
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

   public boolean isValid(EditText signUpPassword, String pass) {
       if(pass.length()<6) {
           signUpPassword.setError("Parola trebuie sa contina minim 6 caractere");
           return false;
       }

       String upperCaseChars = "(.*[A-Z].*)";
       if (!pass.matches(upperCaseChars )) {
           signUpPassword.setError("Parola trebuie sa contina cel putin o majuscula");
           return false;
       }
       String lowerCaseChars = "(.*[a-z].*)";
       if (!pass.matches(lowerCaseChars )) {
           signUpPassword.setError("Parola trebuie sa contina cel putin o litera mica");
           return false;
       }
       String numbers = "(.*[0-9].*)";
       if (!pass.matches(numbers )) {
           signUpPassword.setError("Parola trebuie sa contina cel putin o cifra");
           return false;
       }
       return true;
   }

   public void createUser (String mail, String pass, String name) {
       auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()) {
                   Toast.makeText(SignUpActivity.this, "Inregistrare reușită", Toast.LENGTH_SHORT).show();
                   userId = auth.getCurrentUser().getUid();
                   User user = new User(mail, pass, name);

                   user.setNoPoints(0);
                   user.setHasPoints(false);
                   user.setLevel("incepator");

                   db.collection("users").document(userId)
                           .set(user.toMap())
                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Toast.makeText(SignUpActivity.this, "Inregistrare reușită", Toast.LENGTH_SHORT).show();
                                   startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(SignUpActivity.this, "Inregistrare eșuată" +  e.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           });
               } else {
                   Toast.makeText(SignUpActivity.this, "Inregistrare eșuată" +  task.getException().getMessage(), Toast.LENGTH_SHORT).show();

               }
           }
       });
   }
}