package sysc4806.project24.mini_survey_monkey.repositories;

import org.springframework.data.repository.CrudRepository;
import sysc4806.project24.mini_survey_monkey.models.CommentQuestion;
import sysc4806.project24.mini_survey_monkey.models.Response;

public interface ResponseRepository extends CrudRepository<Response, Integer> {}