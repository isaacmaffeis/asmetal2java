/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules.validation;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * A sample validator interface for {@link asmeta.transitionrules.derivedtransitionrules.CaseRule}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface CaseRuleValidator {
	boolean validate();

	boolean validateTerm(Term value);
	boolean validateCaseTerm(EList<Term> value);
	boolean validateOtherwiseBranch(Rule value);
	boolean validateCaseBranches(EList<Rule> value);
}
