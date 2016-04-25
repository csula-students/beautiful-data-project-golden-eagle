package edu.csula.datascience.acquisition;

import java.util.List;

public interface TestCollectorInt<T> {
	
	List<MockTweetData> mungee(List<MockTweetData> src);
}
