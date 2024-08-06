package writer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import separator.Separator;
import writer.CSVWriter;

class CSVWriterTest 
{
	@TempDir
	static File tempDirPath;
	
	final File csvFile = new File(tempDirPath, "csvTest.csv");
	CSVWriter csvWriter = null;

	//-------------------------------------------------------------------
	@BeforeAll
	static void shouldExisteTemporaryDirectory()
	{
	   assertTrue(tempDirPath.isDirectory(), "Should be a directory ");
	}
	
	//-------------------------------------------------------------------
	@BeforeEach
    void instantiateNewCSVWriter()
    {
      csvWriter = assertDoesNotThrow(()-> new CSVWriter(new FileWriter(csvFile)) );
    }
	
	//-------------------------------------------------------------------
	@AfterEach
    void closeCSVWriterAndDeleteTheFile()
    {
      assertDoesNotThrow(()->csvWriter.close());
      assertDoesNotThrow(()->csvFile.delete());
    }

	//-------------------------------------------------------------------
	@Test
	void shouldWriteSeparator()
	{
	   csvWriter.setSeparator(Separator.PIPE);
	   char expected = Separator.PIPE.asChar();

	   assertDoesNotThrow( ()-> {
	      csvWriter.writeSeparator();
	      csvWriter.flush();
	   });

	   char[] buffer = readFileContent(csvFile);
	   char actual = buffer[0];

	   assertEquals(expected, actual);
	}
	
	//-------------------------------------------------------------------
	@Test
	void shouldWriteAnUniqueToken()
	{
	   String token = "Ameixa";

	   assertDoesNotThrow(()->{
	      csvWriter.writeToken(token);
	      csvWriter.flush();
	   });

	   char[] buffer = readFileContent(csvFile);
	   String actual = new String(buffer);

	   assertTrue(actual.equals(token));
	}
	
	//-------------------------------------------------------------------
	@Test
	void shouldWriteTokenAndSeparator()
	{
	   String token = "Ameixa";

	   assertDoesNotThrow(()-> 
	   {
	      csvWriter.writeToken(token);
	      csvWriter.writeSeparator();
	      csvWriter.flush();
	   });
	    
	   String expected = token + csvWriter.getSeparator().asChar();
	    
	   char[] fileContent = readFileContent(csvFile); 
	   String actual = new String(fileContent);
	  
	   assertTrue(actual.equals(expected));
	}
	
	//-------------------------------------------------------------------
	@Test
	void shouldWriteAnArrayOfTokensWithSeparatorsBetweenThem()
	{
	   String expected = "Ameixa;Banana;Maçã-Gala;Batata Doce;Pera;Uva";
	   String[] tokens = expected.split(";");

	   assertDoesNotThrow(()->
	   {
	      csvWriter.setSeparator(Separator.SEMICOLON);
	      csvWriter.writeTokens(tokens);
	      csvWriter.flush();
	   });

	   char[] buff = readFileContent(csvFile);
	   String actual = new String(buff);
	   assertTrue(expected.equals(actual));
	}

	//-------------------------------------------------------------------
//	@Test
//	void shouldWriteALineSetOfTokens()
//	{
//	   String expected = "Ameixa|Banana|Maçã|Laranja Lima|Pera|Uva\r\n"+
//	                     "Pêssego|Maracujá|Fruta do Conde|Graviola\r\n"+
//	                     "Acerola|Limão Rosa|Manga";
//
//	   assertDoesNotThrow(()->
//	   {
//		  csvWriter.setSeparator(Separator.PIPE);
//
//		  String[] lines = expected.split("\r\n");
//	      for(String line : lines)
//	      {
//	         String[] tokens = line.split("\\|");
//	         csvWriter.writeLine(tokens);
//	      }
//	      csvWriter.flush();
//	   });
//
//	   String actual = new String(readFileContent(csvFile));
//	   
//	   assertTrue(expected.equals(actual));
//	}

	
	//-------------------------------------------------------------------
	private char[] readFileContent(File file)
	{
	   return  assertDoesNotThrow(()->
	   {
	      char[] buffer = new char[(int) file.length()];
	      FileReader fileReader = new FileReader(file);
	      int contentSize = fileReader.read(buffer);
	      fileReader.close();
	      
	      return Arrays.copyOf(buffer, contentSize);
	   });
	}	
}
