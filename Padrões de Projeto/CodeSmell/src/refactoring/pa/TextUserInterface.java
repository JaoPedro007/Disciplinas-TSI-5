package refactoring.pa;

import java.util.Scanner;

public class TextUserInterface {
	ArithmeticProgression arithmeticProgression;
	
	public TextUserInterface() {
		
	}

	Scanner console = new Scanner(System.in);
	
	
	public void printAllTerms(int firstTerm, int commonDifference, int numberOfTerms)
	{
		for(int i=1; i<=numberOfTerms; i++)
		{
			int ithTerm = arithmeticProgression.termOfIndex(firstTerm, commonDifference, i);
			
			System.out.printf("a%d=%d\n",i, ithTerm);
		}
	}
	
	
	int readIntInto(String msg, int min, int max)
	{
		int number;
		do 
		{
		   System.out.println(msg);
		   number = console.nextInt();	   
		} while(number > max || number < min);
		
		return number;
	}
	
	public int readNumberOfTerms()
	{
		String msg = "Digite o n�mero de termos da PA:";
		
		return readIntInto(msg, 2, Integer.MAX_VALUE);
	}

	//--------------------------------------------------------------------
	public int readFirstTerm()
	{
		String msg = "Digite o primeiro termo da PA:";
		
		return readIntInto(msg, 1, Integer.MAX_VALUE);
	}
	
	//--------------------------------------------------------------------
	public int readCommonDifference()
	{
		String msg = "Digite a raz�o da PA:";
		
		return readIntInto(msg, 1, Integer.MAX_VALUE);
	}
	

}
