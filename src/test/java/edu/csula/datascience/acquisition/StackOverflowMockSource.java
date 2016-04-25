package edu.csula.datascience.acquisition;


import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * A mock source to provide data
 */
public class StackOverflowMockSource implements Source<StackOverflowMockData> {
    int index = 0;

    @Override
    public boolean hasNext() {
        return index < 1;
    }

    @Override
    public Collection<StackOverflowMockData> next() {
        return Lists.newArrayList(
            new StackOverflowMockData("1", null),
            new StackOverflowMockData("2", "How to parse a JSON string into JsonNode in Jackson?"),
            new StackOverflowMockData("3", "How to iterate json array?")
           
        );
    }
}

