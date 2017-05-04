package algorithms.mazeGenerator;

import java.io.Serializable;

public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x;
	public int y;
	public int z;

	public Position(int z, int y, int x) {
		this.z = z; // floors
		this.y = y; // rows
		this.x = x; // cols
	}

	public Position(Position startPosition) {
		this.x = startPosition.x;
		this.y = startPosition.y;
		this.z = startPosition.z;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

	@Override
	public boolean equals(Object obj) {
		Position pos = (Position) obj;
		return (this.x == pos.x && this.y == pos.y && this.z == pos.z);
	}

	public Position UP() {
		Position p = new Position(this);
		p.z += 1;
		return p;

	}

	public Position DOWN() {
		Position p = new Position(this);
		p.z -= 1;
		return p;

	}

	public Position LEFT() {
		Position p = new Position(this);
		p.x -= 1;
		return p;

	}

	public Position RIGHT() {
		Position p = new Position(this);
		p.x += 1;
		return p;

	}

	public Position FORWARD() {
		Position p = new Position(this);
		p.y -= 1;
		return p;

	}

	public Position BACKWORD() {
		Position p = new Position(this);
		p.y += 1;
		return p;

	}

}
