package properties;

import java.io.Serializable;

public class Properties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private String generateMazeAlgorithm; // GrwoingTree or SimpleMazeGene
	private String solveMazeAlgorithm; // BFS or DFS

	public int getNumOfThreads() {
		return numOfThreads;
	}

	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	public String getGenerateMazeAlgorithm() {
		return generateMazeAlgorithm;
	}

	/**
	 * options: GrowingTree, Simple
	 */
	public void setGenerateMazeAlgorithm(String generateMazeAlgorithm) {
		this.generateMazeAlgorithm = generateMazeAlgorithm;
	}

	public String getSolveMazeAlgorithm() {
		return solveMazeAlgorithm;
	}

	/**
	 * options: BFS, DFS
	 */
	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}

}