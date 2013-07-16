package net.catalyst.model;


public class Point {
	protected int x;
	protected int y;

	public static Point location(int x, int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("Point may not contain non negative values x:" + x + " y:" + y);
		}
		return new Point(x, y);
	}
	
	protected Point() { }

	private Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	

	public final int getY() {
		return y;
	}

	public final int getX() {
		return x;
	}

	public final boolean equals(Object o) {
		if (o instanceof Point) {
			return this.x == ((Point) o).x && this.y == ((Point) o).y;
		}
		return false;
	}

	public int hashCode() {
		return x << 16 | y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	
	
	public boolean inBounds(int x1, int y1, int x2, int y2) {
		return x >= x1 && x <= x2 && y >= y1 && y <= y2;
	}

}
