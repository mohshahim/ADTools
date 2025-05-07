package com.example.hobbit.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hobbit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewHabits;
    private HabitAdapter adapter;
    private List<Habits> habitList;
    private EditText editTextHabit;

    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getContext().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewHabits = root.findViewById(R.id.recyclerViewHabits);
        editTextHabit = root.findViewById(R.id.editTextHabit);
        Button buttonAddHabit = root.findViewById(R.id.buttonAddHabit);

        habitList = new ArrayList<>();

        // Load JSON habits from assets
        String jsonString = loadJSONFromAsset("habits.json");

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String habitName = obj.getString("name");
                habitList.add(new Habits(habitName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new HabitAdapter(habitList);

        recyclerViewHabits.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewHabits.setAdapter(adapter);

        buttonAddHabit.setOnClickListener(v -> {
            String habitName = editTextHabit.getText().toString().trim();
            if (!habitName.isEmpty()) {
                habitList.add(new Habits(habitName));
                adapter.notifyItemInserted(habitList.size() - 1);
                editTextHabit.setText("");
            }
        });

        return root;
    }
}
