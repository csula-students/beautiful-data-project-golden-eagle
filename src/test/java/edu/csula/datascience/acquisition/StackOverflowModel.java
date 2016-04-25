package edu.csula.datascience.acquisition;


/**
 * A StackOverflowModel model for testing
 */
public class StackOverflowModel {
    private final String questionid;
    private final String question;

    public StackOverflowModel(String QuestionId, String Question) {
        this.questionid = QuestionId;
        this.question = Question;
    }

    public String getquestionId() {
        return questionid;
    }

    public String getQuestion() {
        return question;
    }

    public static StackOverflowModel build(StackOverflowMockData data) {
        return new StackOverflowModel(data.getquestionId(), data.getQuestion());
    }
}
