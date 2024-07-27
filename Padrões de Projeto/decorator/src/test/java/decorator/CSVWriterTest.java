package decorator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CSVWriterTest {

	@TempDir
	static File tempDirPath;
	
	@BeforeAll
	void shouldExisteTemporaryDirectory() {
		File tempDirPath = new File("file.test");
		assertTrue(tempDirPath.isDirectory(), "Should be a directory");
	}
		
	@Test
	void shouldWriteSeparatorIntoFile() {
		assertDoesNotThrow( ()-> {
			File csvFile = new File(tempDirPath, "csvTest.csv");
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile));
			csvWriter.setSeparator(Separator.PIPE);
			csvWriter.writeSeparator();
			csvWriter.close();
			
			char expected = Separator.PIPE.asChar();
			FileReader fileReader = new FileReader(csvFile);
			char actual = (char) fileReader.read();
			fileReader.close();
			
			assertEquals(expected, actual);
		});
	}
	

}
