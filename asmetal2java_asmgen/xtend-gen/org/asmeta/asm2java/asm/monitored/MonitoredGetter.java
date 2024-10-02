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
public class MonitoredGetter {
  public static void monitoredGetter(final Asm asm, final StringBuffer sb) {
    String asmName = asm.getName();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("Boolean") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.newLine();
            _builder.append("private boolean get_");
            String _name = ((MonitoredFunction)fd).getName();
            _builder.append(_name);
            _builder.append("(){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("return this.esecuzione.");
            String _name_1 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_1, "\t");
            _builder.append(".get();");
            _builder.newLineIfNotEmpty();
            _builder.append("}");
            _builder.newLine();
            sb.append(_builder);
          }
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("Integer") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.newLine();
            _builder_1.append("private int get_");
            String _name_2 = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name_2);
            _builder_1.append("(){");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("return this.esecuzione.");
            String _name_3 = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name_3, "\t");
            _builder_1.append(".get();");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("}");
            _builder_1.newLine();
            sb.append(_builder_1);
          }
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("String") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.newLine();
            _builder_2.append("private String get_");
            String _name_4 = ((MonitoredFunction)fd).getName();
            _builder_2.append(_name_4);
            _builder_2.append("(){");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t");
            _builder_2.append("return this.esecuzione.");
            String _name_5 = ((MonitoredFunction)fd).getName();
            _builder_2.append(_name_5, "\t");
            _builder_2.append(".get();");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("}");
            _builder_2.newLine();
            sb.append(_builder_2);
          }
          Domain _codomain = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain instanceof ConcreteDomain)) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.newLine();
            _builder_3.append("private int get_");
            String _name_6 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_6);
            _builder_3.append("(){");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("\t");
            _builder_3.append("return this.esecuzione.");
            String _name_7 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_7, "\t");
            _builder_3.append(".get().value;");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("}");
            _builder_3.newLine();
            sb.append(_builder_3);
          }
          if (((((MonitoredFunction)fd).getCodomain() instanceof EnumTd) || 
            (((MonitoredFunction)fd).getCodomain() instanceof AbstractTd))) {
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.newLine();
            _builder_4.append("private ");
            _builder_4.append(asmName);
            _builder_4.append(".");
            String _name_8 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder_4.append(_name_8);
            _builder_4.append(" get_");
            String _name_9 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_9);
            _builder_4.append("(){");
            _builder_4.newLineIfNotEmpty();
            _builder_4.append("\t");
            _builder_4.append("return this.esecuzione.");
            String _name_10 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_10, "\t");
            _builder_4.append(".get();");
            _builder_4.newLineIfNotEmpty();
            _builder_4.append("}");
            _builder_4.newLine();
            sb.append(_builder_4);
          }
        } else {
          EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
          for (final Domain dd : _domain_1) {
            boolean _equals = dd.equals(((MonitoredFunction)fd).getDomain());
            if (_equals) {
              if ((dd instanceof EnumTd)) {
                for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                  {
                    String symbol = new ToString(asm).visit(((EnumTd)dd).getElement().get(i));
                    sb.append(System.lineSeparator());
                    Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
                    if ((_codomain_1 instanceof ConcreteDomain)) {
                      StringBuffer _append = sb.append("\t");
                      StringConcatenation _builder_5 = new StringConcatenation();
                      _builder_5.append("private int get_");
                      String _name_11 = ((MonitoredFunction)fd).getName();
                      _builder_5.append(_name_11);
                      _builder_5.append("_");
                      _builder_5.append(symbol);
                      _builder_5.append("(){");
                      _append.append(_builder_5);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_1 = sb.append("\t\t");
                      StringConcatenation _builder_6 = new StringConcatenation();
                      _builder_6.append("return this.esecuzione.");
                      String _name_12 = ((MonitoredFunction)fd).getName();
                      _builder_6.append(_name_12);
                      _builder_6.append(".get(");
                      _append_1.append(_builder_6);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_2 = sb.append("\t\t\t");
                      StringConcatenation _builder_7 = new StringConcatenation();
                      _builder_7.append("this.esecuzione.");
                      String _name_13 = ((MonitoredFunction)fd).getDomain().getName();
                      _builder_7.append(_name_13);
                      _builder_7.append("_elemsList.get(");
                      _builder_7.append(i);
                      _builder_7.append(")).value;");
                      _append_2.append(_builder_7);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_3 = sb.append("\t");
                      StringConcatenation _builder_8 = new StringConcatenation();
                      _builder_8.append("}");
                      _append_3.append(_builder_8);
                    } else {
                      boolean _equals_1 = ((MonitoredFunction)fd).getCodomain().getName().equals("Integer");
                      if (_equals_1) {
                        StringBuffer _append_4 = sb.append("\t");
                        StringConcatenation _builder_9 = new StringConcatenation();
                        _builder_9.append("private int get_");
                        String _name_14 = ((MonitoredFunction)fd).getName();
                        _builder_9.append(_name_14);
                        _builder_9.append("_");
                        _builder_9.append(symbol);
                        _builder_9.append("(){");
                        _append_4.append(_builder_9);
                      } else {
                        boolean _equals_2 = ((MonitoredFunction)fd).getCodomain().getName().equals("Boolean");
                        if (_equals_2) {
                          StringBuffer _append_5 = sb.append("\t");
                          StringConcatenation _builder_10 = new StringConcatenation();
                          _builder_10.append("private boolean get_");
                          String _name_15 = ((MonitoredFunction)fd).getName();
                          _builder_10.append(_name_15);
                          _builder_10.append("_");
                          _builder_10.append(symbol);
                          _builder_10.append("(){");
                          _append_5.append(_builder_10);
                        } else {
                          boolean _equals_3 = ((MonitoredFunction)fd).getCodomain().getName().equals("String");
                          if (_equals_3) {
                            StringBuffer _append_6 = sb.append("\t");
                            StringConcatenation _builder_11 = new StringConcatenation();
                            _builder_11.append("private String get_");
                            String _name_16 = ((MonitoredFunction)fd).getName();
                            _builder_11.append(_name_16);
                            _builder_11.append("_");
                            _builder_11.append(symbol);
                            _builder_11.append("(){");
                            _append_6.append(_builder_11);
                          } else {
                            StringBuffer _append_7 = sb.append("\t");
                            StringConcatenation _builder_12 = new StringConcatenation();
                            _builder_12.append("private ");
                            _builder_12.append(asmName);
                            _builder_12.append(".");
                            String _name_17 = ((MonitoredFunction)fd).getCodomain().getName();
                            _builder_12.append(_name_17);
                            _builder_12.append(" get_");
                            String _name_18 = ((MonitoredFunction)fd).getName();
                            _builder_12.append(_name_18);
                            _builder_12.append("_");
                            _builder_12.append(symbol);
                            _builder_12.append("(){");
                            _append_7.append(_builder_12);
                          }
                        }
                      }
                      sb.append(System.lineSeparator());
                      StringBuffer _append_8 = sb.append("\t\t");
                      StringConcatenation _builder_13 = new StringConcatenation();
                      _builder_13.append("return this.esecuzione.");
                      String _name_19 = ((MonitoredFunction)fd).getName();
                      _builder_13.append(_name_19);
                      _builder_13.append(".get(");
                      _append_8.append(_builder_13);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_9 = sb.append("\t\t\t");
                      StringConcatenation _builder_14 = new StringConcatenation();
                      _builder_14.append("this.esecuzione.");
                      String _name_20 = ((MonitoredFunction)fd).getDomain().getName();
                      _builder_14.append(_name_20);
                      _builder_14.append("_elemsList.get(");
                      _builder_14.append(i);
                      _builder_14.append("));");
                      _append_9.append(_builder_14);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_10 = sb.append("\t");
                      StringConcatenation _builder_15 = new StringConcatenation();
                      _builder_15.append("}");
                      _append_10.append(_builder_15);
                    }
                    sb.append(System.lineSeparator());
                  }
                }
              } else {
                if ((dd instanceof AbstractTd)) {
                  EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
                  for (final Function sf : _function_1) {
                    if ((sf instanceof StaticFunction)) {
                      if ((((StaticFunction)sf).getCodomain().equals(dd) && (((StaticFunction)sf).getDomain() == null))) {
                        String symbol = ((StaticFunction)sf).getName();
                        sb.append(System.lineSeparator());
                        Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
                        if ((_codomain_1 instanceof ConcreteDomain)) {
                          StringBuffer _append = sb.append("\t");
                          StringConcatenation _builder_5 = new StringConcatenation();
                          _builder_5.append("private int get_");
                          String _name_11 = ((MonitoredFunction)fd).getName();
                          _builder_5.append(_name_11);
                          _builder_5.append("_");
                          _builder_5.append(symbol);
                          _builder_5.append("(){");
                          _append.append(_builder_5);
                          sb.append(System.lineSeparator());
                          StringBuffer _append_1 = sb.append("\t\t");
                          StringConcatenation _builder_6 = new StringConcatenation();
                          _builder_6.append("return this.esecuzione.");
                          String _name_12 = ((MonitoredFunction)fd).getName();
                          _builder_6.append(_name_12);
                          _builder_6.append(".get(");
                          _append_1.append(_builder_6);
                          sb.append(System.lineSeparator());
                          StringBuffer _append_2 = sb.append("\t\t\t");
                          StringConcatenation _builder_7 = new StringConcatenation();
                          _builder_7.append("this.esecuzione.");
                          String _name_13 = ((MonitoredFunction)fd).getDomain().getName();
                          _builder_7.append(_name_13);
                          _builder_7.append("_Class.get(");
                          _append_2.append(_builder_7);
                          sb.append(System.lineSeparator());
                          StringBuffer _append_3 = sb.append("\t\t\t");
                          StringConcatenation _builder_8 = new StringConcatenation();
                          _builder_8.append("this.esecuzione.");
                          String _name_14 = ((MonitoredFunction)fd).getDomain().getName();
                          _builder_8.append(_name_14);
                          _builder_8.append("_elemsList.indexOf(\"");
                          _builder_8.append(symbol);
                          _builder_8.append("\"))).value;");
                          _append_3.append(_builder_8);
                          sb.append(System.lineSeparator());
                          StringBuffer _append_4 = sb.append("\t");
                          StringConcatenation _builder_9 = new StringConcatenation();
                          _builder_9.append("}");
                          _append_4.append(_builder_9);
                        } else {
                          boolean _equals_1 = ((MonitoredFunction)fd).getCodomain().getName().equals("Integer");
                          if (_equals_1) {
                            StringBuffer _append_5 = sb.append("\t");
                            StringConcatenation _builder_10 = new StringConcatenation();
                            _builder_10.append("private int get_");
                            String _name_15 = ((MonitoredFunction)fd).getName();
                            _builder_10.append(_name_15);
                            _builder_10.append("_");
                            _builder_10.append(symbol);
                            _builder_10.append("(){");
                            _append_5.append(_builder_10);
                          } else {
                            boolean _equals_2 = ((MonitoredFunction)fd).getCodomain().getName().equals("Boolean");
                            if (_equals_2) {
                              StringBuffer _append_6 = sb.append("\t");
                              StringConcatenation _builder_11 = new StringConcatenation();
                              _builder_11.append("private boolean get_");
                              String _name_16 = ((MonitoredFunction)fd).getName();
                              _builder_11.append(_name_16);
                              _builder_11.append("_");
                              _builder_11.append(symbol);
                              _builder_11.append("(){");
                              _append_6.append(_builder_11);
                            } else {
                              boolean _equals_3 = ((MonitoredFunction)fd).getCodomain().getName().equals("String");
                              if (_equals_3) {
                                StringBuffer _append_7 = sb.append("\t");
                                StringConcatenation _builder_12 = new StringConcatenation();
                                _builder_12.append("private Srting get_");
                                String _name_17 = ((MonitoredFunction)fd).getName();
                                _builder_12.append(_name_17);
                                _builder_12.append("_");
                                _builder_12.append(symbol);
                                _builder_12.append("(){");
                                _append_7.append(_builder_12);
                              } else {
                                StringBuffer _append_8 = sb.append("\t");
                                StringConcatenation _builder_13 = new StringConcatenation();
                                _builder_13.append("private ");
                                String _name_18 = asm.getName();
                                _builder_13.append(_name_18);
                                _builder_13.append(".");
                                String _name_19 = ((MonitoredFunction)fd).getCodomain().getName();
                                _builder_13.append(_name_19);
                                _builder_13.append(" get_");
                                String _name_20 = ((MonitoredFunction)fd).getName();
                                _builder_13.append(_name_20);
                                _builder_13.append("_");
                                _builder_13.append(symbol);
                                _builder_13.append("(){");
                                _append_8.append(_builder_13);
                              }
                            }
                          }
                          sb.append(System.lineSeparator());
                          StringBuffer _append_9 = sb.append("\t\t");
                          StringConcatenation _builder_14 = new StringConcatenation();
                          _builder_14.append("return this.esecuzione.");
                          String _name_21 = ((MonitoredFunction)fd).getName();
                          _builder_14.append(_name_21);
                          _builder_14.append(".get(");
                          _append_9.append(_builder_14);
                          sb.append(System.lineSeparator());
                          StringBuffer _append_10 = sb.append("\t\t\t");
                          StringConcatenation _builder_15 = new StringConcatenation();
                          _builder_15.append("this.esecuzione.");
                          String _name_22 = ((MonitoredFunction)fd).getDomain().getName();
                          _builder_15.append(_name_22);
                          _builder_15.append("_Class.get(");
                          _append_10.append(_builder_15);
                          sb.append(System.lineSeparator());
                          StringBuffer _append_11 = sb.append("\t\t\t");
                          StringConcatenation _builder_16 = new StringConcatenation();
                          _builder_16.append("this.esecuzione.");
                          String _name_23 = ((MonitoredFunction)fd).getDomain().getName();
                          _builder_16.append(_name_23);
                          _builder_16.append("_elemsList.indexOf(\"");
                          _builder_16.append(symbol);
                          _builder_16.append("\")));");
                          _append_11.append(_builder_16);
                          sb.append(System.lineSeparator());
                          StringBuffer _append_12 = sb.append("\t");
                          StringConcatenation _builder_17 = new StringConcatenation();
                          _builder_17.append("}");
                          _append_12.append(_builder_17);
                        }
                        sb.append(System.lineSeparator());
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
}
