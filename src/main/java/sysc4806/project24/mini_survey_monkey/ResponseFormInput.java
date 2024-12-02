package sysc4806.project24.mini_survey_monkey;

import jakarta.persistence.criteria.CriteriaBuilder;

public final class ResponseFormInput {
    private String responseText;

    private Integer responseScaleSelection;

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public Integer getResponseScaleSelection() {
        return responseScaleSelection;
    }

    public void setResponseScaleSelection(Integer responseScaleSelection) {
        this.responseScaleSelection = responseScaleSelection;
    }
}

