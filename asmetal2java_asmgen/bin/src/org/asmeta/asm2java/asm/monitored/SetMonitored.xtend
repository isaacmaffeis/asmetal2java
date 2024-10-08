package org.asmeta.asm2java.asm.monitored

import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.MonitoredFunction
import asmeta.structure.Asm
import org.asmeta.asm2java.ToString

class SetMonitored {
	
	static 	def setMonitored(Asm asm) {
		var sb = new StringBuffer;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) { // [] -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					if (fd.codomain instanceof EnumTd) { // [] -> Enum
						sb.append('''
						this.esecuzione.«fd.name».set(«fd.name»);
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}
					else if (fd.codomain instanceof ConcreteDomain) { // [] -> ConcreteDomain
						sb.append('''
						this.esecuzione.«fd.name».set(
							«asm.name».«fd.codomain.name».valueOf(
							this.esecuzione.«fd.codomain.name»_elems.get(
							«fd.name» - this.esecuzione.«fd.codomain.name»_elems.get(0))));
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}
					else if (fd.codomain instanceof AbstractTd) { // [] -> Abstract
						sb.append('''
						this.esecuzione.«fd.name».set(
						this.esecuzione.«fd.codomain.name»_Class.get(
						this.esecuzione.«fd.codomain.name»_elemsList.indexOf(«fd.name»)));
						System.out.println("Set «fd.name» = " + «fd.name»);''')
				    	sb.append(System.lineSeparator)
				    	sb.append(System.lineSeparator)
					}
					else{ // [] -> (Integer|String|Boolean)
						sb.append('''
						this.esecuzione.«fd.name».set(«fd.name»);
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}
				} else { // (Enum|Abstract) -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					var dd = fd.domain
					if(dd instanceof EnumTd){ // Enum -> ...
						for (var int i = 0; i < dd.element.size; i++) {
							var symbol = new ToString(asm).visit(dd.element.get(i))
							if(fd.codomain instanceof ConcreteDomain){ // Enum -> ConcreteDomain
								sb.append('''
								this.esecuzione.«fd.name».set(
								«asm.name».«dd.name».«symbol»,
								«asm.name».«fd.codomain.name».valueOf(this.esecuzione.«fd.codomain.name»_elems.get(«fd.name»_«symbol»)));
								System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);''')
							}
							else{ // Enum -> (Integer|String|Boolean|Enum|Abstract)
								sb.append('''
								this.esecuzione.«fd.name».set(
								«asm.name».«dd.name».«symbol», «fd.name»_«symbol»);''');
							}
							sb.append(System.lineSeparator)
							sb.append(System.lineSeparator)
						}
					}
					else if(fd.domain instanceof AbstractTd){ // Abstract -> ...
						for (sf : asm.headerSection.signature.function) {
							if(sf instanceof StaticFunction){
								if(sf.codomain.equals(fd.domain) && sf.domain===null){
									var symbol = sf.name
									if(fd.codomain instanceof ConcreteDomain){ // Abstract -> ConcreteDomain
										sb.append('''
										this.esecuzione.«fd.name».set(
										this.esecuzione.«fd.domain.name»_Class.get(
										this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")),
										«asm.name».«fd.codomain.name».valueOf(this.esecuzione.«fd.codomain.name»_elems.get(«fd.name»_«symbol»)));
										System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);''')
									}
									else{ // Abstract -> (Integer|String|Boolean|Enum|Abstract)
										sb.append('''
										this.esecuzione.«fd.name».set(
										this.esecuzione.«fd.domain.name»_Class.get(
										this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")),«fd.name»_«symbol»);
										System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);''')
									}
									sb.append(System.lineSeparator)
									sb.append(System.lineSeparator)
								}
							}
						}
					}
				}
			}
		}

		return sb.toString
	}
	
}