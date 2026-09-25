/**
 *			When using server-side processing, DataTables will make an XHR
 *         request to the server for each draw of the information on the page
 *         (i.e. when paging, sorting, filtering etc). DataTables will send a
 *         number of variables to the server to allow it to perform the required
 *         processing, and then return the data in the format required by
 *         DataTables.
 */
package mx.gob.imss.ctirss.clasificador.model.controller;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author Lucio Duran Silva 
 * 			
 * The following information is sent to the server for
 *         each draw request. Your server-side script must use this information
 *         to obtain the data required for the draw.
 */
@SuppressWarnings("serial")
public abstract class AbstractDataTableSend implements Serializable {
	

	
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
		
		
		System.out.println("aoData .:::" + aoData);
		
		
		if(aoData != null){
			Iterator<LinkedHashMap<String , Object>> it = aoData.iterator();
			while(it.hasNext()){
				LinkedHashMap<String, Object> lb = it.next();
				
			System.out.println("1. All Names :"+lb.get("name"));
			System.out.println("2. All Values :"+lb.get("value"));
				
			
			
			String name = (String )lb.get("name");
			Object value = lb.get("value");
				
			try {
				
				
				System.out.println("Setting property :" + name + "with value :" + value);
				
				BeanUtils.setProperty(this, name, value);
				
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
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
}
