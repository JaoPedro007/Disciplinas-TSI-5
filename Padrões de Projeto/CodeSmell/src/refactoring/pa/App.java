package refactoring.pa;

public class App {
	
	private ArithmeticProgression pA = null;
	private TextUserInterface textUserInterface = new TextUserInterface();
	
	public void createArithmeticProgression()
	{
		int firstTerm = this.textUserInterface.readFirstTerm();
		int ratio = this.textUserInterface.readRatio();
		this.pA = new ArithmeticProgression(firstTerm, ratio);
	}
	
	public void printArithmeticProgressionData()
	{
		int numberOfTerms = textUserInterface.readNumberOfTerms();
		int [] sequences = pA.sequences(numberOfTerms);
		int sum = pA.sumUntilElementAt(numberOfTerms);
		
		textUserInterface.printTerms(sequences);
		textUserInterface.printSumOfTerms(numberOfTerms, sum);
	}

	public static void main(String[] args) {
		App app = new App();
		app.createArithmeticProgression();
		app.printArithmeticProgressionData();

	}

}
