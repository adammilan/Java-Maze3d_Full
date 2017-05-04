package algorithms.mazeGenerator;

public abstract class Maze3dGeneratorBase implements Maze3dGenerator {

	protected boolean isDone = false;

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	@Override
	public String measureAlgorithmTime(int floors, int rows, int cols) {
		long startTime = System.currentTimeMillis();

		this.generate(floors, rows, cols);
		long endTime = System.currentTimeMillis();
		return String.valueOf(endTime - startTime);
	}

}
