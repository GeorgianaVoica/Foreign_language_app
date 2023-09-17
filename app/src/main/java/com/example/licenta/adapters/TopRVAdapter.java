package com.example.licenta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.classes.User;

import java.util.ArrayList;

public class TopRVAdapter extends RecyclerView.Adapter<TopRVAdapter.AViewHolder> {
    Context context;
    ArrayList<User> users;
    String s;

    public TopRVAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public TopRVAdapter.AViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_top, parent, false);
        return new AViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRVAdapter.AViewHolder holder, int position) {
        User user = users.get(position);
        holder.nume.setText(user.getName());
        holder.punctaj.setText(String.valueOf(user.getNoPoints()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class AViewHolder extends RecyclerView.ViewHolder{

        TextView nume, punctaj;

        public AViewHolder(@NonNull View itemView) {
            super(itemView);
            nume = itemView.findViewById(R.id.tvnumeUser);
            punctaj = itemView.findViewById(R.id.tvPunctajUser);
        }
    }
}
