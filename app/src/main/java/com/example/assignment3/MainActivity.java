package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment3.database.AppDatabase;
import com.example.assignment3.database.StudentResult;
import com.example.assignment3.databinding.ActivityMainBinding;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PredictionHelper predictionHelper;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Database
        database = AppDatabase.getInstance(this);

        // Initialize TFLite Helper
        try {
            predictionHelper = new PredictionHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading model. Make sure student_performance_model.tflite is in assets.", Toast.LENGTH_LONG).show();
        }

        setupPredictButton();
    }

    private void setupPredictButton() {
        binding.btnPredict.setOnClickListener(v -> {
            if (validateInputs()) {
                predictScore();
            }
        });
    }

    private boolean validateInputs() {
        if (binding.etStudyHours.getText().toString().isEmpty() ||
            binding.etAttendance.getText().toString().isEmpty() ||
            binding.etPrevSGPA.getText().toString().isEmpty() ||
            binding.etCredits.getText().toString().isEmpty() ||
            binding.etIncome.getText().toString().isEmpty()) {
            
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void predictScore() {
        try {
            float studyHours = Float.parseFloat(binding.etStudyHours.getText().toString());
            float attendance = Float.parseFloat(binding.etAttendance.getText().toString());
            float prevSGPA = Float.parseFloat(binding.etPrevSGPA.getText().toString());
            float credits = Float.parseFloat(binding.etCredits.getText().toString());
            float income = Float.parseFloat(binding.etIncome.getText().toString());

            if (predictionHelper != null) {
                float prediction = predictionHelper.predict(studyHours, attendance, prevSGPA, credits, income);
                displayResult(prediction);
                saveToDatabase(studyHours, attendance, prevSGPA, credits, income, prediction);
            } else {
                 Toast.makeText(this, "Model not loaded", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input format", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayResult(float prediction) {
        binding.cardResult.setVisibility(View.VISIBLE);
        binding.tvResult.setText(String.format(Locale.getDefault(), "%.2f", prediction));
    }

    private void saveToDatabase(float studyHours, float attendance, float prevSGPA, float credits, float income, float pred) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        StudentResult result = new StudentResult(studyHours, attendance, prevSGPA, credits, income, pred, timestamp);
        database.studentDao().insert(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (predictionHelper != null) {
            predictionHelper.close();
        }
    }
}