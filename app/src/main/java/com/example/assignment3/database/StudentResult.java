package com.example.assignment3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_results")
public class StudentResult {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public float studyHours;
    public float attendance;
    public float prevSGPA;
    public float creditsCompleted;
    public float familyIncome;
    public float predictedCGPA;
    public String timestamp;

    public StudentResult(float studyHours, float attendance, float prevSGPA, float creditsCompleted, float familyIncome, float predictedCGPA, String timestamp) {
        this.studyHours = studyHours;
        this.attendance = attendance;
        this.prevSGPA = prevSGPA;
        this.creditsCompleted = creditsCompleted;
        this.familyIncome = familyIncome;
        this.predictedCGPA = predictedCGPA;
        this.timestamp = timestamp;
    }
}