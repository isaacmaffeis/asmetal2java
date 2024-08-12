package org.asmeta.asm2java.main;

import java.io.File;

import org.asmeta.asm2java.compiler.CompilatoreJava;
import org.asmeta.asm2java.compiler.CompileResult;
import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;

public class MainClass {
	
	private static final String SRC_GEN = "../asmetal2java_examples/src/";

	// the generator for the code
	static private JavaGenerator jGenerator = new JavaGenerator();

	private static TranslatorOptions options = new TranslatorOptions(true, true, true);
	
	public static CompileResult generate(String asmspec, TranslatorOptions userOptions) throws Exception {
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


	public static void main(String[] args) {
		
		String asmspec = "examples/RegistroDiCassa.asm";
		try {
			generate(asmspec, options).getSuccess();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
