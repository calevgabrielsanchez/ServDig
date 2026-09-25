/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Juan Manuel Lopez Lozano
 * @since  29/09/2011
 *
 */
@SuppressWarnings("rawtypes")  // TODO - SuppressWarnings!!
public class WrapperDataTable<T extends AbstractModel> {

	
	private  List aoData;
	
	private T oForm;

	/**
	 * @return the aoData
	 */
	public List getAoData() {
		return aoData;
	}

	/**
	 * @param aoData the aoData to set
	 */
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
	 * @param oForm the oForm to set
	 */
	public void setoForm(T oForm) {
		this.oForm = oForm;
	}

		
	
	
	
}
