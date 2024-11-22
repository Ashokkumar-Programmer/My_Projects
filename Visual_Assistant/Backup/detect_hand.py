import cv2
import mediapipe as mp
import time
import os

folder_path = "Detected/"
file_list = os.listdir(folder_path)

for file_name in file_list:
    file_path = os.path.join(folder_path, file_name)
    os.remove(file_path)

mp_hands = mp.solutions.hands
hands = mp_hands.Hands(static_image_mode=False, max_num_hands=1, min_detection_confidence=0.5)
mp_draw = mp.solutions.drawing_utils

cap = cv2.VideoCapture(0)

Image_Counter = 1
visualization_interval = 5  # Display visualization every 5 frames
frame_count = 0

while cap.isOpened():
    ret, frame = cap.read()
    if not ret:
        continue

    frame_count += 1
    if frame_count % visualization_interval == 0:
        rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        results = hands.process(rgb_frame)
        if results.multi_hand_landmarks:
            for hand_landmarks in results.multi_hand_landmarks:
                mp_draw.draw_landmarks(frame, hand_landmarks, mp_hands.HAND_CONNECTIONS)
                print("Ready to detect")
                f = open(f'Detected/Detect_Image_{Image_Counter}.txt', "w")
                f.write(str(hand_landmarks))
                f.close()
                cv2.imwrite(f'Detect_Image_{Image_Counter}.jpg', frame)
                Image_Counter += 1


    cv2.imshow('Hand Tracking', frame)

    key = cv2.waitKey(1)

    if key == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
