package algorithms_search;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DFS<T> extends CommonSearcher<T> {

	ArrayList<State<T>> close = new ArrayList<State<T>>();

	@Override
	public Solution<T> search(Searchable<T> s) {
		State<T> goalState = s.getGoalState();
		State<T> startState = s.getStartState();

		PriorityQueue<State<T>> open = new PriorityQueue<State<T>>();

		open.add(startState);

		while (!open.isEmpty()) {
			State<T> current = open.poll();
			evaluatedNodes++;
			if (current.equals(goalState)) {
				return backTrace(current);
			}

			List<State<T>> neighbors = s.getAllPossibleStates(current);

			for (State<T> n : neighbors) {
				if (!close.contains(n) && !open.contains(n)) {
					open.add(n);
					n.setCameFrom(current);
					n.setCost(n.getCost() + s.getMoveCost(current, n));
				}
			}
			close.add(current);
		}

		return null;

	}
}