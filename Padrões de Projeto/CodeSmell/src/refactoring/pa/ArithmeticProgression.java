package refactoring.pa;

public class ArithmeticProgression {

	private int firstElement;
	private int ratio;

	public ArithmeticProgression(int firstElement, int ratio) {
		this.firstElement = firstElement;
		this.ratio = ratio;

	}

	public int elementAt(int position) {
		return firstElement + ratio * (position - 1);
	}

	public int[] sequences(int size) {

		int[] sequence = new int[size];

		for (int i = 1; i <= size; i++) {
			sequence[i - 1] = elementAt(i);
		}

		return sequence;
	}

	public int sumUntilElementAt(int position) {
		return (position / 2) * (2 * firstElement + (position - 1) * ratio);
	}

}
