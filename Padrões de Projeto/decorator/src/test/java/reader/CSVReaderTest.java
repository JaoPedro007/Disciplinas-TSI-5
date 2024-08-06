package reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    void instantiateNewCSVReader() {
        assertDoesNotThrow(() -> {
            FileWriter writer = new FileWriter(csvFile);
            writer.write("Ameixa\n");
            writer.write("Banana,Morango\n");
            writer.close();
            csvReader = new CSVReader(new FileReader(csvFile));
        });
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
    void shouldReadTokens() {
        assertDoesNotThrow(() -> {
            String[] tokens = csvReader.readTokens();
            assertArrayEquals(new String[]{"Ameixa"}, tokens);
        });
    }
	
	
	//-------------------------------------------------------------------
	
    @Test
    void shouldReadAllTokens() {
        assertDoesNotThrow(() -> {
            List<String[]> lines = csvReader.readAllTokens();
            assertEquals(2, lines.size());
            assertArrayEquals(new String[]{"Ameixa"}, lines.get(0));
            assertArrayEquals(new String[]{"Banana", "Morango"}, lines.get(1));
        });
    }
	
   
}
