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

	public List<MockTweetData> getNext() {
		List<MockTweetData> tweets = new ArrayList<MockTweetData>();
		
		MockTweetData data1 = new MockTweetData("tom", "USA", 1, "Test Content 1");
		MockTweetData data2 = new MockTweetData("sammy", null, 2, "Test Content 2");
		MockTweetData data3 = new MockTweetData("John", "Canada", 3, "Test Content 3");
		MockTweetData data4 = new MockTweetData("Tony", "Mexico", 4, "http://www.foufos.gr");
		
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
