package com.example.licenta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.classes.TutorForm;
import com.example.licenta.classes.User;

import java.util.ArrayList;

public class FormRVAdapter extends RecyclerView.Adapter<FormRVAdapter.MyViewHolder> {
    Context context;
    ArrayList<TutorForm> formList;

    public FormRVAdapter(Context context, ArrayList<TutorForm> formList) {
        this.context = context;
        this.formList = formList;
    }

    @NonNull
    @Override
    public FormRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.offer, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FormRVAdapter.MyViewHolder holder, int position) {
        TutorForm form = formList.get(position);
        holder.descriere.setText(form.getDescriere());
        holder.durata.setText(form.getDurata());
        holder.pret.setText(form.getPret());
        holder.contact.setText(form.getContact());
    }

    @Override
    public int getItemCount() {
        return formList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView descriere, durata, pret, contact;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            descriere= itemView.findViewById(R.id.descriere);
            durata = itemView.findViewById(R.id.durata);
            pret = itemView.findViewById(R.id.pret);
            contact = itemView.findViewById(R.id.contact);

        }
    }
}
