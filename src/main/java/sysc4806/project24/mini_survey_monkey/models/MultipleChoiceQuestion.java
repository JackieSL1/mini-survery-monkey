package sysc4806.project24.mini_survey_monkey.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class MultipleChoiceQuestion extends Question {

    @ElementCollection
    @CollectionTable(name = "multiple_choice_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option")
    private List<String> options;

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
