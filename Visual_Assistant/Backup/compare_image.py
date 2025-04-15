import os
import math

def list_files_in_folder(folder_path):
    file_names = []
    for file_name in os.listdir(folder_path):
        if os.path.isfile(os.path.join(folder_path, file_name)):
            file_names.append(file_name)
    return file_names

def convert_string_to_landmarks(landmarks_str):
    lines = landmarks_str.strip().split('\n')
    landmark_dict = {'x': [], 'y': [], 'z': []}
    for line in lines:
        if "x" in line:
            landmark_dict['x'].append(float(line.split(': ')[1]))
        elif "y" in line:
            landmark_dict['y'].append(float(line.split(': ')[1]))
        elif "z" in line:
            landmark_dict['z'].append(float(line.split(': ')[1]))
    return landmark_dict

def calculate_accuracy_for_landmarks(landmarks1, landmarks2):
    if len(landmarks1['x']) != len(landmarks2['x']) or \
       len(landmarks1['y']) != len(landmarks2['y']) or \
       len(landmarks1['z']) != len(landmarks2['z']):
        return "Error: Number of landmarks in both sets is not the same"
    distances = []
    for i in range(len(landmarks1['x'])):
        dist = math.sqrt((landmarks1['x'][i] - landmarks2['x'][i])**2 +
                         (landmarks1['y'][i] - landmarks2['y'][i])**2 +
                         (landmarks1['z'][i] - landmarks2['z'][i])**2)
        distances.append(dist)
    avg_distance = sum(distances) / len(distances)
    accuracy = 1 / (1 + avg_distance)
    return accuracy

Temp_Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
Alphabet1 = [*Temp_Alphabet]

detected_folder = "Detected/"
model_folder = "Model/"

# Dictionary to store dynamic thresholds for each alphabet
thresholds = {
    'A': 0.7,
    'B': 0.7,
    'C': 0.7,
    'D': 0.7,
    'E': 0.7,
    'F': 0.7,
    'G': 0.7,
    'H': 0.7,
    'I': 0.7,
    'J': 0.7,
    'K': 0.7,
    'L': 0.7,
    'M': 0.7,
    'N': 0.7,
    'O': 0.7,
    'P': 0.7,
    'Q': 0.7,
    'R': 0.7,
    'S': 0.7,
    'T': 0.7,
    'U': 0.7,
    'V': 0.7,
    'W': 0.7,
    'X': 0.7,
    'Y': 0.7,
    'Z': 0.7,
}

Alphabet_Dict = {}

for alphabet in Alphabet1:
    model_files = list_files_in_folder(f"{model_folder}{alphabet}/")
    for detected_file in list_files_in_folder(detected_folder):
        with open(f"{detected_folder}{detected_file}") as f:
            detected_landmarks_str = f.read()
        detected_landmarks = convert_string_to_landmarks(detected_landmarks_str)
        match_found = False  # Flag to track if a match is found
        for model_file in model_files:
            with open(f"{model_folder}{alphabet}/{model_file}") as f:
                model_landmarks_str = f.read()
            model_landmarks = convert_string_to_landmarks(model_landmarks_str)
            accuracy = calculate_accuracy_for_landmarks(detected_landmarks, model_landmarks)
            dynamic_threshold = thresholds.get(alphabet, 0.5)  # Default threshold if not specified
            if accuracy >= dynamic_threshold:
                Alphabet_Dict.setdefault(alphabet, []).append(accuracy)
                match_found = True
                break  # Exit the loop once a match is found
        if match_found:
            break  # Exit the loop over detected files if a match is found

for alphabet, accuracies in Alphabet_Dict.items():
    print(f"Alphabet: {alphabet}")
    print("Average Accuracy:", float(sum(accuracies) / len(accuracies)))
