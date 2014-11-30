#Import libs
import pygame
from _random import Random
from pygame import math
from random import randrange
from aptdaemon.logger import BLUE
#init
pygame.init()

# Define some colors
BLACK    = (   0,   0,   0)
WHITE    = ( 255, 255, 255)
GREEN    = (   0, 255,   0)
RED      = ( 255,   0,   0)


size = (700, 500)
screen = pygame.display.set_mode(size)
pygame.display.set_caption("My Wonderfully Shitty Game!")


# Loop until the user clicks the close button.
done = False
 
# Used to manage how fast the screen updates
clock = pygame.time.Clock()
 
# -------- Main Program Loop -----------
screen.fill(WHITE)

while not done:
    # --- Main event loop
    font = pygame.font.SysFont('Calibri', 25, False, False)
    text = font.render("My Text",True,BLACK)
    screen.blit(text, [250, 250])
 
    # --- Game logic should go here
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            print("User asked to quit.")
            done = True
        elif event.type == pygame.KEYDOWN:
            print("User pressed a key.")
            screen.fill(WHITE)
            pygame.draw.rect(screen, RED, [randrange(50,300), randrange(25,150), randrange(20,500), randrange(20,350)])
        elif event.type == pygame.KEYUP:
            pygame.draw.ellipse(screen, BLUE, [randrange(50,300), randrange(25,150), randrange(20,500), randrange(20,350)], 2)
            print("User let go of a key.")
        elif event.type == pygame.MOUSEBUTTONDOWN:
            print("User pressed a mouse button")
            pygame.draw.line(screen, GREEN, [randrange(700), randrange(500)], [randrange(700), randrange(500)], 5)
    # --- Drawing code should go here
 
    # First, clear the screen to white. Don't put other drawing commands
    # above this, or they will be erased with this command.
    
 
    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()
 
    # --- Limit to 60 frames per second
    clock.tick(60)

pygame.quit()