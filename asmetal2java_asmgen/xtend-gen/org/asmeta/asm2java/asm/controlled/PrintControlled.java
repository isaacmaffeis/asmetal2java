package org.asmeta.asm2java.asm.controlled;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class PrintControlled {
  public static String printControlled(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Domain> _domain = asm.getHeaderSection().getSignature().getDomain();
    for (final Domain dd : _domain) {
      if ((dd instanceof AbstractTd)) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("System.out.print(\"");
        String _name = ((AbstractTd)dd).getName();
        _builder.append(_name);
        _builder.append("\"+ \" = {\");");
        _builder.newLineIfNotEmpty();
        _builder.append("for(int i=0 ; i< esecuzione.");
        String _name_1 = ((AbstractTd)dd).getName();
        _builder.append(_name_1);
        _builder.append("_elemsList.size(); i++)");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("if(i!= esecuzione.");
        String _name_2 = ((AbstractTd)dd).getName();
        _builder.append(_name_2, "\t");
        _builder.append("_elemsList.size()-1)");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("System.out.print(esecuzione.");
        String _name_3 = ((AbstractTd)dd).getName();
        _builder.append(_name_3, "\t\t");
        _builder.append("_elemsList.get(i) +\", \");");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("else");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("System.out.print(esecuzione.");
        String _name_4 = ((AbstractTd)dd).getName();
        _builder.append(_name_4, "\t\t");
        _builder.append("_elemsList.get(i));");
        _builder.newLineIfNotEmpty();
        _builder.append("System.out.println(\"}\");");
        _builder.newLine();
        sb.append(_builder);
      }
    }
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof ControlledFunction)) {
        Domain _domain_1 = ((ControlledFunction)fd).getDomain();
        boolean _tripleEquals = (_domain_1 == null);
        if (_tripleEquals) {
          Domain _codomain = ((ControlledFunction)fd).getCodomain();
          if ((_codomain instanceof ConcreteDomain)) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("System.out.println(\"");
            String _name_5 = ((ControlledFunction)fd).getName();
            _builder_1.append(_name_5);
            _builder_1.append(" = \" + esecuzione.");
            String _name_6 = ((ControlledFunction)fd).getName();
            _builder_1.append(_name_6);
            _builder_1.append(".get().value);");
            _builder_1.newLineIfNotEmpty();
            sb.append(_builder_1);
          }
          if (((((ControlledFunction)fd).getCodomain().getName().equals("Integer") || ((ControlledFunction)fd).getCodomain().getName().equals("Boolean")) || 
            ((ControlledFunction)fd).getCodomain().getName().equals("String"))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("System.out.println(\"");
            String _name_7 = ((ControlledFunction)fd).getName();
            _builder_2.append(_name_7);
            _builder_2.append(" = \" + esecuzione.");
            String _name_8 = ((ControlledFunction)fd).getName();
            _builder_2.append(_name_8);
            _builder_2.append(".get());");
            _builder_2.newLineIfNotEmpty();
            sb.append(_builder_2);
          }
          Domain _codomain_1 = ((ControlledFunction)fd).getCodomain();
          if ((_codomain_1 instanceof MapDomain)) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("System.out.println(\"");
            String _name_9 = ((ControlledFunction)fd).getName();
            _builder_3.append(_name_9);
            _builder_3.append(" = \" + esecuzione.");
            String _name_10 = ((ControlledFunction)fd).getName();
            _builder_3.append(_name_10);
            _builder_3.append(".get());");
            _builder_3.newLineIfNotEmpty();
            sb.append(_builder_3);
          }
          Domain _codomain_2 = ((ControlledFunction)fd).getCodomain();
          if ((_codomain_2 instanceof SequenceDomain)) {
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append("System.out.println(\"");
            String _name_11 = ((ControlledFunction)fd).getName();
            _builder_4.append(_name_11);
            _builder_4.append(" = \" + esecuzione.");
            String _name_12 = ((ControlledFunction)fd).getName();
            _builder_4.append(_name_12);
            _builder_4.append(".get());");
            _builder_4.newLineIfNotEmpty();
            sb.append(_builder_4);
          }
          Domain _codomain_3 = ((ControlledFunction)fd).getCodomain();
          if ((_codomain_3 instanceof EnumTd)) {
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append("System.out.println(\"");
            String _name_13 = ((ControlledFunction)fd).getName();
            _builder_5.append(_name_13);
            _builder_5.append(" = \" + esecuzione.");
            String _name_14 = ((ControlledFunction)fd).getName();
            _builder_5.append(_name_14);
            _builder_5.append(".oldValue.name());");
            _builder_5.newLineIfNotEmpty();
            sb.append(_builder_5);
          }
        } else {
          if (((((ControlledFunction)fd).getDomain() instanceof EnumTd) && (((ControlledFunction)fd).getCodomain() instanceof ConcreteDomain))) {
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append("for(int i=0; i < esecuzione.");
            String _name_15 = ((ControlledFunction)fd).getDomain().getName();
            _builder_6.append(_name_15);
            _builder_6.append("_elemsList.size(); i++){");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("\t");
            _builder_6.append("System.out.println(\" ");
            String _name_16 = ((ControlledFunction)fd).getName();
            _builder_6.append(_name_16, "\t");
            _builder_6.append(" =>  (\" + esecuzione.");
            String _name_17 = ((ControlledFunction)fd).getDomain().getName();
            _builder_6.append(_name_17, "\t");
            _builder_6.append("_elemsList.get(i) +");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("\t");
            _builder_6.append("\") = \" + esecuzione.");
            String _name_18 = ((ControlledFunction)fd).getName();
            _builder_6.append(_name_18, "\t");
            _builder_6.append(".oldValues.get(esecuzione.");
            String _name_19 = ((ControlledFunction)fd).getDomain().getName();
            _builder_6.append(_name_19, "\t");
            _builder_6.append("_elemsList.get(i)).value);");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("}");
            _builder_6.newLine();
            sb.append(_builder_6);
          }
          if (((((ControlledFunction)fd).getDomain() instanceof EnumTd) && (((ControlledFunction)fd).getCodomain() instanceof EnumTd))) {
            StringConcatenation _builder_7 = new StringConcatenation();
            _builder_7.append("for(int i=0; i < esecuzione.");
            String _name_20 = ((ControlledFunction)fd).getDomain().getName();
            _builder_7.append(_name_20);
            _builder_7.append("_elemsList.size(); i++){");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("\t");
            _builder_7.append("System.out.println(\"");
            String _name_21 = ((ControlledFunction)fd).getName();
            _builder_7.append(_name_21, "\t");
            _builder_7.append(" =>  (\" + esecuzione.");
            String _name_22 = ((ControlledFunction)fd).getDomain().getName();
            _builder_7.append(_name_22, "\t");
            _builder_7.append("_elemsList.get(i) +");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("\t");
            _builder_7.append("\") = \"+ esecuzione.");
            String _name_23 = ((ControlledFunction)fd).getName();
            _builder_7.append(_name_23, "\t");
            _builder_7.append(".oldValues.get(esecuzione.");
            String _name_24 = ((ControlledFunction)fd).getDomain().getName();
            _builder_7.append(_name_24, "\t");
            _builder_7.append("_elemsList.get(i)));");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("}");
            _builder_7.newLine();
            sb.append(_builder_7);
          }
        }
      }
    }
    return sb.toString();
  }
}
