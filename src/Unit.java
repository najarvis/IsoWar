import org.newdawn.slick.Image;

public class Unit extends Entity {

	public Unit(Vector3d pos, Image[] sprites, double orientation, boolean controllable) {
		super(pos, sprites, orientation, controllable);
		this.speed = 50;
		//this.range = 7;
		this.health = 5;
	}

	public Unit(double x, double y, Image[] sprites, double orientation, boolean controllable) {
		this(new Vector3d(x, y), sprites, orientation, controllable);
	}
	
	public Unit(double x, double y, Image[] sprites, boolean controllable) {
		this(new Vector3d(x, y), sprites, 0, controllable);
	}
	
	public Unit(Vector3d pos, Image[] sprites, boolean controllable) {
		this(pos, sprites, 0, controllable);
	}
	
	public Unit(Unit toCopy) {
		this(toCopy.pos, toCopy.sprites, toCopy.orientation, toCopy.controllable);
	}

	@Override
	public Entity clone() {
		return new Unit(pos, sprites, orientation, controllable);
	}

	@Override
	public String toString(){
		return "UNIT - ID: " + id + "/" + globalID + ", Position: " + pos + ", Orientation: " + orientation;
	}

	@Override
	public void handleCollision(Entity e) {
		// This line just checks if it is on the other team
		if (e.controllable == !controllable){
			if (e instanceof Tank)
				e.health -= 2;
			if (e instanceof Unit)
				e.health -= 5;
			if (e instanceof Building)
				e.health -= 5;
		}
		
	}

}
