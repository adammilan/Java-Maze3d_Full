package algorithem.Demo;

import algorithms.mazeGenerator.Maze3d;
import algorithms.mazeGenerator.Position;
import algorithms_search.Searchable;
import algorithms_search.State;

import java.util.ArrayList;
import java.util.List;

public class MazeAdapter implements Searchable<Position> {

	Maze3d myMaze;

	public MazeAdapter(Maze3d maze) {
		myMaze = new Maze3d(maze);
		myMaze.setStartPosition(maze.getStartPosition());
		myMaze.setGoalPosition(maze.getGoalPosition());
	}

	@Override
	public State<Position> getStartState() {
		Position p = new Position(myMaze.getStartPosition());// Create to a new
																// position with
																// the c'py
																// cons..
		State<Position> s = new State<Position>(p);
		return s;
	}

	@Override
	public State<Position> getGoalState() {
		Position p = new Position(myMaze.getGoalPosition());// Create to a new
															// position with the
															// c'py cons..
		State<Position> s = new State<Position>(p);
		return s;
	}

	@Override
	public List<State<Position>> getAllPossibleStates(State<Position> s) {

		List<State<Position>> mazeList = new ArrayList<State<Position>>();
		Position p = new Position(s.getValue());
		if (!myMaze.wallExist(p.UP()))
			mazeList.add(new State<Position>(p.UP()));
		if (!myMaze.wallExist(p.DOWN()))
			mazeList.add(new State<Position>(p.DOWN()));
		if (!myMaze.wallExist(p.RIGHT()))
			mazeList.add(new State<Position>(p.RIGHT()));
		if (!myMaze.wallExist(p.LEFT()))
			mazeList.add(new State<Position>(p.LEFT()));
		if (!myMaze.wallExist(p.FORWARD()))
			mazeList.add(new State<Position>(p.FORWARD()));
		if (!myMaze.wallExist(p.BACKWORD()))
			mazeList.add(new State<Position>(p.BACKWORD()));

		// myMaze.setWall(p);
		return mazeList;

	}

	@Override
	public double getMoveCost(State<Position> currState, State<Position> neighbor) {
		return 1;
	}
}
