package refactoring.pa;

public class ArithmeticProgression {
	
	private int firstTerm;
	private int ratio;
	
	public ArithmeticProgression(int firsTerm, int ratio) {
		this.firstTerm=firsTerm;
		this.ratio=ratio;
		
	}
	public int termOfIndex(int index)
	{
		return firstTerm + (index-1) * ratio;
	}
	
	//--------------------------------------------------------------------
	public int sumOfAllTerms(int numberOfTerms)
	{
		int lastTerm = termOfIndex(numberOfTerms);
		
		return ( ((firstTerm + lastTerm) * numberOfTerms) / 2);
	}
	
	public int [] sequences(int numberOfTerms) {
		
		int[] sequence = new int [numberOfTerms];
		
		for(int i=0; i<numberOfTerms; i++) {
			sequence[i] = termOfIndex(i);
		}
		
		return sequence;
	}
	
	//--------------------------------------------------------------------
	static public 
	int determineRatio(int firstTerm, int lastTerm, int numberOfTerms)
	{
		return (lastTerm - firstTerm) / (numberOfTerms - 1);
	}

}
