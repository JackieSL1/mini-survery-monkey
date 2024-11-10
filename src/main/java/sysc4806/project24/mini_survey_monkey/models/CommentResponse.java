package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.Entity;

@Entity
public class CommentResponse extends Response {

    private String text;

    public CommentResponse(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
