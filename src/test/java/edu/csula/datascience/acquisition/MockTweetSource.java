package edu.csula.datascience.acquisition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public class MockTweetSource implements Source<MockTweetData>{

	int position = 0;
	
	@Override
	public boolean hasNext() {
		return position < 1;
	}

	public List<TweetModel> getNext() {
		List<TweetModel> tweets = new ArrayList<TweetModel>();
		
		TweetModel data1 = new TweetModel("tom", "USA", 1, "Test Content 1");
		TweetModel data2 = new TweetModel("sammy", null, 2, "Test Content 2");
		TweetModel data3 = new TweetModel("John", "Canada", 3, "Test Content 3");
		TweetModel data4 = new TweetModel("Tony", "Mexico", 4, "http://www.foufos.gr");
		
		tweets.add(data1);
		tweets.add(data2);
		tweets.add(data3);
		tweets.add(data4);
		
	    return tweets;
	}

	@Override
	public Collection<MockTweetData> next() {
		return null;
	}

}
