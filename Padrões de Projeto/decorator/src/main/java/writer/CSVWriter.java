package writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

import separator.Separator;


public class CSVWriter extends Writer
{
	private BufferedWriter bufferedWriter = null;
	private Separator separator = Separator.COMMA;
	
	//--------------------------------------------------------------
	public CSVWriter(Writer writer)
	{
		super(writer);
		
		if(writer instanceof BufferedWriter)
			this.bufferedWriter = (BufferedWriter) writer;
		else
			this.bufferedWriter = new BufferedWriter(writer);
	}
	
	//--------------------------------------------------------------
	public void setSeparator(Separator separator)
	{
		this.separator = separator;
	}
	
	//--------------------------------------------------------------
	public Separator getSeparator()
	{
		return this.separator;
	}
	
	//--------------------------------------------------------------
	public void writeSeparator() throws IOException
	{
		bufferedWriter.append(separator.asChar());
	}
	
	//--------------------------------------------------------------
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException 
	{
		this.bufferedWriter.write(cbuf, off, len);
	}

	//------------------------------------------------------------------
	public void writeToken(String token) throws IOException
	{
	   bufferedWriter.append(token);
	}

	//------------------------------------------------------------------
	public void writeTokenAndSeparator(String token) throws IOException
	{
	   bufferedWriter.append(token);
	   bufferedWriter.append(separator.asChar());
	}

	//------------------------------------------------------------------
	public void writeTokens(String[] tokens) throws IOException
	{
	   for(int i=0; i<tokens.length;)
	   {
	      bufferedWriter.append( tokens[i] );
	      
	      if(++i < tokens.length)
	         bufferedWriter.append(separator.asChar());
	   }
	}
	
	//------------------------------------------------------------------
   public void writeLine(String[] tokens) throws IOException
   {
      writeTokens(tokens);
      bufferedWriter.newLine();
   }

	//--------------------------------------------------------------
	@Override
	public void flush() throws IOException 
	{
		this.bufferedWriter.flush();
	}

	//--------------------------------------------------------------
	@Override
	public void close() throws IOException 
	{
		this.bufferedWriter.close();
	}

}