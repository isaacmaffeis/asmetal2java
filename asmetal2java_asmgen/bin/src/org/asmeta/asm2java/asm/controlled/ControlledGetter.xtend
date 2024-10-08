package org.asmeta.asm2java.asm.controlled

import asmeta.structure.Asm
import asmeta.definitions.ControlledFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.AbstractTd
import org.asmeta.asm2java.ToString
import asmeta.definitions.StaticFunction

class ControlledGetter {
	
	static def controlledGetter(Asm asm, StringBuffer sb) {
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
					if (fd.codomain instanceof ConcreteDomain) {
						sb.append('''

								public int get_«fd.name»(){
									return this.esecuzione.«fd.name».get().value;
								}
						 	''');
					}
					if (fd.codomain instanceof EnumTd ||
						fd.codomain instanceof AbstractTd) {
						sb.append('''

								public «asmName».«fd.codomain.name» get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
				}
				else{ // getter per le funzioni con Dominio -> Codominio

					for(dd : asm.headerSection.signature.domain){
						if(dd.equals(fd.domain)){
							if(dd instanceof EnumTd){
								for (var int i = 0; i < dd.element.size; i++) {
									var symbol = new ToString(asm).visit(dd.element.get(i))
									sb.append(System.lineSeparator)
									if(fd.codomain instanceof ConcreteDomain){ // considero subsetOf Integer
										sb.append("\t").append('''public int get_«fd.name»_«symbol»(){''');
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''return this.esecuzione.«fd.name».oldValues.get(''');
										sb.append(System.lineSeparator)
										sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.get(«i»)).value;''');
										sb.append(System.lineSeparator)
										sb.append("\t").append('''}''');
									} else{
										if (fd.codomain.name.equals("Integer")){
											sb.append("\t").append('''public int get_«fd.name»_«symbol»(){''');
										}
										else if (fd.codomain.name.equals("Boolean")){
											sb.append("\t").append('''public boolean get_«fd.name»_«symbol»(){''');
										}
										else if (fd.codomain.name.equals("String")){
											sb.append("\t").append('''public String get_«fd.name»_«symbol»(){''');
										}
										else{
											sb.append("\t").append('''public «asmName».«fd.codomain.name» get_«fd.name»_«symbol»(){''');
										}
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''return this.esecuzione.«fd.name».oldValues.get(''');
										sb.append(System.lineSeparator)
										sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.get(«i»));''');
										sb.append(System.lineSeparator)
										sb.append("\t").append('''}''');
									}
									sb.append(System.lineSeparator)
								}
							}// TODO: Ritornare pubblicamente dei valori astratti crea problemi perchè non si possono confrontare
							else if(dd instanceof AbstractTd){
								for (sf : asm.headerSection.signature.function) {
									if(sf instanceof StaticFunction){
										if(sf.codomain.equals(dd) && sf.domain===null){
											var symbol = sf.name
											sb.append(System.lineSeparator)
											if(fd.codomain instanceof ConcreteDomain){
												sb.append("\t").append('''public int get_«fd.name»_«symbol»(){''');
												sb.append(System.lineSeparator)
												sb.append("\t\t").append('''return this.esecuzione.«fd.name».oldValues.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_Class.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")).value);''');
												sb.append(System.lineSeparator)
												sb.append("\t").append('''}''');
											} else{
												if (fd.codomain.name.equals("Integer")){
													sb.append("\t").append('''public int get_«fd.name»_«symbol»(){''');
												}
												else if (fd.codomain.name.equals("Boolean")){
													sb.append("\t").append('''public boolean get_«fd.name»_«symbol»(){''');
												}
												else if (fd.codomain.name.equals("String")){
													sb.append("\t").append('''public Srting get_«fd.name»_«symbol»(){''');
												}
												else{
													sb.append("\t").append('''public «asm.name».«fd.codomain.name» get_«fd.name»_«symbol»(){''');
												}
												sb.append(System.lineSeparator)
												sb.append("\t\t").append('''return this.esecuzione.«fd.name».oldValues.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_Class.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")));''');
												sb.append(System.lineSeparator)
												sb.append("\t").append('''}''');
											}
											sb.append(System.lineSeparator)
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
}