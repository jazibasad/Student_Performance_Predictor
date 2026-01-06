package com.example.assignment3;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.assignment3.database.AppDatabase;
import com.example.assignment3.database.StudentResult;
import com.example.assignment3.databinding.ActivityHistoryBinding;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        AppDatabase db = AppDatabase.getInstance(this);
        List<StudentResult> results = db.studentDao().getAllResults();
        
        HistoryAdapter adapter = new HistoryAdapter(results);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}