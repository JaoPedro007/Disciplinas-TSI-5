package motor.observable;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fusca.motor.Motor1500;
import motor.event.MotorEvent;
import motor.event.listenerOldStyle.MotorEventListener;

class ObservableMotorTest 
{
	boolean beforeStart    = false, afterStart    = false,
			beforeStop     = false, afterStop     = false,
			beforeSlowDown = false, afterSlowDown = false,
			beforeSpeedUp  = false, afterSpeedUp  = false;
	
	final MotorEventListener motorListener = new MotorEventListener() 
	   {
	      @Override
	      public void beforeStart(MotorEvent me)
	      { beforeStart = true; }

	      @Override
	      public void afterStart(MotorEvent me) 
	      { afterStart = true; }

	      @Override
	      public void beforeStop(MotorEvent me)
	      { beforeStop = true; }

	      @Override
	      public void afterStop(MotorEvent me)
	      { afterStop = true; }
	      
	      @Override
	      public void beforeSlowDown(MotorEvent me)
	      { beforeSlowDown = true; }

	      @Override
	      public void afterSlowDown(MotorEvent me)
	      { afterSlowDown = true; }

	      @Override
	      public void beforeSpeedUp(MotorEvent me)
	      { beforeSpeedUp = true; }

	      @Override
	      public void afterSpeedUp(MotorEvent me)
	      { afterSpeedUp = true; }
	   };
	   
	@Test
	void shouldEmitAndCaputureMotorEvents() 
	{
		ObservableMotor motor = new ObservableMotor(new Motor1500());
	    motor.addMotorListener(motorListener);
				
	    motor.start();
	    motor.speedUp(0.5f);
	    motor.slowDown(0.3f);
	    motor.stop();
	    
	    assertTrue(beforeStart);
	    assertTrue(afterStart);
	    
	    assertTrue(beforeStop);
	    assertTrue(afterStop);
	  
	    assertTrue(beforeSpeedUp);
	    assertTrue(afterSpeedUp);
	    
	    assertTrue(beforeSlowDown);
	    assertTrue(afterSlowDown); 
	}

}