package com.example.hobbit.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hobbit.R;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private List<Habits> habitList;

    public HabitAdapter(List<Habits> habitList) {
        this.habitList = habitList;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        holder.textView.setText(habitList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewHabitName);

        }
    }
}
