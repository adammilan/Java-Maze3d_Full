package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

import algorithms.mazeGenerator.Position;

/**
 * This class responsible on the game character Own all the method that
 * determine that character position and appearance
 * 
 * @author ben & adam
 *
 */
public class GameCharacter {

	int z, y, x;

	/**
	 * constructor
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */

	public GameCharacter(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;

	}

	public GameCharacter(Position p) {
		this.z = p.z;
		this.y = p.y;
		this.x = p.x;

	}

	public void paint(PaintEvent e, int w, int h) {
		e.gc.setForeground(new Color(null, 255, 0, 0));
		e.gc.setBackground(new Color(null, 255, 0, 0));

		e.gc.fillOval(x * w, y * h, w, h);
		e.gc.drawOval(x * w, y * h, w, h);

		// e.gc.fillOval(5,h*y, w, h);
		// e.gc.drawOval(5,h*y, w, h);

	}

	public void setPosition(int z, int y, int x) {

		this.z = z; // floors
		this.y = y;// rows
		this.x = x;// cols
	}

	public Position getGcPosition() {

		Position p = new Position(z, y, x);
		return p;

	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

}
