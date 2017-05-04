package algorithms.mazeGenerator;

import java.io.Serializable;
import java.util.ArrayList;

public class Maze3d implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[][][] maze;
	private int rows;
	private int cols;
	private int floors;
	private Position startPosition;
	private Position goalPosition;

	public static final int FREE = 0;
	public static final int WALL = 1;

	public Maze3d(int floors, int rows, int cols) {
		this.floors = floors;
		this.rows = rows;
		this.cols = cols;
		maze = new int[floors][rows][cols];
	}

	public Maze3d(Maze3d myMaze) {
		this.floors = myMaze.getFloors();
		this.rows = myMaze.getRows();
		this.cols = myMaze.getCols();
		// CPY CONSTRACTOR
		maze = new int[floors][rows][cols];
		for (int i = 0; i < floors; i++) {
			for (int j = 0; j < rows; j++) {
				for (int k = 0; k < cols; k++) {
					this.maze[i][j][k] = myMaze.maze[i][j][k];
				}
			}
		}

		this.startPosition = new Position(myMaze.getStartPosition());
		this.goalPosition = new Position(myMaze.getGoalPosition());
	}

	public String getPossibleMoves(Position pos) {

		ArrayList<Position> neighbors = new ArrayList<Position>();

		if (pos.x - 1 >= 0 && maze[pos.z][pos.y][pos.x - 1] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y, pos.x - 1));
		}
		if (pos.x + 1 < getCols() && maze[pos.z][pos.y][pos.x + 1] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y, pos.x + 1));
		}
		if (pos.y - 1 >= 0 && maze[pos.z][pos.y - 1][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y - 1, pos.x));
		}
		if (pos.y + 1 < getRows() && maze[pos.z][pos.y + 1][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y + 1, pos.x));
		}
		if (pos.z - 1 >= 0 && maze[pos.z - 1][pos.y][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z - 1, pos.y, pos.x));
		}
		if (pos.z + 1 < getFloors() && maze[pos.z + 1][pos.y][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z + 1, pos.y, pos.x));
		}

		return String.join("," + neighbors.toArray());

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int z = 0; z < floors; z++) {

			for (int y = 0; y < rows; y++) {

				for (int x = 0; x < cols; x++) {

					if (z == startPosition.z && y == startPosition.y && x == startPosition.x)
						sb.append("E" + " ");
					else if (z == goalPosition.z && y == goalPosition.y && x == goalPosition.x)
						sb.append("X" + " ");
					else
						sb.append(maze[z][y][x] + " ");

				}
				sb.append("\n");

			}
			sb.append("------\n");

		}
		return sb.toString();

	}

	public void setWall(int z, int y, int x) {
		maze[z][y][x] = WALL;
	}

	public int getCell(int floors, int rows, int cols) {
		return maze[floors][rows][cols];
	}

	public static int getFree() {
		return FREE;
	}

	public static int getWall() {
		return WALL;
	}

	public int[][][] getMaze() {
		return maze;
	}

	public void setMaze(int[][][] maze) {
		this.maze = maze;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public Position getStartPosition() {
		return startPosition;
	}

	public String getStartPositionString() {
		return startPosition.toString();
	}

	public String getGoalPositionString() {
		return goalPosition.toString();
	}

	public void setStartPosition(Position startPosition) {
		this.startPosition = new Position(startPosition);
	}

	public Position getGoalPosition() {
		return goalPosition;
	}

	public void setGoalPosition(Position goal) {
		this.goalPosition = new Position(goal);
	}

	public void setFree(int z, int y, int x) {
		maze[z][y][x] = FREE;
	}

	public boolean wallExist(Position p) {
		int px = p.x;
		int py = p.y;
		int pz = p.z;

		// check if p is out of bounds
		if (pz >= floors || pz < 0 || py >= rows || py < 0 || px >= cols || px < 0)
			return true;

		// check if p is in bounds
		if (pz < floors && pz >= 0 && py < rows && py >= 0 && px < cols && px >= 0) {
			// p is in the maze
			// check if the cell of p is a wall
			if (maze[pz][py][px] == 1)
				return true;

			// check if the cell of p is empty
			if (maze[pz][py][px] == 0)
				return false;
		}
		return true;

	}

	public void setWall(Position p) {
		maze[p.z][p.y][p.x] = 1;
	}

	public Maze3d(byte[] arr) {

		int k = 0;
		this.floors = arr[k++];
		this.rows = arr[k++];
		this.cols = arr[k++];
		maze = new int[floors][rows][cols];

		Position startPos = new Position(arr[k++], arr[k++], arr[k++]);
		this.setStartPosition(startPos);

		Position goalPos = new Position(arr[k++], arr[k++], arr[k++]);
		this.setGoalPosition(goalPos);

		for (int z = 0; z < floors; z++) {
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++) {
					maze[z][y][x] = arr[k++];
				}
			}
		}
	}

	public int[][] getCrossSectionByX(int x)

	{

		int[][] CrossSectionByX = new int[this.floors][this.cols];

		for (int z = 0; z < this.floors; z++) {

			for (int i = 0; i < this.cols; i++) {

				CrossSectionByX[z][i] = this.getMaze()[z][x][i];
			}
		}

		return CrossSectionByX;

	}

	public int[][] getCrossSectionByY(int y)

	{

		int[][] CrossSectionByY = new int[this.floors][this.rows];

		for (int i = 0; i < this.floors; i++)

			for (int j = 0; j < this.rows; j++)

				CrossSectionByY[i][j] = this.getMaze()[i][j][y];

		return CrossSectionByY;

	}

	public int[][] getCrossSectionByZ(int z)

	{

		int[][] CrossSectionByZ = new int[this.rows][this.cols];

		for (int i = 0; i < this.rows; i++)

			for (int j = 0; j < this.cols; j++)

				CrossSectionByZ[i][j] = this.getMaze()[z][i][j];

		return CrossSectionByZ;

	}

	public byte[] toByteArray() {

		ArrayList<Byte> byteArrayList = new ArrayList<Byte>();
		byteArrayList.add((byte) this.floors);
		byteArrayList.add((byte) this.rows);
		byteArrayList.add((byte) this.cols);
		byteArrayList.add((byte) this.startPosition.x);
		byteArrayList.add((byte) this.startPosition.y);
		byteArrayList.add((byte) this.startPosition.z);
		byteArrayList.add((byte) this.goalPosition.x);
		byteArrayList.add((byte) this.goalPosition.y);
		byteArrayList.add((byte) this.goalPosition.z);

		/*
		 * System.out.println("Printing the byte...:"); for (byte number :
		 * byteArrayList) { System.out.println("Number = " + number); }
		 */

		for (int z = 0; z < floors; z++) {
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++)
					byteArrayList.add((byte) maze[z][y][x]);
			}

		}

		// Copy the array list to array of bytes

		byte[] bytes = new byte[byteArrayList.size()];
		for (int i = 0; i < byteArrayList.size(); i++) {
			bytes[i] = byteArrayList.get(i);
		}

		return bytes;

	}

}
