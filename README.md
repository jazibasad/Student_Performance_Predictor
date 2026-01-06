# Student Performance Predictor

## Setup Instructions

1. **Generate the TensorFlow Lite Model:**
   - Ensure you have Python installed with `tensorflow`, `pandas`, and `scikit-learn`.
   - Run the training script located in the `ml` folder:
     ```bash
     cd ml
     python train_model.py
     ```
   - This will generate a file named `student_performance_model.tflite`.

2. **Move Model to Assets:**
   - Copy the generated `student_performance_model.tflite` file into the Android app's assets folder:
     `app/src/main/assets/`

3. **Build and Run:**
   - Open the project in Android Studio.
   - Sync Gradle.
   - Run the app on an emulator or device.

## Project Structure
- **app/src/main/java**: Java source code.
- **app/src/main/res**: Layouts, strings, and colors.
- **app/src/main/assets**: Location for the TFLite model.
- **ml/**: Python scripts and dataset for model training.
