package sysc4806.project24.mini_survey_monkey.repositories;

import org.springframework.data.repository.CrudRepository;
import sysc4806.project24.mini_survey_monkey.models.Survey;

public interface SurveyRepository extends CrudRepository<Survey, Integer> {
    public Survey findById(int id);
}