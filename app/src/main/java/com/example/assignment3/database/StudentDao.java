package com.example.assignment3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insert(StudentResult result);

    @Query("SELECT * FROM student_results ORDER BY id DESC")
    List<StudentResult> getAllResults();
}