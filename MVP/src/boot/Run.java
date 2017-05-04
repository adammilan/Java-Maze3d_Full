package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;
import view.MyView;

public class Run {

	/**
	 * Run function creating the View,Model,and presenter
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);

		MyModel m = new MyModel(); // he loads the Properties also

		/**
		 * CLI
		 */
		if (args.length > 0 && args[0].equals("CLI")) {

			MyView ui = new MyView(in, out);

			Presenter p = new Presenter(ui, m);
			ui.addObserver(p);
			m.addObserver(p);

			ui.start();

		}

		/**
		 * GUI
		 */

		else {

			MazeWindow v = new MazeWindow("maze", 1550, 750);
			Presenter p = new Presenter(v, m);
			v.addObserver(p);
			m.addObserver(p);
			v.run();

		}

	}

}
