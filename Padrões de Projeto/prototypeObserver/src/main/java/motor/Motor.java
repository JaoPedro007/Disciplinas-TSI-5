package motor;

public interface Motor {
	public MotorState getState();

	public boolean isOff();

	public boolean isOn();

	public void speedUp(float percent);

	public void slowDown(float percent);

	public void start();

	public void stop();
}
