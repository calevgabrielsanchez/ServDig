package mx.imss.estrados.paginado.dto;

import java.io.Serializable;

public class FiltroColumna implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2699678598675796321L;
	/**
	 * 
	 */
	

	private Integer sortCol;
	private String sortDir;

	public FiltroColumna(Integer sortCol, String sortDir) {
		super();
		this.sortCol = sortCol;
		this.sortDir = sortDir;
	}

	public Integer getSortCol() {
		return sortCol;
	}

	public void setSortCol(Integer sortCol) {
		this.sortCol = sortCol;
	}

	public String getSortDir() {
		return sortDir;
	}

	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

}
