package algorithms.mazeGenerator;

import java.util.ArrayList;
import java.util.Random;

public class SimpleMaze3dGenerator extends Maze3dGeneratorBase {

	private Random rand = new Random(); // Random wall

	private static final float WALLS_RATIO = 0.5F;
	private static final float PATH_RATIO = 0.1F;

	ArrayList<Position> path = new ArrayList<>();

	@Override
	public Maze3d generate(int floors, int rows, int cols) {
		Maze3d maze3d = new Maze3d(floors, rows, cols);

		// fill the maze with 1 and 0
		int wallsNum = (int) (WALLS_RATIO * rows * cols * floors);
		for (int i = 0; i < wallsNum; i++) {
			int z = rand.nextInt(floors);
			int x = rand.nextInt(cols);
			int y = rand.nextInt(rows);

			maze3d.setWall(z, y, x);
		}

		// choosing a start position in the middle of the maze
		Position startPos = new Position(rand.nextInt(floors), rand.nextInt(rows), rand.nextInt(cols));
		// we might encounter a wall, lets free it.
		maze3d.setFree(startPos.z, startPos.y, startPos.x);
		maze3d.setStartPosition(startPos);
		path.add(startPos);

		Position oldPosition = startPos;
		Position newPosition = null;
		int stepsNum = (int) (PATH_RATIO * rows * cols);
		for (int i = 0; i < stepsNum; i++) {
			MoveType moveType = randomMoveType();
			switch (moveType) {
			case Left:
				newPosition = new Position(oldPosition.z, oldPosition.y, oldPosition.x - 1);
				break;
			case Right:
				newPosition = new Position(oldPosition.z, oldPosition.y, oldPosition.x + 1);
				break;
			case Up:
				newPosition = new Position(oldPosition.z, oldPosition.y - 1, oldPosition.x);
				break;
			case Down:
				newPosition = new Position(oldPosition.z, oldPosition.y + 1, oldPosition.x);
				break;
			case FloorUp:
				newPosition = new Position(oldPosition.z + 1, oldPosition.y, oldPosition.x);
				break;
			case FloorDown:
				newPosition = new Position(oldPosition.z - 1, oldPosition.y, oldPosition.x);
				break;
			}
			boolean isNewPositionInMaze = isNewPositionInMaze(maze3d, newPosition);
			boolean isNewPositionInPath = isPositionInPath(newPosition);
			if (isNewPositionInMaze && !isNewPositionInPath) {
				path.add(newPosition);
				maze3d.setFree(newPosition.z, newPosition.y, newPosition.x);
				oldPosition = newPosition;
			} else {
				// if we didn't succeed in creating a move, we stay on the same
				// i.
				i--;
			}
		}

		maze3d.setGoalPosition(newPosition);

		return maze3d;
	}

	private boolean isPositionInPath(Position newPosition) {
		return path.contains(newPosition);
	}

	private boolean isNewPositionInMaze(Maze3d maze3d, Position newPosition) {
		return newPosition.x >= 0 && newPosition.x < maze3d.getCols() && newPosition.y >= 0
				&& newPosition.y < maze3d.getRows() && newPosition.z >= 0 && newPosition.z < maze3d.getFloors();
	}

	private MoveType randomMoveType() {
		int pick = rand.nextInt(MoveType.values().length);
		return MoveType.values()[pick];
	}

}
