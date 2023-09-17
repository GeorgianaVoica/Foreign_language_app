package com.example.licenta.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.licenta.R;
import com.example.licenta.adapters.ProfileRVAdapter;
import com.example.licenta.classes.ProfileModel;
import com.example.licenta.interfaces.IRecyclerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LessonTypeActivity extends AppCompatActivity implements IRecyclerView {

    Toolbar toolbar;
    ArrayList<ProfileModel> profileModels = new ArrayList<>();
    int[] icons = {R.drawable.german_courses, R.drawable.teacher};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_type);

        toolbar = findViewById(R.id.toolbarLessonType);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        setProfilemodels();
        ProfileRVAdapter adapter = new ProfileRVAdapter(this, profileModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void setProfilemodels() {
        String[] controls = getResources().getStringArray(R.array.lesson_type);

        for(int i = 0; i<controls.length;i++) {
            profileModels.add(new ProfileModel(controls[i], icons[i]));
        }
    }
    @Override
    public void onItemClick(int position) {
        if(position == 0)
            startActivity(new Intent(this, LevelActivity.class));

        if(position == 1)
            startActivity(new Intent(this, SeeOffersActivity.class));
    }
}