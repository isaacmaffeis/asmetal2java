package org.asmeta.asm2java.asm.cover

import asmeta.definitions.ControlledFunction
import asmeta.structure.Asm

class FinalState {
	
	static def ifFinalState(Asm asm, StringBuffer sb, String[] finalStateConditions){
		if(finalStateConditions !== null && !finalStateConditions.isEmpty){
				sb.append("\t\t\t\t" ).append('''/* final state condition */''')
				sb.append(System.lineSeparator)
				sb.append("\t\t\t\t" ).append('''if(isFinalState()){
						System.out.println("\n<Stato finale>");
				}
				else''')
				sb.append(System.lineSeparator)
			}
	}
	
	static def setIsFinalState(Asm asm, StringBuffer sb, String[] finalStateConditions){
		if(finalStateConditions !== null && !finalStateConditions.isEmpty){
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
			sb.append("\t").append('''// final state condition''')
			sb.append(System.lineSeparator)
			sb.append("\t").append('''public boolean isFinalState(){''')
			sb.append(System.lineSeparator)
			sb.append("\t\t").append('''return''')
			var numberOfConditionsExpected = 0
			var numberOfConditionsActual = 0
			var ss = new StringBuffer;
			for(condition : finalStateConditions){
				numberOfConditionsExpected += 1
				val cond_name = condition.replaceAll("^\\s*(\\w+)\\s*.*$", "$1")
				var cond_value = condition.replaceAll("^\\s*\\w+\\s*(.*)$", "$1")

				if(cond_name.toLowerCase.equals("stato")){
					ss.append(System.lineSeparator)
					ss.append("\t\t\t").append('''this.stato «cond_value» &&''')
					numberOfConditionsActual +=1
				} else {
					if(!cond_value.matches("\\d+")){ // se la condizione non è numerica
						var cond_value_operators = cond_value.replaceAll("^([><=!]+).*", "$1");
						cond_value = '''«asm.name».«cond_value.replaceAll("^([><=!]+)(.*)","$2")»''' ; // aggiungo il prefisso
						cond_value = cond_value_operators.concat(cond_value)
					}
					for(fd : asm.headerSection.signature.function){
						if(fd instanceof ControlledFunction && fd.name.equals(cond_name)){
							ss.append(System.lineSeparator)
							ss.append("\t\t\t").append('''this.get_«fd.name»() «cond_value» &&''')
							numberOfConditionsActual +=1
						}
					}
				}
			}
			if(numberOfConditionsActual == 0){
				sb.append(" true;")
				sb.append(System.lineSeparator)
				sb.append("\t").append('''// ERROR - NO Conditions found''')
			}
			else if(numberOfConditionsActual == numberOfConditionsExpected){
				sb.append(ss.toString)
				sb.setLength(sb.length() - 3)
				sb.append(''';''')
			}
			else{
				sb.append(ss.toString)
				sb.setLength(sb.length() - 3)
				sb.append(''';''')
				sb.append(System.lineSeparator)
				sb.append("\t").append('''// ERROR - «numberOfConditionsExpected-numberOfConditionsActual» Conditions not generated''')
			}
			sb.append(System.lineSeparator)
			sb.append("\t").append('''}''')
			sb.append(System.lineSeparator)
		}
	}

}