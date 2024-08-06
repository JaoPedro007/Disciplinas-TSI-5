package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import separator.Separator;


public class CSVReader extends Reader{
	
	private BufferedReader buffReader = null;
	private Separator separator = Separator.COMMA;
	
	public CSVReader(Reader reader) {
		super(reader);
		
		if(reader instanceof BufferedReader)
			this.buffReader = (BufferedReader) reader;
		else
			this.buffReader = new BufferedReader(reader);
	}

	
	public void setSeparator(Separator newSeparator) {
		separator = newSeparator;
	}
	
	public Separator getSeparator() {
		return this.separator;
	}
	
    public String[] readTokens() throws IOException {
        String line = this.buffReader.readLine();
        if (line != null) {
            return line.split(Character.toString(separator.asChar()));
        }
        return null;
    }
    
    public List<String[]> readAllTokens() throws IOException {
        List<String[]> allTokens = new ArrayList<>();
        String[] tokens;
        while ((tokens = readTokens()) != null) {
            allTokens.add(tokens);
        }
        return allTokens;
    }
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
        throw new UnsupportedOperationException("Operation not supported");
	}

	@Override
	public void close() throws IOException {
		this.buffReader.close();
		
	}
	
	

}
