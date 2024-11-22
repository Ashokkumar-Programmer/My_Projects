import cv2
import mediapipe as mp
import keyboard
import time
import pygame
import random

pygame.init()

WIDTH, HEIGHT = 1200, 720
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Snake Game and Hand Tracking")

BLACK = (0, 0, 0)
GREEN = (0, 255, 0)
RED = (255, 0, 0)

cell_size = 20
snake = [(100, 100)]
snake_direction = (1, 0)
food = (200, 200)
score = 0
clock = pygame.time.Clock()

def generate_food():
    return (random.randint(0, (800 // 2 - cell_size) // cell_size) * cell_size,
            random.randint(0, (HEIGHT - cell_size) // cell_size) * cell_size)

snake = [(800 // 4, HEIGHT // 2)]
food = generate_food()

def move_snake():
    global snake_direction, food
    head_x, head_y = snake[0]

    if snake_direction in [(1, 0), (-1, 0)]:
        if action == "Up":
            snake_direction = (0, -1)
        elif action == "Down":
            snake_direction = (0, 1)
    elif snake_direction in [(0, 1), (0, -1)]:
        if action == "Left":
            snake_direction = (-1, 0)
        elif action == "Right":
            snake_direction = (1, 0)

    new_head = (head_x + snake_direction[0] * cell_size, head_y + snake_direction[1] * cell_size)

    new_head_x = new_head[0] % WIDTH
    new_head_y = new_head[1] % HEIGHT

    new_head = (new_head_x, new_head_y)

    if new_head in snake[1:]:
        pygame.quit()
    snake.insert(0, new_head)

    if new_head == food:
        global score
        score += 1
        food = generate_food()
    else:
        snake.pop()

draw = mp.solutions.drawing_utils
hand = mp.solutions.hands

cap = cv2.VideoCapture(0)

UP = 0.2
DOWN = 0.8
LEFT = 0.2
RIGHT = 0.8
ACTION_DELAY = 0.5

cv2.namedWindow('Snake Game', cv2.WINDOW_NORMAL)
cv2.resizeWindow('Snake Game', 800, 720)

def get_hand_position(landmarks):
    x_coords = [landmark.x for landmark in landmarks.landmark]
    y_coords = [landmark.y for landmark in landmarks.landmark]
    mean_x = sum(x_coords) / len(x_coords)
    mean_y = sum(y_coords) / len(y_coords)
    return mean_x, mean_y

def determine_action(hand_x, hand_y):
    if hand_x < LEFT:
        return "Right"
    elif hand_x > RIGHT:
        return "Left"
    elif hand_y < UP:
        return "Up"
    elif hand_y > DOWN:
        return "Down"
    else:
        return "No action"

last_action_time = time.time()

SNAKE_SCREEN_WIDTH = WIDTH // 2
SNAKE_SCREEN_HEIGHT = HEIGHT
HAND_SCREEN_WIDTH = WIDTH // 2
HAND_SCREEN_HEIGHT = HEIGHT

snake_screen = pygame.Surface((SNAKE_SCREEN_WIDTH, SNAKE_SCREEN_HEIGHT))
hand_screen = pygame.Surface((HAND_SCREEN_WIDTH, HAND_SCREEN_HEIGHT))

with hand.Hands(min_detection_confidence=0.2, min_tracking_confidence=0.5) as hands:
    while cap.isOpened():
        ret, frame = cap.read()
        if not ret:
            continue
        frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        results = hands.process(frame)
        action = "No action"
        current_time = time.time()

        if results.multi_hand_landmarks:
            for landmarks in results.multi_hand_landmarks:
                X, Y = get_hand_position(landmarks)
                action = determine_action(X, Y)
                draw.draw_landmarks(frame, landmarks, hand.HAND_CONNECTIONS)
                cv2.putText(frame, f"Action: {action}", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)

                if action != "No action" and current_time - last_action_time > ACTION_DELAY:
                    last_action_time = current_time
                    if action == "Up":
                        snake_direction = (-1, 0)
                    elif action == "Down":
                        snake_direction = (1, 0)
                    elif action == "Left":
                        snake_direction = (0, -1)
                    elif action == "Right":
                        snake_direction = (0, 1)

        snake_screen = pygame.Surface((WIDTH // 2, HEIGHT))
        hand_screen = pygame.Surface((WIDTH // 2, HEIGHT))

        move_snake()

        snake = [(x % 800, y % HEIGHT) for x, y in snake]
        food = (food[0] % 800, food[1] % HEIGHT)

        snake_screen.fill(BLACK)
        hand_screen.fill(BLACK)

        move_snake()

        for segment in snake:
            pygame.draw.rect(snake_screen, GREEN, (segment[0], segment[1], cell_size, cell_size))
        pygame.draw.rect(snake_screen, RED, (food[0], food[1], cell_size, cell_size))
        screen.blit(snake_screen, (0, 0))

        rotated_frame = cv2.rotate(frame, cv2.ROTATE_90_COUNTERCLOCKWISE)
        hand_screen.blit(pygame.surfarray.make_surface(cv2.cvtColor(rotated_frame, cv2.COLOR_RGB2BGR)), (0, 0))
        screen.blit(hand_screen, (SNAKE_SCREEN_WIDTH, 0))

        pygame.draw.rect(screen, BLACK, (10, 10, 200, 40))
        pygame.font.init()
        font = pygame.font.SysFont(None, 36)
        text_surface = font.render(f"{action}", True, GREEN)
        screen.blit(text_surface, (10, 20))

        pygame.display.update()
        clock.tick(2)

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                cv2.destroyAllWindows()
                cap.release()
                exit()

        if current_time - last_action_time > ACTION_DELAY:
            keyboard.release('up')
            keyboard.release('down')
            keyboard.release('left')
            keyboard.release('right')

cv2.destroyAllWindows()
cap.release()
pygame.quit()
