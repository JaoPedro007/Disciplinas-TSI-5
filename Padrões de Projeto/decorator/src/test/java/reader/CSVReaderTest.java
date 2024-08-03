package reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CSVReaderTest {
	
    @TempDir
    static File tempDirPath;
    
	final File csvFile = new File(tempDirPath, "csvTest.csv");
	CSVReader csvReader = null;
	
	//-------------------------------------------------------------------

    @BeforeAll
    static void shouldExistTemporaryDirectory() {
        assertTrue(tempDirPath.isDirectory(), "Should be a directory");
    }
    
	//-------------------------------------------------------------------

	@BeforeEach
    void instantiateNewCSVReader()
    {
      csvReader = assertDoesNotThrow(()-> new CSVReader(new FileReader(csvFile)) );
    }
	
	//-------------------------------------------------------------------
	@AfterEach
    void closeCSVWriterAndDeleteTheFile()
    {
      assertDoesNotThrow(()->csvReader.close());
      assertDoesNotThrow(()->csvFile.delete());
    }
	//-------------------------------------------------------------------

	@Test
	void shouldReadLine() {
		String expected = "Ameixa";
		
		assertDoesNotThrow(()->{
			csvReader.readLine();
		});

		char[] buffer = readFileContent(csvFile);
		String actual = new String(buffer);

		assertTrue(actual.equals(expected));
		
	}
	
	//-------------------------------------------------------------------
	
	@Test
	void shouldReadLines() {
		
	}
	
	
	
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
