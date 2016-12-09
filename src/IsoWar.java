import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import OldFiles.Vector2d;

public class IsoWar extends BasicGame{
	
	Image[] greenTankFrames;
	Image[] redTankFrames;
	
	Camera camera;
	Input input;
	
	ArrayList<Entity> entities;
	ArrayList<IsoTile> tiles;
	
	final static int WIDTH = 1600,
			         HEIGHT = 800;
	
	static AppGameContainer app;
	
	Player player;
	Player computer;
	
	public IsoWar() {
		super("IsoWar!");
	}
	
	public static void main(String[] args) {
		try {
			app = new AppGameContainer(new IsoWar());
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		Random random = new Random();
		camera = new Camera(-WIDTH / 2, 0);
		
		// Set up all the entities.
		EntityInfo GT = EntityInfo.GreenTank;
		EntityInfo GU = EntityInfo.GreenUnit;
		EntityInfo GB = EntityInfo.GreenBase;
		
		EntityInfo RT = EntityInfo.RedTank;
		EntityInfo RU = EntityInfo.RedUnit;
		EntityInfo RB = EntityInfo.RedBase;
		
				
		// Add tiles
		tiles = new ArrayList<IsoTile>();
		
		//TODO: Fix this so the tiles look normal
		for (int y = -15; y < 15; y++){
			for (int x = -15; x < 15; x++){
				IsoTile toAdd = new IsoTile(new Vector3d(x * 190, y * 128), "DarkBase.png");
				tiles.add(toAdd);
			}
		}
		
		// Add entities
		entities = new ArrayList<Entity>();
		
		/*
		// Single tank for testing
		entities.add(RT.generateEntity(20, 20));
		
		// Testing adding lots of entities
		for (int i = 0; i < 0; i++){
			entities.add(GT.generateEntity(random.nextInt(800), random.nextInt(450)));
			entities.add(GU.generateEntity(random.nextInt(800), random.nextInt(450)));
			entities.add(RU.generateEntity(random.nextInt(800), random.nextInt(450)));
		}
		*/
		
		// Main Buildings
		entities.add(GB.generateEntity(-450, 450)); // Green Base (player)
		entities.add(RB.generateEntity(1150, 450)); // Red Base (enemy)
		

		player = new Player(new Vector3d(-450, 450), new Vector3d(1150, 450), true);
		computer = new Player(new Vector3d(1150, 450), new Vector3d(-450, 450), false);
		
	}
	
	private void selectEntity(Vector3d clickPos) {
		for (Entity e : entities){
			if (e.controllable && e.testIntersection(clickPos, camera)){
				e.selected = true;
				continue;
			}
			e.selected = false;
		}
	}
	
	/* Sorts entities by their position on the screen for rendering
	 * 
	 * If an entity is further away from the screen, it should draw behind other objects
	 */
	private void sortEntities() {
		quicksort(entities, 0, entities.size() - 1);
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		input = container.getInput();
		double timePassedSeconds = delta * 0.001;
		
		// Select an entity
		if (input.isMouseButtonDown(input.MOUSE_LEFT_BUTTON)){
			selectEntity(new Vector3d(input.getMouseX(), input.getMouseY()));
		}
		
		// Order an entity somewhere
		if (input.isMouseButtonDown(input.MOUSE_RIGHT_BUTTON)){
			for (Entity e : entities) {
				if (e.selected && e.controllable){
					e.setDestination(IsoFuncs.EucToIso(new Vector3d(input.getMouseX(), input.getMouseY()), camera));
				}
			}
		}
		
		player.update(entities, timePassedSeconds, input);
		computer.update(entities, timePassedSeconds, input);
		
		for (Entity e : entities) {
			e.update(container, timePassedSeconds);
		}
		
		app.setTitle(IsoFuncs.EucToIso(new Vector3d(input.getMouseX(), input.getMouseY()), camera).toString());
		
		sortEntities();
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setBackground(Color.pink);
		
		for (IsoTile t : tiles){
			t.render(camera);
		}
		
		for (Entity e : entities) {
			e.draw(camera, g);
		}
		
		Input input = container.getInput();
	}
	
	

	
	
	/* Quicksort algorithm, source: Wikipedia
	 * https://en.wikipedia.org/wiki/Quicksort
	 * 
	 * Quicksort algorithm for Entities. Probably should make this generic, but eh.
	 */
	private void quicksort(ArrayList<Entity> A, int low, int high){
		if (low < high){
			int p = partition(A, low, high);
			quicksort(A, low, p - 1);
			quicksort(A, p + 1, high);
		}
	}
	
	/* Part of the quicksort algorithm*/
	private int partition(ArrayList<Entity> A, int low, int high){
		Entity pivot = A.get(high);
		int i = low;
		for (int j = low; j < high; j++){
			if (A.get(j).compareTo(pivot) <= 0){
				Entity tmp = A.get(i);
				A.set(i, A.get(j));
				A.set(j, tmp);
				i += 1;
			}
		}
		Entity tmp = A.get(i);
		A.set(i, A.get(high));
		A.set(high, tmp);
		return i;
	}


}
