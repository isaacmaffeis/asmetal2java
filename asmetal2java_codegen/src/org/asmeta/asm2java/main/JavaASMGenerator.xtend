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

class JavaASMGenerator extends AsmToJavaGenerator {

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
			//Classe per l'esecuzione tramite una Abstract State Machine (ASM) dei file java generati.

			import java.util.Scanner;

			class «asmName»_ASM {

				private final «asmName» esecuzione;
				private int stato;

			    /**
			     * Costruttore della classe {@code «asmName»_ASM}. Crea un'istanza privata della asm
			     * {@link «asmName»} e imposta lo stato iniziale della macchina a stati a 1.
			     */
				public «asmName»_ASM(){
					this.esecuzione = new «asmName»();
					this.stato = 0;
				}

				public void step(''');

			for (fd : asm.headerSection.signature.function) {
				if (fd instanceof MonitoredFunction) {
					if (fd.domain === null) {
						if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
							sb.append('''\nboolean «fd.name»),''')
						}
						else if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''\nint «fd.name»),''')
						}
						else if (fd.codomain instanceof EnumTd) {
						sb.append('''\n«fd.codomain.name» «fd.name»),''')
						}
						else if (fd.codomain instanceof ConcreteDomain) {
						sb.append('''\n«fd.codomain.name» «fd.name»),''')
						}
						else if (fd.codomain instanceof AbstractTd) {
						sb.append('''\n«fd.codomain.name» «fd.name»),''')
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
			sb.append('''){
					System.out.println("<State "+ stato +" (controlled)>");

					//Aggiornamento valori dell'ASM e inserimento dati monitorati

					printControlled(esecuzione);
					setMonitored(''');

			for (fd : asm.headerSection.signature.function) {
				if (fd instanceof MonitoredFunction) {
					if (fd.domain === null) {
						sb.append('''«fd.name»),''')
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
					printControlled(esecuzione);


			  	    stato++;
				}

				// ASM Methods

				static void printControlled(«asmName» esecuzione) {

					«printControlled(asm)»

				}

				static void setMonitored(«asmName» esecuzione) {

					«setMonitored(asm)»

				}
			}

		''');

		return sb.toString();

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
							for(int i=0; i < esecuzione.«fd.domain.name»_elemsList.size(); i++)
									{
										System.out.println(" «fd.name» =>  (" + esecuzione.«fd.domain.name»_elemsList.get(i) +")
										= " + esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(i)).value );
									}
						''')
					}

					if (fd.domain instanceof EnumTd && fd.codomain instanceof EnumTd) {
						sb.append('''
							for(int i=0; i < esecuzione.«fd.domain.name»_elemsList.size(); i++)
									{
										System.out.println("«fd.name» =>  (" + esecuzione.«fd.domain.name»_elemsList.get(i) +")
										= "+ esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(i)));
									}
						''')
					}

				}
			}

		}

		return sb.toString
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
							System.out.println("Set «fd.name» = " + «fd.name»);

						''')


					}

					if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''
							this.esecuzione.«fd.name».set(«fd.name»);
							System.out.println("Set «fd.name» = " + «fd.name»);

						''')
					}

					if (fd.codomain instanceof EnumTd) {
						sb.append('''
							this.esecuzione.«fd.name».set(«fd.name»);
							System.out.println("Set «fd.name» = " + «fd.name»);

						''')
					}

					if (fd.codomain instanceof ConcreteDomain) {
						sb.append('''
							this.esecuzione.«fd.name»_supporto.value = «fd.name»;
							this.esecuzione.«fd.name».set(esecuzione.«fd.name»_supporto);
							System.out.println("Set «fd.name» = " + «fd.name»);

						''')
					}

					if (fd.codomain instanceof AbstractTd) {
						sb.append('''

				    		this.esecuzione.«fd.name».set(this.esecuzione.«fd.codomain»_Class.get(«fd.name»));
				    		System.out.println("Set «fd.name» = " + «fd.name»);

				    		''')
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
