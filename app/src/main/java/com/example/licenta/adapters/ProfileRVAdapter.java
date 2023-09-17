package com.example.licenta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenta.R;
import com.example.licenta.classes.ProfileModel;
import com.example.licenta.interfaces.IRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVAdapter.MyViewHolder> {
    private final IRecyclerView recyclerViewInterface;
    Context context;
    List<ProfileModel> profileModels;

    public ProfileRVAdapter(Context context, ArrayList<ProfileModel> profileModels, IRecyclerView recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.profileModels = profileModels;
    }
    @NonNull
    @Override
    //aici inflaye layout
    public ProfileRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new ProfileRVAdapter.MyViewHolder(view, recyclerViewInterface);

    }

    //aici asociem valori view urilor create in recycler view
    //practic schimba valoarea view ului in fct de pozitia la care e
    @Override
    public void onBindViewHolder(@NonNull ProfileRVAdapter.MyViewHolder holder, int position) {
        holder.tvLesson.setText(profileModels.get(position).getLessons());
        holder.imageView.setImageResource(profileModels.get(position).getImage());
    }

    //retine cat de multe date avem
    @Override
    public int getItemCount() {
        return profileModels.size();
    }

    //un fel de onCreate
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvLesson;

        public MyViewHolder(@NonNull View itemView, IRecyclerView recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvLesson = itemView.findViewById(R.id.tvLesson);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
