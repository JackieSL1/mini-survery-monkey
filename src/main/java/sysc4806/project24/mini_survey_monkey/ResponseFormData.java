package sysc4806.project24.mini_survey_monkey;

import sysc4806.project24.mini_survey_monkey.models.Question;

public final class ResponseFormData {
    private Question question;
    private String responseText;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
};

