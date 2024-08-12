package org.asmeta.asm2java.main;

/** 
 * contains the translation options used by the generators to decide the behaviors of the generators
 */
public class TranslatorOptions {
	
	// code to be formatted?
	private boolean formatter;
	
	private boolean shuffleRandom;
	
	// if true -> only those used (to improve code coverage)
	private boolean optimizeSeqMacroRule;
	
	/** default constructor */
	TranslatorOptions(){
		this(false,false,false);
	}
	
	public TranslatorOptions(boolean formatter, boolean shuffleRandom, boolean optmizeSeqRule){
		this.formatter = formatter;
		this.shuffleRandom = shuffleRandom;
		this.optimizeSeqMacroRule = optmizeSeqRule;
	}

	public boolean getShuffleRandom() {
		return shuffleRandom;
	}
	
	public boolean getFormatter() {
		return formatter;
	}
	
	public boolean getOptimizeSeqMacroRule() {
		return optimizeSeqMacroRule;
	}
	
	public void setValue(String propertyName, String propertyValue) {
		switch(propertyName) {
		case "formatter":
			formatter = Boolean.parseBoolean(propertyValue);
			break;
		case "shuffleRandom":
			formatter = Boolean.parseBoolean(propertyValue);
			break;
		case "optimizeSeqMacroRule":
			optimizeSeqMacroRule = Boolean.parseBoolean(propertyValue);
			break;
		}
	
	}
	
	
	

}
