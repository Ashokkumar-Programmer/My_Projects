import cv2
import mediapipe as mp
import numpy as np

mp_hands = mp.solutions.hands
hands = mp_hands.Hands(static_image_mode=False, max_num_hands=1, min_detection_confidence=0.5)

mp_draw = mp.solutions.drawing_utils

cap = cv2.VideoCapture(0)

Image_Counter = 1

letter = input("Enter the alphabet to train: ")

while cap.isOpened():
    ret, frame = cap.read()
    if not ret:
        continue

    rgb_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    results = hands.process(rgb_frame)

    if results.multi_hand_landmarks:
        for hand_landmarks in results.multi_hand_landmarks:
            mp_draw.draw_landmarks(frame, hand_landmarks, mp_hands.HAND_CONNECTIONS)

    cv2.imshow('Hand Tracking', frame)

    key = cv2.waitKey(1)
    if key == ord('s'):
        blank_image = np.zeros((720, 1280, 3), np.uint8)
        blank_image.fill(255)

        mp_draw.draw_landmarks(blank_image, hand_landmarks, mp_hands.HAND_CONNECTIONS)

        f = open(f'Model/{letter.upper()}/{letter.upper()}_{Image_Counter}.txt',"w")
        f.write(str(hand_landmarks))
        f.close()

        #cv2.imwrite(f'Image_Model/A_{Image_Counter}.jpg', blank_image)

        Image_Counter+=1

    if key == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
