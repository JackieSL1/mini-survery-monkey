package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;

@Entity
public class SharingLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String link;

    @OneToOne
    private Survey survey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
