package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.beans;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;

/**
 * @author Marco Sánchez
 * 
 */

public class DocumentoProbatorioFormWrapper extends AbstractModel {

	private static final long serialVersionUID = -2988306549438870199L;

	private Nacimiento actaNacimiento;
	private CURP docProbRENAPO;

	private TipoDocumentoProbatorio tipoDocumentoProbatorio;

	/**
	 * @return the actaNacimiento
	 */
	public Nacimiento getActaNacimiento() {
		return actaNacimiento;
	}

	/**
	 * @param actaNacimiento
	 *            the actaNacimiento to set
	 */
	public void setActaNacimiento(Nacimiento actaNacimiento) {
		this.actaNacimiento = actaNacimiento;
	}

	/**
	 * @return the docProbRENAPO
	 */
	public CURP getDocProbRENAPO() {
		return docProbRENAPO;
	}

	/**
	 * @param docProbRENAPO
	 *            the docProbRENAPO to set
	 */
	public void setDocProbRENAPO(CURP docProbRENAPO) {
		this.docProbRENAPO = docProbRENAPO;
	}

	/**
	 * @return the tipoDocumentoProbatorio
	 */
	public TipoDocumentoProbatorio getTipoDocumentoProbatorio() {
		return tipoDocumentoProbatorio;
	}

	/**
	 * @param tipoDocumentoProbatorio
	 *            the tipoDocumentoProbatorio to set
	 */
	public void setTipoDocumentoProbatorio(
			TipoDocumentoProbatorio tipoDocumentoProbatorio) {
		this.tipoDocumentoProbatorio = tipoDocumentoProbatorio;
	}

}
