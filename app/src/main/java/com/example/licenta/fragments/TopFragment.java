package com.example.licenta.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.licenta.R;
import com.example.licenta.activities.ProfileActivity;
import com.example.licenta.adapters.TopRVAdapter;
import com.example.licenta.classes.User;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class TopFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<User> userList;
    TopRVAdapter topRVAdapter;
    FirebaseFirestore db;
    Button btBack;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        recyclerView = view.findViewById(R.id.rvSettings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        db = FirebaseFirestore.getInstance();
        userList = new ArrayList<User>();
        topRVAdapter = new TopRVAdapter(getActivity(), userList);
        recyclerView.setAdapter(topRVAdapter);

        btBack = view.findViewById(R.id.btBack);

        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange dc: value.getDocumentChanges()) {
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                userList.add(dc.getDocument().toObject(User.class));
                                Collections.sort(userList);
                            }

                            topRVAdapter.notifyDataSetChanged();
                        }
                    }
                });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
        return view;
    }
}