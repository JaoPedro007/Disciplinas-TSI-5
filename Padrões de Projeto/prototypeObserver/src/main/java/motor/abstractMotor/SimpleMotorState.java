package motor.abstractMotor;

import motor.MotorState;

public class SimpleMotorState implements MotorState {
	private Status currentStatus = Status.OFF;
	private float rotationsPerMinute = 0.0f;
	private float accelerationFraction = 0.0f;

	public SimpleMotorState() {
		super();

	}

	public SimpleMotorState(Status currentStatus, float rotationsPerMinute, float accelerationFraction) {
		this();
		this.currentStatus = currentStatus;
		this.rotationsPerMinute = rotationsPerMinute;
		this.accelerationFraction = accelerationFraction;
	}

	@Override
	public MotorState.Status currentStatus() {
		return currentStatus;
	}

	@Override
	public void setCurrentStatus(Status newValue) {
		currentStatus = newValue;
	}

	@Override
	public float rotationsPerMinute() {
		return rotationsPerMinute;
	}

	@Override
	public void setRotationPerMinute(float newValue) {
		rotationsPerMinute = newValue;
	}

	@Override
	public float accelerationFraction() {
		return accelerationFraction;
	}

	@Override
	public void setAccelerationFraction(float newValue) {
		accelerationFraction = newValue;
	}

	@Override
	public MotorState clone() {
		try {
			Object clone = super.clone();
			return (MotorState) clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean equals(Object other) {

		if (!(other instanceof SimpleMotorState))
			return false;

		SimpleMotorState another = (SimpleMotorState) other;

		boolean equals = ((this.currentStatus() == another.currentStatus())
				&& areEquals(this.rotationsPerMinute(), another.rotationsPerMinute())
				&& areEquals(this.accelerationFraction(), another.accelerationFraction()));

		return equals;
	}

	private boolean areEquals(float f1, float f2) {
		return (Float.compare(f1, f2) == 0);
	}

}
