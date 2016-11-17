import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class IsoWar extends BasicGame{

	Image greenTankSheet;
	Image[] greenTankFrames;
	Animation greenTankAnimation;
	ArrayList<Entity> entities;
	
	public IsoWar() {
		super("IsoWar!");
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new IsoWar());
			app.setDisplayMode(500, 400, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		greenTankSheet = new Image("res/Tanks/GreenTankSheet.png");
		
		float scale = 0.25f;
		greenTankSheet = greenTankSheet.getScaledCopy(scale);
		
		greenTankFrames = new Image[8];
		for (int i = 0; i < 8; i++) {
			greenTankFrames[i] = (greenTankSheet.getSubImage((int) (i * 256 * scale), 0, (int) (256 * scale), (int) (192 * scale)));
		}
		
		entities = new ArrayList<Entity>();
		entities.add(new Entity(20, 20, greenTankFrames, true));
		entities.add(new Entity(400, 20, greenTankFrames, false));
		entities.add(new Entity(300, 250, greenTankFrames, false));
		entities.add(new Entity(400, 250, greenTankFrames, false));
		
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		for (Entity e : entities) {
			e.update(container, delta);
		}
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setBackground(Color.pink);
		for (Entity e : entities) {
			e.draw();
		}
	}

}
