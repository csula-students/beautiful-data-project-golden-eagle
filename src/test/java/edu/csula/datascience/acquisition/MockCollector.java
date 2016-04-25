package edu.csula.datascience.acquisition;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A mock implementation of collector for testing
 */
public class MockCollector implements Collector<SimpleModel, MockData> {

/*	
    public Collection<SimpleModel> mungee(Collection<MockData> src) {
        // in your example, you might need to check src.hasNext() first
        return src
            .stream()
            .filter(data -> data.getContent() != null)
            .map(SimpleModel::build)
            .collect(Collectors.toList());
    }

    public void save(Collection<SimpleModel> data) {
    }
*/
	@Override
	public List<TweetModel> mungee(List<TweetModel> src) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(TweetModel data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TweetModel> getAllTweets() {
		// TODO Auto-generated method stub
		return null;
	}
}
