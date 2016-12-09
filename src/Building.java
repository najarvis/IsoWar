import org.newdawn.slick.Image;

public class Building extends Entity {

	public Building(Vector3d pos, Image[] sprites, double orientation, boolean controllable) {
		super(pos, sprites, orientation, controllable);
		this.speed = 0;
		//this.range = 15;
	}

	public Building(double x, double y, Image[] sprites, double orientation, boolean controllable){
		this(new Vector3d(x, y), sprites, orientation, controllable);
	}
	
	public Building(double x, double y, Image[] sprites, boolean controllable){
		this(new Vector3d(x, y), sprites, 0, controllable);
	}
	
	public Building(Building toCopy) {
		this(toCopy.pos, toCopy.sprites, toCopy.orientation, toCopy.controllable);
	}

	@Override
	public Entity clone() {
		return new Building(pos, sprites, orientation, controllable);
	}

	@Override
	public String toString(){
		return "BUILDING - ID: " + id + "/" + globalID + ", Position: " + pos + ", Orientation: " + orientation;
	}

	@Override
	public void handleCollision(Entity e) {
		if (e.controllable == !controllable){
			if (e instanceof Tank)
				e.health -= 15;
			if (e instanceof Unit)
				e.health -= 5;
			if (e instanceof Building) // This really shouldn't happen
				;
		}
		
	}
}
