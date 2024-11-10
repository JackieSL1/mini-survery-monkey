package sysc4806.project24.mini_survey_monkey.repositories;

import org.springframework.data.repository.CrudRepository;
import sysc4806.project24.mini_survey_monkey.models.CommentQuestion;
import sysc4806.project24.mini_survey_monkey.models.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer> {}