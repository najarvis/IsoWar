import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class IsoWar extends BasicGame{

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
		
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		
	}

}
