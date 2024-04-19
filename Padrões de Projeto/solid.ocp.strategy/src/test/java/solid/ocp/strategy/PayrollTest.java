package solid.ocp.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PayrollTest {

	@Test
	void shouldReturnCalculateSalaryTeamLeaderCorrectly() {
		//given
		float hours = 10f;
		TeamLeader teamLeader = new TeamLeader();
		
		//act
		float obtained = teamLeader.calculateSalary(hours);

		//check
		float expected = 350f;
		assertEquals(expected, obtained, 0.001);
	}
	

	@Test
	void shouldReturnCalculateSalaryRecruiterCorrectly() {
		//given
		float hours = 10f;
		Recruiter recruiter = new Recruiter();
		
		//act
		float obtained = recruiter.calculateSalary(hours);

		//check
		float expected = 200f;
		assertEquals(expected, obtained, 0.001);
	}
	

	@Test
	void shouldReturnCalculateSalaryDeveloperCorrectly() {
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
