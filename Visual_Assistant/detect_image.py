import cv2
import os

alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

def list_files_in_folder(folder_path):
    file_paths = []
    for file_name in os.listdir(folder_path):
        file_path = os.path.join(folder_path, file_name)
        if os.path.isfile(file_path):
            file_paths.append(file_path)
    return file_paths

def mse(image1_path, image2_path):
    image1 = cv2.imread(image1_path)
    image2 = cv2.imread(image2_path)

    if image1 is None or image2 is None:
        print("Error: Unable to load one or both images.")
        return None

    image1 = cv2.resize(image1, (min(image1.shape[1], image2.shape[1]), min(image1.shape[0], image2.shape[0])))
    image2 = cv2.resize(image2, (min(image1.shape[1], image2.shape[1]), min(image1.shape[0], image2.shape[0])))

    mse_value = ((image1 - image2) ** 2).mean()
    return mse_value

detected_path = "Detected/"
result_set = []
for detected_image_name in os.listdir(detected_path):
    detected_image_path = os.path.join(detected_path, detected_image_name)
    dict1 = {}
    for alphabet_char in alphabet:
        model_path = os.path.join("Model", alphabet_char)
        temp = []
        for model_image_name in os.listdir(model_path):
            model_image_path = os.path.join(model_path, model_image_name)
            mse_value = mse(detected_image_path, model_image_path)
            if mse_value is not None:
                temp.append(round(mse_value,1))
        dict1[alphabet_char] = min(temp)
    #print(f"{detected_image_name}  -  {min(dict1, key=dict1.get)}")
    result_set.append(min(dict1, key=dict1.get))
print(result_set)
cv2.destroyAllWindows()
