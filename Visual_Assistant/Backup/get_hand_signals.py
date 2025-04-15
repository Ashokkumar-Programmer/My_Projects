import cv2
import mediapipe as mp
import numpy as np
import time

mp_hands = mp.solutions.hands
hands = mp_hands.Hands(static_image_mode=False, max_num_hands=1, min_detection_confidence=0.5)

mp_draw = mp.solutions.drawing_utils

blank_image = np.zeros((720, 1280, 3), np.uint8)
blank_image.fill(255)

results = hands.process(cv2.cvtColor(blank_image, cv2.COLOR_BGR2RGB))

cap = cv2.VideoCapture(0)

counter = 0

while cap.isOpened():
    ret, frame = cap.read()
    if not ret:
        continue

    rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    results = hands.process(rgb_frame)

    if results.multi_hand_landmarks:
        for hand_landmarks in results.multi_hand_landmarks:
            mp_draw.draw_landmarks(blank_image, hand_landmarks, mp_hands.HAND_CONNECTIONS)

            #filename = f'Image_Model/pointer_{counter}.jpg'
            #cv2.imwrite(filename, frame)

            counter += 1
            time.sleep(2)

    cv2.imshow('Hand Tracking', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
