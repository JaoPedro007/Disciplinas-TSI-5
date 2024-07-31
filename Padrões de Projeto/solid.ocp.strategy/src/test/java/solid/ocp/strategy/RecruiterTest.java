package solid.ocp.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RecruiterTest {

	@Test
	void shouldReturnCalculateSalaryCorrectly() {
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
	void shouldReturnCalculateSalaryWithBonusCorrectly() {
		//given
		float hours = 150f;
		Recruiter recruiter = new Recruiter();
		
		//act
		float obtained = recruiter.calculateSalary(hours);

		//check
		float expected = ((hours * 20 ) + 500);
		assertEquals(expected, obtained, 0.001);
	}
	

}
