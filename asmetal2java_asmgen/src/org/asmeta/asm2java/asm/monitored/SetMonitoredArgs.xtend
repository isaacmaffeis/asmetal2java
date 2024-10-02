package org.asmeta.asm2java.asm.monitored

import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.MonitoredFunction
import asmeta.structure.Asm
import org.asmeta.asm2java.ToString

class SetMonitoredArgs {
	
	static def setMonitoredArgsMet(Asm asm, StringBuffer sb){
		sb.append('''setMonitored( ''');
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) { // [] -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					sb.append(System.lineSeparator)
					sb.append("\t\t\t\t\t").append('''«fd.name»,''')
				}
				else {
					var dd = fd.domain
					if(dd instanceof EnumTd){ // Enum -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
						for (var int i = 0; i < dd.element.size; i++) {
							var symbol = new ToString(asm).visit(dd.element.get(i))
								sb.append(System.lineSeparator())
								sb.append("\t\t\t\t\t").append('''«fd.name»_«symbol»,''')
						}
					}
					if(fd.domain instanceof AbstractTd){ // Abstract -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
						for (sf : asm.headerSection.signature.function) {
							if(sf instanceof StaticFunction){
								if(sf.codomain.equals(fd.domain) && sf.domain===null){
									var symbol = sf.name
									sb.append(System.lineSeparator())
									sb.append("\t\t\t\t\t").append('''«fd.name»_«symbol»,''')
								}
							}
						}
					}
				}
			}
		}
		sb.setLength(sb.length() - 1);
		sb.append(''');''').append(System.lineSeparator());
	}
	
	static def setMonitoredArgsFunc(Asm asm, StringBuffer sb) {
		val asmName = asm.name
		sb.append(''' ''')
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) { // [] -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					if (fd.codomain.name.equals("Boolean")) { // [] -> Boolean
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''boolean «fd.name»,''')
					}
					else if (fd.codomain.name.equals("Integer") || (fd.codomain instanceof ConcreteDomain)) { // [] -> (Integer|ConcreteDomain)
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''int «fd.name»,''')
					}
					else if (fd.codomain.name.equals("String")) { // [] -> String
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''String «fd.name»,''')
					}
					else if (fd.codomain instanceof EnumTd) { // [] -> Enum
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''«asmName».«fd.codomain.name» «fd.name»,''')
					}
					else if (fd.codomain instanceof AbstractTd) { // [] -> Abstract
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''String «fd.name»,''')
					}
				}
				else { // (Enum|Abstract) -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					var dd = fd.domain
					if(dd instanceof EnumTd){ // Enum -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
						for (var int i = 0; i < dd.element.size; i++) {
							var symbol = new ToString(asm).visit(dd.element.get(i))
							if(fd.codomain.name.equals("Integer") || (fd.codomain instanceof ConcreteDomain)){ // Enum -> (Integer|ConcreteDomain)
								sb.append(System.lineSeparator()).append("\t\t")
								sb.append('''int «fd.name»_«symbol»,''')
							}
							else if(fd.codomain.name.equals("Boolean")){ // Enum -> Boolean
								sb.append(System.lineSeparator()).append("\t\t")
								sb.append('''boolean «fd.name»_«symbol»,''')
							}
							else if(fd.codomain.name.equals("String")){ // Enum -> String
								sb.append(System.lineSeparator()).append("\t\t")
								sb.append('''String «fd.name»_«symbol»,''')
							}
							else /*if (fd.codomain instanceof EnumTd || fd.codomain instanceof AbstractTd)*/ { // Enum -> (Enum|Abstract)
								sb.append(System.lineSeparator()).append("\t\t")
								sb.append('''«asmName».«fd.codomain.name» «fd.name»_«symbol»,''')
							}
						}
					}
					else if(fd.domain instanceof AbstractTd){ // Abstract -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
						for (sf : asm.headerSection.signature.function) {
							if(sf instanceof StaticFunction ){
								if(sf.codomain.equals(fd.domain) && sf.domain===null){
									var symbol = sf.name
									if(fd.codomain.name.equals("Integer") || (fd.codomain instanceof ConcreteDomain)){ // Abstract -> (Integer|ConcreteDomain)
										sb.append(System.lineSeparator()).append("\t\t")
										sb.append('''int «fd.name»_«symbol»,''')
									}
									else if(fd.codomain.name.equals("Boolean")){ // Abstract -> Boolean
										sb.append(System.lineSeparator()).append("\t\t")
										sb.append('''boolean «fd.name»_«symbol»,''')
									}
									else if(fd.codomain.name.equals("String")){ // Abstract -> String
										sb.append(System.lineSeparator()).append("\t\t")
										sb.append('''String «fd.name»_«symbol»,''')
									}
									else /*if (fd.codomain instanceof EnumTd || fd.codomain instanceof AbstractTd)*/ { // Abstract -> (Enum|Abstract)
										sb.append(System.lineSeparator()).append("\t\t")
										sb.append('''«asmName».«fd.codomain.name» «fd.name»_«symbol»,''')
									}
								}
							}
						}
					}
				}
			}
		}
		sb.setLength(sb.length() - 1);
	}
}