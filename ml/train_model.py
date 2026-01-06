import pandas as pd
import numpy as np
import tensorflow as tf
import re
import os
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

# ==============================
# Configuration
# ==============================
DATASET_FILE = 'Students_Performance_data_set.xlsx'
MODEL_FILE = 'student_performance_model.tflite'

def clean_column_name(col):
    col = col.strip()
    col = re.sub(r'[^\w\s]', '', col)
    col = col.replace(' ', '_').lower()
    return col

def parse_numeric(value):
    if pd.isna(value):
        return np.nan
    val_str = str(value).strip().replace('%', '')
    if '-' in val_str:
        try:
            parts = val_str.split('-')
            return (float(parts[0]) + float(parts[1])) / 2
        except:
            return np.nan
    try:
        return float(val_str)
    except ValueError:
        return np.nan

# ==============================
# Main Execution
# ==============================
if not os.path.exists(DATASET_FILE):
    print(f"❌ Error: {DATASET_FILE} not found in current directory.")
    print("Please place the Excel file in this folder and try again.")
    exit(1)

print("Loading dataset...")
df = pd.read_excel(DATASET_FILE)

# Clean columns
df.columns = [clean_column_name(c) for c in df.columns]

# Features and Target
feature_columns = [
    'how_many_hour_do_you_study_daily',
    'average_attendance_on_class',
    'what_was_your_previous_sgpa',
    'how_many_credit_did_you_have_completed',
    'what_is_your_monthly_family_income'
]
target_column = 'what_is_your_current_cgpa'

print("Preprocessing data...")
for col in feature_columns + [target_column]:
    if col in df.columns:
        df[col] = df[col].apply(parse_numeric)
    else:
        print(f"❌ Column missing: {col}")
        print(f"Available columns: {df.columns.tolist()}")
        exit(1)

# Drop rows with NaN
df.dropna(subset=feature_columns + [target_column], inplace=True)

X = df[feature_columns].values.astype(np.float32)
y = df[target_column].values.astype(np.float32)

# Normalize
scaler = StandardScaler()
X = scaler.fit_transform(X)

print("\n" + "="*50)
print("✅ COPY THESE VALUES TO PredictionHelper.java")
print("="*50)
print(f"MEAN:  {scaler.mean_.tolist()}")
print(f"STD:   {scaler.scale_.tolist()}")
print("="*50 + "\n")

# Split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Model
model = tf.keras.Sequential([
    tf.keras.layers.Dense(32, activation='relu', input_shape=(5,)),
    tf.keras.layers.Dense(16, activation='relu'),
    tf.keras.layers.Dense(1)
])

model.compile(optimizer='adam', loss='mse', metrics=['mae'])

print("Training model...")
model.fit(X_train, y_train, epochs=50, batch_size=16, validation_split=0.1, verbose=1)

# Convert
converter = tf.lite.TFLiteConverter.from_keras_model(model)
tflite_model = converter.convert()

with open(MODEL_FILE, 'wb') as f:
    f.write(tflite_model)

print(f"✅ Model saved as {MODEL_FILE}")