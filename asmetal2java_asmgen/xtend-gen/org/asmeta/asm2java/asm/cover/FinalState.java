package org.asmeta.asm2java.asm.cover;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.Function;
import asmeta.structure.Asm;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class FinalState {
  public static StringBuffer ifFinalState(final Asm asm, final StringBuffer sb, final String[] finalStateConditions) {
    StringBuffer _xifexpression = null;
    if (((finalStateConditions != null) && (!((List<String>)Conversions.doWrapArray(finalStateConditions)).isEmpty()))) {
      StringBuffer _xblockexpression = null;
      {
        StringBuffer _append = sb.append("\t\t\t\t");
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("/* final state condition */");
        _append.append(_builder);
        sb.append(System.lineSeparator());
        StringBuffer _append_1 = sb.append("\t\t\t\t");
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("if(isFinalState()){");
        _builder_1.newLine();
        _builder_1.append("\t\t\t\t\t\t");
        _builder_1.append("System.out.println(\"\\n<Stato finale>\");");
        _builder_1.newLine();
        _builder_1.append("\t\t\t\t");
        _builder_1.append("}");
        _builder_1.newLine();
        _builder_1.append("\t\t\t\t");
        _builder_1.append("else");
        _append_1.append(_builder_1);
        _xblockexpression = sb.append(System.lineSeparator());
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }

  public static StringBuffer setIsFinalState(final Asm asm, final StringBuffer sb, final String[] finalStateConditions) {
    StringBuffer _xifexpression = null;
    if (((finalStateConditions != null) && (!((List<String>)Conversions.doWrapArray(finalStateConditions)).isEmpty()))) {
      StringBuffer _xblockexpression = null;
      {
        sb.append(System.lineSeparator());
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
        int numberOfConditionsExpected = 0;
        int numberOfConditionsActual = 0;
        StringBuffer ss = new StringBuffer();
        for (final String condition : finalStateConditions) {
          {
            int _numberOfConditionsExpected = numberOfConditionsExpected;
            numberOfConditionsExpected = (_numberOfConditionsExpected + 1);
            final String cond_name = condition.replaceAll("^\\s*(\\w+)\\s*.*$", "$1");
            String cond_value = condition.replaceAll("^\\s*\\w+\\s*(.*)$", "$1");
            boolean _equals = cond_name.toLowerCase().equals("stato");
            if (_equals) {
              ss.append(System.lineSeparator());
              StringBuffer _append_3 = ss.append("\t\t\t");
              StringConcatenation _builder_3 = new StringConcatenation();
              _builder_3.append("this.stato ");
              _builder_3.append(cond_value);
              _builder_3.append(" &&");
              _append_3.append(_builder_3);
              int _numberOfConditionsActual = numberOfConditionsActual;
              numberOfConditionsActual = (_numberOfConditionsActual + 1);
            } else {
              boolean _matches = cond_value.matches("\\d+");
              boolean _not = (!_matches);
              if (_not) {
                String cond_value_operators = cond_value.replaceAll("^([><=!]+).*", "$1");
                StringConcatenation _builder_4 = new StringConcatenation();
                String _name = asm.getName();
                _builder_4.append(_name);
                _builder_4.append(".");
                String _replaceAll = cond_value.replaceAll("^([><=!]+)(.*)", "$2");
                _builder_4.append(_replaceAll);
                cond_value = _builder_4.toString();
                cond_value = cond_value_operators.concat(cond_value);
              }
              EList<Function> _function = asm.getHeaderSection().getSignature().getFunction();
              for (final Function fd : _function) {
                if (((fd instanceof ControlledFunction) && fd.getName().equals(cond_name))) {
                  ss.append(System.lineSeparator());
                  StringBuffer _append_4 = ss.append("\t\t\t");
                  StringConcatenation _builder_5 = new StringConcatenation();
                  _builder_5.append("this.get_");
                  String _name_1 = fd.getName();
                  _builder_5.append(_name_1);
                  _builder_5.append("() ");
                  _builder_5.append(cond_value);
                  _builder_5.append(" &&");
                  _append_4.append(_builder_5);
                  int _numberOfConditionsActual_1 = numberOfConditionsActual;
                  numberOfConditionsActual = (_numberOfConditionsActual_1 + 1);
                }
              }
            }
          }
        }
        if ((numberOfConditionsActual == 0)) {
          sb.append(" true;");
          sb.append(System.lineSeparator());
          StringBuffer _append_3 = sb.append("\t");
          StringConcatenation _builder_3 = new StringConcatenation();
          _builder_3.append("// ERROR - NO Conditions found");
          _append_3.append(_builder_3);
        } else {
          if ((numberOfConditionsActual == numberOfConditionsExpected)) {
            sb.append(ss.toString());
            int _length = sb.length();
            int _minus = (_length - 3);
            sb.setLength(_minus);
            StringConcatenation _builder_4 = new StringConcatenation();
            _builder_4.append(";");
            sb.append(_builder_4);
          } else {
            sb.append(ss.toString());
            int _length_1 = sb.length();
            int _minus_1 = (_length_1 - 3);
            sb.setLength(_minus_1);
            StringConcatenation _builder_5 = new StringConcatenation();
            _builder_5.append(";");
            sb.append(_builder_5);
            sb.append(System.lineSeparator());
            StringBuffer _append_4 = sb.append("\t");
            StringConcatenation _builder_6 = new StringConcatenation();
            _builder_6.append("// ERROR - ");
            _builder_6.append((numberOfConditionsExpected - numberOfConditionsActual));
            _builder_6.append(" Conditions not generated");
            _append_4.append(_builder_6);
          }
        }
        sb.append(System.lineSeparator());
        StringBuffer _append_5 = sb.append("\t");
        StringConcatenation _builder_7 = new StringConcatenation();
        _builder_7.append("}");
        _append_5.append(_builder_7);
        _xblockexpression = sb.append(System.lineSeparator());
      }
      _xifexpression = _xblockexpression;
    }
    return _xifexpression;
  }
}
