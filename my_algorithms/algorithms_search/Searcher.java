package algorithms_search;

public interface Searcher<T> {
	public Solution<T> search(Searchable<T> s);

	public int getNumberOfNodesEvaluated();
}
