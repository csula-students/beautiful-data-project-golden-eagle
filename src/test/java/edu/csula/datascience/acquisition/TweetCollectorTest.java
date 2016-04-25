package edu.csula.datascience.acquisition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class TweetCollectorTest {
	 private Collector<SimpleTweetModel, MockTweetData> collector;
	 private MockTweetSource source;
	 
	@Before
    public void setup() {
        collector = new MockTweetCollector();
        source = new MockTweetSource();
    }
	
	@Test
    public void mungee() throws Exception {
		List<TweetModel> getTweets = new ArrayList<TweetModel>();
		
		getTweets.addAll(collector.mungee(source.getNext()));
		

      List<SimpleTweetModel> expectedList = Lists.newArrayList(
          new SimpleTweetModel("tom", "USA", 1, "Test Content 1"),
          new SimpleTweetModel("John", "Canada", 3, "Test Content 3")
      );

      Assert.assertEquals(getTweets.size(), 2);
        for (int i = 0; i < 2; i ++) {
           Assert.assertEquals(getTweets.get(i).getTweetId(), expectedList.get(i).getTweetId());
           Assert.assertEquals(getTweets.get(i).getUsername(), expectedList.get(i).getUsername());
           Assert.assertEquals(getTweets.get(i).getProfileLocation(), expectedList.get(i).getProfileLocation());
           Assert.assertEquals(getTweets.get(i).getContent(), expectedList.get(i).getContent());
           
       //    System.out.println("Original" + getTweets.get(i).getTweetId() + " Expected " + expectedList.get(i).getTweetId());
       }
    }
}
