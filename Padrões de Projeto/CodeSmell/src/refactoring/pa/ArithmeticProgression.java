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

		for (int i = 0; i < sequence.length; i++) {
			sequence[i] = elementAt(i);
		}

		return sequence;
	}

	public int sumUntilElementAt(int position) {
		return ((firstElement + elementAt(position) * position) /2 );
	}

}
