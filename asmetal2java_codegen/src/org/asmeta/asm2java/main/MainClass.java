package org.asmeta.asm2java.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
		File javaFile = new File(SRC_GEN + File.separator + name + ".java");
    //File javaFile = new File(dir.getPath() + File.separator + name + ".java");
		File javaFileCompilazione = new File(dirCompilazione + File.separator + name + ".java");

		// Se il file java esiste di gia, lo cancella

		if (javaFile.exists())
			javaFile.delete();
		assert !javaFile.exists();

		if (javaFileCompilazione.exists())
			javaFileCompilazione.delete();
		assert !javaFileCompilazione.exists();

		System.out.println("\n\n===" + name + " ===================");

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

		// copy the file.java into the outputFolder
		File javaOutFile = new File(outputFolder + File.separator + name + ".java");
		assert dir.exists() && dir.isDirectory();
		try (
				InputStream in = new BufferedInputStream(
            Files.newInputStream(javaFile.toPath()));
				OutputStream out = new BufferedOutputStream(
            Files.newOutputStream(javaOutFile.toPath()))) {

			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		} catch (NoSuchFileException e){
			logger.error("Export Failed. Please specify an existing output folder...");
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}

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
				  .desc("The ASM input file (required)")
				  .build();
		
		// output directory
		Option output = Option.builder("output")
				.argName("output")
				.type(String.class)
				.hasArg(true)
				.desc("The output folder (optional, defaults to `./output/`)")
				.build();
		
		// property
		Option property = Option.builder("D")
				.numberOfArgs(2)
				.argName("property=value")
				.valueSeparator('=')
				.required(false)
				.optionalArg(false)
				.type(String.class)
				.desc("use value for given translator property (optional):\n"
						+ "formatter=true/false (if you want the code to be formatted),\n"
						+ " shuffleRandom=true/false (use random shuffle),\n"
						+ " optimizeSeqMacroRule=true/false (if true -> only those used (to improve code coverage))")
				.build();
		
		options.addOption(help);
		options.addOption(input);
		options.addOption(output);
		options.addOption(property);

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
			outputFolder = "output/";
		} else {
			outputFolder = line.getOptionValue("output");
		}
		
		try {
			CompileResult compileResult = generate(asmspec, translatorOptions, outputFolder);
			if(compileResult.getSuccess()){
				logger.info("Generation succeed : " + compileResult.toString());
			}
			else{
				logger.error("Generation failed : " + compileResult.toString());
			}

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
			logger.info("Performing requested operation ...");
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
