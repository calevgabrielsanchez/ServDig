/**
 * 
 */
package mx.gob.imss.ctirss.delta.utilities.planificador.base.web;

import java.util.List;

import mx.gob.imss.ctirss.delta.utilities.planificador.base.model.AbstractModel;

/**
 * @author Juan Manuel Lopez Lozano
 * @since 29/09/2011
 * 
 */
public class WrapperDataTable<T extends AbstractModel> {

	@SuppressWarnings("rawtypes")
	private List aoData;

	private T oForm;

	/**
	 * @return the aoData
	 */
	@SuppressWarnings("rawtypes")
	public List getAoData() {
		return aoData;
	}

	/**
	 * @param aoData
	 *            the aoData to set
	 */
	@SuppressWarnings("rawtypes")
	public void setAoData(List aoData) {
		this.aoData = aoData;
	}

	/**
	 * @return the oForm
	 */
	public T getoForm() {
		return oForm;
	}

	/**
	 * @param oForm
	 *            the oForm to set
	 */
	public void setoForm(T oForm) {
		this.oForm = oForm;
	}

}
