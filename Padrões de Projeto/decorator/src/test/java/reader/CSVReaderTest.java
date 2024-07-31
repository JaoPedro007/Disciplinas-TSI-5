package reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import writer.CSVWriter;
import writer.Separator;

class CSVReaderTest {
	
    @TempDir
    static File tempDirPath;

    @BeforeAll
    static void shouldExistTemporaryDirectory() {
        assertTrue(tempDirPath.isDirectory(), "Should be a directory");
    }

    @Test
    void shouldReadLine() throws IOException {
        File csvFile = new File(tempDirPath, "csvTest.csv");
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            csvWriter.writeToken("test1");
            csvWriter.newLine();
            csvWriter.writeToken("test2");
        }

        try (CSVReader csvReader = new CSVReader(new java.io.FileReader(csvFile))) {
            String line1 = csvReader.readLine();
            String line2 = csvReader.readLine();
            assertEquals("test1", line1);
            assertEquals("test2", line2);
        }
    }
    
    @Test
    void shouldReadAll() throws IOException {
        File csvFile = new File(tempDirPath, "csvTest.csv");
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            csvWriter.writeTokens(new String[]{"test1", "test2", "test3"});
            csvWriter.newLine();
            csvWriter.writeTokens(new String[]{"test4", "test5", "test6"});
        }

        try (CSVReader csvReader = new CSVReader(new java.io.FileReader(csvFile))) {
            List<String[]> allTokens = csvReader.readAll();
            assertEquals(2, allTokens.size());
            assertArrayEquals(new String[]{"test1", "test2", "test3"}, allTokens.get(0));
            assertArrayEquals(new String[]{"test4", "test5", "test6"}, allTokens.get(1));
        }
    }
    
    @Test
    void shouldSetSeparator() throws IOException {
        String csvContent = "test1;test2;test3\ntest4;test5;test6\n";
        try (CSVReader csvReader = new CSVReader(new StringReader(csvContent))) {
            csvReader.setSeparator(Separator.SEMICOLON);
            assertEquals(Separator.SEMICOLON, csvReader.getSeparator());
        }
    }

}
