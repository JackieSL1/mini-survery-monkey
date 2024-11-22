package sysc4806.project24.mini_survey_monkey.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ScaleQuestion extends Question {
    private int minValue;
    private int maxValue;
    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<ScaleResponse> scaleResponses;

    // Getters and Setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public List<ScaleResponse> getScaleResponses() {
        return scaleResponses;
    }

    public void setScaleResponses(List<ScaleResponse> scaleResponses) {
        this.scaleResponses = scaleResponses;
    }
}
