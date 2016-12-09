import OldFiles.Vector2d;

public class IsoFuncs {

	public static Vector3d IsoToEuc(Vector3d pos, Camera camera) {
		double carX = (pos.x - pos.y) / 1.5 - camera.pos.x;
		double carY = pos.x / 3.0 + pos.y / 1.5 - camera.pos.y;
		return new Vector3d(carX, carY);
		//return new Vector2d(0.5 * (pos.x - pos.y) - camera.pos.x,  0.5 * (pos.x + pos.y) - pos.z - camera.pos.y);
	}
	
	public static Vector3d EucToIso(Vector3d vector3d, Camera camera) {
		vector3d = vector3d.add(camera.pos);
		double isoX = vector3d.x + vector3d.y;
		double isoY = vector3d.y - vector3d.x / 2;
		return new Vector3d(isoX, isoY);
	}
}
