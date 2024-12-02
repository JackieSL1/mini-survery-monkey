package sysc4806.project24.mini_survey_monkey.repositories;

import org.springframework.data.repository.CrudRepository;
import sysc4806.project24.mini_survey_monkey.models.Survey;
import sysc4806.project24.mini_survey_monkey.models.User;

import java.util.List;

public interface SurveyRepository extends CrudRepository<Survey, Integer> {
    public Survey findById(int id);
    public List<Survey> findAllByUser(User user);
}