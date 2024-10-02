package org.asmeta.asm2java.asm.cover;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.structure.Asm;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class CoverFunctions {
  public static void coverFunctions(final Asm asm, final StringBuffer sb, final boolean monitored) {
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof MonitoredFunction) && (monitored == true))) {
        sb.append(System.lineSeparator());
        StringBuffer _append = sb.append("\t\t");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("cover_");
        String _name = fd.getName();
        _builder.append(_name);
        _builder.append("();");
        _append.append(_builder);
      }
    }
    EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd_1 : _function_1) {
      if (((fd_1 instanceof ControlledFunction) && (monitored == false))) {
        sb.append(System.lineSeparator());
        StringBuffer _append_1 = sb.append("\t\t");
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("cover_");
        String _name_1 = fd_1.getName();
        _builder_1.append(_name_1);
        _builder_1.append("();");
        _append_1.append(_builder_1);
      }
    }
  }
}
