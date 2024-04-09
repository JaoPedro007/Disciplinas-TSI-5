package equation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SecondDegreeEquationTest {

	@Test
	void shouldInstantiateCorrectly() {
		
		float a = 1;
		float b = 2;
		float c = 3;
		
		SecondDegreeEquation equation = new SecondDegreeEquation(a, b, c);
		
		float obtained;
		
		obtained = equation.getA();
		assertTrue((Math.abs(a - obtained) < 0.0001));
		
		obtained = equation.getB();
		assertTrue((Math.abs(b - obtained) < 0.0001));
		
		obtained = equation.getC();
		assertTrue((Math.abs(c - obtained) < 0.0001));
		
	}
	
	@Test 
	void shouldThrowsExceptionWithAnInvalidEquation() {
		float a = 0;
		float b = 1;
		float c = 2;
		
		SecondDegreeEquation equation = new SecondDegreeEquation(a, b, c);
		
	}
	
	@Test
	void shouldReturnTrueOrFalseAsEquationHaveRealSolution() {
		
	}

}
