package com.example.assignment3;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.assignment3.database.StudentResult;
import com.example.assignment3.databinding.ItemHistoryBinding;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<StudentResult> results;

    public HistoryAdapter(List<StudentResult> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentResult result = results.get(position);
        holder.bind(result);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemHistoryBinding binding;

        public ViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StudentResult result) {
            binding.tvDate.setText(result.timestamp);
            binding.tvScore.setText(String.format(Locale.getDefault(), "CGPA: %.2f", result.predictedCGPA));
            
            String details = String.format(Locale.getDefault(), 
                "Hours: %.1f | SGPA: %.2f\nAtt: %.0f%% | Credits: %.0f | Income: %.0f",
                result.studyHours, 
                result.prevSGPA,
                result.attendance,
                result.creditsCompleted,
                result.familyIncome);
                
            binding.tvDetails.setText(details);
        }
    }
}