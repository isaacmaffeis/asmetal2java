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
public class SetMonitoredArgs {
  public static StringBuffer setMonitoredArgsMet(final Asm asm, final StringBuffer sb) {
    StringBuffer _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("setMonitored( ");
      sb.append(_builder);
      EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
      for (final Function fd : _function) {
        if ((fd instanceof MonitoredFunction)) {
          Domain _domain = ((MonitoredFunction)fd).getDomain();
          boolean _tripleEquals = (_domain == null);
          if (_tripleEquals) {
            sb.append(System.lineSeparator());
            StringBuffer _append = sb.append("\t\t\t\t\t");
            StringConcatenation _builder_1 = new StringConcatenation();
            String _name = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name);
            _builder_1.append(",");
            _append.append(_builder_1);
          } else {
            Domain dd = ((MonitoredFunction)fd).getDomain();
            if ((dd instanceof EnumTd)) {
              for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                {
                  String symbol = new ToString(asm).visit(((EnumTd)dd).getElement().get(i));
                  sb.append(System.lineSeparator());
                  StringBuffer _append_1 = sb.append("\t\t\t\t\t");
                  StringConcatenation _builder_2 = new StringConcatenation();
                  String _name_1 = ((MonitoredFunction)fd).getName();
                  _builder_2.append(_name_1);
                  _builder_2.append("_");
                  _builder_2.append(symbol);
                  _builder_2.append(",");
                  _append_1.append(_builder_2);
                }
              }
            }
            Domain _domain_1 = ((MonitoredFunction)fd).getDomain();
            if ((_domain_1 instanceof AbstractTd)) {
              EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
              for (final Function sf : _function_1) {
                if ((sf instanceof StaticFunction)) {
                  if ((((StaticFunction)sf).getCodomain().equals(((MonitoredFunction)fd).getDomain()) && (((StaticFunction)sf).getDomain() == null))) {
                    String symbol = ((StaticFunction)sf).getName();
                    sb.append(System.lineSeparator());
                    StringBuffer _append_1 = sb.append("\t\t\t\t\t");
                    StringConcatenation _builder_2 = new StringConcatenation();
                    String _name_1 = ((MonitoredFunction)fd).getName();
                    _builder_2.append(_name_1);
                    _builder_2.append("_");
                    _builder_2.append(symbol);
                    _builder_2.append(",");
                    _append_1.append(_builder_2);
                  }
                }
              }
            }
          }
        }
      }
      int _length = sb.length();
      int _minus = (_length - 1);
      sb.setLength(_minus);
      StringConcatenation _builder_3 = new StringConcatenation();
      _builder_3.append(");");
      _xblockexpression = sb.append(_builder_3).append(System.lineSeparator());
    }
    return _xblockexpression;
  }

  public static void setMonitoredArgsFunc(final Asm asm, final StringBuffer sb) {
    final String asmName = asm.getName();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append(" ");
    sb.append(_builder);
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          boolean _equals = ((MonitoredFunction)fd).getCodomain().getName().equals("Boolean");
          if (_equals) {
            sb.append(System.lineSeparator()).append("\t\t");
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("boolean ");
            String _name = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name);
            _builder_1.append(",");
            sb.append(_builder_1);
          } else {
            if ((((MonitoredFunction)fd).getCodomain().getName().equals("Integer") || (((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain))) {
              sb.append(System.lineSeparator()).append("\t\t");
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("int ");
              String _name_1 = ((MonitoredFunction)fd).getName();
              _builder_2.append(_name_1);
              _builder_2.append(",");
              sb.append(_builder_2);
            } else {
              boolean _equals_1 = ((MonitoredFunction)fd).getCodomain().getName().equals("String");
              if (_equals_1) {
                sb.append(System.lineSeparator()).append("\t\t");
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append("String ");
                String _name_2 = ((MonitoredFunction)fd).getName();
                _builder_3.append(_name_2);
                _builder_3.append(",");
                sb.append(_builder_3);
              } else {
                Domain _codomain = ((MonitoredFunction)fd).getCodomain();
                if ((_codomain instanceof EnumTd)) {
                  sb.append(System.lineSeparator()).append("\t\t");
                  StringConcatenation _builder_4 = new StringConcatenation();
                  _builder_4.append(asmName);
                  _builder_4.append(".");
                  String _name_3 = ((MonitoredFunction)fd).getCodomain().getName();
                  _builder_4.append(_name_3);
                  _builder_4.append(" ");
                  String _name_4 = ((MonitoredFunction)fd).getName();
                  _builder_4.append(_name_4);
                  _builder_4.append(",");
                  sb.append(_builder_4);
                } else {
                  Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
                  if ((_codomain_1 instanceof AbstractTd)) {
                    sb.append(System.lineSeparator()).append("\t\t");
                    StringConcatenation _builder_5 = new StringConcatenation();
                    _builder_5.append("String ");
                    String _name_5 = ((MonitoredFunction)fd).getName();
                    _builder_5.append(_name_5);
                    _builder_5.append(",");
                    sb.append(_builder_5);
                  }
                }
              }
            }
          }
        } else {
          Domain dd = ((MonitoredFunction)fd).getDomain();
          if ((dd instanceof EnumTd)) {
            for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
              {
                String symbol = new ToString(asm).visit(((EnumTd)dd).getElement().get(i));
                if ((((MonitoredFunction)fd).getCodomain().getName().equals("Integer") || (((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain))) {
                  sb.append(System.lineSeparator()).append("\t\t");
                  StringConcatenation _builder_6 = new StringConcatenation();
                  _builder_6.append("int ");
                  String _name_6 = ((MonitoredFunction)fd).getName();
                  _builder_6.append(_name_6);
                  _builder_6.append("_");
                  _builder_6.append(symbol);
                  _builder_6.append(",");
                  sb.append(_builder_6);
                } else {
                  boolean _equals_2 = ((MonitoredFunction)fd).getCodomain().getName().equals("Boolean");
                  if (_equals_2) {
                    sb.append(System.lineSeparator()).append("\t\t");
                    StringConcatenation _builder_7 = new StringConcatenation();
                    _builder_7.append("boolean ");
                    String _name_7 = ((MonitoredFunction)fd).getName();
                    _builder_7.append(_name_7);
                    _builder_7.append("_");
                    _builder_7.append(symbol);
                    _builder_7.append(",");
                    sb.append(_builder_7);
                  } else {
                    boolean _equals_3 = ((MonitoredFunction)fd).getCodomain().getName().equals("String");
                    if (_equals_3) {
                      sb.append(System.lineSeparator()).append("\t\t");
                      StringConcatenation _builder_8 = new StringConcatenation();
                      _builder_8.append("String ");
                      String _name_8 = ((MonitoredFunction)fd).getName();
                      _builder_8.append(_name_8);
                      _builder_8.append("_");
                      _builder_8.append(symbol);
                      _builder_8.append(",");
                      sb.append(_builder_8);
                    } else {
                      sb.append(System.lineSeparator()).append("\t\t");
                      StringConcatenation _builder_9 = new StringConcatenation();
                      _builder_9.append(asmName);
                      _builder_9.append(".");
                      String _name_9 = ((MonitoredFunction)fd).getCodomain().getName();
                      _builder_9.append(_name_9);
                      _builder_9.append(" ");
                      String _name_10 = ((MonitoredFunction)fd).getName();
                      _builder_9.append(_name_10);
                      _builder_9.append("_");
                      _builder_9.append(symbol);
                      _builder_9.append(",");
                      sb.append(_builder_9);
                    }
                  }
                }
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
                    if ((((MonitoredFunction)fd).getCodomain().getName().equals("Integer") || (((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain))) {
                      sb.append(System.lineSeparator()).append("\t\t");
                      StringConcatenation _builder_6 = new StringConcatenation();
                      _builder_6.append("int ");
                      String _name_6 = ((MonitoredFunction)fd).getName();
                      _builder_6.append(_name_6);
                      _builder_6.append("_");
                      _builder_6.append(symbol);
                      _builder_6.append(",");
                      sb.append(_builder_6);
                    } else {
                      boolean _equals_2 = ((MonitoredFunction)fd).getCodomain().getName().equals("Boolean");
                      if (_equals_2) {
                        sb.append(System.lineSeparator()).append("\t\t");
                        StringConcatenation _builder_7 = new StringConcatenation();
                        _builder_7.append("boolean ");
                        String _name_7 = ((MonitoredFunction)fd).getName();
                        _builder_7.append(_name_7);
                        _builder_7.append("_");
                        _builder_7.append(symbol);
                        _builder_7.append(",");
                        sb.append(_builder_7);
                      } else {
                        boolean _equals_3 = ((MonitoredFunction)fd).getCodomain().getName().equals("String");
                        if (_equals_3) {
                          sb.append(System.lineSeparator()).append("\t\t");
                          StringConcatenation _builder_8 = new StringConcatenation();
                          _builder_8.append("String ");
                          String _name_8 = ((MonitoredFunction)fd).getName();
                          _builder_8.append(_name_8);
                          _builder_8.append("_");
                          _builder_8.append(symbol);
                          _builder_8.append(",");
                          sb.append(_builder_8);
                        } else {
                          sb.append(System.lineSeparator()).append("\t\t");
                          StringConcatenation _builder_9 = new StringConcatenation();
                          _builder_9.append(asmName);
                          _builder_9.append(".");
                          String _name_9 = ((MonitoredFunction)fd).getCodomain().getName();
                          _builder_9.append(_name_9);
                          _builder_9.append(" ");
                          String _name_10 = ((MonitoredFunction)fd).getName();
                          _builder_9.append(_name_10);
                          _builder_9.append("_");
                          _builder_9.append(symbol);
                          _builder_9.append(",");
                          sb.append(_builder_9);
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
    int _length = sb.length();
    int _minus = (_length - 1);
    sb.setLength(_minus);
  }
}
