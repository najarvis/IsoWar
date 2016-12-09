import org.newdawn.slick.Image;

public class Building extends Entity {

	public Building(Vector3d pos, Image[] sprites, double orientation, boolean controllable) {
		super(pos, sprites, orientation, controllable);
		this.speed = 0;
	}

	public Building(double x, double y, Image[] sprites, double orientation, boolean controllable){
		this(new Vector3d(x, y), sprites, orientation, controllable);
	}
	
	public Building(double x, double y, Image[] sprites, boolean controllable){
		this(new Vector3d(x, y), sprites, 0, controllable);
	}

	public Entity clone() {
		return new Building(pos, sprites, orientation, controllable);
	}

}
