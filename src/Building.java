import org.newdawn.slick.Image;

public class Building extends Entity {

	public Building(Vector3d pos, Image[] sprites, double orientation) {
		super(pos, sprites, orientation);
		this.speed = 0;
	}

	public Building(double x, double y, Image[] sprites, double orientation){
		this(new Vector3d(x, y), sprites, orientation);
	}
	
	public Building(double x, double y, Image[] sprites){
		this(new Vector3d(x, y), sprites, 0);
	}

}
