import pandas as pd
import numpy as np
import re
import os

# ==============================
# CONFIGURATION
# ==============================
DATASET_FILE = 'Students_Performance_data_set.xlsx'

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

if not os.path.exists(DATASET_FILE):
    print(f"❌ Error: {DATASET_FILE} not found.")
    print("Please download the dataset (xlsx) and place it in this folder.")
    exit(1)

print("Reading dataset...")
df = pd.read_excel(DATASET_FILE)
df.columns = [clean_column_name(c) for c in df.columns]

feature_columns = [
    'how_many_hour_do_you_study_daily',
    'average_attendance_on_class',
    'what_was_your_previous_sgpa',
    'how_many_credit_did_you_have_completed',
    'what_is_your_monthly_family_income'
]

# Clean Data
for col in feature_columns:
    df[col] = df[col].apply(parse_numeric)
df.dropna(subset=feature_columns, inplace=True)

# Calculate Stats
data = df[feature_columns].values
mean = np.mean(data, axis=0)
std = np.std(data, axis=0)

print("\n" + "="*60)
print("✅ COPY AND PASTE THESE LINES INTO PredictionHelper.java")
print("="*60)
print(f"private float[] MEAN = {{ {mean[0]:.4f}f, {mean[1]:.4f}f, {mean[2]:.4f}f, {mean[3]:.4f}f, {mean[4]:.4f}f }};")
print(f"private float[] STD  = {{ {std[0]:.4f}f, {std[1]:.4f}f, {std[2]:.4f}f, {std[3]:.4f}f, {std[4]:.4f}f }};")
print("="*60 + "\n")
