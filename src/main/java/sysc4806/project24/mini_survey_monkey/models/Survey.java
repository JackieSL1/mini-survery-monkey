package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int surveyId;

    private String title;

    @OneToOne
    private Surveyor owner;

    @OneToMany
    private List<Question> questions;

    private State state;

    public Survey() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Surveyor getOwner() {
        return owner;
    }

    public void setOwner(Surveyor owner) {
        this.owner = owner;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
