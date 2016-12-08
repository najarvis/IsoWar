
public class IsoFuncs {

	public static Vector2d IsoToEuc(Vector3d pos, Camera camera) {
		double carX = (pos.x - pos.y) / 1.5 - camera.pos.x;
		double carY = pos.x / 3.0 + pos.y / 1.5 - camera.pos.y;
		return new Vector2d(carX, carY);
		//return new Vector2d(0.5 * (pos.x - pos.y) - camera.pos.x,  0.5 * (pos.x + pos.y) - pos.z - camera.pos.y);
	}
	
	public static Vector3d EucToIso(Vector2d pos, Camera camera) {
		pos = pos.add(camera.pos);
		double isoX = pos.x + pos.y;
		double isoY = pos.y - pos.x / 2;
		return new Vector3d(isoX, isoY);
	}
}
