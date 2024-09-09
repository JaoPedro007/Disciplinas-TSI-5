package app;

import fusca.motor.Motor1500;
import motor.Motor;
import motor.MotorState;
import motor.event.MotorEvent;
import motor.event.listenerOldStyle.MotorEventListener;
import motor.observable.ObservableMotor;

public class App {
	private ObservableMotor motor = null;

	MotorEventListener motorListener = new MotorEventListener() {
		@Override
		public void beforeStart(MotorEvent me) {
			System.out.println("Before Start " + MotorState.stringfy(me.state));
		}

		@Override
		public void afterStart(MotorEvent me) {
			System.out.println("After Start " + MotorState.stringfy(me.state));
		}

		@Override
		public void beforeStop(MotorEvent me) {
			System.out.println("Before Stop " + MotorState.stringfy(me.state));
		}

		@Override
		public void afterStop(MotorEvent me) {
			System.out.println("After Stop " + MotorState.stringfy(me.state));
		}

		@Override
		public void beforeSlowDown(MotorEvent me) {
			System.out.println("Before Slow Down " + MotorState.stringfy(me.state));
		}

		@Override
		public void afterSlowDown(MotorEvent me) {
			System.out.println("After Slow Down " + MotorState.stringfy(me.state));
		}

		@Override
		public void beforeSpeedUp(MotorEvent me) {
			System.out.println("Before Speed Up " + MotorState.stringfy(me.state));
		}

		@Override
		public void afterSpeedUp(MotorEvent me) {
			System.out.println("After Speed Up " + MotorState.stringfy(me.state));
		}
	};

	public App() {
		Motor motor1500 = new Motor1500();
		motor = new ObservableMotor(motor1500);
		motor.addMotorListener(motorListener);
	}

	public static void main(String args[]) {
		App app = new App();

		app.motor.start();
		app.motor.speedUp(1.5f);
		app.motor.slowDown(0.6f);
		app.motor.stop();
	}

}