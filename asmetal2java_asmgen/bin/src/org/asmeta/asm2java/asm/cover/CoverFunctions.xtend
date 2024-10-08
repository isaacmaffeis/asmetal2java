package org.asmeta.asm2java.asm.cover

import asmeta.structure.Asm
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.ControlledFunction

class CoverFunctions {

	static 	def coverFunctions(Asm asm, StringBuffer sb, boolean monitored) {

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

}