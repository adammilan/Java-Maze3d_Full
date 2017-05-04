package view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerator.Maze3d;
import algorithms.mazeGenerator.Position;
import algorithms_search.Solution;

/**
 * responsible on the maze display Own all the methods that supplies the maze
 * display components
 * 
 * @author ben & adam
 *
 */
public abstract class MazeDisplay extends Canvas {

	GameCharacter gc;
	int[][] mazeData;
	Maze3d maze3d;
	String characterPosition;

	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		gc = new GameCharacter(0, 0, 0);

	}

	public Maze3d getMaze3d() {
		return maze3d;
	}

	public void setMaze3d(Maze3d maze3d) {
		this.maze3d = maze3d;
	}

	public int[][] getMazeData() {
		return mazeData;
	}

	public void setMazeData(int[][] mazeData) {

		this.mazeData = mazeData;
	}

	public void setGcPosition(Position p) {

		gc.setPosition(p.z, p.y, p.x);
	}

	public void setGc(GameCharacter gc) {
		this.gc = gc;
	}

	public GameCharacter getGc() {
		return this.gc;
	}

	public MazeDisplay(Composite parent, int style, int x) {
		super(parent, style);
		setBackground(new Color(null, 255, 255, 255));
		gc = new GameCharacter(200, 1, 1);

		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {

	/*			 setForeground(new Color(null,0,0,0));
				
				  
				  e.gc.setForeground(new Color(null,0,0,0));
				  e.gc.setBackground(new Color(null,0,0,0));
				  
				  int width=getSize().x; int height=getSize().y;
				  
				  int w=width/mazeData[0].length; int h=height/mazeData.length;
				  
				  for(int i=0;i<mazeData.length;i++) for(int
				  j=0;j<mazeData[i].length;j++){ int x=j*w; int y=i*h;
				  if(mazeData[i][j]!=0) e.gc.fillRectangle(x,y,w,h); }
				 */
				// getGc().setPosition(100, 100);
				
				getGc().paint(e, 200, 100);

			}

		});

	}// end of constructor

	public abstract void moveUp();

	public abstract void moveDown();

	public abstract void moveLeft();

	public abstract void moveRight();

	public abstract void pageUp(int floor);

	public abstract void pageDown(int floor);

	public abstract void printSolution(Solution<Position> sol);

}
