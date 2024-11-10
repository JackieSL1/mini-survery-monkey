package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Surveyor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int surveyorId;

    private String username;

    private String password;

    public Surveyor() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
