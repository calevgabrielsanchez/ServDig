/**
 * AbstractPaginadorRespuesta.java
 * mx.gob.imss.delta.base.paginador.model
 * model-business-pojo
 * 
 * 
 * When using server-side processing, DataTables will make an XHR request to the server 
 * for each draw of the information on the page (i.e. when paging, sorting, filtering etc). DataTables will send a number of variables to the server to allow it to perform the required processing, and then return the data in the format required by DataTables.
 * 
 */
package mx.gob.imss.ctirss.delta.framework.paginador.model;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.base.web.ObjectError;

/**
 * @author Lucio Duran Silva
 * 09/09/2011
 * 
 * 
 *   In reply to each request for information that DataTables makes
 *         to the server, it expects to get a well formed JSON object with the
 *         following parameters.
 */
public  class DatosSalidaPaginador<T extends AbstractModel> implements Serializable {

	

	/**
	 * @param iTotalRecords
	 *            : Total records, before filtering (i.e. the total number of
	 *            records in the database)
	 */
	private int iTotalRecords;
	
	/**
	 * @param iTotalDisplayRecords
	 *            : Total records, after filtering (i.e. the total number of
	 *            records after filtering has been applied - not just the number
	 *            of records being returned in this result set)
	 */
	private int iTotalDisplayRecords;
	
	
	/**
	 * @param sEcho
	 *            : An unaltered copy of sEcho sent from the client side. This
	 *            parameter will change with each draw (it is basically a draw
	 *            count) - so it is important that this is implemented. Note
	 *            that it strongly recommended for security reasons that you
	 *            'cast' this parameter to an integer in order to prevent Cross
	 *            Site Scripting (XSS) attacks.
	 */
	private String sEcho;
	
	/**
	 * @param sColumns
	 *            : Optional - this is a string of column names, comma separated
	 *            (used in combination with sName) which will allow DataTables
	 *            to reorder data on the client-side if required for display.
	 *            Note that the number of column names returned must exactly
	 *            match the number of columns in the table. For a more flexible
	 *            JSON format, please consider using mDataProp.
	 */
	private String sColumns;
	
	/**
	 * @param aaData
	 *            : The data in a 2D array. Note that you can change the name of
	 *            this parameter with sAjaxDataProp.
	 */
	private List<T> aaData;
	
	/**
	 * @param errores
	 *            : La lista de errores a mostrar de los campos que no cumplen
	 *            con las validaciones
	 * 
	 */
	private List<ObjectError> erroresCaptura;

	/**
	 * @return the iTotalRecords
	 */
	public int getiTotalRecords() {
		return iTotalRecords;
	}

	/**
	 * @param iTotalRecords the iTotalRecords to set
	 */
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	/**
	 * @return the iTotalDisplayRecords
	 */
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	/**
	 * @param iTotalDisplayRecords the iTotalDisplayRecords to set
	 */
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	/**
	 * @return the sEcho
	 */
	public String getsEcho() {
		return sEcho;
	}

	/**
	 * @param sEcho the sEcho to set
	 */
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	/**
	 * @return the sColumns
	 */
	public String getsColumns() {
		return sColumns;
	}

	/**
	 * @param sColumns the sColumns to set
	 */
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

	/**
	 * @return the aaData
	 */
	public List<T> getAaData() {
		return aaData;
	}

	/**
	 * @param aaData the aaData to set
	 */
	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}

	/**
	 * @return the erroresCaptura
	 */
	public List<ObjectError> getErroresCaptura() {
		return erroresCaptura;
	}

	/**
	 * @param erroresCaptura
	 *            the erroresCaptura to set
	 */
	public void setErroresCaptura(List<ObjectError> erroresCaptura) {
		this.erroresCaptura = erroresCaptura;
	}

	
}
