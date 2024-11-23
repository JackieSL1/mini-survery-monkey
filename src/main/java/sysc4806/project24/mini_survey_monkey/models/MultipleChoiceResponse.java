package sysc4806.project24.mini_survey_monkey.models;


import jakarta.persistence.Entity;

@Entity
public class MultipleChoiceResponse extends Response {

    private String selectedOption;

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}

