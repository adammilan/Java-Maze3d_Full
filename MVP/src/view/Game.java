package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import algorithms.mazeGenerator.Maze3d;
import algorithms.mazeGenerator.Position;
import algorithms_search.Solution;

public class Game {
	Maze2d maze2d;
	Maze3d maze3d;
	Position position;

	public Game(Maze2d maze2d, Maze3d maze3d) {
		this.maze2d = maze2d;
		this.maze3d = maze3d;
		this.position = new Position(0, 0, 0);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void startGame() {

		maze2d.setMazeData(maze3d.getCrossSectionByZ(maze3d.getStartPosition().z));
		maze2d.setGcPosition(maze3d.getStartPosition());
		maze2d.gc.setPosition(maze3d.getStartPosition().z, maze3d.getStartPosition().y, maze3d.getStartPosition().x);
		setPosition(maze3d.getStartPosition());
		maze2d.startGame();

		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {

				int key = e.keyCode;
				switch (key) {

				case SWT.ARROW_UP: {
					if (position.y - 1 >= 0 && maze3d.getCell(position.z, (position.y) - 1, position.x) == 0) {

						position.y = position.y - 1;
						maze2d.moveUp();

					}

				}
					break;
				case SWT.ARROW_DOWN: {
					if (position.y + 1 < maze3d.getRows()
							&& maze3d.getCell(position.z, (position.y) + 1, position.x) == 0) {

						position.y = position.y + 1;
						maze2d.moveDown();

					}

				}
					break;
				case SWT.ARROW_RIGHT: {
					if (position.x + 1 < maze3d.getCols()
							&& maze3d.getCell(position.z, (position.y), position.x + 1) == 0) {

						position.x = position.x + 1;
						maze2d.moveRight();

					}
				}
					break;
				case SWT.ARROW_LEFT: {
					if (position.x - 1 >= 0 && maze3d.getCell(position.z, (position.y), position.x - 1) == 0) {

						position.x = position.x - 1;
						maze2d.moveLeft();

					}
				}
					break;
				case SWT.PAGE_UP:
					if (position.z + 1 < maze3d.getFloors()
							&& maze3d.getCell(position.z + 1, position.y, (position.x)) == 0) {
						maze2d.setMazeData(maze3d.getCrossSectionByZ(position.z + 1));
						position.z++;
						maze2d.pageUp((position.z) + 1);

					}
					break;
				case SWT.PAGE_DOWN:

					if (position.z - 1 >= 0 && maze3d.getCell(position.z - 1, position.y, (position.x)) == 0) {
						maze2d.setMazeData(maze3d.getCrossSectionByZ(position.z - 1));
						position.z--;
						maze2d.pageDown((position.z) - 1);

					}
					break;

				}

			}
		};

		maze2d.addKeyListener(keyListener);

	}

	public void solveGame(Solution<Position> sol) {
		maze2d.printSolution(sol);

	}

}
