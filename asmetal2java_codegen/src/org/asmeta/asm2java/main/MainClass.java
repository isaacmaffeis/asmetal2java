package org.asmeta.asm2java.main;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.asmeta.asm2java.compiler.CompilatoreJava;
import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;

public class MainClass {
	
	private static final Logger logger = Logger.getLogger(MainClass.class);
	
	private static final String SRC_GEN = "../asmetal2java_examples/src/";

	// the generator for the code
	static private JavaGenerator jGenerator = new JavaGenerator();

	// default
	private static TranslatorOptions translatorOptions = new TranslatorOptions(true, true, true);
	
	public static CompileResult generate(String asmspec, TranslatorOptions userOptions, String outputFolder) throws Exception {
		//
		// PARSE THE SPECIFICATION
		// parse using the asmeta parser

		File asmFile = new File(asmspec);
		assert asmFile.exists();
		String asmname = asmFile.getName();
		String name = asmname.substring(0, asmname.lastIndexOf("."));

		final AsmCollection model = ASMParser.setUpReadAsm(asmFile);
		File dir = asmFile.getParentFile();
		assert dir.exists() && dir.isDirectory();

		String dirCompilazione = asmFile.getParentFile().getPath() + "/compilazione";

		// AC
		File javaFile = new File(outputFolder + File.separator + name + ".java");
//		File javaFile = new File(dir.getPath() + File.separator + name + ".java");
		File javaFileCompilazione = new File(dirCompilazione + File.separator + name + ".java");

		// Se il file java esiste di gi , lo cancella

		if (javaFile.exists())
			javaFile.delete();
		assert !javaFile.exists();

		if (javaFileCompilazione.exists())
			javaFileCompilazione.delete();
		assert !javaFileCompilazione.exists();

		System.out.println("\n\n===" + name + " ===================");

		try {

		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}

		// write java

		try {
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			jGenerator.compileAndWrite(model.getMain(), javaFileCompilazione.getCanonicalPath(), userOptions);

		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}

		System.out.println("Generated java file: " + javaFile.getCanonicalPath());
		System.out.println("Generated java file: " + javaFileCompilazione.getCanonicalPath());

		CompileResult result = CompilatoreJava.compile(name + ".java", dir, true);

		return result;
	}

	public static Options getCommandLineOptions() {
		Options options = new Options();

		// print help
		Option help = new Option("help", "print this message");
		

		// input file
		  Option input = Option.builder("input")
				  .argName("input")
				  .type(String.class)
				  .hasArg(true)
				  .desc("asm input file")
				  .build();
		
		// output directory
		Option output = Option.builder("output")
				.argName("output")
				.type(String.class)
				.hasArg(true)
				.desc("output folder")
				.build();
		
		options.addOption(help);
		options.addOption(input);
		options.addOption(output);
		
		return options;
	}

	public CommandLine parseCommandLine(String[] args, Options options) {
		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println("Failed to parse commandline arguments.");
		}
		return line;
	}
	
	private void setGlobalProperties(CommandLine line) {
		Properties properties = line.getOptionProperties("D");
		Set<String> propertyNames = new HashSet<>(Arrays.asList("formatter", "shuffleRandom", "optimizeSeqMacroRule"));

        for (String propertyName : properties.stringPropertyNames()) {

            if (!propertyNames.contains(propertyName)) {
				logger.error("* Unknown property: " + propertyName);
			}

            String propertyValue = properties.getProperty(propertyName);

            try {
            	translatorOptions.setValue(propertyName, propertyValue);
				
			} catch (Exception e) {
				System.err.println("Invalid value for property " + propertyName + ": " + propertyValue);
			}
		}
		
	}
	
	private void execute (CommandLine line, Options options) {

		setGlobalProperties (line);
		
		String asmspec = "";
		
		if (line.hasOption("input")) {
			asmspec = line.getOptionValue("input");
		}else {
			System.out.println("input option needs a path to the asm file");
		}
		
		String outputFolder = "";
		if(!line.hasOption("output")){
			outputFolder = SRC_GEN;
		} else {
			outputFolder = line.getOptionValue("output");
		}
		
		try {
			generate(asmspec, translatorOptions, outputFolder).getSuccess();
		} catch (Exception e) {
			logger.error("An error occurred");
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public static void main(String[] args) {
		
		try {
			MainClass main = new MainClass ();
			Options options = getCommandLineOptions();
			CommandLine line = main.parseCommandLine(args, options);
			System.out.println("Performing requested operation ...");
			if (line == null || line.hasOption("help") || line.getOptions().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				// Do not sort				
				formatter.setOptionComparator(null);
				// Header and footer strings
				String header = "Asmetal2java\n\n";
				String footer = "\nthis project is part of Asmeta, see https://github.com/asmeta/asmeta for information or to report problems";
				 
				formatter.printHelp("Asmetal2java",header, options, footer , false);
			}else if(!line.hasOption("input")){
				logger.error("Please specify the asm input file path");
			}
			else{
			main.execute(line, options);
			}
			logger.info("Requested operation completed.");
		}
		catch (Exception e) {
			logger.error("An error occurred");
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);

	}

}
