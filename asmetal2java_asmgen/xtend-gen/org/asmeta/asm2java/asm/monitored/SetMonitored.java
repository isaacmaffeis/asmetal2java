package org.asmeta.asm2java.asm.monitored;

import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.structure.Asm;
import org.asmeta.asm2java.ToString;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class SetMonitored {
  public static String setMonitored(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          Domain _codomain = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain instanceof EnumTd)) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("this.esecuzione.");
            String _name = ((MonitoredFunction)fd).getName();
            _builder.append(_name);
            _builder.append(".set(");
            String _name_1 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_1);
            _builder.append(");");
            _builder.newLineIfNotEmpty();
            _builder.append("System.out.println(\"Set ");
            String _name_2 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_2);
            _builder.append(" = \" + ");
            String _name_3 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_3);
            _builder.append(");");
            sb.append(_builder);
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
          } else {
            Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
            if ((_codomain_1 instanceof ConcreteDomain)) {
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("this.esecuzione.");
              String _name_4 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_4);
              _builder_1.append(".set(");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t");
              String _name_5 = asm.getName();
              _builder_1.append(_name_5, "\t");
              _builder_1.append(".");
              String _name_6 = ((MonitoredFunction)fd).getCodomain().getName();
              _builder_1.append(_name_6, "\t");
              _builder_1.append(".valueOf(");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t");
              _builder_1.append("this.esecuzione.");
              String _name_7 = ((MonitoredFunction)fd).getCodomain().getName();
              _builder_1.append(_name_7, "\t");
              _builder_1.append("_elems.get(");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("\t");
              String _name_8 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_8, "\t");
              _builder_1.append(" - this.esecuzione.");
              String _name_9 = ((MonitoredFunction)fd).getCodomain().getName();
              _builder_1.append(_name_9, "\t");
              _builder_1.append("_elems.get(0))));");
              _builder_1.newLineIfNotEmpty();
              _builder_1.append("System.out.println(\"Set ");
              String _name_10 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_10);
              _builder_1.append(" = \" + ");
              String _name_11 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_11);
              _builder_1.append(");");
              sb.append(_builder_1);
              sb.append(System.lineSeparator());
              sb.append(System.lineSeparator());
            } else {
              Domain _codomain_2 = ((MonitoredFunction)fd).getCodomain();
              if ((_codomain_2 instanceof AbstractTd)) {
                StringConcatenation _builder_2 = new StringConcatenation();
                _builder_2.append("this.esecuzione.");
                String _name_12 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_12);
                _builder_2.append(".set(");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("this.esecuzione.");
                String _name_13 = ((MonitoredFunction)fd).getCodomain().getName();
                _builder_2.append(_name_13);
                _builder_2.append("_Class.get(");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("this.esecuzione.");
                String _name_14 = ((MonitoredFunction)fd).getCodomain().getName();
                _builder_2.append(_name_14);
                _builder_2.append("_elemsList.indexOf(");
                String _name_15 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_15);
                _builder_2.append(")));");
                _builder_2.newLineIfNotEmpty();
                _builder_2.append("System.out.println(\"Set ");
                String _name_16 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_16);
                _builder_2.append(" = \" + ");
                String _name_17 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_17);
                _builder_2.append(");");
                sb.append(_builder_2);
                sb.append(System.lineSeparator());
                sb.append(System.lineSeparator());
              } else {
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append("this.esecuzione.");
                String _name_18 = ((MonitoredFunction)fd).getName();
                _builder_3.append(_name_18);
                _builder_3.append(".set(");
                String _name_19 = ((MonitoredFunction)fd).getName();
                _builder_3.append(_name_19);
                _builder_3.append(");");
                _builder_3.newLineIfNotEmpty();
                _builder_3.append("System.out.println(\"Set ");
                String _name_20 = ((MonitoredFunction)fd).getName();
                _builder_3.append(_name_20);
                _builder_3.append(" = \" + ");
                String _name_21 = ((MonitoredFunction)fd).getName();
                _builder_3.append(_name_21);
                _builder_3.append(");");
                sb.append(_builder_3);
                sb.append(System.lineSeparator());
                sb.append(System.lineSeparator());
              }
            }
          }
        } else {
          Domain dd = ((MonitoredFunction)fd).getDomain();
          if ((dd instanceof EnumTd)) {
            for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
              {
                String symbol = new ToString(asm).visit(((EnumTd)dd).getElement().get(i));
                Domain _codomain_3 = ((MonitoredFunction)fd).getCodomain();
                if ((_codomain_3 instanceof ConcreteDomain)) {
                  StringConcatenation _builder_4 = new StringConcatenation();
                  _builder_4.append("this.esecuzione.");
                  String _name_22 = ((MonitoredFunction)fd).getName();
                  _builder_4.append(_name_22);
                  _builder_4.append(".set(");
                  _builder_4.newLineIfNotEmpty();
                  String _name_23 = asm.getName();
                  _builder_4.append(_name_23);
                  _builder_4.append(".");
                  String _name_24 = ((EnumTd)dd).getName();
                  _builder_4.append(_name_24);
                  _builder_4.append(".");
                  _builder_4.append(symbol);
                  _builder_4.append(",");
                  _builder_4.newLineIfNotEmpty();
                  String _name_25 = asm.getName();
                  _builder_4.append(_name_25);
                  _builder_4.append(".");
                  String _name_26 = ((MonitoredFunction)fd).getCodomain().getName();
                  _builder_4.append(_name_26);
                  _builder_4.append(".valueOf(this.esecuzione.");
                  String _name_27 = ((MonitoredFunction)fd).getCodomain().getName();
                  _builder_4.append(_name_27);
                  _builder_4.append("_elems.get(");
                  String _name_28 = ((MonitoredFunction)fd).getName();
                  _builder_4.append(_name_28);
                  _builder_4.append("_");
                  _builder_4.append(symbol);
                  _builder_4.append(")));");
                  _builder_4.newLineIfNotEmpty();
                  _builder_4.append("System.out.println(\"Set ");
                  String _name_29 = ((MonitoredFunction)fd).getName();
                  _builder_4.append(_name_29);
                  _builder_4.append("_");
                  _builder_4.append(symbol);
                  _builder_4.append(" = \" + ");
                  String _name_30 = ((MonitoredFunction)fd).getName();
                  _builder_4.append(_name_30);
                  _builder_4.append("_");
                  _builder_4.append(symbol);
                  _builder_4.append(");");
                  sb.append(_builder_4);
                } else {
                  StringConcatenation _builder_5 = new StringConcatenation();
                  _builder_5.append("this.esecuzione.");
                  String _name_31 = ((MonitoredFunction)fd).getName();
                  _builder_5.append(_name_31);
                  _builder_5.append(".set(");
                  _builder_5.newLineIfNotEmpty();
                  String _name_32 = asm.getName();
                  _builder_5.append(_name_32);
                  _builder_5.append(".");
                  String _name_33 = ((EnumTd)dd).getName();
                  _builder_5.append(_name_33);
                  _builder_5.append(".");
                  _builder_5.append(symbol);
                  _builder_5.append(", ");
                  String _name_34 = ((MonitoredFunction)fd).getName();
                  _builder_5.append(_name_34);
                  _builder_5.append("_");
                  _builder_5.append(symbol);
                  _builder_5.append(");");
                  sb.append(_builder_5);
                }
                sb.append(System.lineSeparator());
                sb.append(System.lineSeparator());
              }
            }
          } else {
            Domain _domain_1 = ((MonitoredFunction)fd).getDomain();
            if ((_domain_1 instanceof AbstractTd)) {
              EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
              for (final Function sf : _function_1) {
                if ((sf instanceof StaticFunction)) {
                  if ((((StaticFunction)sf).getCodomain().equals(((MonitoredFunction)fd).getDomain()) && (((StaticFunction)sf).getDomain() == null))) {
                    String symbol = ((StaticFunction)sf).getName();
                    Domain _codomain_3 = ((MonitoredFunction)fd).getCodomain();
                    if ((_codomain_3 instanceof ConcreteDomain)) {
                      StringConcatenation _builder_4 = new StringConcatenation();
                      _builder_4.append("this.esecuzione.");
                      String _name_22 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_22);
                      _builder_4.append(".set(");
                      _builder_4.newLineIfNotEmpty();
                      _builder_4.append("this.esecuzione.");
                      String _name_23 = ((MonitoredFunction)fd).getDomain().getName();
                      _builder_4.append(_name_23);
                      _builder_4.append("_Class.get(");
                      _builder_4.newLineIfNotEmpty();
                      _builder_4.append("this.esecuzione.");
                      String _name_24 = ((MonitoredFunction)fd).getDomain().getName();
                      _builder_4.append(_name_24);
                      _builder_4.append("_elemsList.indexOf(\"");
                      _builder_4.append(symbol);
                      _builder_4.append("\")),");
                      _builder_4.newLineIfNotEmpty();
                      String _name_25 = asm.getName();
                      _builder_4.append(_name_25);
                      _builder_4.append(".");
                      String _name_26 = ((MonitoredFunction)fd).getCodomain().getName();
                      _builder_4.append(_name_26);
                      _builder_4.append(".valueOf(this.esecuzione.");
                      String _name_27 = ((MonitoredFunction)fd).getCodomain().getName();
                      _builder_4.append(_name_27);
                      _builder_4.append("_elems.get(");
                      String _name_28 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_28);
                      _builder_4.append("_");
                      _builder_4.append(symbol);
                      _builder_4.append(")));");
                      _builder_4.newLineIfNotEmpty();
                      _builder_4.append("System.out.println(\"Set ");
                      String _name_29 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_29);
                      _builder_4.append("_");
                      _builder_4.append(symbol);
                      _builder_4.append(" = \" + ");
                      String _name_30 = ((MonitoredFunction)fd).getName();
                      _builder_4.append(_name_30);
                      _builder_4.append("_");
                      _builder_4.append(symbol);
                      _builder_4.append(");");
                      sb.append(_builder_4);
                    } else {
                      StringConcatenation _builder_5 = new StringConcatenation();
                      _builder_5.append("this.esecuzione.");
                      String _name_31 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_31);
                      _builder_5.append(".set(");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("this.esecuzione.");
                      String _name_32 = ((MonitoredFunction)fd).getDomain().getName();
                      _builder_5.append(_name_32);
                      _builder_5.append("_Class.get(");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("this.esecuzione.");
                      String _name_33 = ((MonitoredFunction)fd).getDomain().getName();
                      _builder_5.append(_name_33);
                      _builder_5.append("_elemsList.indexOf(\"");
                      _builder_5.append(symbol);
                      _builder_5.append("\")),");
                      String _name_34 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_34);
                      _builder_5.append("_");
                      _builder_5.append(symbol);
                      _builder_5.append(");");
                      _builder_5.newLineIfNotEmpty();
                      _builder_5.append("System.out.println(\"Set ");
                      String _name_35 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_35);
                      _builder_5.append("_");
                      _builder_5.append(symbol);
                      _builder_5.append(" = \" + ");
                      String _name_36 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_36);
                      _builder_5.append("_");
                      _builder_5.append(symbol);
                      _builder_5.append(");");
                      sb.append(_builder_5);
                    }
                    sb.append(System.lineSeparator());
                    sb.append(System.lineSeparator());
                  }
                }
              }
            }
          }
        }
      }
    }
    return sb.toString();
  }
}
