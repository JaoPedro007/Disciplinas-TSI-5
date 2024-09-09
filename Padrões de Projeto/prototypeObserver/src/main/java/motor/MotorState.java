package motor;

public interface MotorState extends Cloneable {
	enum Status {
		ON, OFF
	};

	Status currentStatus();

	float rotationsPerMinute();

	float accelerationFraction();

	void setCurrentStatus(Status newValue);

	void setRotationPerMinute(float newValue);

	void setAccelerationFraction(float newValue);

	public MotorState clone();

	public static String stringfy(MotorState motorState) {
		return "{ Current Status: " + motorState.currentStatus() + " RPM: " + motorState.rotationsPerMinute()
				+ " Acceleration Fraction: " + motorState.accelerationFraction() + " }";
	}
}
