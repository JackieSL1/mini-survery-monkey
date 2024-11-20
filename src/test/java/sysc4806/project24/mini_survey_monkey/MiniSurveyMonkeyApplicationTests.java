package sysc4806.project24.mini_survey_monkey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sysc4806.project24.mini_survey_monkey.controllers.BananaController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MiniSurveyMonkeyApplicationTests {

	@Autowired
	private BananaController bananaController;

	@Test
	void contextLoads() {
		assertThat(bananaController).isNotNull();
	}

}
