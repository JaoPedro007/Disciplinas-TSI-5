package solid.ocp.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TeamLeaderTest {

	@Test
	void shouldReturnCalculateSalaryCorrectly() {
		//given
		float hours = 10f;
		TeamLeader teamLeader = new TeamLeader();
		
		//act
		float obtained = teamLeader.calculateSalary(hours);

		//check
		float expected = 350f;
		assertEquals(expected, obtained, 0.001);
	}


}
