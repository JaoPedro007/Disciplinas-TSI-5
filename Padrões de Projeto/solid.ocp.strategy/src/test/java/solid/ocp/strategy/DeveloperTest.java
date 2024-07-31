package solid.ocp.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeveloperTest {

	@Test
	void shouldReturnCalculateSalaryCorrectly() {
		//given
		float hours = 10f;
		Developer developer = new Developer();
		
		//act
		float obtained = developer.calculateSalary(hours);

		//check
		float expected = 400f;
		assertEquals(expected, obtained, 0.001);
	}

}
