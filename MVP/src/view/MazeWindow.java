package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerator.GrowingTreeGenerator;
import algorithms.mazeGenerator.Maze3d;
import algorithms.mazeGenerator.Position;
import algorithms_search.Solution;
import presenter.Command;
import properties.Properties;
import properties.PropertiesLoader;

/**
 * This class extends BasicWindow implements View responsible on the program
 * display
 * 
 * @author ben & adam
 *
 */
public class MazeWindow extends BasicWindow implements View {

	Maze3d maze3d;
	Maze2d maze2d;
	Game game;
	Spinner SpinnerFloors;
	Spinner SpinnerRows;
	Spinner SpinnerCols;
	private Button GrowingTreeGenerator;
	private Button Simple;
	// private Composite groupSolution;
	private Combo comboSolve;
	String[] algorithmsSolve = { "BFS", "DFS" };
	private Button solve;
	private Button save;
	private Menu menuBar;
	private MenuItem fileMenuHeader;
	private Menu fileMenu;
	// private MenuItem filePropertiesItem;
	private MenuItem fileSaveItem;
	private MenuItem fileLoadItem;

	Image back;
	Image dots;
	private Button Properties;
	private Text textName;
	private Button exit;

	public Maze3d getMaze3d() {
		return maze3d;
	}

	public void setMaze3d(Maze3d maze) {
		this.maze3d = maze;
	}

	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
	}

	/**
	 * 
	 * All the model's methods that responsible of the commands coming from the
	 * presenter At the end of each method, setChange() and motifyObservers()
	 * are call and the presenter get updated about the changes that were done
	 */

	@Override
	public void notifySolutionIsReady(String name) {

		displayMessageBox(name + " solution is ready", "Message");

	}

	@Override
	public void notifyMazeIsReady(String name) {
	}

	@Override
	public void displayMaze(Maze3d maze) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					setMaze3d(maze);
					maze2d.setMaze3d(maze);
					game = new Game(maze2d, maze);
					game.startGame();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
	}

	@Override
	public void display_cross_section(int[][] maze2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCommands(HashMap<String, Command> command) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display_solution(Solution<Position> solve) {

		game.solveGame(solve);
	}

	@Override
	public void view_path(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayMessage(String msg) {
		displayMessageBox(msg, "Message");
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));

		Group groupTextName = new Group(shell, SWT.BORDER);
		groupTextName.setLayout(new GridLayout(2, true));
		groupTextName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(groupTextName, SWT.NONE).setText("Name");
		textName = new Text(groupTextName, SWT.BORDER);

		maze2d = new Maze2d(shell, SWT.BORDER);
		maze2d.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 8));

		Button generate = new Button(shell, SWT.PUSH);
		generate.setText("Start");
		generate.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		MazeSettings();

		Properties.addSelectionListener(new SelectionListener() {

			private String fileName;

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String arr = "properties_msg";
				setChanged();
				notifyObservers(arr);

				Properties properties = PropertiesLoader.getInstance().getProperties();

				// clean selection
				GrowingTreeGenerator.setSelection(false);
				Simple.setSelection(false);

				// set the right selection
				if (properties.getGenerateMazeAlgorithm().equals("GrowingTree")) {
					GrowingTreeGenerator.setSelection(true);
				} else if (properties.getGenerateMazeAlgorithm().equals("Simple")) {
					Simple.setSelection(true);
				}
				if (properties.getSolveMazeAlgorithm().equals("BFS")) {
					comboSolve.select(0);
				} else if (properties.getSolveMazeAlgorithm().equals("DFS")) {
					comboSolve.select(1);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});

		exit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setChanged();
				notifyObservers("exit");

				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		generate.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if (textName.getText().equals(""))

					displayMessageBox("please insert name", "Error");

				else {

					dots = new Image(display, "src/images/dots.png");

					// maze2d.setLayoutData(new GridData(SWT.DOWN, SWT.DOWN,
					// false, false, 1, 10));
					maze2d.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
					maze2d.setFocus();

					String generateCommand = "generate_maze " + textName.getText() + " " + SpinnerFloors.getText() + " "
							+ SpinnerRows.getText() + " " + SpinnerCols.getText();
					solve.setEnabled(true);

					sendCommandArgsToPresenter(generateCommand);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		solve.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				String arr = "solve " + textName.getText() + " " + comboSolve.getText();

				setChanged();
				notifyObservers(arr);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		fileSaveItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] { "Batch Files", "All Files (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.*" }); // Windows
				// wild
				// cards
				dialog.setFilterPath("c:\\"); // Windows path
				dialog.setFileName("MyMaze");
				// System.out.println("Save to: " + dialog.open());
				// while (!shell.isDisposed()) {
				// if (!display.readAndDispatch())
				// display.sleep();
				// }
				// display.dispose();
				String arr = "save_maze " + textName.getText() + " " + textName.getText();

				/*
				 * String Line = dialog.getFilterPath(); String[] dirs =
				 * Line.split(Pattern.quote(File.separator)); String path = new
				 * String(); for (int i = 0; i < dirs.length; i++) { path =
				 * path.concat(dirs[i] + "/"); } path =
				 * path.concat(dialog.getFileName());
				 */
				// arr.add(path);

				setChanged();
				notifyObservers(arr);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		fileLoadItem.addSelectionListener(new SelectionListener() {

			private String fileName;

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setText("Load");
				// dialog.setFilterNames(new String[] { "Batch Files", "All
				// Files (*.*)" });
				dialog.setFilterPath("");
				String[] filterExt = { "*" };
				dialog.setFilterExtensions(filterExt);

				fileName = dialog.open();

				// dialog.setFilterPath("c:\\");
				// dialog.setFileName("MyMaze");
				// ArrayList<String> arr = new ArrayList<String>();
				// String arr = "load_maze " + "FilesTestingSave" + " " +
				// textName.getText();

				String arr = "load_maze " + dialog.getFileName() + " " + dialog.getFileName();

				/*
				 * String Line = dialog.getFilterPath(); String[] dirs =
				 * Line.split(Pattern.quote(File.separator)); String path = new
				 * String(); for (int i = 0; i < dirs.length; i++) { path =
				 * path.concat(dirs[i] + "/"); } path =
				 * path.concat(dialog.getFileName());
				 */

				setChanged();
				notifyObservers(arr);
				solve.setEnabled(true);
				textName.setText(dialog.getFileName());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void MazeSettings() {
		// set Floors
		Group groupSizeMaze = new Group(shell, SWT.BORDER);
		groupSizeMaze.setLayout(new GridLayout(6, false));
		groupSizeMaze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(groupSizeMaze, SWT.NONE).setText("Floors:");
		SpinnerFloors = new Spinner(groupSizeMaze, SWT.BORDER);
		SpinnerFloors.setMinimum(1);
		SpinnerFloors.setMaximum(40);
		SpinnerFloors.setSelection(8);
		SpinnerFloors.setIncrement(1);
		SpinnerFloors.setPageIncrement(100);
		SpinnerFloors.pack();

		// setRows
		new Label(groupSizeMaze, SWT.NONE).setText("Rows:");
		SpinnerRows = new Spinner(groupSizeMaze, SWT.BORDER);
		SpinnerRows.setMinimum(3);
		SpinnerRows.setMaximum(40);
		SpinnerRows.setSelection(8);
		SpinnerRows.setIncrement(1);
		SpinnerRows.setPageIncrement(100);
		SpinnerRows.pack();

		// Set Cols
		new Label(groupSizeMaze, SWT.NONE).setText("Columns:");
		SpinnerCols = new Spinner(groupSizeMaze, SWT.BORDER);
		SpinnerCols.setMinimum(3);
		SpinnerCols.setMaximum(40);
		SpinnerCols.setSelection(8);
		SpinnerCols.setIncrement(1);
		SpinnerCols.setPageIncrement(100);
		SpinnerCols.pack();

		Group groupTypeMaze = new Group(shell, SWT.BORDER);
		groupTypeMaze.setLayout(new GridLayout(2, true));
		groupTypeMaze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		groupTypeMaze.setText("Maze Generation Algoritm : ");
		GrowingTreeGenerator = new Button(groupTypeMaze, SWT.RADIO);
		GrowingTreeGenerator.setText("GrowingTreeGenerator");
		GrowingTreeGenerator.setSelection(true);
		Simple = new Button(groupTypeMaze, SWT.RADIO);
		Simple.setText("SimpleMaze");

		Group groupSolution = new Group(shell, SWT.BORDER);
		groupSolution.setLayout(new GridLayout(1, true));
		groupSolution.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(groupSolution, SWT.NONE).setText("Maze Solution Algorithm :");

		comboSolve = new Combo(groupSolution, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboSolve.setItems(algorithmsSolve);
		comboSolve.select(0);
		comboSolve.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		solve = new Button(groupSolution, SWT.PUSH);
		solve.setText("Solve");
		solve.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		solve.setEnabled(false);

		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);

		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		/*
		 * filePropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
		 * filePropertiesItem.setText("&Properties");
		 */

		fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("&Save");

		fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
		fileLoadItem.setText("&Load");

		Properties = new Button(shell, SWT.PUSH);
		Properties.setText("insert properties");
		Properties.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		exit = new Button(shell, SWT.PUSH);
		exit.setText("Exit");
		exit.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

	}

	public void sendCommandArgsToPresenter(String commandArgs) {
		setChanged();
		notifyObservers(commandArgs);
	}

	public void displayMessageBox(String s, String title) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_WORKING | SWT.OK);
				messageBox.setText(title);
				messageBox.setMessage(s);
				messageBox.open();
			}
		});
	}

}
