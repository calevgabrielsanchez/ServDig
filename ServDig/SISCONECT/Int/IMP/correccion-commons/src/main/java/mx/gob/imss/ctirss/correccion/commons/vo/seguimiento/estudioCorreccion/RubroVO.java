package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;

public class RubroVO  implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idRow;
	private int idRubro;
	private int cvePercepcion;
	private String concepto;
	private String autoDeterminacion;
	private String importeAclarado;
	private String importePorAclarar;
	private String total;
	private boolean datoCorrecto;
	
	public int getIdRow() {
		return idRow;
	}
	public void setIdRow(int idRow) {
		this.idRow = idRow;
	}
	public int getIdRubro() {
		return idRubro;
	}
	public void setIdRubro(int idRubro) {
		this.idRubro = idRubro;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getAutoDeterminacion() {
		return autoDeterminacion;
	}
	public void setAutoDeterminacion(String autoDeterminacion) {
		this.autoDeterminacion = autoDeterminacion;
	}
	public String getImporteAclarado() {
		return importeAclarado;
	}
	public void setImporteAclarado(String importeAclarado) {
		this.importeAclarado = importeAclarado;
	}
	public String getImportePorAclarar() {
		return importePorAclarar;
	}
	public void setImportePorAclarar(String importePorAclarar) {
		this.importePorAclarar = importePorAclarar;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	public int getCvePercepcion() {
		return cvePercepcion;
	}
	public void setCvePercepcion(int cvePercepcion) {
		this.cvePercepcion = cvePercepcion;
	}
	public boolean isDatoCorrecto() {
		return datoCorrecto;
	}
	public void setDatoCorrecto(boolean datoCorrecto) {
		this.datoCorrecto = datoCorrecto;
	}
	
	
}
