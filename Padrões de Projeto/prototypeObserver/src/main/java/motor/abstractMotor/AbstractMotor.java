package motor.abstractMotor;

import motor.Motor;
import motor.MotorState;

public abstract class AbstractMotor implements Motor {
	protected MotorState state = null;

	protected AbstractMotor(MotorState state) {
		this.state = state;
	}

	public MotorState getState() {
		return state.clone();
	}

	public float getAccelerationFraction() {
		return state.accelerationFraction();
	}

	public float getRotationsPerMinute() {
		return state.rotationsPerMinute();
	}

	@Override
	public void start() {
		state.setCurrentStatus(MotorState.Status.ON);
		state.setAccelerationFraction(0.1f);
		state.setRotationPerMinute(determineRotationsPerMinute());
	}

	@Override
	public void stop() {
		state.setCurrentStatus(MotorState.Status.OFF);
		state.setAccelerationFraction(0.0f);
		state.setRotationPerMinute(0.0f);
	}

	@Override
	public void speedUp(final float percent) {
		if (this.isOff())
			return;

		if (percent < 0) {
			slowDown(Math.abs(percent));
			return;
		}

		float increment = (1 - state.accelerationFraction()) * percent;
		state.setAccelerationFraction(state.accelerationFraction() + increment);

		state.setRotationPerMinute(determineRotationsPerMinute());
	}

	@Override
	public void slowDown(float percent) {
		if (this.isOff())
			return;

		if (percent < 0) {
			speedUp(Math.abs(percent));
			return;
		}

		float base = (float) Math.pow((Math.exp(-state.accelerationFraction()) / 2.71), 2);
		float decrement = (state.accelerationFraction()) * (base) * percent;
		state.setAccelerationFraction(state.accelerationFraction() - decrement);

		state.setRotationPerMinute(determineRotationsPerMinute());
	}

	@Override
	public boolean isOff() {
		return state.currentStatus() == MotorState.Status.OFF;
	}

	@Override
	public boolean isOn() {
		return state.currentStatus() == MotorState.Status.ON;
	}

	public abstract float getPower();

	protected abstract float determineRotationsPerMinute();
}
