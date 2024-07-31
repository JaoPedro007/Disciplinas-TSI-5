package writer;

public enum Separator {
	
	COMMA(','), 
	SEMICOLON(';'), 
	COLON(':'), 
	TAB('\t'), 
	SPACE(' '), 
	PIPE('|');
	
	private char value = ',';
	
	private Separator(char value) {
		this.value = value;
	}
	
	public char asChar() {
		return this.value;
	}
	
	public String asString() {
		return "" + this.value;
	}

}
