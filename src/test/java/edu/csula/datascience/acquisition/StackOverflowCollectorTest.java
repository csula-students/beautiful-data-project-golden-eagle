package edu.csula.datascience.acquisition;




import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;



/**
 * A test case to show how to use Collector and Source
 */
public class StackOverflowCollectorTest {
    private Collector<StackOverflowModel, StackOverflowMockData> collector;
    private Source<StackOverflowMockData> source;

    @Before
    public void setup() {
        collector = new StackOverflowMockCollector();
        source = new StackOverflowMockSource();
    }

    @Test
    public void mungee() throws Exception {
        List<StackOverflowModel> list = (List<StackOverflowModel>) collector.mungee(source.next());
        List<StackOverflowModel> expectedList = Lists.newArrayList(
            new StackOverflowModel("2", "How to parse a JSON string into JsonNode in Jackson?"),
            new StackOverflowModel("3", "How to iterate json array?")
        );

        Assert.assertEquals(list.size(), 2);

        for (int i = 0; i < 2; i ++) {
            Assert.assertEquals(list.get(i).getquestionId(), expectedList.get(i).getquestionId());
            Assert.assertEquals(list.get(i).getQuestion(), expectedList.get(i).getQuestion());
        }
    }
}