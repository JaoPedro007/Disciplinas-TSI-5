package motor.event.listenerOldStyle;

import java.util.ArrayList;
import java.util.List;

import motor.event.MotorEvent;

public class MotorEventListenerManager {
	private final List<MotorEventListener> listeners = new ArrayList<>();

	public void addMotorListener(MotorEventListener listener) {
		listeners.add(listener);
	}

	public void removeMotorListener(MotorEventListener listener) {
		listeners.remove(listener);
	}

	public void fireBeforeStart(MotorEvent event) {
		for (MotorEventListener listener : listeners) {
			listener.beforeStart(event);
		}
	}

	public void fireAfterStart(MotorEvent event) {
		for (MotorEventListener listener : listeners) {
			listener.afterStart(event);
		}
	}

	public void fireBeforeStop(MotorEvent event) {
		for (MotorEventListener listener : listeners) {
			listener.beforeStop(event);
		}
	}

	public void fireAfterStop(MotorEvent event) {
		for (MotorEventListener listener : listeners) {
			listener.afterStop(event);
		}
	}

	public void fireBeforeSlowDown(MotorEvent event) {
		for (MotorEventListener listener : listeners) {
			listener.beforeSlowDown(event);
		}
	}

	public void fireAfterSlowDown(MotorEvent event) {
		for (MotorEventListener listener : listeners) {
			listener.afterSlowDown(event);
		}
	}

	public void fireBeforeSpeedUp(MotorEvent event) {
		for (MotorEventListener listener : listeners) {
			listener.beforeSpeedUp(event);
		}
	}

	public void fireAfterSpeedUp(MotorEvent event) {
		for (MotorEventListener listener : listeners) {
			listener.afterSpeedUp(event);
		}
	}
}
