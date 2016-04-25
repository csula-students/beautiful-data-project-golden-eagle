package edu.csula.datascience.acquisition;

import java.util.List;
import java.util.regex.Pattern;

public class MockTweetCollector implements TestCollectorInt<MockTweetData> {

	@Override
	public List<MockTweetData> mungee(List<MockTweetData> src) {
		
		String pattern = "(https?:\\/\\/(?:www\\.|(?!www))[^\\s\\.]+\\.[^\\s]{2,}|www\\.[^\\s]+\\.[^\\s]{2,})";
		
		for (int i = 0; i < src.size(); i++) {
			//Remove record if field contains null
			if (src.get(i).getContent() == null || src.get(i).getProfileLocation() == null 
					|| src.get(i).getUsername() == null ) {
				src.remove(i);
			}
			// Check if the Data contains only links
			// The regex will be updated in future to remove special characters and more
			if (src.get(i).getContent().matches(pattern)) {
				src.remove(i);
			}
		}
		
		return src;		
	}
}
