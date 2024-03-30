package pa;

import java.util.Scanner;

public class ExpectedFirstRefactoring {

	Scanner console = new Scanner(System.in);

	// --------------------------------------------------------------------
	private int readIntInto(String msg, int min, int max) {
		int number;
		
		do {
			System.out.println(msg);
			number = console.nextInt();
		} while (number > max || number < min);

		return number;
	}

	// --------------------------------------------------------------------
	public int readNumberOfTerms() {
		String msg = "Digite o n�mero de termos da PA:";
		
		return readIntInto(msg, 2, Integer.MAX_VALUE);
	}

	// --------------------------------------------------------------------
	public int readFirstTerm() {
		String msg = "Digite o primeiro termo da PA:";

		return readIntInto(msg, 1, Integer.MAX_VALUE);
	}

	// --------------------------------------------------------------------
	public int readCommonDifference() {
		String msg = "Digite a raz�o da PA:";

		return readIntInto(msg, 1, Integer.MAX_VALUE);
	}

	// --------------------------------------------------------------------
	public int termOfIndex(int firstTerm, int commonDifference, int index) {
		return firstTerm + (index - 1) * commonDifference;
	}

	// --------------------------------------------------------------------
	public int sumOfAllTerms(int firstTerm, int commonDifference, int numberOfTerms) {
		int lastTerm = termOfIndex(firstTerm, commonDifference, numberOfTerms);

		return (((firstTerm + lastTerm) * numberOfTerms) / 2);
	}

	// --------------------------------------------------------------------
	public void printAllTerms(int firstTerm, int commonDifference, int numberOfTerms) {
		for (int i = 1; i <= numberOfTerms; i++) {
			
			int ithTerm = termOfIndex(firstTerm, commonDifference, i);

			System.out.printf("a%d=%d\n", i, ithTerm);
		}
	}

	// --------------------------------------------------------------------
	static public void main(String[] args) {
		ExpectedFirstRefactoring pa = new ExpectedFirstRefactoring();

		int firstTerm, reason, numberOfTerms;

		firstTerm = pa.readFirstTerm();
		reason = pa.readCommonDifference();
		numberOfTerms = pa.readNumberOfTerms();

		pa.printAllTerms(firstTerm, reason, numberOfTerms);

		int sum = pa.sumOfAllTerms(firstTerm, reason, numberOfTerms);
		
		System.out.println("A soma �: " + sum);

		pa.console.close();
	}

}
