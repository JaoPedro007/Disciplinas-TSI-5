package motor.abstractMotor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import motor.MotorState;

class SimpleMotorStateTest
{

	@Test
	void shouldReturnCorrectClone()
	{
	   MotorState state = new SimpleMotorState(
	          MotorState.Status.ON,
	          2.000f,
	          0.1f );

	   MotorState clone = state.clone();

	   boolean areDistinctRefs = (state != clone); 
	   assertTrue(areDistinctRefs);

	   assertTrue(state.equals(clone));
	}

}
