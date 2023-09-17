package com.example.licenta.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.licenta.R;
import com.example.licenta.activities.LessonTypeActivity;
import com.example.licenta.activities.TeachingFormActivity;
import com.example.licenta.adapters.ProfileRVAdapter;
import com.example.licenta.classes.ProfileModel;
import com.example.licenta.interfaces.IRecyclerView;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements IRecyclerView {

    ArrayList<ProfileModel> profileModels = new ArrayList<>();
    int[] icons = {R.drawable.books_adobe_express, R.drawable.happy_woman_study_free_vector_adobe_express};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        setProfilemodels();

        ProfileRVAdapter adapter = new ProfileRVAdapter(getActivity(), profileModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;

    }

    private void setProfilemodels() {
         String[] controls = getResources().getStringArray(R.array.profile_conrols);

         for(int i = 0; i<controls.length;i++) {
             profileModels.add(new ProfileModel(controls[i], icons[i]));
         }
    }

    @Override
    public void onItemClick(int position) {
        if(position == 0)
            startActivity(new Intent(getActivity(), LessonTypeActivity.class));

        if(position == 1)
            startActivity(new Intent(getActivity(), TeachingFormActivity.class));
    }
}