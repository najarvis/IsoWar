import org.newdawn.slick.Image;

public class Tank extends Entity {

	public Tank(double x, double y, Image[] sprites, double orientation) {
		super(x, y, sprites, orientation);
	}
	
	public Tank(Vector3d pos, Image[] sprites, double orientation) {
		super(pos, sprites, orientation);
	}
	
	public Tank(Vector3d pos, Image[] sprites) {
		super(pos, sprites);
	}

}
