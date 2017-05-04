package Boot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algorithem.Demo.MazeAdapter;
import algorithms.mazeGenerator.GrowingTreeGenerator;
import algorithms.mazeGenerator.Maze3d;
import algorithms.mazeGenerator.Position;
import algorithms.mazeGenerator.SimpleMaze3dGenerator;
import algorithms_search.BFS;
import algorithms_search.DFS;
import algorithms_search.Searchable;
import algorithms_search.Searcher;
import algorithms_search.Solution;
import algorithms_search.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/*	public static void main(String[] args){

	GrowingTreeGenerator gen = new GrowingTreeGenerator();
	Maze3d maze = gen.generate(2,5,5);
	System.out.println(maze);
	private static void testMazeGenerator(Maze3dGenerator mg) {
		// prints the time it takes the algorithm to run
		System.out.println(mg.measureAlgorithmTime(100,100,100));
		// generate another 3d maze
		Maze3d maze = mg.generate(3,3,3);
		// get the maze entrance
		Position p = maze.getStartPosition();
		// print the position
		System.out.println(p); // format "{x,y,z}"
		// get all the possible moves from a position
		//String[] moves = maze.getPossibleMoves(p);
		// print the moves
		for (String move : moves)
			System.out.println(move);
		// prints the maze exit position
		System.out.println(maze.getGoalPosition());

		try {
			// get 2d cross sections of the 3d maze
			int[][] maze2dx = maze.getCrossSectionByX(2);
			// TODO add code to print the array
			int[][] maze2dy = maze.getCrossSectionByY(5);
			// TODO add code to print the array
			int[][] maze2dz = maze.getCrossSectionByZ(0);
			// TODO add code to print the array
			// this should throw an exception!
			maze.getCrossSectionByX(-1);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("good!");
		}
	}
	}





	testMazeGenerator(new SimpleMaze3dGenerator());
		testMazeGenerator(new GrowingTreeGenerator());

	}
 */public class Run {

	public static void run() throws Exception {

		SimpleMaze3dGenerator SMG = new SimpleMaze3dGenerator();
		Maze3d myMaze = SMG.generate(2, 7, 11);
		//2 floors z ,7 (x)  on (y) 11
		
		System.out.println("\n" + "MAZE:" + "\n" + myMaze.toString());

		System.out.println("Enter:" + myMaze.getStartPositionString());
		System.out.println("Exit:" + myMaze.getGoalPositionString());

		// myMaze.toString();

		State<Position> StartState = new State<Position>(myMaze.getStartPosition());
		State<Position> EndState = new State<Position>(myMaze.getGoalPosition());

		Searchable<Position> mts = new MazeAdapter(myMaze);

		Searcher<Position> searcherDFS = new DFS<Position>();
		Searcher<Position> searcherBFS = new BFS<Position>();

		printAlgorithmOutput(searcherBFS, mts);
		printAlgorithmOutput(searcherDFS, mts);

		// GENERATE A NEW MAZE - testing
		// SimpleMaze3dGenerator SMGByte = new SimpleMaze3dGenerator();

		// save it to a file
		OutputStream out;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream("1.maz"));
			/* byte[] arr = myMaze.toByteArray(); */

			// out.write(arr.length);
			out.write(myMaze.toByteArray());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InputStream in;
		try {
			in = new MyDecompressorInputStream(new FileInputStream("1.maz"));

			/* int size = in.read(); */
			byte b[] = new byte[myMaze.toByteArray().length];

			in.read(b);
			in.close();

			Maze3d loaded = new Maze3d(b);
			loaded.setStartPosition(myMaze.getStartPosition());
			loaded.setGoalPosition(myMaze.getGoalPosition());

			System.out.println("maze loaded from file:");
			// if (loaded.equals(myMaze))
			System.out.println(loaded.toString());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void printAlgorithmOutput(Searcher<Position> searcher, Searchable<Position> mts) {
		Solution<Position> solution = searcher.search(mts);
		if (solution != null) {
			String s = solution.toString();
			System.out.print(s);
		}
		System.out.println(searcher.getClass().getName() + ":" + searcher.getNumberOfNodesEvaluated());
	}

	public static void main(String[] args) throws Exception {
		run();
	}
}
