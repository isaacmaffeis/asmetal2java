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
import org.asmeta.asm2java.ToString;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.junit.Assert;

@SuppressWarnings("all")
public class JavaASMGenerator2 extends AsmToJavaGenerator {
  public void compileAndWrite(final Asm asm, final String writerPath, final TranslatorOptions userOptions) {
    Assert.assertTrue(writerPath.endsWith(".java"));
    this.compileAndWrite(asm, writerPath, "JAVA", userOptions);
  }

  private List<Rule> seqCalledRules;

  private String supp;

  private String[] finalStateConditions;

  public String[] setFinalStateConditions(final String[] finalStateConditions) {
    return this.finalStateConditions = finalStateConditions;
  }

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
    _builder.newLine();
    _builder.append("// ");
    _builder.append(asmName);
    _builder.append("_ASM.java automatically generated from ASM2CODE");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import java.util.Scanner;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("/**");
    _builder.newLine();
    _builder.append("* This class allows you to simulate the behavior of an Abstract State Machine (ASM)");
    _builder.newLine();
    _builder.append("* without asking the user for input values ​​of the monitored functions.");
    _builder.newLine();
    _builder.append("*");
    _builder.newLine();
    _builder.append("* <p>");
    _builder.newLine();
    _builder.append("* It has been optimized to be used with evosuite in order to automatically generate test scenarios.");
    _builder.newLine();
    _builder.append("* </p>");
    _builder.newLine();
    _builder.append("*/");
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
    _builder.append("* Constructor of the {@code ");
    _builder.append(asmName, "     ");
    _builder.append("_ASM} class. Creates a private instance of the asm");
    _builder.newLineIfNotEmpty();
    _builder.append("     ");
    _builder.append("* {@link ");
    _builder.append(asmName, "     ");
    _builder.append("} and sets the initial state of the state machine to 1.");
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
    _builder.append("/** The step function is the only public method of the ASM,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("* it receives as parameters the values ​​of the monitored functions to be updated ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("* and allows to perform a step of the asm by incrementing the state.");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void step(");
    sb.append(_builder);
    this.setMonitoredArgs(asm, sb);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("){");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("System.out.println(\"<State \"+ stato +\" (controlled)>\");");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("printControlled();");
    _builder_1.newLine();
    _builder_1.append("\t\t\t\t");
    _builder_1.append("setMonitored(");
    sb.append(_builder_1);
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          sb.append(System.lineSeparator());
          StringBuffer _append = sb.append("\t\t\t\t\t");
          StringConcatenation _builder_2 = new StringConcatenation();
          String _name = ((MonitoredFunction)fd).getName();
          _builder_2.append(_name);
          _builder_2.append(",");
          _append.append(_builder_2);
        } else {
        }
      }
    }
    int _length = sb.length();
    int _minus = (_length - 1);
    sb.setLength(_minus);
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append(");");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("this.esecuzione.updateASM();");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("System.out.println(\"</State \"+ stato +\" (controlled)>\");");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("System.out.println(\"\\n<Stato attuale>\");");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("printControlled();");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("/* monitored */");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("coverMonitored();");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("/* controlled */");
    _builder_3.newLine();
    _builder_3.append("\t\t\t\t");
    _builder_3.append("coverControlled();");
    sb.append(_builder_3);
    sb.append(System.lineSeparator());
    if (((this.finalStateConditions != null) && (!((List<String>)Conversions.doWrapArray(this.finalStateConditions)).isEmpty()))) {
      StringBuffer _append_1 = sb.append("\t\t\t\t");
      StringConcatenation _builder_4 = new StringConcatenation();
      _builder_4.append("/* final state condition */");
      _append_1.append(_builder_4);
      sb.append(System.lineSeparator());
      StringBuffer _append_2 = sb.append("\t\t\t\t");
      StringConcatenation _builder_5 = new StringConcatenation();
      _builder_5.append("if(isFinalState()){");
      _builder_5.newLine();
      _builder_5.append("\t\t\t\t\t\t");
      _builder_5.append("System.out.println(\"\\n<Stato finale>\");");
      _builder_5.newLine();
      _builder_5.append("\t\t\t\t");
      _builder_5.append("}");
      _builder_5.newLine();
      _builder_5.append("\t\t\t\t");
      _builder_5.append("else");
      _append_2.append(_builder_5);
      sb.append(System.lineSeparator());
    }
    StringBuffer _append_3 = sb.append("\t\t\t\t\t\t");
    StringConcatenation _builder_6 = new StringConcatenation();
    _builder_6.append("stato++;");
    _builder_6.newLine();
    _builder_6.append("\t\t\t\t");
    _builder_6.append("}");
    _append_3.append(_builder_6);
    this.setIsFinalState(asm, sb);
    sb.append(System.lineSeparator());
    StringBuffer _append_4 = sb.append("\t");
    StringConcatenation _builder_7 = new StringConcatenation();
    _builder_7.append("// Monitored getters");
    _append_4.append(_builder_7);
    this.monitoredGetter(asm, sb);
    sb.append(System.lineSeparator());
    StringBuffer _append_5 = sb.append("\t\t");
    StringConcatenation _builder_8 = new StringConcatenation();
    _builder_8.append("// Controlled getters");
    _append_5.append(_builder_8);
    sb.append(System.lineSeparator());
    this.controlledGetter(asm, sb);
    sb.append(System.lineSeparator());
    StringBuffer _append_6 = sb.append("\t");
    StringConcatenation _builder_9 = new StringConcatenation();
    _builder_9.append("// Cover functions");
    _append_6.append(_builder_9);
    sb.append(System.lineSeparator());
    sb.append(System.lineSeparator());
    StringBuffer _append_7 = sb.append("\t");
    StringConcatenation _builder_10 = new StringConcatenation();
    _builder_10.append("private void coverMonitored(){");
    _append_7.append(_builder_10);
    this.coverFunctions(asm, sb, true);
    sb.append(System.lineSeparator());
    StringBuffer _append_8 = sb.append("\t");
    StringConcatenation _builder_11 = new StringConcatenation();
    _builder_11.append("}");
    _builder_11.newLine();
    _builder_11.newLine();
    _builder_11.append("\t");
    _builder_11.append("private void coverControlled(){");
    _append_8.append(_builder_11);
    this.coverFunctions(asm, sb, false);
    sb.append(System.lineSeparator());
    StringBuffer _append_9 = sb.append("\t");
    StringConcatenation _builder_12 = new StringConcatenation();
    _builder_12.append("}");
    _append_9.append(_builder_12);
    sb.append(System.lineSeparator());
    sb.append(System.lineSeparator());
    this.coverBranches(asm, sb);
    StringConcatenation _builder_13 = new StringConcatenation();
    _builder_13.newLine();
    _builder_13.append("\t");
    _builder_13.append("// ASM Methods");
    _builder_13.newLine();
    _builder_13.append("\t");
    _builder_13.newLine();
    _builder_13.append("\t");
    _builder_13.append("private void printControlled() {");
    _builder_13.newLine();
    _builder_13.append("\t");
    _builder_13.newLine();
    _builder_13.append("\t\t");
    String _printControlled = this.printControlled(asm);
    _builder_13.append(_printControlled, "\t\t");
    _builder_13.newLineIfNotEmpty();
    _builder_13.append("\t");
    _builder_13.newLine();
    _builder_13.append("\t");
    _builder_13.append("}");
    _builder_13.newLine();
    _builder_13.append("\t");
    _builder_13.newLine();
    _builder_13.append("\t");
    _builder_13.append("private void setMonitored(");
    sb.append(_builder_13);
    this.setMonitoredArgs(asm, sb);
    StringConcatenation _builder_14 = new StringConcatenation();
    _builder_14.append(") {");
    _builder_14.newLine();
    _builder_14.append("\t\t\t\t\t");
    _builder_14.newLine();
    _builder_14.append("\t\t\t\t\t");
    String _setMonitored = this.setMonitored(asm);
    _builder_14.append(_setMonitored, "\t\t\t\t\t");
    _builder_14.newLineIfNotEmpty();
    _builder_14.append("\t\t\t\t");
    _builder_14.append("}");
    _builder_14.newLine();
    _builder_14.append("\t\t\t");
    _builder_14.append("}");
    sb.append(_builder_14);
    return sb.toString();
  }

  public void coverFunctions(final Asm asm, final StringBuffer sb, final boolean monitored) {
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

  public void coverBranches(final Asm asm, final StringBuffer sb) {
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if (((fd instanceof MonitoredFunction) || (fd instanceof ControlledFunction))) {
        Domain _domain = fd.getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          StringBuffer _append = sb.append("\t");
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("private void cover_");
          String _name = fd.getName();
          _builder.append(_name);
          _builder.append("(){");
          _append.append(_builder);
          Domain _codomain = fd.getCodomain();
          if ((_codomain instanceof EnumTd)) {
            sb.append(System.lineSeparator());
            StringBuffer _append_1 = sb.append("\t\t");
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("switch(this.get_");
            String _name_1 = fd.getName();
            _builder_1.append(_name_1);
            _builder_1.append("()){");
            _append_1.append(_builder_1);
            EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
            for (final Domain dd : _domain_1) {
              boolean _equals = dd.equals(fd.getCodomain());
              if (_equals) {
                if ((dd instanceof EnumTd)) {
                  for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                    {
                      String symbol = new ToString(asm).visit(((EnumTd)dd).getElement().get(i));
                      sb.append(System.lineSeparator());
                      StringBuffer _append_2 = sb.append("\t\t\t");
                      StringConcatenation _builder_2 = new StringConcatenation();
                      _builder_2.append("case ");
                      _builder_2.append(symbol);
                      _builder_2.append(" :");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t\t\t\t\t\t\t");
                      _builder_2.append("System.out.println(\"Branch ");
                      String _name_2 = fd.getCodomain().getName();
                      _builder_2.append(_name_2, "\t\t\t\t\t\t\t");
                      _builder_2.append(" ");
                      _builder_2.append(symbol, "\t\t\t\t\t\t\t");
                      _builder_2.append(" covered\");");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t\t\t\t\t\t\t");
                      _builder_2.append("// Branch ");
                      String _name_3 = fd.getCodomain().getName();
                      _builder_2.append(_name_3, "\t\t\t\t\t\t\t");
                      _builder_2.append(" ");
                      _builder_2.append(symbol, "\t\t\t\t\t\t\t");
                      _builder_2.append(" covered");
                      _builder_2.newLineIfNotEmpty();
                      _builder_2.append("\t\t\t\t\t\t\t");
                      _builder_2.append("break;");
                      _append_2.append(_builder_2);
                    }
                  }
                }
              }
            }
            sb.append(System.lineSeparator());
            sb.append("\t\t\t");
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.append("}");
            sb.append(_builder_2);
          } else {
            sb.append(System.lineSeparator());
            StringBuffer _append_2 = sb.append("\t\t");
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("this.get_");
            String _name_2 = fd.getName();
            _builder_3.append(_name_2);
            _builder_3.append("();");
            _append_2.append(_builder_3);
            sb.append(System.lineSeparator());
            StringBuffer _append_3 = sb.append("\t\t");
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append("//1 Branch covered");
            _append_3.append(_builder_4);
          }
          sb.append(System.lineSeparator());
          StringBuffer _append_4 = sb.append("\t");
          StringConcatenation _builder_5 = new StringConcatenation();
          _builder_5.append("}");
          _append_4.append(_builder_5);
          sb.append(System.lineSeparator());
          sb.append(System.lineSeparator());
        } else {
          EList<Domain> _domain_2 = asm.getHeaderSection().getSignature().getDomain();
          for (final Domain dd_1 : _domain_2) {
            boolean _equals_1 = dd_1.equals(fd.getDomain());
            if (_equals_1) {
              if ((dd_1 instanceof EnumTd)) {
                StringBuffer _append_5 = sb.append("\t");
                StringConcatenation _builder_6 = new StringConcatenation();
                _builder_6.append("private void cover_");
                String _name_3 = fd.getName();
                _builder_6.append(_name_3);
                _builder_6.append("(){");
                _append_5.append(_builder_6);
                for (int i = 0; (i < ((EnumTd)dd_1).getElement().size()); i++) {
                  {
                    String symbol = new ToString(asm).visit(((EnumTd)dd_1).getElement().get(i));
                    sb.append(System.lineSeparator());
                    StringBuffer _append_6 = sb.append("\t\t");
                    StringConcatenation _builder_7 = new StringConcatenation();
                    _builder_7.append("this.get_");
                    String _name_4 = fd.getName();
                    _builder_7.append(_name_4);
                    _builder_7.append("_");
                    _builder_7.append(symbol);
                    _builder_7.append("();");
                    _append_6.append(_builder_7);
                  }
                }
                sb.append(System.lineSeparator());
                StringBuffer _append_6 = sb.append("\t\t");
                StringConcatenation _builder_7 = new StringConcatenation();
                _builder_7.append("// ");
                int _size = ((EnumTd)dd_1).getElement().size();
                _builder_7.append(_size);
                _builder_7.append(" Branch covered");
                _append_6.append(_builder_7);
                sb.append(System.lineSeparator());
                StringBuffer _append_7 = sb.append("\t");
                StringConcatenation _builder_8 = new StringConcatenation();
                _builder_8.append("}");
                _append_7.append(_builder_8);
                sb.append(System.lineSeparator());
                sb.append(System.lineSeparator());
              }
            }
          }
        }
      }
    }
  }

  public void monitoredGetter(final Asm asm, final StringBuffer sb) {
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
            _builder.append("\t\t");
            _builder.append("private boolean get_");
            String _name = ((MonitoredFunction)fd).getName();
            _builder.append(_name, "\t\t");
            _builder.append("(){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("return this.esecuzione.");
            String _name_1 = ((MonitoredFunction)fd).getName();
            _builder.append(_name_1, "\t\t\t");
            _builder.append(".get();");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("}");
            _builder.newLine();
            sb.append(_builder);
          }
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("Integer") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.newLine();
            _builder_1.append("\t\t");
            _builder_1.append("private int get_");
            String _name_2 = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name_2, "\t\t");
            _builder_1.append("(){");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t\t\t");
            _builder_1.append("return this.esecuzione.");
            String _name_3 = ((MonitoredFunction)fd).getName();
            _builder_1.append(_name_3, "\t\t\t");
            _builder_1.append(".get();");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t\t");
            _builder_1.append("}");
            _builder_1.newLine();
            sb.append(_builder_1);
          }
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("String") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.newLine();
            _builder_2.append("\t\t");
            _builder_2.append("private String get_");
            String _name_4 = ((MonitoredFunction)fd).getName();
            _builder_2.append(_name_4, "\t\t");
            _builder_2.append("(){");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t\t");
            _builder_2.append("return this.esecuzione.");
            String _name_5 = ((MonitoredFunction)fd).getName();
            _builder_2.append(_name_5, "\t\t\t");
            _builder_2.append(".get();");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t");
            _builder_2.append("}");
            _builder_2.newLine();
            sb.append(_builder_2);
          }
          if ((((((MonitoredFunction)fd).getCodomain() instanceof EnumTd) || 
            (((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)) || 
            (((MonitoredFunction)fd).getCodomain() instanceof AbstractTd))) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.newLine();
            _builder_3.append("\t\t");
            _builder_3.append("private ");
            _builder_3.append(asmName, "\t\t");
            _builder_3.append(".");
            String _name_6 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder_3.append(_name_6, "\t\t");
            _builder_3.append(" get_");
            String _name_7 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_7, "\t\t");
            _builder_3.append("(){");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("\t\t\t");
            _builder_3.append("return this.esecuzione.");
            String _name_8 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_8, "\t\t\t");
            _builder_3.append(".get();");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("\t\t");
            _builder_3.append("}");
            _builder_3.newLine();
            sb.append(_builder_3);
          }
        }
      }
    }
  }

  public void controlledGetter(final Asm asm, final StringBuffer sb) {
    String asmName = asm.getName();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof ControlledFunction)) {
        Domain _domain = ((ControlledFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          if ((((ControlledFunction)fd).getCodomain().getName().equals("Boolean") && (!(((ControlledFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("public boolean get_");
            String _name = ((ControlledFunction)fd).getName();
            _builder.append(_name, "\t\t");
            _builder.append("(){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t\t");
            _builder.append("return this.esecuzione.");
            String _name_1 = ((ControlledFunction)fd).getName();
            _builder.append(_name_1, "\t\t\t");
            _builder.append(".get();");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("}");
            _builder.newLine();
            sb.append(_builder);
          }
          if ((((ControlledFunction)fd).getCodomain().getName().equals("Integer") && (!(((ControlledFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.newLine();
            _builder_1.append("\t\t");
            _builder_1.append("public int get_");
            String _name_2 = ((ControlledFunction)fd).getName();
            _builder_1.append(_name_2, "\t\t");
            _builder_1.append("(){");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t\t\t");
            _builder_1.append("return this.esecuzione.");
            String _name_3 = ((ControlledFunction)fd).getName();
            _builder_1.append(_name_3, "\t\t\t");
            _builder_1.append(".get();");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t\t");
            _builder_1.append("}");
            _builder_1.newLine();
            sb.append(_builder_1);
          }
          if ((((ControlledFunction)fd).getCodomain().getName().equals("String") && (!(((ControlledFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            StringConcatenation _builder_2 = new StringConcatenation();
            _builder_2.newLine();
            _builder_2.append("\t\t");
            _builder_2.append("public String get_");
            String _name_4 = ((ControlledFunction)fd).getName();
            _builder_2.append(_name_4, "\t\t");
            _builder_2.append("(){");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t\t");
            _builder_2.append("return this.esecuzione.");
            String _name_5 = ((ControlledFunction)fd).getName();
            _builder_2.append(_name_5, "\t\t\t");
            _builder_2.append(".get();");
            _builder_2.newLineIfNotEmpty();
            _builder_2.append("\t\t");
            _builder_2.append("}");
            _builder_2.newLine();
            sb.append(_builder_2);
          }
          if ((((((ControlledFunction)fd).getCodomain() instanceof EnumTd) || 
            (((ControlledFunction)fd).getCodomain() instanceof ConcreteDomain)) || 
            (((ControlledFunction)fd).getCodomain() instanceof AbstractTd))) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.newLine();
            _builder_3.append("\t\t");
            _builder_3.append("public ");
            _builder_3.append(asmName, "\t\t");
            _builder_3.append(".");
            String _name_6 = ((ControlledFunction)fd).getCodomain().getName();
            _builder_3.append(_name_6, "\t\t");
            _builder_3.append(" get_");
            String _name_7 = ((ControlledFunction)fd).getName();
            _builder_3.append(_name_7, "\t\t");
            _builder_3.append("(){");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("\t\t\t");
            _builder_3.append("return this.esecuzione.");
            String _name_8 = ((ControlledFunction)fd).getName();
            _builder_3.append(_name_8, "\t\t\t");
            _builder_3.append(".get();");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("\t\t");
            _builder_3.append("}");
            _builder_3.newLine();
            sb.append(_builder_3);
          }
        } else {
          EList<Domain> _domain_1 = asm.getHeaderSection().getSignature().getDomain();
          for (final Domain dd : _domain_1) {
            boolean _equals = dd.equals(((ControlledFunction)fd).getDomain());
            if (_equals) {
              if ((dd instanceof EnumTd)) {
                for (int i = 0; (i < ((EnumTd)dd).getElement().size()); i++) {
                  {
                    String symbol = new ToString(asm).visit(((EnumTd)dd).getElement().get(i));
                    sb.append(System.lineSeparator());
                    Domain _codomain = ((ControlledFunction)fd).getCodomain();
                    if ((_codomain instanceof ConcreteDomain)) {
                      StringBuffer _append = sb.append("\t");
                      StringConcatenation _builder_4 = new StringConcatenation();
                      _builder_4.append("public int get_");
                      String _name_9 = ((ControlledFunction)fd).getName();
                      _builder_4.append(_name_9);
                      _builder_4.append("_");
                      _builder_4.append(symbol);
                      _builder_4.append("(){");
                      _append.append(_builder_4);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_1 = sb.append("\t\t");
                      StringConcatenation _builder_5 = new StringConcatenation();
                      _builder_5.append("return this.esecuzione.");
                      String _name_10 = ((ControlledFunction)fd).getName();
                      _builder_5.append(_name_10);
                      _builder_5.append(".oldValues.get(");
                      _append_1.append(_builder_5);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_2 = sb.append("\t\t\t");
                      StringConcatenation _builder_6 = new StringConcatenation();
                      _builder_6.append("this.esecuzione.");
                      String _name_11 = ((ControlledFunction)fd).getDomain().getName();
                      _builder_6.append(_name_11);
                      _builder_6.append("_elemsList.get(");
                      _builder_6.append(i);
                      _builder_6.append(")).value;");
                      _append_2.append(_builder_6);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_3 = sb.append("\t");
                      StringConcatenation _builder_7 = new StringConcatenation();
                      _builder_7.append("}");
                      _append_3.append(_builder_7);
                    } else {
                      StringBuffer _append_4 = sb.append("\t");
                      StringConcatenation _builder_8 = new StringConcatenation();
                      _builder_8.append("public ");
                      _builder_8.append(asmName);
                      _builder_8.append(".");
                      String _name_12 = ((ControlledFunction)fd).getCodomain().getName();
                      _builder_8.append(_name_12);
                      _builder_8.append(" get_");
                      String _name_13 = ((ControlledFunction)fd).getName();
                      _builder_8.append(_name_13);
                      _builder_8.append("_");
                      _builder_8.append(symbol);
                      _builder_8.append("(){");
                      _append_4.append(_builder_8);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_5 = sb.append("\t\t");
                      StringConcatenation _builder_9 = new StringConcatenation();
                      _builder_9.append("return this.esecuzione.");
                      String _name_14 = ((ControlledFunction)fd).getName();
                      _builder_9.append(_name_14);
                      _builder_9.append(".oldValues.get(");
                      _append_5.append(_builder_9);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_6 = sb.append("\t\t\t");
                      StringConcatenation _builder_10 = new StringConcatenation();
                      _builder_10.append("this.esecuzione.");
                      String _name_15 = ((ControlledFunction)fd).getDomain().getName();
                      _builder_10.append(_name_15);
                      _builder_10.append("_elemsList.get(");
                      _builder_10.append(i);
                      _builder_10.append("));");
                      _append_6.append(_builder_10);
                      sb.append(System.lineSeparator());
                      StringBuffer _append_7 = sb.append("\t");
                      StringConcatenation _builder_11 = new StringConcatenation();
                      _builder_11.append("}");
                      _append_7.append(_builder_11);
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
            _builder_6.append("_elemsList.get(i) + ");
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
            _builder_7.append("_elemsList.get(i) + ");
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

  public void setMonitoredArgs(final Asm asm, final StringBuffer sb) {
    final String asmName = asm.getName();
    EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
    for (final Function fd : _function) {
      if ((fd instanceof MonitoredFunction)) {
        Domain _domain = ((MonitoredFunction)fd).getDomain();
        boolean _tripleEquals = (_domain == null);
        if (_tripleEquals) {
          if ((((MonitoredFunction)fd).getCodomain().getName().equals("Boolean") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
            sb.append(System.lineSeparator()).append("\t\t");
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("boolean ");
            String _name = ((MonitoredFunction)fd).getName();
            _builder.append(_name);
            _builder.append(",");
            sb.append(_builder);
          } else {
            if ((((MonitoredFunction)fd).getCodomain().getName().equals("Integer") && (!(((MonitoredFunction)fd).getCodomain() instanceof ConcreteDomain)))) {
              sb.append(System.lineSeparator()).append("\t\t");
              StringConcatenation _builder_1 = new StringConcatenation();
              _builder_1.append("int ");
              String _name_1 = ((MonitoredFunction)fd).getName();
              _builder_1.append(_name_1);
              _builder_1.append(",");
              sb.append(_builder_1);
            } else {
              Domain _codomain = ((MonitoredFunction)fd).getCodomain();
              if ((_codomain instanceof EnumTd)) {
                sb.append(System.lineSeparator()).append("\t\t");
                StringConcatenation _builder_2 = new StringConcatenation();
                _builder_2.append(asmName);
                _builder_2.append(".");
                String _name_2 = ((MonitoredFunction)fd).getCodomain().getName();
                _builder_2.append(_name_2);
                _builder_2.append(" ");
                String _name_3 = ((MonitoredFunction)fd).getName();
                _builder_2.append(_name_3);
                _builder_2.append(",");
                sb.append(_builder_2);
              } else {
                Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
                if ((_codomain_1 instanceof ConcreteDomain)) {
                  sb.append(System.lineSeparator()).append("\t\t");
                  StringConcatenation _builder_3 = new StringConcatenation();
                  _builder_3.append("int ");
                  String _name_4 = ((MonitoredFunction)fd).getName();
                  _builder_3.append(_name_4);
                  _builder_3.append(",");
                  sb.append(_builder_3);
                } else {
                  Domain _codomain_2 = ((MonitoredFunction)fd).getCodomain();
                  if ((_codomain_2 instanceof AbstractTd)) {
                    sb.append(System.lineSeparator()).append("\t\t");
                    StringConcatenation _builder_4 = new StringConcatenation();
                    _builder_4.append("int ");
                    String _name_5 = ((MonitoredFunction)fd).getName();
                    _builder_4.append(_name_5);
                    _builder_4.append(",");
                    sb.append(_builder_4);
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
            sb.append(_builder);
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
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
            sb.append(_builder_1);
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
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
            sb.append(_builder_2);
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
          }
          Domain _codomain_1 = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain_1 instanceof ConcreteDomain)) {
            StringConcatenation _builder_3 = new StringConcatenation();
            _builder_3.append("this.esecuzione.");
            String _name_12 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_12);
            _builder_3.append(".set(");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("\t");
            String _name_13 = asm.getName();
            _builder_3.append(_name_13, "\t");
            _builder_3.append(".");
            String _name_14 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder_3.append(_name_14, "\t");
            _builder_3.append(".valueOf(");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("\t");
            _builder_3.append("this.esecuzione.");
            String _name_15 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder_3.append(_name_15, "\t");
            _builder_3.append("_elems.get(");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("\t");
            String _name_16 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_16, "\t");
            _builder_3.append(" - this.esecuzione.");
            String _name_17 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder_3.append(_name_17, "\t");
            _builder_3.append("_elems.get(0))));");
            _builder_3.newLineIfNotEmpty();
            _builder_3.append("System.out.println(\"Set ");
            String _name_18 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_18);
            _builder_3.append(" = \" + ");
            String _name_19 = ((MonitoredFunction)fd).getName();
            _builder_3.append(_name_19);
            _builder_3.append(");");
            sb.append(_builder_3);
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
          }
          Domain _codomain_2 = ((MonitoredFunction)fd).getCodomain();
          if ((_codomain_2 instanceof AbstractTd)) {
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append("this.esecuzione.");
            String _name_20 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_20);
            _builder_4.append(".set(this.esecuzione.");
            String _name_21 = ((MonitoredFunction)fd).getCodomain().getName();
            _builder_4.append(_name_21);
            _builder_4.append("_Class.get(");
            String _name_22 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_22);
            _builder_4.append("));");
            _builder_4.newLineIfNotEmpty();
            _builder_4.append("System.out.println(\"Set ");
            String _name_23 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_23);
            _builder_4.append(" = \" + ");
            String _name_24 = ((MonitoredFunction)fd).getName();
            _builder_4.append(_name_24);
            _builder_4.append(");");
            sb.append(_builder_4);
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
          }
        } else {
        }
      }
    }
    return sb.toString();
  }

  public StringBuffer setIsFinalState(final Asm asm, final StringBuffer sb) {
    StringBuffer _xifexpression = null;
    if (((this.finalStateConditions != null) && (!((List<String>)Conversions.doWrapArray(this.finalStateConditions)).isEmpty()))) {
      StringBuffer _xblockexpression = null;
      {
        sb.append(System.lineSeparator());
        StringBuffer _append = sb.append("\t");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("// final state condition");
        _append.append(_builder);
        sb.append(System.lineSeparator());
        StringBuffer _append_1 = sb.append("\t");
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("public boolean isFinalState(){");
        _append_1.append(_builder_1);
        sb.append(System.lineSeparator());
        StringBuffer _append_2 = sb.append("\t\t");
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("return");
        _append_2.append(_builder_2);
        for (final String condition : this.finalStateConditions) {
          {
            final String cond_name = condition.replaceAll("^\\s*(\\w+)\\s*.*$", "$1");
            final String cond_value = condition.replaceAll("^\\s*\\w+\\s*(.*)$", "$1");
            boolean _equals = cond_name.toLowerCase().equals("stato");
            if (_equals) {
              sb.append(System.lineSeparator());
              StringBuffer _append_3 = sb.append("\t\t\t");
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("this.stato ");
              _builder_3.append(cond_value);
              _builder_3.append(" &&");
              _append_3.append(_builder_3);
            } else {
              EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
              for (final Function fd : _function) {
                if (((fd instanceof ControlledFunction) && fd.getName().equals(cond_name))) {
                  sb.append(System.lineSeparator());
                  StringBuffer _append_4 = sb.append("\t\t\t");
                  StringConcatenation _builder_4 = new StringConcatenation();
                  _builder_4.append("this.get_");
                  String _name = fd.getName();
                  _builder_4.append(_name);
                  _builder_4.append("() ");
                  _builder_4.append(cond_value);
                  _builder_4.append(" &&");
                  _append_4.append(_builder_4);
                }
              }
            }
          }
        }
        int _length = sb.length();
        int _minus = (_length - 3);
        sb.setLength(_minus);
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append(";");
        sb.append(_builder_3);
        sb.append(System.lineSeparator());
        StringBuffer _append_3 = sb.append("\t");
        StringConcatenation _builder_4 = new StringConcatenation();
        _builder_4.append("}");
        _xblockexpression = _append_3.append(_builder_4);
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
}
