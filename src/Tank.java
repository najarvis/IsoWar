import org.newdawn.slick.Image;

public class Tank extends Entity {

	public Tank(Vector3d pos, Image[] sprites, double orientation) {
		super(pos, sprites, orientation);
		this.speed = 40;
	}

	public Tank(double x, double y, Image[] sprites, double orientation) {
		this(new Vector3d(x, y), sprites, orientation);
	}
	
	public Tank(double x, double y, Image[] sprites) {
		this(new Vector3d(x, y), sprites, 0);
	}
	
	public Tank(Vector3d pos, Image[] sprites) {
		this(pos, sprites, 0);
	}
	
	public Tank clone(){
		globalID -= 1;
		return new Tank(pos, sprites, orientation);
	}
	
	public void fire() {
		
	}

}
