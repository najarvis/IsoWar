
public class Vector2d {

	double x, y;
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// A blank constructor will set x and y to 0.
	public Vector2d() {
		this(0, 0);
	}
	
	@Override
	public String toString() {
		return "Vector2d: (" + x + ", " + y + ")";
	}
	
	public double[] unpack() {
		return new double[]{x, y};
	}
	
	public float[] unpackf() {
		return new float[]{(float)x, (float)y};
	}
	
	// Adds another vector to the current one and returns a new vector
	public Vector2d add(Vector2d other) {
		return new Vector2d(x + other.x, y + other.y);
	}
	
	// Multiplies the vector by a scalar and returns a new vector
	public Vector2d mul(double scalar){
		return new Vector2d(x * scalar, y * scalar);
	}

	// Returns the magnitude of the vector
	public double getMagnitude() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	// Returns a unit-length vector with the same direction of the current vector
	public Vector2d normalize() {
		return mul(getMagnitude());
	}
	
	// Returns the distance between the current vector and another vector supplied.
	public double distanceTo(Vector2d other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}
	
	// Returns the vector from the current vector to the other vector.
	public Vector2d fromOther(Vector2d other) {
		return add(other.mul(-1));
	}
}
