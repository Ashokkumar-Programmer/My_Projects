from bs4 import BeautifulSoup
import requests
import urllib.parse
import re
import subprocess
import pyttsx3
import pygame
import asyncio
import websockets
import webbrowser
import cv2
import os
from spellchecker import SpellChecker
from cvzone.HandTrackingModule import HandDetector
import time

#This function is used to play music

def play_music(file_path):
    pygame.init()
    pygame.mixer.music.load(file_path)
    pygame.mixer.music.play()
    pygame.quit()

#This function is used to open the .mp4 file in connected android device

def open_file(file_path):
    encoded_file_path = urllib.parse.quote(file_path)
    adb_command = ['A:\\Competition\\Visual_Assistant\\ADB\\adb.exe', 'shell', 'am', 'start', '-a', 'android.intent.action.VIEW', '-d', 'file://' + encoded_file_path]
    subprocess.run(adb_command)

#This function is used to convert given text to audio

engine = pyttsx3.init()

def text_to_speech(text):
    engine.setProperty('rate', 130)
    engine.say(text)
    engine.runAndWait()
    open_file("/storage/emulated/0/Movies/inshot/waiting_long.mp4")


#This function is used to get whole HTML code of given query

def fetch_search_results(query):
    url = f"https://www.google.com/search?q={query}"
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3'}
    response = requests.get(url, headers=headers)
    soup = BeautifulSoup(response.text, 'html.parser')
    return soup

#This function is used to process whole HTML code and get the exact answer

def extract_results(soup, query):
    try:
        if "what about" in query or "tell about" in query or "about" in query:
            search_results = soup.find('div', class_='BNeawe s3v9rd AP7Wnd')
            #print("1st")
            return search_results.getText()   
        
        elif "population" in query or "about" in query: 
            search_results = soup.find('span', class_='FCUp0c rQMQod')
            #print("2nd")
            return search_results.getText()
        
        elif "population" in query:
            #print("3rd")
            content_list = []
            for div in search_results_page.find_all('div'):
                content_list.append(div.get_text(strip=True))

            for span in search_results_page.find_all('span'):
                content_list.append(span.get_text(strip=True))

            for p in search_results_page.find_all('p'):
                content_list.append(p.get_text(strip=True))

            largest_country = []
            for i in content_list:
                pattern = r'(\d+)\s*([A-Za-z]+)\s*(\d+,?\d*,?\d*,?\d*)'
                matches = re.findall(pattern, i)
                countries = [(match[1], match[2]) for match in matches]
                try:
                    largest_country = max(countries, key=lambda x: int(''.join(x[1].split(','))))
                    result = "The country with the largest population is "+ largest_country[1]+ " in "+ largest_country[0]
                    return result
                except:
                    largest_country = []
        
        #general questions
        search_results = soup.find('div', class_='BNeawe iBp4i AP7Wnd')
        return search_results.get_text()
    except:
        print("Couldn't process the command")

#Detect the hand sign
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

#Check the spelling
def correct_spelling(incorrect_word):
    spell = SpellChecker()
    return spell.correction(incorrect_word)

#remove files
def remove_files_in_folder(folder_path):
    file_list = os.listdir(folder_path)
    for file_name in file_list:
        file_path = os.path.join(folder_path, file_name)
        if os.path.isfile(file_path):
            os.remove(file_path)

global search_results_page
status = "speech"
#This function is used to get the value from website and process it
async def handle(websocket, path):
    transcript = await websocket.recv()
    query = transcript
    if query=="None":
        open_file("/storage/emulated/0/Movies/inshot/angry.mp4")
        text_to_speech("Wrong Command. Say the sentence correctly")
    elif "your age" in query:
        open_file("/storage/emulated/0/Movies/inshot/explain_long.mp4")
        text_to_speech("I like a child baby. My age is approximately 1 day. Thanking for asking my age.")
    elif "your name" in query:
        open_file("/storage/emulated/0/Movies/inshot/general_talk_long.mp4")
        text_to_speech("My name is AK. Thanks for asking my name")
    elif "yourself" in query:
        open_file("/storage/emulated/0/Movies/inshot/general_talk_long.mp4")
        text_to_speech("I'm AK. I am developed for helping people day to day life and I make people happy. I am just a model of Visual Assistant. Thank you for asking about me")
    elif "music" in query:
        open_file("/storage/emulated/0/Movies/inshot/play_music_long.mp4")
        play_music("music.mp3")
    elif "bye" in query or "Goodbye" in query or "boy" in query or "goodboy" in query:
        open_file("/storage/emulated/0/Movies/inshot/bye.mp4")
        text_to_speech("Thank you bye. Next time we meet")
        exit(0)
    elif "can't able to talk" in query or "talk" in query or "able to" in query:
        status = "speechless"
        folder_path = "Detected/"
        remove_files_in_folder(folder_path)
        cap = cv2.VideoCapture(0)

        detector = HandDetector(maxHands=1)

        offset = 20
        image_counter = 1
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
        meaningful_word = ["age", "name", "your", "music", "bye"]
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
            print(dict1)
            #print(f"{detected_image_name}  -  {min(dict1, key=dict1.get)}")
            result_set.append(min(dict1, key=dict1.get))
        word = ''.join(result_set)
        spell = SpellChecker()
        query = "age"
        meaningful_words = []
        for i in range(2, len(word) + 1):
            for j in range(len(word) - i + 1):
                substring = word[j:j + i]
                corrected_substring = spell.correction(substring)
                if corrected_substring == substring:
                    meaningful_words.append(substring)
        meaningful_words = list(set(meaningful_words))
        if query=="None":
            open_file("/storage/emulated/0/Movies/inshot/angry.mp4")
            text_to_speech("Wrong Command. Say the sentence correctly.  In hardware mode there will be a button for speechless person.")
        elif "your age" in query or "age" in query:
            open_file("/storage/emulated/0/Movies/inshot/explain_long.mp4")
            text_to_speech("I like a child baby. My age is approximately 2 months. Thanking for asking my age. In hardware mode there will be a button for speechless person.")
        elif "your name" in query or "name" in query:
            open_file("/storage/emulated/0/Movies/inshot/general_talk_long.mp4")
            text_to_speech("My name is AK. Thanks for asking my name.  In hardware mode there will be a button for speechless person.")
        elif "yourself" in query or "your" in query:
            open_file("/storage/emulated/0/Movies/inshot/general_talk_long.mp4")
            text_to_speech("I'm AK. I am developed for helping people day to day life and I make people happy. I am just a model of Visual Assistant. Thank you for asking about me.  In hardware mode there will be a button for speechless person.")
        elif "music" in query:
            open_file("/storage/emulated/0/Movies/inshot/play_music_long.mp4")
            play_music("music.mp3")
        elif "bye" in query or "Goodbye" in query or "boy" in query or "goodboy" in query:
            open_file("/storage/emulated/0/Movies/inshot/bye.mp4")
            text_to_speech("Thank you bye. Next time we meet.  In hardware mode there will be a button for speechless person.")
    else:
        search_results_page = fetch_search_results(query)
        results = extract_results(search_results_page, query.lower())
        print(results)
        open_file("/storage/emulated/0/Movies/inshot/explain_long.mp4")
        text_to_speech(results)


#This function is used to initiate the process
def user_speech():
    webbrowser.open("file:///A:/Competition/Visual_Assistant/Get_Voice.html")
    open_file("/storage/emulated/0/Movies/inshot/waiting_long.mp4")
    print("Listening....")
    start_server = websockets.serve(handle, "localhost", 8000)
    asyncio.get_event_loop().run_until_complete(start_server)
    asyncio.get_event_loop().run_forever()


#Implementing the process
while(True):
    user_speech()
    '''if query=="None":
        open_file("/storage/emulated/0/Movies/inshot/angry.mp4")
        text_to_speech("Wrong Command. Say the sentence correctly")
    elif "your age" in query:
        open_file("/storage/emulated/0/Movies/inshot/explain_long.mp4")
        text_to_speech("I like a child baby. My age is approximately 1 day. Thanking for asking my age.")
    elif "your name" in query:
        open_file("/storage/emulated/0/Movies/inshot/general_talk_long.mp4")
        text_to_speech("My name is AK. Thanks for asking my name")
    elif "yourself" in query:
        open_file("/storage/emulated/0/Movies/inshot/general_talk_long.mp4")
        text_to_speech("I'm AK. I am developed for helping people day to day life and I make people happy. I am just a model of Visual Assistant. Thank you for asking about me")
    elif "music" in query:
        open_file("/storage/emulated/0/Movies/inshot/play_music_long.mp4")
        play_music("music.mp3")
    elif "bye" in query or "Goodbye" in query or "boy" in query or "goodboy" in query:
        open_file("/storage/emulated/0/Movies/inshot/bye.mp4")
        text_to_speech("Thank you bye. Next time we meet")
        exit(0)
    else:
        search_results_page = fetch_search_results(query)
        results = extract_results(search_results_page, query.lower())
        print(results)
        open_file("/storage/emulated/0/Movies/inshot/explain_long.mp4")
        text_to_speech(results)'''