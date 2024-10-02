package org.asmeta.asm2java.asm.controlled

import asmeta.structure.Asm
import asmeta.definitions.ControlledFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.SequenceDomain

class PrintControlled {
	
	static def printControlled(Asm asm) {

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
	
}