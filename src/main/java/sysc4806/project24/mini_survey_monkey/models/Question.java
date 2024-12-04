package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Response> responses;

    private String question;

    private int number;

    @ManyToOne
    @JoinColumn
    private Survey survey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String questionTitle) {
        this.question = questionTitle;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int questionNumber) {
        this.number = questionNumber;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
}
