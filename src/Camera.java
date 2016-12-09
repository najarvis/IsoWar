import OldFiles.Vector2d;

public class Camera {

	public Vector3d pos;
	
	public Camera(Vector3d pos) {
		this.pos = pos;
	}
	
	public Camera(double x, double y) {
		this(new Vector3d(x, y));
	}
	
	public Camera(){
		this(new Vector3d());
	}
	
}
