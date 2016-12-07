import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class IsoWar extends BasicGame{
	
	Image[] greenTankFrames;
	Image[] redTankFrames;
	
	Camera camera;
	ArrayList<Entity> entities;
	final static int WIDTH = 1600,
			         HEIGHT = 900;
	
	static AppGameContainer app;
	
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
		
		Image greenTankSheet = new Image("res/Tanks/GreenTankSheet.png");
		Image redTankSheet = new Image("res/Tanks/RedTankSheet.png");
		
		float scale = 0.125f;
		greenTankSheet = greenTankSheet.getScaledCopy(scale);
		redTankSheet = redTankSheet.getScaledCopy(scale);
		
		greenTankFrames = new Image[8];
		redTankFrames = new Image[8];
		for (int i = 0; i < 8; i++) {
			greenTankFrames[i] = (greenTankSheet.getSubImage((int) (i * 256 * scale), 0, (int) (256 * scale), (int) (192 * scale)));
			redTankFrames[i] = (redTankSheet.getSubImage((int) (i * 256 * scale), 0, (int) (256 * scale), (int) (192 * scale)));
		}
		
		entities = new ArrayList<Entity>();
		Entity testing = new Entity(20, 20, redTankFrames);
		testing.setDestination(new Vector3d(500, 300));
		entities.add(testing);
		/*
		entities.add(new Entity(400, 20, greenTankFrames, false));
		entities.add(new Entity(300, 250, greenTankFrames, false));
		entities.add(new Entity(400, 250, greenTankFrames, false));*/
		
		for (int i = 0; i < 20; i++){
//			Entity toAdd = ;
//			toAdd.setDestination(new Vector3d(500, 300));
//			entities.add(new Entity(random.nextInt(800), random.nextInt(450), greenTankFrames));
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
		double timePassedSeconds = delta * 0.001;
		for (Entity e : entities) {
			e.update(container, timePassedSeconds);
		}
		sortEntities();
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setBackground(Color.pink);
		for (Entity e : entities) {
			e.draw(camera);
//			app.setTitle(String.valueOf(e.pos.distanceTo(e.destination)));
		}
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
				Entity tmp = A.get(i).clone();
				A.set(i, A.get(j));
				A.set(j, tmp);
				i += 1;
			}
		}
		Entity tmp = A.get(i).clone();
		A.set(i, A.get(high));
		A.set(high, tmp);
		return i;
	}


}
