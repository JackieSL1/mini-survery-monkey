package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Survey {

    public static final String DEFAULT_TITLE = "Untitled";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    @OneToOne
    private User user;

    @OneToMany
    private List<Question> questions;

    private State state;

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", questions=" + questions +
                ", state=" + state +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User owner) {
        this.user = owner;
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
