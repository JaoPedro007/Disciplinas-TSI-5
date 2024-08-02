package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import writer.Separator;


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

	
	
	public String readLine() throws IOException{
		return this.buffReader.readLine();
	}
	
	public void setSeparator(Separator newSeparator) {
		separator = newSeparator;
	}
	
	public Separator getSeparator() {
		return this.separator;
	}

	public String[] readTokens() throws IOException{
		String line = readLine();
		if(line !=null) {
			return line.split(Character.toString(separator.asChar()));
		}
		return new String[0];
	}
	
	public List<String[]> readAll() throws IOException{
		List<String[]> allTokens = new ArrayList<String[]>();
		String line;
		while((line = readLine()) !=null) {
			allTokens.add(line.split(Character.toString(separator.asChar())));
		}
		return allTokens;
		
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		return this.buffReader.read(cbuf, off, len);
	}

	@Override
	public void close() throws IOException {
		this.buffReader.close();
		
	}
	
	

}
