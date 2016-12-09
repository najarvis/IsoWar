import java.io.RandomAccessFile;
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


/*
 * An "Isometric minimalist Real time strategy game"
 * Create a tank with the 'T' key, 25 resources, 2s. cooldown
 * Create a unit with the 'U' key, 5 resources, 0.5s. cooldown
 * Check how many resources you have on the title bar
 */
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
	
	
	final static int NOWIN = 0, PLAYERWIN = 1, COMPUTERWIN = 2;
	int GAMEFLAG = NOWIN;
	
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
	
	public void saveLevel(String filename){
		/*
		 * Levels are saved as the following:
		 * 
		 * player base position
		 * computer base position <- We can create both players from just these two data points
		 * number of tanks
		 * tanks
		 * number of units
		 * units
		 * 
		 */
		
		try{
			ArrayList<Entity> tanks = new ArrayList<Entity>();
			ArrayList<Entity> units = new ArrayList<Entity>();
			
			for (Entity e : entities){
				if (e instanceof Tank)
					tanks.add(e);
				if (e instanceof Unit)
					units.add(e);
			}
			
			
			RandomAccessFile raf = new RandomAccessFile(filename, "rw");
			
			raf.writeDouble(player.basePos.x);
			raf.writeDouble(player.basePos.y);
			raf.writeDouble(player.otherBasePos.x);
			raf.writeDouble(player.otherBasePos.y);
			
			// Number of units
			raf.writeInt(tanks.size());
			for (Entity t : tanks){
				raf.writeDouble(t.pos.x);
				raf.writeDouble(t.pos.y);
				raf.writeBoolean(t.controllable);
			}
			
			raf.writeInt(units.size());
			for (Entity u : units){
				raf.writeDouble(u.pos.x);
				raf.writeDouble(u.pos.y);
				raf.writeBoolean(u.controllable);
			}
			
			
		} catch (Exception e) {}
	}
	
	public void loadLevel(String filename){
		entities = new ArrayList<Entity>();
		
		try {
			EntityInfo GU = EntityInfo.GreenUnit;
			EntityInfo RU = EntityInfo.RedUnit;

			EntityInfo GT = EntityInfo.GreenTank;
			EntityInfo RT = EntityInfo.RedTank;
			
			EntityInfo GB = EntityInfo.GreenBase;
			EntityInfo RB = EntityInfo.RedBase;
			
			RandomAccessFile raf = new RandomAccessFile(filename, "rw");
			
			double pbpx = raf.readDouble();
			double pbpy = raf.readDouble();
			double pobpx = raf.readDouble();
			double pobpy = raf.readDouble();
			
			player = new Player(new Vector3d(pbpx, pbpy), new Vector3d(pobpx, pobpy), true);
			computer = new Player(new Vector3d(pobpx, pobpy), new Vector3d(pbpx, pbpy), false);

			entities.add(GB.generateEntity(new Vector3d(pbpx, pbpy)));
			entities.add(RB.generateEntity(new Vector3d(pobpx, pobpy)));
			
			int numTanks = raf.readInt();
			for (int i = 0; i < numTanks; i++){
				double tpx = raf.readDouble();
				double tpy = raf.readDouble();
				boolean tc = raf.readBoolean();
				Entity toAdd;
				if (tc){
					toAdd = GT.generateEntity(new Vector3d(tpx, tpy));
					toAdd.destination = new Vector3d(pobpx, pobpy);
				}
				else {
					toAdd = RT.generateEntity(new Vector3d(tpx, tpy));
					toAdd.destination = new Vector3d(pbpx, pbpy);
				}
				entities.add(toAdd);
			}
			int numUnits = raf.readInt();
			for (int i = 0; i < numUnits; i++){
				double upx = raf.readDouble();
				double upy = raf.readDouble();
				boolean uc = raf.readBoolean();
				Entity toAdd;
				if (uc){
					toAdd = GU.generateEntity(new Vector3d(upx, upy));
					toAdd.destination = new Vector3d(pobpx, pobpy);
				}
				else {
					toAdd = RU.generateEntity(new Vector3d(upx, upy));
					toAdd.destination = new Vector3d(pbpx, pbpy);
				}
				entities.add(toAdd);
			}
			
			GAMEFLAG = NOWIN;
			
		} catch(Exception e) {}
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		Random random = new Random();
		camera = new Camera(-WIDTH / 2, 0); // Position the camera so everything is centered

		// Add tiles
		tiles = new ArrayList<IsoTile>();
		
		//TODO: Fix this so the tiles look normal
		for (int y = -15; y < 15; y++){
			for (int x = -15; x < 15; x++){
				IsoTile toAdd = new IsoTile(new Vector3d(x * 190, y * 128), "DarkBase.png");
				tiles.add(toAdd);
			}
		}
		
		
		loadLevel("MainLevel.bin");
		//saveLevel("MainLevel.bin");
		
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
		
		// Can't update if the base doesn't exist
		if (GAMEFLAG != COMPUTERWIN){
			player.update(entities, timePassedSeconds, input);
		}
		
		if (GAMEFLAG != PLAYERWIN){
			computer.update(entities, timePassedSeconds, input);
		}
		
		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		
		if (GAMEFLAG == NOWIN){
			for (Entity e : entities) {
				e.update(container, timePassedSeconds);
				e.checkCollision(entities);
				if (e.health <= 0)
					toRemove.add(e);
			}
		}
		
		for (Entity e : toRemove){
			if (e instanceof Building){
				if (e.controllable)
					GAMEFLAG = COMPUTERWIN;
				if (!e.controllable)
					GAMEFLAG = PLAYERWIN;
			}
			entities.remove(e);
			e = null;
		}
		
		// The player has won, do something
		if (GAMEFLAG == PLAYERWIN){
			if (input.isKeyDown(input.KEY_SPACE))
				loadLevel("MainLevel.bin");
		}
		
		// The player has lost, do something
		if (GAMEFLAG == COMPUTERWIN){
			if (input.isKeyDown(input.KEY_SPACE))
				loadLevel("MainLevel.bin");
		}
		
		// Debugging
		//app.setTitle(IsoFuncs.EucToIso(new Vector3d(input.getMouseX(), input.getMouseY()), camera).toString() + " Resources: " + player.resources);
		
		//app.setTitle("Resources: " + player.resources);
		
		// Sort the entities for drawing
		sortEntities();
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		// A color that I will easily be able to see if there are holes in the ground
		g.setBackground(Color.pink);
		
		// Draw tiles
		for (IsoTile t : tiles){
			t.render(camera);
		}
		
		// Draw all the entities (Tanks, Units, Buildings)
		for (Entity e : entities) {
			e.draw(camera, g);
		}

		g.drawString("Resources: " + (float)player.resources, 0, HEIGHT - 25);
		g.drawString("Press T to make a tank, cost 25 resources. Cooldown: " + (float)player.tankCooldown, 0, HEIGHT - 45);
		g.drawString("Press U to make a unit, cost: 5 resources. Cooldown: " + (float)player.unitCooldown, 0, HEIGHT - 65);
		
		// The player has won, do something
		if (GAMEFLAG == PLAYERWIN){
			g.drawString("YOU WIN! Press space to restart", 0, 0);
		}
		
		// The player has lost, do something
		if (GAMEFLAG == COMPUTERWIN){
			g.drawString("You lose. Way to suck. Press space to restart", 0, 0);
		}
	}
	
	/* Quicksort algorithm, source: Wikipedia
	 * https://en.wikipedia.org/wiki/Quicksort
	 * 
	 * Quicksort algorithm for Entities. Probably should make this generic, but
	 * currently there is no need
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
