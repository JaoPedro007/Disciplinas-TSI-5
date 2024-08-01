package decorator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import writer.CSVWriter;
import writer.Separator;

class CSVWriterTest {

    @TempDir
    static File tempDirPath;

    @BeforeAll
    static void shouldExisteTemporaryDirectory() {
        assertTrue(tempDirPath.isDirectory(), "Should be a directory");
    }

    @Test
    void shouldSetSeparator() {
        assertDoesNotThrow(() -> {
            File csvFile = new File(tempDirPath, "csvTest.csv");
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile));
            
            Separator expected = Separator.COMMA;
            csvWriter.setSeparator(expected);
            Separator actual = csvWriter.getSeparator();
            
            csvWriter.close();
            
            assertEquals(expected, actual);
        });
    }

    @Test
    void shouldWriteSeparator() {
        assertDoesNotThrow(() -> {
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
