import org.newdawn.slick.Image;

public class Unit extends Entity {

	public Unit(Vector3d pos, Image[] sprites, double orientation) {
		super(pos, sprites, orientation);
		this.speed = 30;
	}

	public Unit(double x, double y, Image[] sprites, double orientation) {
		this(new Vector3d(x, y), sprites, orientation);
	}
	
	public Unit(double x, double y, Image[] sprites) {
		this(new Vector3d(x, y), sprites, 0);
	}
	
	public Unit(Vector3d pos, Image[] sprites) {
		this(pos, sprites, 0);
	}

	@Override
	public Entity clone() {
		globalID -= 1;
		return new Unit(pos, sprites, orientation);
	}

}
