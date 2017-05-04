package view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import algorithms.mazeGenerator.Maze3d;
import algorithms.mazeGenerator.Position;
import algorithms_search.Solution;

/**
 * This class extends MazeDisplay responsible on 1 floor of the maze display
 * 
 * @author ben & adam
 *
 */
public class Maze2d extends MazeDisplay {

	Maze3d maze3d;
	Position correct, goal, start;
	Image lamp, arrowUp, arrowDown, arrowUpDown, goalPic, victory, b, dots, startDot;
	Image palace;

	/**
	 * constructor
	 * 
	 * @param parent
	 * @param style
	 */
	public Maze2d(Composite parent, int style) {
		super(parent, SWT.DOUBLE_BUFFERED);

		
		arrowUp = new Image(getDisplay(), "src/images/arrowUp.png");
		arrowDown = new Image(getDisplay(), "src/images/arrowDown.png");
		arrowUpDown = new Image(getDisplay(), "src/images/arrowUpDown.png");
		lamp = new Image(getDisplay(), "src/images/lamp.jpg");
		victory = new Image(getDisplay(), "src/images/victory.png");
		palace = new Image(getDisplay(), "src/images/palace.jpg");
		dots = new Image(getDisplay(), "src/images/dots.png");
		startDot = new Image(getDisplay(), "src/images/startDot.png");
		b = new Image(getDisplay(), "src/images/aladin.gif");

	}

	public void startGame() {
		setBackground(new Color(null, 255, 255, 255));
		addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				/*
				 * goal=maze3d.getGoalPosition();
				 * start=maze3d.getStartPosition();
				 */

				e.gc.setForeground(new Color(null, 0, 0, 0));
				e.gc.setBackground(new Color(null, 0, 0, 0));
				int width = getSize().x;
				int height = getSize().y;
				int w = width / mazeData[0].length;
				int h = height / mazeData.length;

				// gc.paint(e, w, h);

				for (int i = 0; i < mazeData.length; i++) {
					for (int j = 0; j < mazeData[i].length; j++) {
						int x = j * w;
						int y = i * h;

						setBackgroundImage(palace);
						if (mazeData[i][j] != 0) {

						}
						// e.gc.drawImage(palace, 0, 0, 275, 183, x, y, w, h);

						else {
							dots = new Image(getDisplay(), "src/images/dots.png");
							e.gc.drawImage(dots, 0, 0, 685, 701, x, y, w, h);

							// e.gc.drawImage(dots, x, y);
						}

						// else
						//

						if ((gc.getZ() + 1 < maze3d.getFloors() && maze3d.getCell(gc.getZ(), i, j) == 0
								&& maze3d.getCell(gc.getZ() + 1, i, j) == 0)
								&& (gc.getZ() - 1 > 0 && maze3d.getCell(gc.getZ(), i, j) == 0
										&& maze3d.getCell(gc.getZ() - 1, i, j) == 0))
							e.gc.drawImage(arrowUpDown, 0, 0, 685, 701, x, y, w, h);
						else {
							if (gc.getZ() + 1 < maze3d.getFloors() && maze3d.getCell(gc.getZ(), i, j) == 0
									&& maze3d.getCell(gc.getZ() + 1, i, j) == 0)
								e.gc.drawImage(arrowUp, 0, 0, 685, 701, x, y, w, h);
							if (gc.getZ() - 1 > 0 && maze3d.getCell(gc.getZ(), i, j) == 0
									&& maze3d.getCell(gc.getZ() - 1, i, j) == 0)
								e.gc.drawImage(arrowDown, 0, 0, 685, 701, x, y, w, h);
						}

					}
				}

				if (gc.getZ() == maze3d.getGoalPosition().z) {
					e.gc.drawImage(lamp, 0, 0, 192, 298, maze3d.getGoalPosition().x * w, maze3d.getGoalPosition().y * h,
							w, h);

				}

				if (gc.getZ() == maze3d.getStartPosition().z) {
					e.gc.drawImage(startDot, 0, 0, 685, 701, maze3d.getStartPosition().x * w,
							maze3d.getStartPosition().y * h, w, h);

				}

				if (gc.getGcPosition().equals(maze3d.getGoalPosition())) {
					e.gc.drawImage(victory, 0, 0, 248, 203, width / 5, height / 5, 250, 250);
				}
				e.gc.drawImage(b, 0, 0, 500, 598, gc.x * w, gc.y * h, w, h);

			}
		});
	}

	public void setCharacterPosition(int row, int col) {
		gc.setY(row);
		gc.setX(col);
		redraw();
	}

	public Maze3d getMaze3d() {
		return maze3d;
	}

	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
	}

	@Override
	public void moveUp() {
		setCharacterPosition(gc.getY() - 1, gc.getX());

	}

	@Override
	public void moveDown() {
		setCharacterPosition(gc.getY() + 1, gc.getX());

	}

	@Override
	public void moveLeft() {
		setCharacterPosition(gc.getY(), gc.getX() - 1);

	}

	@Override
	public void moveRight() {
		setCharacterPosition(gc.getY(), gc.getX() + 1);

	}

	@Override
	public void pageUp(int floor) {
		gc.setZ(gc.getZ() + 1);
		redraw();
	}

	@Override
	public void pageDown(int floor) {
		gc.setZ(gc.getZ() - 1);
		redraw();
	}

	@Override
	public void printSolution(Solution<Position> sol) {

		System.out.println(maze3d.getStartPosition());
		System.out.println(maze3d.getGoalPosition());
		// System.out.println(sol.getStates());
		gc.setPosition(maze3d.getStartPosition().z, maze3d.getStartPosition().y, maze3d.getStartPosition().x);
		setMazeData(maze3d.getCrossSectionByZ(gc.getZ()));

		ArrayList<Position> arr = new ArrayList<Position>();
		for (int i = 0; i < sol.getStates().size(); i++) {
			arr.add((Position) sol.getStates().get(i).getValue());
		}
		Collections.reverse(arr);

		getDisplay().asyncExec(new Runnable() {
			public void run() {
				getDisplay().timerExec(200, new Runnable() {
					int index = (arr.size()) - 1;

					@Override
					public void run() {

						if (gc.getZ() < arr.get(index).z) {
							setMazeData(maze3d.getCrossSectionByZ(gc.getZ() + 1));
							System.out.println("Floor Up");
							pageUp(gc.getZ() + 1);
							redraw();
						}

						else if (gc.getZ() > arr.get(index).z) {
							setMazeData(maze3d.getCrossSectionByZ(gc.getZ() - 1));
							System.out.println("Floor Down");
							pageDown(gc.getZ() - 1);
							redraw();
						} else {
							if (gc.getX() < arr.get(index).x) {
								System.out.println("move Right");
								moveRight();
							}

							if (gc.getX() > arr.get(index).x) {
								System.out.println("move Left");
								moveLeft();

							}
							if (gc.getY() > arr.get(index).y) {
								System.out.println("move Up");
								moveUp();

							}
							if (gc.getY() < arr.get(index).y) {
								System.out.println("move Down");
								moveDown();

							}

						}
						getDisplay().timerExec(200, this);

						index--;

						if (index == -1) {
							getDisplay().timerExec(-1, this);

						}
					}

				});
			}
		});

	}

}
