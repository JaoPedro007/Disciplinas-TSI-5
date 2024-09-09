package motor.event;

import motor.Motor;
import motor.MotorState;

public class MotorEvent {
	public final Motor motor;
    public final MotorState state;

    public MotorEvent(Motor motor, MotorState state) {
        this.motor = motor;
        this.state = state;
    }
}
