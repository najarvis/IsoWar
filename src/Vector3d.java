
public class Vector3d {

	double x, y, z;
	
	public Vector3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	// A blank constructor will set x and y to 0.
	public Vector3d() {
		this(0, 0, 0);
	}
	
	public Vector3d(double x, double y){
		this(x, y, 0);
	}
	
	@Override
	public String toString() {
		return "Vector2d: (" + x + ", " + y + ", " + z + ")";
	}
	
	public double[] unpack() {
		return new double[]{x, y, z};
	}
	
	public float[] unpackf() {
		return new float[]{(float)x, (float)y, (float)z};
	}
	
	public Vector3d clone() {
		return new Vector3d(this.x, this.y, this.z);
	}
	
	// Adds another vector to the current one and returns a new vector
	public Vector3d add(Vector3d other) {
		return new Vector3d(x + other.x, y + other.y, z + other.z);
	}
	
	// Multiplies the vector by a scalar and returns a new vector
	public Vector3d mul(double scalar){
		return new Vector3d(x * scalar, y * scalar, z * scalar);
	}

	// Returns the magnitude of the vector
	public double getMagnitude() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	// Returns a unit-length vector with the same direction of the current vector
	public Vector3d normalize() {
		return mul(1 / getMagnitude());
	}
	
	// Returns the distance between the current vector and another vector supplied.
	public double distanceTo(Vector3d other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
	}
	
	// Returns the vector from the current vector to the other vector.
	public Vector3d fromOther(Vector3d other) {
		return other.add(this.mul(-1));
	}
	
	public double angleTo(Vector3d other) {
		Vector3d between = other.fromOther(this);
		return ((Math.atan2(between.y, between.x)) / (Math.PI * 2)) + 0.5;
	}
}
