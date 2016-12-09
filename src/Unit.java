import org.newdawn.slick.Image;

public class Unit extends Entity {

	public Unit(Vector3d pos, Image[] sprites, double orientation, boolean controllable) {
		super(pos, sprites, orientation, controllable);
		this.speed = 30;
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

	@Override
	public Entity clone() {
		return new Unit(pos, sprites, orientation, controllable);
	}

}
