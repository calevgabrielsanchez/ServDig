/**
 * AbstractPaginador.java
 * mx.gob.imss.delta.base.paginador.model
 * model-business-pojo
 * 
 *  When using server-side processing, DataTables will make an XHR
 *         request to the server for each draw of the information on the page
 *         (i.e. when paging, sorting, filtering etc). DataTables will send a
 *         number of variables to the server to allow it to perform the required
 *         processing, and then return the data in the format required by
 *         DataTables.
 * 
 * 
 */
package mx.gob.imss.ctirss.correccion.base.paginador.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
 

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author Lucio Duran Silva
 * 09/09/2011
 * 
 *  The following information is sent to the server for
 *         each draw request. Your server-side script must use this information
 *         to obtain the data required for the draw.
 * 
 */
public  class DatosEntradaPaginador<T extends AbstractModel> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7498561999956348161L;

	/**
	 * @param iDisplayLength
	 *            : Number of records that the table can display in the current
	 *            draw. It is expected that the number of records returned will
	 *            be equal to this number, unless the server has fewer records
	 *            to return.
	 */
	private int iDisplayLength;
	
	/**
	 * @param iDisplayStart: Display start point in the current data set.
	 */
	private int iDisplayStart;
	
	/**
	 * @param iColumns : Number of columns being displayed (useful for getting individual column search info)
	 */
	private String iColumns;
	
	
	/**
	 * @param sSearch : Global search field
	 */
	private String sSearch;
	
	/**
	 * @param bRegex
	 *            : True if the global filter should be treated as a regular
	 *            expression for advanced filtering, false if not.
	 */
	private boolean bRegex;

	/**
	 * @param iSortingCols : Number of columns to sort on
	 */
	private int iSortingCols;
	
	/**
	 * @param sEcho : Information for DataTables to use for rendering.
	 */
	private String sEcho;
	
	
	
	/**
	 * @param dispatch: 
	 */
	private String dispatch;

	/**
	 * Columna para ordenar.
	 */
	private String iSortCol_0;
	/**
	 * direccion del ordenamiento.
	 */
	private String sSortDir_0;
	
	private T modelo;
	
	
	/**
	 * @return the iDisplayLength
	 */
	public int getiDisplayLength() {
		return iDisplayLength;
	}

	/**
	 * @param iDisplayLength the iDisplayLength to set
	 */
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	/**
	 * @return the iDisplayStart
	 */
	public int getiDisplayStart() {
		return iDisplayStart;
	}

	/**
	 * @param iDisplayStart the iDisplayStart to set
	 */
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	/**
	 * @return the iColumns
	 */
	public String getiColumns() {
		return iColumns;
	}

	/**
	 * @param iColumns the iColumns to set
	 */
	public void setiColumns(String iColumns) {
		this.iColumns = iColumns;
	}

	/**
	 * @return the sSearch
	 */
	public String getsSearch() {
		return sSearch;
	}

	/**
	 * @param sSearch the sSearch to set
	 */
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	/**
	 * @return the bRegex
	 */
	public boolean isbRegex() {
		return bRegex;
	}

	/**
	 * @param bRegex the bRegex to set
	 */
	public void setbRegex(boolean bRegex) {
		this.bRegex = bRegex;
	}

	/**
	 * @return the iSortingCols
	 */
	public int getiSortingCols() {
		return iSortingCols;
	}

	/**
	 * @param iSortingCols the iSortingCols to set
	 */
	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
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
	 * 
	 * @param aoData
	 */
	public void parserArray(List aoData){
		
		if(aoData != null){
			Iterator<LinkedHashMap<String , Object>> it = aoData.iterator();
			while(it.hasNext()){
				LinkedHashMap<String, Object> lb = it.next();
			String name = (String )lb.get("name");
			Object value = lb.get("value");
				
			try {
				
				BeanUtils.setProperty(this, name, value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
				
			}
		}
		
	
		
	}

	/**
	 * @return the dispatch
	 */
	public String getDispatch() {
		return dispatch;
	}

	/**
	 * @param dispatch the dispatch to set
	 */
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	/**
	 * @return the modelo
	 */
	public T getModelo() {
		return modelo;
	}

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(T modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the iSortCol_0
	 */
	public String getiSortCol_0() {
		return iSortCol_0;
	}

	/**
	 * @param iSortCol_0 the iSortCol_0 to set
	 */
	public void setiSortCol_0(String iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	/**
	 * @return the sSortDir_0
	 */
	public String getsSortDir_0() {
		return sSortDir_0;
	}

	/**
	 * @param sSortDir_0 the sSortDir_0 to set
	 */
	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	
}
