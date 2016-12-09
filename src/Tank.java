import org.newdawn.slick.Image;

public class Tank extends Entity {

	public Tank(Vector3d pos, Image[] sprites, double orientation, boolean controllable) {
		super(pos, sprites, orientation, controllable);
		this.speed = 40;
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
	
	public Tank clone(){
		return new Tank(pos, sprites, orientation, controllable);
	}
	
	public void fire() {
		//TODO: Future feature maybe?
	}

}
