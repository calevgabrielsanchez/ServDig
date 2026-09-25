/**
 * 
 */
package mx.gob.imss.ctirss.clasificador.model.controller;

/**
 * @author lucio
 *
 */
@SuppressWarnings("serial")
public class FraccionDataTableSend extends AbstractDataTableSend {
	
	/**
	 * @param cveGrupo : Clave del grupo para buscar las fracciones.
	 */
	private int cveGrupo;
	
	/**
	 * @param cveDivision : Clave de la division del grupo seleccionado.
	 */
	private int cveDivision;

	/**
	 * @return the cveGrupo
	 */
	public int getCveGrupo() {
		return cveGrupo;
	}

	/**
	 * @param cveGrupo the cveGrupo to set
	 */
	public void setCveGrupo(int cveGrupo) {
		this.cveGrupo = cveGrupo;
	}

	/**
	 * @return the cveDivision
	 */
	public int getCveDivision() {
		return cveDivision;
	}

	/**
	 * @param cveDivision the cveDivision to set
	 */
	public void setCveDivision(int cveDivision) {
		this.cveDivision = cveDivision;
	}

}
