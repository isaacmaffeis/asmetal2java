package org.asmeta.asm2java.main;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;
import java.util.ArrayList;
import java.util.List;
import org.asmeta.asm2java.SeqRuleCollector;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.junit.Assert;

@SuppressWarnings("all")
public class JavaASMGenerator2 extends AsmToJavaGenerator {
  public void compileAndWrite(final Asm asm, final String writerPath, final TranslatorOptions userOptions) {
    Assert.assertTrue(writerPath.endsWith(".java"));
    this.compileAndWrite(asm, writerPath, "JAVA", userOptions);
  }

  private List<Rule> seqCalledRules;

  private String supp;

  @Override
  public String compileAsm(final Asm asm) {
    boolean _optimizeSeqMacroRule = this.options.getOptimizeSeqMacroRule();
    if (_optimizeSeqMacroRule) {
      ArrayList<Rule> _arrayList = new ArrayList<Rule>();
      this.seqCalledRules = _arrayList;
      EList<RuleDeclaration> _ruleDeclaration = asm.getBodySection().getRuleDeclaration();
      for (final RuleDeclaration r : _ruleDeclaration) {
        this.seqCalledRules.addAll(new SeqRuleCollector(false).visit(r));
      }
    }
    final String asmName = asm.getName();
    StringBuffer sb = new StringBuffer();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// ");
    _builder.append(asmName);
    _builder.append("_ASM.java automatically generated from ASM2CODE");
    _builder.newLineIfNotEmpty();
    _builder.append("//Classe per l\'esecuzione tramite una Abstract State Machine (ASM) dei file java generati.");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import java.util.Scanner;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("class ");
    _builder.append(asmName);
    _builder.append("_ASM {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private final ");
    _builder.append(asmName, "\t");
    _builder.append(" esecuzione;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("private int stato;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("     ");
    _builder.append("* Costruttore della classe {@code ");
    _builder.append(asmName, "     ");
    _builder.append("_ASM}. Crea un\'istanza privata della asm");
    _builder.newLineIfNotEmpty();
    _builder.append("     ");
    _builder.append("* {@link ");
    _builder.append(asmName, "     ");
    _builder.append("} e imposta lo stato iniziale della macchina a stati a 1.");
    _builder.newLineIfNotEmpty();
    _builder.append("     ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    _builder.append(asmName, "\t");
    _builder.append("_ASM(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("this.esecuzione = new ");
    _builder.append(asmName, "\t\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("this.stato = 0;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void step(");
    sb.append(_builder);
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("Boolean") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("\\nboolean ");
            String _name = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name);
            _builder_1.append("),");
            sb.append(_builder_1);
          } else {
            if ((((MonitoredFunction)fd).getCodomain().getName().equals("Integer") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
              StringConcatenation _builder_2 = new StringConcatenation();
              _builder_2.append("\\nint ");
              String _name_1 = ((MonitoredFunction)fd).getName();
              _builder_2.append(_name_1);
              _builder_2.append("),");
              sb.append(_builder_2);
            } else {
              Domain _codomain = ((MonitoredFunction)fd).getCodomain();
              if ((_codomain instanceof EnumTd)) {
                StringConcatenation _builder_3 = new StringConcatenation();
                _builder_3.append("\\n");
                String _name_2 = ((MonitoredFunction)fd).getCodomain().getName();
                _builder_3.append(_name_2);
                _builder_3.append(" ");
                String _name_3 = ((MonitoredFunction)fd).getName();
                _builder_3.append(_name_3);
                _builder_3.append("),");
                sb.append(_builder_3);
              } else {
                Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
                if ((_codomain_1 instanceof ConcreteDomain)) {
                  StringConcatenation _builder_4 = new StringConcatenation();
                  _builder_4.append("\\n");
                  String _name_4 = ((MonitoredFunction)fd).getCodomain().getName();
                  _builder_4.append(_name_4);
                  _builder_4.append(" ");
                  String _name_5 = ((MonitoredFunction)fd).getName();
                  _builder_4.append(_name_5);
                  _builder_4.append("),");
                  sb.append(_builder_4);
                } else {
                  Domain _codomain_2 = ((MonitoredFunction)fd).getCodomain();
                  if ((_codomain_2 instanceof AbstractTd)) {
                    StringConcatenation _builder_5 = new StringConcatenation();
                    _builder_5.append("\\n");
                    String _name_6 = ((MonitoredFunction)fd).getCodomain().getName();
                    _builder_5.append(_name_6);
                    _builder_5.append(" ");
                    String _name_7 = ((MonitoredFunction)fd).getName();
                    _builder_5.append(_name_7);
                    _builder_5.append("),");
                    sb.append(_builder_5);
                  }
                }
              }
            }
          }
        } else {
        }
      }
    }
    int _length = sb.length();
    int _minus = (_length - 1);
    sb.setLength(_minus);
    StringConcatenation _builder_6 = new StringConcatenation();
    _builder_6.append("){");
    _builder_6.newLine();
    _builder_6.append("\t\t\t\t\t");
    _builder_6.append("System.out.println(\"<State \"+ stato +\" (controlled)>\");");
    _builder_6.newLine();
    _builder_6.append("\t\t\t\t\t");
    _builder_6.newLine();
    _builder_6.append("\t\t\t\t\t");
    _builder_6.append("//Aggiornamento valori dell\'ASM e inserimento dati monitorati");
    _builder_6.newLine();
    _builder_6.append("\t\t\t\t\t");
    _builder_6.newLine();
    _builder_6.append("\t\t\t\t\t");
    _builder_6.append("printControlled(esecuzione);");
    _builder_6.newLine();
    _builder_6.append("\t\t\t\t\t");
    _builder_6.append("setMonitored(");
    sb.append(_builder_6);
    EList<Function> _function_1 = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd_1 : _function_1) {
      if ((fd_1 instanceof MonitoredFunction)) {
        Domain _domain_1 = ((MonitoredFunction)fd_1).getDomain();
        boolean _tripleEquals_1 = (_domain_1 == null);
        if (_tripleEquals_1) {
          StringConcatenation _builder_7 = new StringConcatenation();
          String _name_8 = ((MonitoredFunction)fd_1).getName();
          _builder_7.append(_name_8);
          _builder_7.append("),");
          sb.append(_builder_7);
        } else {
        }
      }
    }
    int _length_1 = sb.length();
    int _minus_1 = (_length_1 - 1);
    sb.setLength(_minus_1);
    StringConcatenation _builder_8 = new StringConcatenation();
    _builder_8.append(");");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.append("this.esecuzione.updateASM();");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.append("System.out.println(\"</State \"+ stato +\" (controlled)>\");");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.append("System.out.println(\"\\n<Stato attuale>\");");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.append("printControlled(esecuzione);");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t  \t    ");
    _builder_8.newLine();
    _builder_8.append("\t\t\t  \t    ");
    _builder_8.append("stato++;");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.append("}");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.append("// ASM Methods");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.append("static void printControlled(");
    _builder_8.append(asmName, "\t\t\t\t");
    _builder_8.append(" esecuzione) {");
    _builder_8.newLineIfNotEmpty();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    String _printControlled = this.printControlled(asm);
    _builder_8.append(_printControlled, "\t\t\t\t\t");
    _builder_8.newLineIfNotEmpty();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.append("}");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.append("static void setMonitored(");
    _builder_8.append(asmName, "\t\t\t\t");
    _builder_8.append(" esecuzione) {");
    _builder_8.newLineIfNotEmpty();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t\t");
    String _setMonitored = this.setMonitored(asm);
    _builder_8.append(_setMonitored, "\t\t\t\t\t");
    _builder_8.newLineIfNotEmpty();
    _builder_8.append("\t\t\t\t\t");
    _builder_8.newLine();
    _builder_8.append("\t\t\t\t");
    _builder_8.append("}");
    _builder_8.newLine();
    _builder_8.append("\t\t\t");
    _builder_8.append("}");
    _builder_8.newLine();
    _builder_8.append("\t\t\t");
    _builder_8.newLine();
    sb.append(_builder_8);
    return sb.toString();
  }

  public String printControlled(final Asm asm) {
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
        _builder.append("if(i!= esecuzione.");
        String _name_2 = ((AbstractTd)dd).getName();
        _builder.append(_name_2);
        _builder.append("_elemsList.size()-1)");
        _builder.newLineIfNotEmpty();
        _builder.append("System.out.print(esecuzione.");
        String _name_3 = ((AbstractTd)dd).getName();
        _builder.append(_name_3);
        _builder.append("_elemsList.get(i) +\", \");");
        _builder.newLineIfNotEmpty();
        _builder.append("else");
        _builder.newLine();
        _builder.append("System.out.print(esecuzione.");
        String _name_4 = ((AbstractTd)dd).getName();
        _builder.append(_name_4);
        _builder.append("_elemsList.get(i));\t");
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
            _builder_2.newLine();
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
            _builder_6.append("_elemsList.size(); i++)");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("\t\t");
            _builder_6.append("{");
            _builder_6.newLine();
            _builder_6.append("\t\t\t");
            _builder_6.append("System.out.println(\" ");
            String _name_16 = ((ControlledFunction)fd).getName();
            _builder_6.append(_name_16, "\t\t\t");
            _builder_6.append(" =>  (\" + esecuzione.");
            String _name_17 = ((ControlledFunction)fd).getDomain().getName();
            _builder_6.append(_name_17, "\t\t\t");
            _builder_6.append("_elemsList.get(i) +\") ");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("\t\t\t");
            _builder_6.append("= \" + esecuzione.");
            String _name_18 = ((ControlledFunction)fd).getName();
            _builder_6.append(_name_18, "\t\t\t");
            _builder_6.append(".oldValues.get(esecuzione.");
            String _name_19 = ((ControlledFunction)fd).getDomain().getName();
            _builder_6.append(_name_19, "\t\t\t");
            _builder_6.append("_elemsList.get(i)).value );");
            _builder_6.newLineIfNotEmpty();
            _builder_6.append("\t\t");
            _builder_6.append("}");
            _builder_6.newLine();
            sb.append(_builder_6);
          }
          if (((((ControlledFunction)fd).getDomain() instanceof EnumTd) && (((ControlledFunction)fd).getCodomain() instanceof EnumTd))) {
            StringConcatenation _builder_7 = new StringConcatenation();
            _builder_7.append("for(int i=0; i < esecuzione.");
            String _name_20 = ((ControlledFunction)fd).getDomain().getName();
            _builder_7.append(_name_20);
            _builder_7.append("_elemsList.size(); i++)");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("\t\t");
            _builder_7.append("{");
            _builder_7.newLine();
            _builder_7.append("\t\t\t");
            _builder_7.append("System.out.println(\"");
            String _name_21 = ((ControlledFunction)fd).getName();
            _builder_7.append(_name_21, "\t\t\t");
            _builder_7.append(" =>  (\" + esecuzione.");
            String _name_22 = ((ControlledFunction)fd).getDomain().getName();
            _builder_7.append(_name_22, "\t\t\t");
            _builder_7.append("_elemsList.get(i) +\") ");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("\t\t\t");
            _builder_7.append("= \"+ esecuzione.");
            String _name_23 = ((ControlledFunction)fd).getName();
            _builder_7.append(_name_23, "\t\t\t");
            _builder_7.append(".oldValues.get(esecuzione.");
            String _name_24 = ((ControlledFunction)fd).getDomain().getName();
            _builder_7.append(_name_24, "\t\t\t");
            _builder_7.append("_elemsList.get(i)));");
            _builder_7.newLineIfNotEmpty();
            _builder_7.append("\t\t");
            _builder_7.append("}");
            _builder_7.newLine();
            sb.append(_builder_7);
          }
        }
      }
    }
    return sb.toString();
  }

  public String setMonitored(final Asm asm) {
    StringBuffer sb = new StringBuffer();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("Boolean") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
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
            _builder.newLineIfNotEmpty();
            _builder.newLine();
            sb.append(_builder);
          }
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("Integer") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("this.esecuzione.");
            String _name_4 = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name_4);
            _builder_1.append(".set(");
            String _name_5 = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name_5);
            _builder_1.append(");");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("System.out.println(\"Set ");
            String _name_6 = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name_6);
            _builder_1.append(" = \" + ");
            String _name_7 = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name_7);
            _builder_1.append(");");
            _builder_1.newLineIfNotEmpty();
            _builder_1.newLine();
            sb.append(_builder_1);
          }
          Domain _codomain = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain instanceof EnumTd)) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("this.esecuzione.");
            String _name_8 = ((MonitoredFunction)fd).getName();
            _builder_2.append(_name_8);
            _builder_2.append(".set(");
            String _name_9 = ((MonitoredFunction)fd).getName();
            _builder_2.append(_name_9);
            _builder_2.append(");");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("System.out.println(\"Set ");
            String _name_10 = ((MonitoredFunction)fd).getName();
            _builder_2.append(_name_10);
            _builder_2.append(" = \" + ");
            String _name_11 = ((MonitoredFunction)fd).getName();
            _builder_2.append(_name_11);
            _builder_2.append(");");
            _builder_2.newLineIfNotEmpty();
            _builder_2.newLine();
            sb.append(_builder_2);
          }
          Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain_1 instanceof ConcreteDomain)) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("this.esecuzione.");
            String _name_12 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_12);
            _builder_3.append("_supporto.value = ");
            String _name_13 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_13);
            _builder_3.append(";");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("this.esecuzione.");
            String _name_14 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_14);
            _builder_3.append(".set(esecuzione.");
            String _name_15 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_15);
            _builder_3.append("_supporto);");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("System.out.println(\"Set ");
            String _name_16 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_16);
            _builder_3.append(" = \" + ");
            String _name_17 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_17);
            _builder_3.append(");");
            _builder_3.newLineIfNotEmpty();
            _builder_3.newLine();
            sb.append(_builder_3);
          }
          Domain _codomain_2 = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain_2 instanceof AbstractTd)) {
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.newLine();
            _builder_4.append("this.esecuzione.");
            String _name_18 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_18);
            _builder_4.append(".set(this.esecuzione.");
            Domain _codomain_3 = ((MonitoredFunction)fd).getCodomain();
            _builder_4.append(_codomain_3);
            _builder_4.append("_Class.get(");
            String _name_19 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_19);
            _builder_4.append("));");
            _builder_4.newLineIfNotEmpty();
            _builder_4.append("System.out.println(\"Set ");
            String _name_20 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_20);
            _builder_4.append(" = \" + ");
            String _name_21 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_21);
            _builder_4.append(");");
            _builder_4.newLineIfNotEmpty();
            _builder_4.newLine();
            sb.append(_builder_4);
          }
        } else {
        }
      }
    }
    return sb.toString();
  }
}
