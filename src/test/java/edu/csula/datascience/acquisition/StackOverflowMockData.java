package edu.csula.datascience.acquisition;


/**
 * StackOverflowMock raw data
 */
public class StackOverflowMockData {
    private final String questionid;
    private final String question;

    public StackOverflowMockData(String questionid, String question) {
        this.questionid = questionid;
        this.question = question;
    }

    public String getquestionId() {
        return questionid;
    }

    public String getQuestion() {
        return question;
    }
}
