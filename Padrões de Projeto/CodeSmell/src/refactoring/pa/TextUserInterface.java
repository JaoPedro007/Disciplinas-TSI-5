package refactoring.pa;

import java.util.Scanner;

public class TextUserInterface {

	private final Scanner console = new Scanner(System.in);

	public void printTerms(int[] sequence) {
		final String msgFormat = "\nOs primeiros %d termos da PA são: ";

		System.out.printf(msgFormat, sequence.length);

		for (int term : sequence)
			System.out.print(term + ", ");
		
		System.out.println("...");

	}

	public void printSumOfTerms(int numbersOfTerms, int sum) {
		final String msgFormat = "\nA soma dos %d primeiros termos da PA é: %d";

		System.out.printf(msgFormat, numbersOfTerms, sum);
	}

	private int readNumberOfTerms(String msg, int min) {
		int number;

		do {
			System.out.println(msg);
			number = console.nextInt();
		} while (number < min);

		return number;
	}

	public int readNumberOfTerms() {
		return readNumberOfTerms("Digite o número de termos da PA:", 2);
	}

	public int readFirstTerm() {
		return readNumberOfTerms("Digite o primeiro termo da PA:", 1);
	}

	public int readRatio() {
		return readNumberOfTerms("Digite a razão da PA:", 1);
	}

}
