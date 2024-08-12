/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.validation;

import org.eclipse.emf.common.util.EList;

import asmeta.structure.FunctionInitialization;

/**
 * A sample validator interface for {@link asmeta.definitions.DynamicFunction}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DynamicFunctionValidator {
	boolean validate();

	boolean validateInitialization(EList<FunctionInitialization> value);
}
