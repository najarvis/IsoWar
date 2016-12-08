import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tile {

	Vector3d pos;
	Image image;
	
	public Tile(Vector3d pos, String filename) throws SlickException {
		this.pos = pos;
		this.image = new Image("res/Tiles/" + filename);
	}
		
}
