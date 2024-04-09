package equation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SecondDegreeEquationTest {

	@Test
	void shouldInstantiateCorrectly() {

		// given
		float a = 1;
		float b = 2;
		float c = 3;

		// do /act
		SecondDegreeEquation equation = new SecondDegreeEquation(a, b, c);

		// check
		float obtained;

		obtained = equation.getA();
		assertTrue((Math.abs(a - obtained) < 0.0001));

		obtained = equation.getB();
		assertTrue((Math.abs(b - obtained) < 0.0001));

		obtained = equation.getC();
		assertTrue((Math.abs(c - obtained) < 0.0001));

	}

	@Test
	void shouldThrowsExceptionWithInvalidEquation() {
		float a1;
		float b1;
		float c1;
		
		a1=0f; b1=2f; c1=3f;
		assertThrows(InvalidSecondDregreeEquationException.class, () -> {
			new SecondDegreeEquation(a1, b1, c1);
		});
		
		float a2;
		float b2;
		float c2;
		
		a2=0f; b2=2f; c2=3f;
		assertThrows(InvalidSecondDregreeEquationException.class, () -> {
			new SecondDegreeEquation(a2, b2, c2);
		});

		float a3;
		float b3;
		float c3;
		
		a3=0f; b3=2f; c3=3f;
		assertThrows(InvalidSecondDregreeEquationException.class, () -> {
			new SecondDegreeEquation(a3, b3, c3);
		});

	}

	@Test
	void shoudReturnTrueOrFalseAsEquationHasRealSolution() {
		SecondDegreeEquation eq;
		float a;
		float b;
		float c;

		a = 2;
		b = 2;
		c = 3;
		eq = new SecondDegreeEquation(a, b, c);

		boolean expected = false;
		boolean obtained = eq.hasRealSolution();
		assertEquals(expected, obtained);

		a = 2;
		b = 2;
		c = -3;
		eq = new SecondDegreeEquation(a, b, c);
		expected = true;
		obtained = eq.hasRealSolution();
		assertEquals(expected, obtained);

		a = -2;
		b = 2;
		c = 3;
		eq = new SecondDegreeEquation(a, b, c);
		expected = true;
		obtained = eq.hasRealSolution();
		assertEquals(expected, obtained);

		a = 2;
		b = 4;
		c = 3;
		eq = new SecondDegreeEquation(a, b, c);
		expected = false;
		obtained = eq.hasRealSolution();
		assertEquals(expected, obtained);

		a = 2;
		b = -4;
		c = -3;
		eq = new SecondDegreeEquation(a, b, c);
		expected = true;
		obtained = eq.hasRealSolution();
		assertEquals(expected, obtained);

		a = -2;
		b = 4;
		c = 3;
		eq = new SecondDegreeEquation(a, b, c);
		expected = true;
		obtained = eq.hasRealSolution();
		assertEquals(expected, obtained);

	}

}
