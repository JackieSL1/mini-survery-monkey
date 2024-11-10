package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int responseId;

    public Response() {}
}
