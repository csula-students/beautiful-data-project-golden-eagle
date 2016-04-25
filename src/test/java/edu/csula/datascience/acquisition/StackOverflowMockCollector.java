package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * A mock implementation of collector for testing
 */
public class StackOverflowMockCollector implements Collector<StackOverflowModel, StackOverflowMockData> {
	@Override
	public Collection<StackOverflowModel> mungee(Collection<StackOverflowMockData> src) {
		// in your example, you might need to check src.hasNext() first
		return src.stream().filter(data -> data.getQuestion() != null)
				.map(StackOverflowModel::build).collect(Collectors.toList());
	}

	@Override
	public void save(Collection<StackOverflowModel> data) {
	}

}
