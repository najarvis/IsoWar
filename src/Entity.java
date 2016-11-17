import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Entity {
	
	public Vector2d pos;
	public double orientation;
	public Image[] sprites;
	
	private static int globalID = 0;
	public int id;
	
	boolean player;
	
	public Entity(Vector2d pos, Image[] sprites, double orientation, boolean player) {
		this.pos = pos;
		this.sprites = sprites;
		this.orientation = orientation;
		globalID += 1;
		this.id = globalID;
		this.player = player;
	}
	
	// No provided orientation
	public Entity(Vector2d pos, Image[] sprites, boolean player) {
		this(pos, sprites, 0, player);
	}
	
	// Position in component form
	public Entity(double x, double y, Image[] sprites, double orientation, boolean player) {
		this(new Vector2d(x, y), sprites, orientation, player);
	}
	
	// Position in component form and no provided orientation
	public Entity(double x, double y, Image[] sprites, boolean player){
		this(new Vector2d(x, y), sprites, 0, player);
	}
	
	@Override
	public String toString() {
		return "ENTITY - ID: " + id + "/" + globalID + ", Position: " + pos + ", Orientation: " + orientation;
	}
	
	private Vector2d IsoToEuc() {
		return new Vector2d(0.5 * (pos.x - pos.y), 0.5 * (pos.x + pos.y));
	}
	
	public void update(GameContainer container, int delta) {
		if (player) {
			Input input = container.getInput();
			
			if (input.isKeyDown(Input.KEY_D)) {
				orientation = 0;
				pos.x += 50 * (delta / 1000.0); 
			}
			
			if (input.isKeyDown(Input.KEY_A)) {
				orientation = 0.5;
				pos.x -= 50 * (delta / 1000.0); 
			}
			
			if (input.isKeyDown(Input.KEY_W)) {
				orientation = 0.75;
				pos.y -= 50 * (delta / 1000.0); 
				if (input.isKeyDown(Input.KEY_D)){
					orientation = 0.875;
				} else if (input.isKeyDown(Input.KEY_A)){
					orientation = 0.625;
				}

			}
			
			if (input.isKeyDown(Input.KEY_S)) {
				orientation = 0.25;
				pos.y += 50 * (delta / 1000.0); 
				if (input.isKeyDown(Input.KEY_D)){
					orientation = 0.125;
				} else if (input.isKeyDown(Input.KEY_A)){
					orientation = 0.375;
				}
			}
			
		} else {
			orientation += (delta * 0.001);
			if (orientation > 1) orientation -= (int) orientation;
			
		}
	}
	
	public void draw() {
		Vector2d drawPos = IsoToEuc();
		sprites[(int) (orientation * 8) % 8].draw((float) drawPos.x, (float) drawPos.y);
	}
	
}
