package sysc4806.project24.mini_survey_monkey;

public final class ResponseFormInput {
    private String responseText;

    private int selectedValue;

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public int getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(int responseScaleSelection) {
        this.selectedValue = responseScaleSelection;
    }
}

