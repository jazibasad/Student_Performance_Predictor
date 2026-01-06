# Student Performance Predictor

## 📱 About the App
**Student Performance Predictor** is an offline Android application that uses Machine Learning (TensorFlow Lite) to predict a student's **CGPA** based on their academic and personal habits.

The app uses a **Linear Regression** model trained on student performance data. It takes 5 specific inputs and calculates the expected CGPA instantly on the device, without needing an internet connection.

## ✨ Features
- **Offline Prediction**: Runs the ML model locally on the phone.
- **5 Key Inputs**:
  1. Daily Study Hours
  2. Attendance (%)
  3. Previous SGPA
  4. Credits Completed
  5. Monthly Family Income
- **Local History**: Saves every prediction result to a local database (Room).
- **Clean UI**: Material Design interface with a splash screen and clear input forms.

## 🛠️ Tech Stack
- **Language**: Java
- **IDE**: Android Studio
- **ML Engine**: TensorFlow Lite
- **Database**: Room (SQLite)
- **UI Components**: Material Design 3

## 🚀 Setup Instructions

### Prerequisites
- Android Studio (Koala or newer recommended)
- JDK 17 or higher
- Android SDK API 34+

### 1. Model Configuration (Important)
The app relies on a pre-trained `.tflite` model located in `app/src/main/assets/`.
*   **If you have your own model**: Place your `student_performance_model.tflite` in `app/src/main/assets/`.
*   **Normalization**: The app normalizes inputs (Study Hours, Income, etc.) before feeding them to the model.
    *   If you retrain the model, you **must** update the `MEAN` and `STD` arrays in `PredictionHelper.java`.
    *   Use the provided Python script `ml/get_model_params.py` to calculate these values from your dataset.

### 2. Build and Run
1. Open the project in **Android Studio**.
2. Sync the project with Gradle files.
3. Connect an Android device or start an Emulator.
4. Click **Run** (▶).

## 📂 Project Structure
- **`app/src/main/java`**: Contains the Android logic.
  - `MainActivity.java`: Handles UI interactions.
  - `PredictionHelper.java`: Loads the TFLite model and runs inference.
  - `database/`: Room database setup.
- **`app/src/main/assets`**: Stores the `student_performance_model.tflite` file.
- **`ml/`**: Contains Python scripts for training and data analysis.
  - `train_model.py`: Script to train the model locally.
  - `get_model_params.py`: Script to extract Mean/Std Dev for the Android app.

## 📊 Dataset
The model was trained using the `Students_Performance_data_set.xlsx` containing attributes like study hours, attendance, and family income.

---
*Developed for Assignment 3*
