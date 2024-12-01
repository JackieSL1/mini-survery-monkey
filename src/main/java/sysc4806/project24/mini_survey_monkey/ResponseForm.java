package sysc4806.project24.mini_survey_monkey;

import java.util.List;

public final class ResponseForm {
    private List<ResponseFormInput> responseInputs;

    public List<ResponseFormInput> getResponseInputs() {
        return responseInputs;
    }

    public void setResponseInputs(List<ResponseFormInput> responseInputs) {
        this.responseInputs = responseInputs;
    }
};

