package org.asmeta.asm2java.main

import asmeta.structure.Asm
import java.util.List
import asmeta.transitionrules.basictransitionrules.Rule
import org.junit.Assert
import java.util.ArrayList
import org.asmeta.asm2java.SeqRuleCollector
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.ControlledFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.MonitoredFunction

class JavaASMGenerator2 extends AsmToJavaGenerator {

	def compileAndWrite(Asm asm, String writerPath, TranslatorOptions userOptions) {
		Assert.assertTrue(writerPath.endsWith(".java"));
		compileAndWrite(asm, writerPath, "JAVA", userOptions)
	}

	// all the rules that must translate in two versions seq and not seq
	// if null, translate all
	List<Rule> seqCalledRules;

	String supp

	override compileAsm(Asm asm) {
		// collect alla the seq rules if required
		if (options.optimizeSeqMacroRule) {
			seqCalledRules = new ArrayList
			for (r : asm.bodySection.ruleDeclaration)
				seqCalledRules.addAll(new SeqRuleCollector(false).visit(r))
		}
		//
		val asmName = asm.name

		// TODO fix include list
		var sb = new StringBuffer;
				
		sb.append(
			'''
			
			// «asmName»_ASM.java automatically generated from ASM2CODE
			
			import java.util.Scanner;
			
			/**
			* This class allows you to simulate the behavior of an Abstract State Machine (ASM)
			* without asking the user for input values ​​of the monitored functions.
			*
			* <p>
			* It has been optimized to be used with evosuite in order to automatically generate test scenarios.
			* </p>
			*/
			class «asmName»_ASM {
				
				private final «asmName» esecuzione;
				private int stato;
				
			    /**
			     * Constructor of the {@code «asmName»_ASM} class. Creates a private instance of the asm
			     * {@link «asmName»} and sets the initial state of the state machine to 1.
			     */
				public «asmName»_ASM(){
					this.esecuzione = new «asmName»();
					this.stato = 0;
				}
				
				/** The step function is the only public method of the ASM,
				* it receives as parameters the values ​​of the monitored functions to be updated 
				* and allows to perform a step of the asm by incrementing the state.
				*/
				public void step(''');
 			
 			setMonitoredArgs(asm,sb);
 			
			sb.append('''){
				System.out.println("<State "+ stato +" (controlled)>");
				
				printControlled();
				setMonitored(''');
			
			for (fd : asm.headerSection.signature.function) {
				if (fd instanceof MonitoredFunction) {
					if (fd.domain === null) {
						sb.append('''«fd.name»,''')
					}
					else {
						/* TODO: fix the index i
						sb.append('''boolean «fd.name»_i),''')
						*/
					}
				}
			}
			
			sb.setLength(sb.length() - 1);
			sb.append(''');
				this.esecuzione.updateASM();
				
				System.out.println("</State "+ stato +" (controlled)>");
				
				System.out.println("\n<Stato attuale>");
				printControlled();
				
				/* monitored */
				coverMonitored();
				/* controlled */
				coverControlled();
				
				stato++;
		}
			
		// Monitored getters
			''');
			
			monitoredGetter(asm, sb);
			
			sb.append(System.lineSeparator)
			sb.append("\t\t").append('''// Controlled getters''');
			sb.append(System.lineSeparator)
			
			controllerGetter(asm,sb);
			
			sb.append(System.lineSeparator)
			sb.append("\t").append('''// Cover functions''');
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
			sb.append("\t").append('''
			private void coverMonitored(){''');
					
			coverFunctions(asm,sb,true)
			sb.append(System.lineSeparator)
			
			sb.append("\t").append('''
			}
			
				private void coverControlled(){''');

			coverFunctions(asm,sb,false)
			
			sb.append(System.lineSeparator)
			sb.append("\t").append('''}''');
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
			
			coverBranches(asm,sb);
			
			sb.append('''
				
					// ASM Methods
					
					private void printControlled() {
					
						«printControlled(asm)»
					
					}
					
					private void setMonitored(''');
				
			setMonitoredArgs(asm,sb);

			sb.append(''') {
					
					«setMonitored(asm)»
				}
			}''');
		
		return sb.toString();

	}
	
	def coverFunctions(Asm asm, StringBuffer sb, boolean monitored) {
		
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction && monitored==true) {
				sb.append(System.lineSeparator)
				sb.append("\t\t").append('''cover_«fd.name»();''')
			}
		}
		
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof ControlledFunction && monitored==false) {
				sb.append(System.lineSeparator)
				sb.append("\t\t").append('''cover_«fd.name»();''')
			}
		}
	}
	
	def coverBranches(Asm asm, StringBuffer sb) {
		for (fd : asm.headerSection.signature.function){
			sb.append("\t").append('''private void cover_«fd.name»(){''');	
			if(fd.codomain instanceof EnumTd){
				sb.append(System.lineSeparator)
				sb.append("\t\t").append('''switch(this.get_«fd.name»()){''');
				var codomainContents = fd.codomain.eContents;				
				for(enumerativeLog: codomainContents){
					val startIndex = enumerativeLog.toString().indexOf("symbol: ") + "symbol: ".length
					val endIndex = enumerativeLog.toString().indexOf(")", startIndex)
					val symbol = enumerativeLog.toString().substring(startIndex, endIndex)
					sb.append(System.lineSeparator)
					sb.append("\t\t\t").append('''case «symbol» :
					System.out.println("«fd.codomain.name» «symbol» covered");
					// «fd.codomain.name» «symbol» covered
					break;''');
				}
				sb.append(System.lineSeparator)
				sb.append("\t\t\t")sb.append('''}''');
			}
			else{
				sb.append(System.lineSeparator)
				sb.append("\t\t").append('''// No covered''');
			}
			sb.append(System.lineSeparator)
			sb.append("\t").append('''}''');
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
		}
	}
	
	def monitoredGetter(Asm asm, StringBuffer sb) {
		var asmName = asm.name;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) {
					if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
						
								private boolean get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
						
								private int get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain.name.equals("String") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
						
								private String get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain instanceof EnumTd || 
						fd.codomain instanceof ConcreteDomain || 
						fd.codomain instanceof AbstractTd) {
						sb.append('''
						
								private «asmName».«fd.codomain.name» get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
				}
			}
		}
	}
	
	def controllerGetter(Asm asm, StringBuffer sb) {
		var asmName = asm.name;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof ControlledFunction) {
				if (fd.domain === null) {
					if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
						
								public boolean get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
						
								public int get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain.name.equals("String") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
						
								public String get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain instanceof EnumTd || 
						fd.codomain instanceof ConcreteDomain || 
						fd.codomain instanceof AbstractTd) {
						sb.append('''
						
								public «asmName».«fd.codomain.name» get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
				}
			}
		}
	}
	
	def printControlled(Asm asm) {

		var sb = new StringBuffer;

		for (dd : asm.headerSection.signature.domain) {
			if (dd instanceof AbstractTd) {

				sb.append('''
					System.out.print("«dd.name»"+ " = {");
					for(int i=0 ; i< esecuzione.«dd.name»_elemsList.size(); i++)
						if(i!= esecuzione.«dd.name»_elemsList.size()-1)
							System.out.print(esecuzione.«dd.name»_elemsList.get(i) +", ");
						else
							System.out.print(esecuzione.«dd.name»_elemsList.get(i));	
					System.out.println("}");
				''')

			}
		}

		for (fd : asm.headerSection.signature.function) {

			// Studio dei casi controlled con il dominio nullo, quindi funzioni che ricadono nella struttura zeroC<Valore>
			if (fd instanceof ControlledFunction) {
				if (fd.domain === null) {
					if (fd.codomain instanceof ConcreteDomain)
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».get().value);
						''')
					if (fd.codomain.name.equals("Integer") || fd.codomain.name.equals("Boolean") ||
						fd.codomain.name.equals("String"))
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
						''')
					if (fd.codomain instanceof MapDomain)
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
						''')
					if (fd.codomain instanceof SequenceDomain)
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
						''')
					if (fd.codomain instanceof EnumTd)
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».oldValue.name());
						''')
				} else {

					if (fd.domain instanceof EnumTd && fd.codomain instanceof ConcreteDomain) {
						sb.append('''
							for(int i=0; i < esecuzione.«fd.domain.name»_elemsList.size(); i++){
								System.out.println(" «fd.name» =>  (" + esecuzione.«fd.domain.name»_elemsList.get(i) + 
								") = " + esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(i)).value);
							}
						''')
					}

					if (fd.domain instanceof EnumTd && fd.codomain instanceof EnumTd) {
						sb.append('''
							for(int i=0; i < esecuzione.«fd.domain.name»_elemsList.size(); i++){
								System.out.println("«fd.name» =>  (" + esecuzione.«fd.domain.name»_elemsList.get(i) + 
								") = "+ esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(i)));
							}
						''')
					}

				}
			}

		}

		return sb.toString
	}
	
	def setMonitoredArgs(Asm asm, StringBuffer sb) {
		val asmName = asm.name
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) {
					if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''boolean «fd.name»,''')
					}
					else if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''int «fd.name»,''')
					}
					else if (fd.codomain instanceof EnumTd) {
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''«asmName».«fd.codomain.name» «fd.name»,''')
					}
					else if (fd.codomain instanceof ConcreteDomain) {
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''int «fd.name»,''')
					}
					else if (fd.codomain instanceof AbstractTd) {
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''int «fd.name»,''')
					}
				}
				else {
					/* TODO: fix the index i
					if (fd.domain instanceof ConcreteDomain && fd.codomain.name.equals("Boolean")) {
						sb.append('''boolean «fd.name»_i),''')
					}
					if (fd.domain instanceof EnumTd && fd.codomain.name.equals("Boolean")) {
						sb.append('''boolean «fd.name»_i),''')
					}
					*/
				}
			}
		}
		sb.setLength(sb.length() - 1);
	}

	def setMonitored(Asm asm) {

		var sb = new StringBuffer;

		for (fd : asm.headerSection.signature.function) {

			if (fd instanceof MonitoredFunction) {
				// Solo se il dominio » nullo, quindi funzioni che ricadono nella struttura zero<Valore> 
				if (fd.domain === null) {
					// Caso relativo alle variabili booleane non concrete
					if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
						this.esecuzione.«fd.name».set(«fd.name»);
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}

					if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
						this.esecuzione.«fd.name».set(«fd.name»);
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}

					if (fd.codomain instanceof EnumTd) {
						sb.append('''
						this.esecuzione.«fd.name».set(«fd.name»);
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}

					if (fd.codomain instanceof ConcreteDomain) {
						sb.append('''
						this.esecuzione.«fd.name»_supporto.value = «fd.name»;
						this.esecuzione.«fd.name».set(this.esecuzione.«fd.name»_supporto);
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}

					if (fd.codomain instanceof AbstractTd) {
						sb.append('''
						this.esecuzione.«fd.name».set(this.esecuzione.«fd.codomain.name»_Class.get(«fd.name»));
						System.out.println("Set «fd.name» = " + «fd.name»);''')
				    	sb.append(System.lineSeparator)
				    	sb.append(System.lineSeparator)
					}

				} else {
					/* TODO: fix the index i
					if (fd.domain instanceof ConcreteDomain && fd.codomain.name.equals("Boolean")) {
						sb.append('''
							for(int i=0; i< esecuzione.«fd.domain.name»_elems.size() ; i++) {
								esecuzione.«fd.domain.name»_elem.value = this.esecuzione.«fd.domain.name»_elems.get(i);
								System.out.println("Set «fd.name» = " + «fd.name»);
								this.esecuzione.«fd.name».set(this.esecuzione.«fd.domain.name»_elem,«fd.name» + "_" + i);
							}
							
						''')
					}

					if (fd.domain instanceof EnumTd && fd.codomain.name.equals("Boolean")) {

						sb.append('''
							for(int i=0; i < esecuzione.«fd.domain.name»_elemsList.size(); i++) {
								this.esecuzione.«fd.name».set(this.esecuzione.«fd.domain.name»_elemsList.get(i), «fd.name» + "_" + i);				    		
							}
							
						''')
					}
					*/
				}

			}

		}

		return sb.toString
	}

}
