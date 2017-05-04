package algorithms.mazeGenerator;

import java.util.ArrayList;
import java.util.Random;

public class GrowingTreeGenerator extends Maze3dGeneratorBase {

	private Random rand = new Random();

	@Override
	public Maze3d generate(int floors, int rows, int cols) {

		Maze3d maze = new Maze3d(floors, rows, cols);

		ArrayList<Position> cells = new ArrayList<Position>();
		// Initialize all the maze with walls
		initialize(maze);

		// Choose a random starting cell (must be in an even row and column)
		Position startPos = chooseRandomPosition(maze);
		maze.setStartPosition(startPos);
		maze.setFree(startPos.z, startPos.y, startPos.x);

		cells.add(startPos);

		while (!cells.isEmpty()) {
			// Choose the last cell from the list
			// TODO: Enable the user to choose the selection method
			Position pos = cells.get(cells.size() - 1);

			// Find the unvisited neighbors of this cell
			ArrayList<Position> neighbors = findUnvisitedNeighbors(maze, pos);

			if (!neighbors.isEmpty()) {
				// Choose a random neighbor
				int idx = rand.nextInt(neighbors.size());
				Position neighbor = neighbors.get(idx);

				// Carve a passage between current cell and the neighbor
				carvePassageBetweenCells(maze, pos, neighbor);
				cells.add(neighbor);
			} else {
				cells.remove(pos);
			}
		}

		Position goalPosition = chooseRandomGoalPosition(maze);
		maze.setGoalPosition(goalPosition);

		return maze;
	}

	private void initialize(Maze3d maze) {
		for (int z = 0; z < maze.getFloors(); z++) {
			for (int y = 0; y < maze.getRows(); y++) {
				for (int x = 0; x < maze.getCols(); x++) {
					maze.setWall(z, y, x);
				}
			}
		}
	}

	private Position chooseRandomPosition(Maze3d maze3d) {
		int x = rand.nextInt(maze3d.getCols());
		/*
		 * while (x % 2 != 0) { x = rand.nextInt(maze3d.getCols()); }
		 */

		int y = rand.nextInt(maze3d.getRows());
		/*
		 * while (y % 2 != 0) {
		 * 
		 * y = rand.nextInt(maze3d.getRows()); }
		 */

		int z = rand.nextInt(maze3d.getFloors());
		/*
		 * while (z % 2 != 0) { z = rand.nextInt(maze3d.getFloors()); }
		 */

		return new Position(z, y, x);
	}

	private ArrayList<Position> findUnvisitedNeighbors(Maze3d maze, Position pos) {
		int[][][] mat = maze.getMaze();
		ArrayList<Position> neighbors = new ArrayList<Position>();

		if (pos.x - 2 >= 0 && mat[pos.z][pos.y][pos.x - 2] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y, pos.x - 2));
		}
		if (pos.x + 2 < maze.getCols() && mat[pos.z][pos.y][pos.x + 2] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y, pos.x + 2));
		}
		if (pos.y - 2 >= 0 && mat[pos.z][pos.y - 2][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y - 2, pos.x));
		}
		if (pos.y + 2 < maze.getRows() && mat[pos.z][pos.y + 2][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y + 2, pos.x));
		}
		if (pos.z - 2 >= 0 && mat[pos.z - 2][pos.y][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z - 2, pos.y, pos.x));
		}
		if (pos.z + 2 < maze.getFloors() && mat[pos.z + 2][pos.y][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z + 2, pos.y, pos.x));
		}

		return neighbors;
	}

	private void carvePassageBetweenCells(Maze3d maze, Position pos, Position neighbor) {
		if (neighbor.y == pos.y + 2) {
			maze.setFree(pos.z, pos.y + 1, pos.x);
			maze.setFree(pos.z, pos.y + 2, pos.x);
		} else if (neighbor.y == pos.y - 2) {
			maze.setFree(pos.z, pos.y - 1, pos.x);
			maze.setFree(pos.z, pos.y - 2, pos.x);
		} else if (neighbor.x == pos.x + 2) {
			maze.setFree(pos.z, pos.y, pos.x + 1);
			maze.setFree(pos.z, pos.y, pos.x + 2);
		} else if (neighbor.x == pos.x - 2) {
			maze.setFree(pos.z, pos.y, pos.x - 1);
			maze.setFree(pos.z, pos.y, pos.x - 2);
		} else if (neighbor.z == pos.z + 2) {
			maze.setFree(pos.z + 1, pos.y, pos.x);
			maze.setFree(pos.z + 2, pos.y, pos.x);
		} else if (neighbor.z == pos.z - 2) {
			maze.setFree(pos.z - 1, pos.y, pos.x);
			maze.setFree(pos.z - 2, pos.y, pos.x);
		}
	}

	private Position chooseRandomGoalPosition(Maze3d maze) {
		int[][][] mat = maze.getMaze();

		int z = rand.nextInt(maze.getFloors());
		int x = rand.nextInt(maze.getCols());
		int y = rand.nextInt(maze.getRows());
		while (mat[z][y][x] == Maze3d.WALL) {
			z = rand.nextInt(maze.getFloors());
			x = rand.nextInt(maze.getCols());
			y = rand.nextInt(maze.getRows());
		}

		return new Position(z, y, x);
	}
}
