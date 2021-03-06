import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public abstract class Entity implements Comparable{
	
	public Vector3d pos;
	public Vector3d destination;
	
	
	public double orientation;
	public Image[] sprites;
	public Vector3d dimensions;
	
	protected static int globalID = 0;
	public int id;
	
	public int speed;
	public int range;
	public int health;
	
	private int numFrames;
	
	
	public boolean selected;
	public boolean controllable;
	
	public Entity(Vector3d pos, Image[] sprites, double orientation, boolean controllable) {
		this.pos = pos;
		this.sprites = sprites;
		this.numFrames = sprites.length;
		
		this.orientation = orientation;
		globalID += 1;
		this.id = globalID;

		this.selected = false;
		
		this.dimensions = new Vector3d(sprites[0].getWidth(), sprites[0].getHeight());
		
		// AI stuff down here
		this.destination = this.pos.clone();
		this.controllable = controllable;
		this.speed = 50; // Pixels per second
		this.range = 15;  // How far away it can attack something
		this.health = 5;
		
	}
	
	// No provided orientation
	public Entity(Vector3d pos, Image[] sprites, boolean controllable) {
		this(pos, sprites, 0, controllable);
	}
	
	// Position in component form
	public Entity(double x, double y, Image[] sprites, double orientation, boolean controllable) {
		this(new Vector3d(x, y), sprites, orientation, controllable);
	}
	
	// Position in component form and no provided orientation
	public Entity(double x, double y, Image[] sprites, boolean controllable){
		this(new Vector3d(x, y), sprites, 0, controllable);
	}
	
	public abstract Entity clone();
	
	@Override
	public String toString() {
		return "ENTITY - ID: " + id + "/" + globalID + ", Position: " + pos + ", Orientation: " + orientation;
	}
	
	public void checkCollision(ArrayList<Entity> entities){
		for (Entity e: entities){
			if (pos.distanceTo(e.pos) <= range){
				handleCollision(e);
			}
			
		}
	}
	
	public abstract void handleCollision(Entity e);
	
	public void setDestination(Vector3d dest){
		destination = dest;
	}
	
	public void update(GameContainer container, double delta) {
		
		if (pos.distanceTo(destination) > delta * speed) { // Not at the destination yet
			orientation = pos.angleTo(destination);
			pos = pos.add(pos.fromOther(destination).normalize().mul(delta * speed));
			
		} else {
//			orientation += (delta);
//			if (orientation > 1) orientation -= (int) orientation;
		}
	}
	
	public boolean testIntersection(Vector3d testPos, Camera camera){
		Vector3d drawPos = IsoFuncs.IsoToEuc(pos, camera).add(dimensions.mul(-0.5)); // Basic box collision
		if (drawPos.x < testPos.x && testPos.x < drawPos.x + sprites[0].getWidth() &&
				drawPos.y < testPos.y && testPos.y < drawPos.y + sprites[0].getHeight())
			return true;
		
		return false;
		
	}
	
	public void draw(Camera camera, Graphics g) {
		Vector3d drawPos = IsoFuncs.IsoToEuc(pos, camera).add(dimensions.mul(-0.5));
		if (selected)
			g.fillOval((float)(drawPos.x - (sprites[0].getWidth() * 0.25)), (float)(drawPos.y + sprites[0].getHeight() / 4), (float)(sprites[0].getWidth() * 1.5), sprites[0].getHeight());
		
		sprites[(int) (orientation * numFrames) % numFrames].draw((float) drawPos.x, (float) drawPos.y);
	}

	// Getters and setters below here
	public Vector3d getPos() {
		return pos;
	}

	public void setPos(Vector3d pos) {
		this.pos = pos;
	}

	public double getOrientation() {
		return orientation;
	}

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	public Image[] getSprites() {
		return sprites;
	}

	public void setSprites(Image[] sprites) {
		this.sprites = sprites;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Vector3d getDestination() {
		return destination;
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Entity)) return 0;
		// We add negative one half of the dimensions of the Entity because it draws from the center, not the top left corner.
		Vector3d thisTestPos = new Vector3d(this.pos.x + this.sprites[0].getWidth() / 2, this.pos.y + this.sprites[0].getHeight()).add(dimensions.mul(-0.5));
		Vector3d oTestPos = new Vector3d(((Entity)o).pos.x + ((Entity)o).sprites[0].getWidth() / 2, ((Entity)o).pos.y + ((Entity)o).sprites[0].getHeight()).add(((Entity)o).dimensions.mul(-0.5));
		return (int) (((thisTestPos.x + thisTestPos.y) - (oTestPos.x + oTestPos.y)) * 1000);
	}
	
}
