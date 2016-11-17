public class Camera {

	public Vector2d pos;
	
	public Camera(Vector2d pos) {
		this.pos = pos;
	}
	
	public Camera(double x, double y) {
		this(new Vector2d(x, y));
	}
	
}
