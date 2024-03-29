package refactoring.pa;

public class App {

	public static void main(String[] args) {
		
		ArithmeticProgression artithmeticProgression = new ArithmeticProgression();
		TextUserInterface textUserInterface = new TextUserInterface();
		
		int firstTerm, reason, numberOfTerms;
		
		firstTerm = artithmeticProgression.readFirstTerm();
		reason = artithmeticProgression.readCommonDifference();
		numberOfTerms = artithmeticProgression.readNumberOfTerms();
		
		textUserInterface.printAllTerms(firstTerm, reason, numberOfTerms);
		
		int sum = artithmeticProgression.sumOfAllTerms(firstTerm, reason, numberOfTerms);
		System.out.println("A soma �: " + sum);
		
		textUserInterface.console.close();

	}

}
