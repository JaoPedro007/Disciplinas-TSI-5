package motor;

public interface MotorState extends Cloneable
{
	public enum Status { ON , OFF };
	public Status currentStatus();
	public float rotationsPerMinute();
	public float accelerationFraction();
	
	public MotorState clone();
	
	public static String stringfy(MotorState motorState)
	{
		return "{ currentStatus: " + motorState.currentStatus()
			+ " rpm: " + motorState.rotationsPerMinute()
			+ " accelerationFraction: " + motorState.accelerationFraction() 
			+ " }";
	}	
}


