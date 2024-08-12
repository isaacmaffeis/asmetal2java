/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.validation;

import org.eclipse.emf.common.util.EList;

import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.structure.Header;

/**
 * A sample validator interface for {@link asmeta.structure.Signature}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface SignatureValidator {
	boolean validate();

	boolean validateDomain(EList<Domain> value);
	boolean validateFunction(EList<Function> value);
	boolean validateHeaderSection(Header value);

	boolean validateStructuredDomain(EList<StructuredTd> value);
}
