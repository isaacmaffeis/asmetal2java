package org.asmeta.asm2java.asm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.asm2java.compiler.CompilatoreJava;
import org.asmeta.asm2java.main.*;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import asmeta.AsmCollection;

public class GeneratorCompilerAsmTest {

	static private JavaGenerator jGenerator = new JavaGenerator();
	static private JavaASMGenerator jGeneratorASM = new JavaASMGenerator();

	// default translator options
	private TranslatorOptions options = new TranslatorOptions(true, true, true);


	@Test
	public void testAll() throws IOException, Exception {

		System.out.println("CASO TEST ALL \n\n\n\n");
		List<File> allUasmFiles = new ArrayList<>();
		List<CompileResult> results = new ArrayList<>();

		listf("examples", allUasmFiles);

		allUasmFiles.toString();
		for (File f : allUasmFiles) {
			results.add(test(f.getPath(), options));
		}

		System.out.println("\n\n\n\n\n");
		System.out.println("Elenco dei casi di test con relativi risultati");
		System.out.println("\n\n");

		for (int i = 0; i < allUasmFiles.size(); i++) {
			File f = allUasmFiles.get(i);
			System.out.println(f.getName() + " --> " + results.get(i));
		}
		double avg = 0;
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getSuccess())
				avg = avg + 1;
		}
		System.out.println("\n\n\n");
		System.out.println("SUCCESS RATE: " + (avg * 100) / results.size() + "%");
		System.out.println("\n\n\n");
	}

	// return all the file in a directory
	void listf(String directoryName, List<File> files) {
		File directory = new File(directoryName);

		File[] fList = directory.listFiles();

		for (File file : fList) {

			if (!file.getName().equals("StandardLibrary.asm") && !file.getName().equals("CTLLibrary.asm") && !file.getName().equals("LTLLibrary.asm") ) {
				if (file.isFile() && file.getName().endsWith(ASMParser.ASM_EXTENSION)) {
					files.add(file);
				} else if (file.isDirectory()) {
					listf(file.getAbsolutePath(), files);
				}
			}
		}

	}
	
	/**
	 * 
	 * @param <jGenerator>
	 * @param asmspec      the path of the spec
	 * @return true if success
	 * 
	 * @throws Exception
	 */

	public static CompileResult test(String asmspec, TranslatorOptions userOptions) throws Exception {
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
		String dirEsecuzione = asmFile.getParentFile().getPath() + "/esecuzione";
		String dirTraduzione = asmFile.getParentFile().getPath() + "/traduzione";

		// AC
		File javaFile = new File(dir.getPath() + File.separator + name + ".java");
		File javaFileCompilazione = new File(dirCompilazione + File.separator + name + ".java");

		File javaFileASM = new File(dirEsecuzione + File.separator + name + "_ASM.java");
		File javaFileASMN = new File(dirEsecuzione + File.separator + name + ".java");

		File javaFileT = new File(dirTraduzione + File.separator + name + ".java");
		File javaFileASMT = new File(dirTraduzione + File.separator + name + "_ASM.java");

		File stepFunctionArgs = new File(dir.getPath() + File.separator + "StepFunctionArgs" + ".txt");

		TestUtil.deleteExisting(javaFile);
		TestUtil.deleteExisting(javaFileCompilazione);
		TestUtil.deleteExisting(javaFileASM);
		TestUtil.deleteExisting(javaFileASMN);
		TestUtil.deleteExisting(javaFileASMT);
		TestUtil.deleteExisting(javaFileT);
		TestUtil.deleteExisting(stepFunctionArgs);

		System.out.println("\n\n===" + name + " ===================");

		// write java
		try {
			// Java Class
			jGenerator.compileAndWrite(model.getMain(), javaFile.getCanonicalPath(), userOptions);
			jGenerator.compileAndWrite(model.getMain(),
					javaFileCompilazione.getCanonicalPath(),
					userOptions);
			
			// ASM Class
			String [] stringArray = {};
			jGeneratorASM.setFinalStateConditions(stringArray);
			jGeneratorASM.compileAndWrite(model.getMain(), javaFileASM.getCanonicalPath(), userOptions);
			jGenerator.compileAndWrite(model.getMain(), javaFileASMN.getCanonicalPath(), userOptions);
			jGeneratorASM.compileAndWrite(model.getMain(), javaFileASMT.getCanonicalPath(), userOptions);

			// Parser support
			StringBuffer stepArgs = new StringBuffer();
			jGeneratorASM.setMonitoredArgs(model.getMain(), stepArgs);
			FileWriter fileWriter = new FileWriter(stepFunctionArgs);
			fileWriter.write(stepArgs.toString().
							replaceAll("\t","").
							replaceFirst(" " + System.lineSeparator(),""));
			fileWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
			return new CompileResult(false, e.getMessage());
		}

		System.out.println("Generated java file: " + javaFile.getCanonicalPath());
		System.out.println("Generated java compiled file: " + javaFileCompilazione.getCanonicalPath());
		System.out.println("Generated ASM java file: " + javaFileASMN.getCanonicalPath());
		System.out.println("Generated parser support file: " + stepFunctionArgs.getCanonicalPath());

		System.out.println("All java files Generated in: " + javaFileT.getCanonicalPath());

		CompileResult result = CompilatoreJava.compile(name + ".java", dir, true);

		TestUtil.deleteExisting(javaFile);
		TestUtil.deleteExisting(javaFileCompilazione);
		TestUtil.deleteExisting(javaFileASM);
		TestUtil.deleteExisting(javaFileASMN);
		TestUtil.deleteExisting(javaFileASMT);
		TestUtil.deleteExisting(javaFileT);
		TestUtil.deleteExisting(stepFunctionArgs);
		
		return result;
	}

}
