package solid.ocp.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PayrollTest {

	
	void testPayrollWithCorrectlySalary(IEmployee employee, float hours, float expectedSalary) {
		Payroll payroll = new Payroll(employee);
		
		float obtainedSalary = payroll.calculateSalary(hours);
		
		assertEquals(expectedSalary, obtainedSalary, 0.001);
		
	}
	
	
	@Test
	void shouldCalculateSalaryCorrectly() {
		IEmployee employee;
		float hours;
		float expectedSalary;
		
		employee = new Developer();
		hours = 10;
		expectedSalary = 400f;
		testPayrollWithCorrectlySalary(employee, hours, expectedSalary);
		
		employee = new TeamLeader();
		hours = 10;
		expectedSalary = 350f;
		testPayrollWithCorrectlySalary(employee, hours, expectedSalary);
		
		employee = new Recruiter();
		hours = 100.25f;
		expectedSalary = ((hours * 20 ) + 500);
		testPayrollWithCorrectlySalary(employee, hours, expectedSalary);
		
		employee = new Recruiter();
		hours = 10;
		expectedSalary = 200f;
		testPayrollWithCorrectlySalary(employee, hours, expectedSalary);

	}


}
