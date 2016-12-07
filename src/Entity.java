import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Entity implements Comparable{
	
	public Vector3d pos;
	public Vector3d destination;
	
	public double orientation;
	public Image[] sprites;
	
	private static int globalID = 0;
	public int id;
	
	public int speed;
	private int numFrames;
	
	public Entity(Vector3d pos, Image[] sprites, double orientation) {
		this.pos = pos;
		this.sprites = sprites;
		this.numFrames = sprites.length;
		
		this.orientation = orientation;
		globalID += 1;
		this.id = globalID;
		
		// AI stuff down here
		this.destination = this.pos.clone();
		this.speed = 50; // Pixels per second
		
	}
	
	// No provided orientation
	public Entity(Vector3d pos, Image[] sprites) {
		this(pos, sprites, 0);
	}
	
	// Position in component form
	public Entity(double x, double y, Image[] sprites, double orientation) {
		this(new Vector3d(x, y), sprites, orientation);
	}
	
	// Position in component form and no provided orientation
	public Entity(double x, double y, Image[] sprites){
		this(new Vector3d(x, y), sprites, 0);
	}
	
	public Entity clone() {
		globalID -= 1;
		return new Entity(pos, sprites, orientation);
	}
	
	@Override
	public String toString() {
		return "ENTITY - ID: " + id + "/" + globalID + ", Position: " + pos + ", Orientation: " + orientation;
	}
	
	private Vector2d IsoToEuc(Camera camera) {
		return new Vector2d(0.5 * (pos.x - pos.y) - camera.pos.x,  0.5 * (pos.x + pos.y) - pos.z - camera.pos.y);
	}
	
	public void setDestination(Vector3d dest){
		destination = dest;
	}
	
	public void update(GameContainer container, double delta) {
		
		if (pos.distanceTo(destination) > delta * speed) { // Not at the destination yet
			pos = pos.add(pos.fromOther(destination).normalize().mul(delta * speed));
			
		} else {
			orientation += (delta);
			if (orientation > 1) orientation -= (int) orientation;
		}
	}
	
	public void draw(Camera camera) {
		Vector2d drawPos = IsoToEuc(camera);
		sprites[(int) (orientation * numFrames) % numFrames].draw((float) drawPos.x, (float) drawPos.y);
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Entity)) return 0;
		return (int) (((this.pos.x + this.pos.y) - (((Entity)o).pos.x + ((Entity)o).pos.y)) * 1000);
	}
	
}
