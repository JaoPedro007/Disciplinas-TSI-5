package motor.observable;

import motor.Motor;
import motor.MotorState;
import motor.event.MotorEvent;
import motor.event.listenerOldStyle.MotorEventListener;
import motor.event.listenerOldStyle.MotorEventListenerManager;

public class ObservableMotor implements Motor {
	private final Motor motor;

	private final MotorEventListenerManager listenerManager = new MotorEventListenerManager();

	public ObservableMotor(Motor motor) {
		this.motor = motor;
	}

	public void addMotorListener(MotorEventListener listener) {
		listenerManager.addMotorListener(listener);
	}

	public void removeMotorListener(MotorEventListener listener) {
		listenerManager.removeMotorListener(listener);
	}

	@Override
	public MotorState getState() {
		return motor.getState();
	}

	@Override
	public boolean isOn() {
		return motor.isOn();
	}

	@Override
	public boolean isOff() {
		return motor.isOff();
	}

	@Override
	public void start() {
		this.listenerManager.fireBeforeStart(new MotorEvent(motor, motor.getState()));
		this.motor.start();
		this.listenerManager.fireAfterStart(new MotorEvent(motor, motor.getState()));
	}

	@Override
	public void stop() {
		this.listenerManager.fireBeforeStop(new MotorEvent(motor, motor.getState()));
		this.motor.stop();
		this.listenerManager.fireAfterStop(new MotorEvent(motor, motor.getState()));
	}
	

	@Override
	public void slowDown(float percent) {
		this.listenerManager.fireBeforeSlowDown(new MotorEvent(motor, motor.getState()));
		this.motor.slowDown(percent);
		this.listenerManager.fireAfterSlowDown(new MotorEvent(motor, motor.getState()));
	}

	@Override
	public void speedUp(float percent) {
		this.listenerManager.fireBeforeSpeedUp(new MotorEvent(motor, motor.getState()));
		this.motor.speedUp(percent);
		this.listenerManager.fireAfterSpeedUp(new MotorEvent(motor, motor.getState()));
	}
}