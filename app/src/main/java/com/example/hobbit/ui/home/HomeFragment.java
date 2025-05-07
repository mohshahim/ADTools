package com.example.hobbit.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hobbit.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewHabits;
    private HabitAdapter adapter;
    private List<Habits> habitList;
    private EditText editTextHabit;

    private FirebaseFirestore db;
    private CollectionReference habitsRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewHabits = root.findViewById(R.id.recyclerViewHabits);
        editTextHabit = root.findViewById(R.id.editTextHabit);
        Button buttonAddHabit = root.findViewById(R.id.buttonAddHabit);

        db = FirebaseFirestore.getInstance();
        habitsRef = db.collection("habits");

        habitList = new ArrayList<>();
        adapter = new HabitAdapter(habitList);

        recyclerViewHabits.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewHabits.setAdapter(adapter);

        // Real-time listener
        habitsRef.addSnapshotListener((value, error) -> {
            if (error != null || value == null) return;

            habitList.clear();
            for (QueryDocumentSnapshot doc : value) {
                String name = doc.getString("name");
                if (name != null) {
                    habitList.add(new Habits(name));
                }
            }
            adapter.notifyDataSetChanged();
        });

        buttonAddHabit.setOnClickListener(v -> {
            String habitName = editTextHabit.getText().toString().trim();
            if (!habitName.isEmpty()) {
                Habits newHabit = new Habits(habitName);

                habitsRef.add(newHabit)
                        .addOnSuccessListener(documentReference -> {
                            editTextHabit.setText("");
                            Toast.makeText(getContext(), "Habit added to Firestore", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Failed to add: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            } else {
                Toast.makeText(getContext(), "Please enter a habit", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
