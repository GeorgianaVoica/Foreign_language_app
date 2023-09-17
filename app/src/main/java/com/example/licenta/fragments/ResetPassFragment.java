package com.example.licenta.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.licenta.R;
import com.example.licenta.activities.ProfileActivity;
import com.example.licenta.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ResetPassFragment extends Fragment {

    EditText etResetPass;
    Button btReset;
    FirebaseFirestore db;
    DocumentReference userRef;
    FirebaseAuth auth;
    User user;
    String s;

    public ResetPassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reset_pass, container, false);
        etResetPass = view.findViewById(R.id.reset_pass);
        btReset = view.findViewById(R.id.btReset);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userRef = db.collection("users").document(auth.getCurrentUser().getUid());

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
            }
        });



        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), user.getPassword());
                FirebaseUser utilizator = FirebaseAuth.getInstance().getCurrentUser();
                s = String.valueOf(etResetPass.getText());
                utilizator.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    utilizator.updatePassword(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @SuppressLint("LongLogTag")
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("SchimbareParolaAutorizat", "Password updated");
                                            } else {
                                                Log.d("SchimbareParolaAutorizat", "Error password not updated");
                                            }
                                        }
                                    });
                                    userRef.update("password", s);
                                } else {
                                    Log.d("SchimbareParolaAutorizat", "Error auth failed");
                                }
                            }
                           });
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }

        });

        return view;
    }
}