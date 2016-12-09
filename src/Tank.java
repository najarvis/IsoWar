import org.newdawn.slick.Image;

public class Tank extends Entity {

	public Tank(Vector3d pos, Image[] sprites, double orientation, boolean controllable) {
		super(pos, sprites, orientation, controllable);
		this.speed = 40;
		//this.range = 15;
		this.health = 15;
	}

	public Tank(double x, double y, Image[] sprites, double orientation, boolean controllable) {
		this(new Vector3d(x, y), sprites, orientation, controllable);
	}
	
	public Tank(double x, double y, Image[] sprites, boolean controllable) {
		this(new Vector3d(x, y), sprites, 0, controllable);
	}
	
	public Tank(Vector3d pos, Image[] sprites, boolean controllable) {
		this(pos, sprites, 0, controllable);
	}
	
	public Tank(Tank toCopy) {
		this(toCopy.pos, toCopy.sprites, toCopy.orientation, toCopy.controllable);
	}
	
	@Override
	public Tank clone(){
		return new Tank(pos, sprites, orientation, controllable);
	}
	
	@Override
	public String toString(){
		return "TANK - ID: " + id + "/" + globalID + ", Position: " + pos + ", Orientation: " + orientation;
	}

	@Override
	public void handleCollision(Entity e) {
		if (e.controllable == !controllable){
			if (e instanceof Tank)
				e.health -= 15;
			if (e instanceof Unit)
				e.health -= 5;
			if (e instanceof Building)
				e.health -= 10;
		}
		
	}

}
