import pygame
 
# Define some colors
BLACK    = (   0,   0,   0)
WHITE    = ( 255, 255, 255)
GREEN    = (   0, 255,   0)
RED      = ( 255,   0,   0)
 
pygame.init()
 
# Set the width and height of the screen [width, height]
SCREEN_WIDTH = 700
SCREEN_HEIGHT = 500
size = (SCREEN_WIDTH, SCREEN_HEIGHT)
screen = pygame.display.set_mode(size)

 
pygame.display.set_caption("My Game")
 
# Loop until the user clicks the close button.
done = False
 
# Used to manage how fast the screen updates
clock = pygame.time.Clock()
 
# -------- Main Program Loop -----------
while not done:
    # --- Main event loop
    for event in pygame.event.get(): # User did something
        if event.type == pygame.QUIT: # If user clicked close
            done = True # Flag that we are done so we exit this loop
 
    # --- Game logic should go here
    screen.fill(WHITE)
    
    num_rows = 25
    num_cols = 25
    rect_spacing = 5
    rect_width = (SCREEN_WIDTH / num_rows) - rect_spacing*2
    rect_height = (SCREEN_HEIGHT / num_cols) - rect_spacing*2
    
    for row in range(num_rows):
        for col in range(num_cols):
            rx = (SCREEN_WIDTH / num_rows) * col + rect_spacing
            ry = (SCREEN_HEIGHT / num_cols) * row + rect_spacing
            my_rect = [rx, ry, rect_width, rect_height]
            pygame.draw.rect(screen, GREEN, my_rect)
 
    # --- Drawing code should go here
 
    # First, clear the screen to white. Don't put other drawing commands
    # above this, or they will be erased with this command.
    
 
    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()
 
    # --- Limit to 60 frames per second
    clock.tick(60)
 
# Close the window and quit.
# If you forget this line, the program will 'hang'
# on exit if running from IDLE.
pygame.quit()
