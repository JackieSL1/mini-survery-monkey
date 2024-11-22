package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;

@Entity
public class ScaleResponse extends Response {
    private int selectedValue;

    @ManyToOne
    @JoinColumn(name = "scale_question_id") // Ensures a foreign key column is created
    private ScaleQuestion question;

    // Getters and Setters for selectedValue
    public int getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(int selectedValue) {
        this.selectedValue = selectedValue;
    }

    // Getters and Setters for question
    public ScaleQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ScaleQuestion question) {
        this.question = question;
    }
}
