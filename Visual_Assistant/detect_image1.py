import cv2
from cvzone.HandTrackingModule import HandDetector
import time

cap = cv2.VideoCapture(0)

detector = HandDetector(maxHands=1)

offset = 20
image_counter = 12
start_time = time.time()
interval = 2
while True:
    success, img = cap.read()
    hands, img = detector.findHands(img)
    if hands:
        hand = hands[0]
        x, y, w, h = hand['bbox']
        imgCrop = img[y - offset:y + h + offset, x - offset:x + w + offset]

        #print("Dimensions of imgCrop:", imgCrop.shape)

        if imgCrop.shape[0] > 0 and imgCrop.shape[1] > 0:
            cv2.imshow("ImageCrop", imgCrop)

            if time.time() - start_time >= interval:
                cv2.imwrite(f"Detected/Image_{image_counter}.jpg", imgCrop)
                image_counter += 1
                start_time = time.time()

    key = cv2.waitKey(1)

    if key == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
