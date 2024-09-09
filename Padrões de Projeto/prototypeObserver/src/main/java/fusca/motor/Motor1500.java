package fusca.motor;

import motor.abstractMotor.AbstractMotor;
import motor.abstractMotor.SimpleMotorState;
import motor.MotorState.Status;

public class Motor1500 extends AbstractMotor {
	public Motor1500() {
		super(new SimpleMotorState(Status.OFF, 0, 0));
	}

	@Override
	public float getPower() {
		return 65.0f;
	}

	@Override
	protected float determineRotationsPerMinute() {
		if (super.getAccelerationFraction() < 0.0001)
			return 0;

		float base = super.getAccelerationFraction();
		float rpm = (float) Math.exp(base * base) * 1000;

		return rpm;
	}
}
