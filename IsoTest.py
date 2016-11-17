#!python2

import pygame

def run():
	pygame.init()
	width, height = screen_size = (640, 320)
	screen = pygame.display.set_mode(screen_size)

	# Image is 64x32
	tile_image = pygame.image.load("res/Tiles/DarkBaseSmall.png").convert_alpha()
	tile_w, tile_h = tile_image.get_size()
	start_x = (width - tile_w) / 2

	for y in xrange(10):
		for x in xrange(10):
			pos = [0, 0]
			pos[0] = start_x + (x - y) * (tile_w / 2)
			pos[1] = (x + y) * (tile_h / 2)
			screen.blit(tile_image, pos)

	tank_image = pygame.image.load("res/Tanks/GreenTankBR.png").convert_alpha()
	w, h = tank_image.get_size()
	tank_image = pygame.transform.scale(tank_image, (w / 4, h / 4))
	screen.blit(tank_image, (width / 2 - tank_image.get_width() / 2, height / 2 - tank_image.get_height() / 2))

	tank_image = pygame.image.load("res/Tanks/RedTankTL.png").convert_alpha()
	w, h = tank_image.get_size()
	tank_image = pygame.transform.scale(tank_image, (w / 4, h / 4))
	screen.blit(tank_image, (width / 2 + tank_image.get_width() / 2, height / 2 + tank_image.get_height() / 2))

	done = False
	while not done:
		for event in pygame.event.get():
			if event.type == pygame.QUIT:
				done = True

			elif event.type == pygame.KEYDOWN:
				if event.key == pygame.K_ESCAPE:
					done = True

				if event.key == pygame.K_F2:
					pygame.image.save(screen, "Screenshot.png")

		pygame.display.update()

	pygame.quit()

def carToIso(x, y):
	return 5

if __name__ == "__main__":
	run()