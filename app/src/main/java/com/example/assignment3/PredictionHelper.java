package com.example.assignment3;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PredictionHelper {
    private Interpreter interpreter;
    private static final String MODEL_FILE = "student_performance_model.tflite";

    // IMPORTANT: These values must match the normalization parameters used during training.
    // Updated with values from get_model_params.py
    private float[] MEAN = { 3.1315f, 88.2580f, 2.6839f, 71.8425f, 62488.1189f };
    private float[] STD  = { 1.6489f, 15.3079f, 0.8712f, 48.0542f, 76141.6529f };

    public PredictionHelper(Context context) throws IOException {
        interpreter = new Interpreter(loadModelFile(context));
    }

    private MappedByteBuffer loadModelFile(Context context) throws IOException {
        AssetFileDescriptor fileDescriptor = context.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public float predict(float studyHours, float attendance, float prevSGPA, float credits, float income) {
        // 1. Prepare Input Array
        float[] rawInput = {studyHours, attendance, prevSGPA, credits, income};
        
        // 2. Normalize Input (StandardScaler: (x - u) / s)
        float[][] input = new float[1][5];
        for (int i = 0; i < 5; i++) {
            if (STD[i] != 0) {
                input[0][i] = (rawInput[i] - MEAN[i]) / STD[i];
            } else {
                input[0][i] = rawInput[i];
            }
        }

        // 3. Run Inference
        float[][] output = new float[1][1];
        interpreter.run(input, output);

        // 4. Return Predicted CGPA
        return output[0][0];
    }

    public void close() {
        if (interpreter != null) {
            interpreter.close();
        }
    }
}