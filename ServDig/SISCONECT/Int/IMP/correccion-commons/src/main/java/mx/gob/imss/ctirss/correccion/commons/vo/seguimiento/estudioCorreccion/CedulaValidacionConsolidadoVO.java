package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.math.BigDecimal;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CedulaValidacionConsolidadoVO extends AbstractModel implements Serializable {
	
	/**
	 * VO utilizado en la cedula de validacion,
	 * contiene la informacion del detalle de la cedula.
	 */
	private static final long serialVersionUID = 1L;
	private int idRow;
	private String concepto;
	private Integer cvePercepcion;
	private String importexAclarar;
	private String aclarado;
	private boolean autorizaAclarado;
	private String diferenciaAclarado;
	private String aclaradoOficioResultados;
	private boolean autorizaAclaradoOficioRes;
	private String totalAPagar;
	private String totalPagado;
	private boolean autorizaTotalPagado;
	private String diferenciaPagado;
	
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * @return the importexAclarar
	 */
	public String getImportexAclarar() {
		return importexAclarar;
	}
	/**
	 * @param importexAclarar the importexAclarar to set
	 */
	public void setImportexAclarar(String importexAclarar) {
		this.importexAclarar = importexAclarar;
	}
	/**
	 * @return the aclarado
	 */
	public String getAclarado() {
		return aclarado;
	}
	/**
	 * @param aclarado the aclarado to set
	 */
	public void setAclarado(String aclarado) {
		this.aclarado = aclarado;
	}
	/**
	 * @return the diferenciaAclarado
	 */
	public String getDiferenciaAclarado() {
		return diferenciaAclarado;
	}
	/**
	 * @param diferenciaAclarado the diferenciaAclarado to set
	 */
	public void setDiferenciaAclarado(String diferenciaAclarado) {
		this.diferenciaAclarado = diferenciaAclarado;
	}
	/**
	 * @param 
	 */
	public void setDiferenciaAclarado() {
		BigDecimal bdImportexAclarar = Functions.parserBigDecimal(this.importexAclarar);
		BigDecimal bdAclarado = Functions.parserBigDecimal(this.aclarado);
		BigDecimal bdResultado = BigDecimal.ZERO;
		
		bdResultado = bdImportexAclarar.subtract(bdAclarado);
		this.diferenciaAclarado = bdResultado.toString();
	}	
	/**
	 * @return the aclaradoOficioResultados
	 */
	public String getAclaradoOficioResultados() {
		return aclaradoOficioResultados;
	}
	/**
	 * @param aclaradoOficioResultados the aclaradoOficioResultados to set
	 */
	public void setAclaradoOficioResultados(String aclaradoOficioResultados) {
		this.aclaradoOficioResultados = aclaradoOficioResultados;
	}
	/**
	 * @return the totalAPagar
	 */
	public String getTotalAPagar() {
		return totalAPagar;
	}
	/**
	 * @param totalAPagar the totalAPagar to set
	 */
	public void setTotalAPagar(String totalAPagar) {
		this.totalAPagar = totalAPagar;
	}
	public void setTotalAPagar() {
		BigDecimal bdDiferenciaAclarado = Functions.parserBigDecimal(this.diferenciaAclarado);
		BigDecimal bdAclaradoOficioResultados = Functions.parserBigDecimal(this.aclaradoOficioResultados);
		BigDecimal bdResultadoTP = BigDecimal.ZERO;
		
		bdResultadoTP = bdDiferenciaAclarado.subtract(bdAclaradoOficioResultados);
		this.totalAPagar = bdResultadoTP.toString();
	}		

	/**
	 * @return the totalPagado
	 */
	public String getTotalPagado() {
		return totalPagado;
	}
	/**
	 * @param totalPagado the totalPagado to set
	 */
	public void setTotalPagado(String totalPagado) {
		this.totalPagado = totalPagado;
	}
	/**
	 * @return the diferenciaPagado
	 */
	public String getDiferenciaPagado() {
		return diferenciaPagado;
	}
	
	public void setDiferenciaPagado() {
		BigDecimal bdTotalAPagar = Functions.parserBigDecimal(this.totalAPagar);
		BigDecimal bdTotalPagado = Functions.parserBigDecimal(this.totalPagado);
		BigDecimal bdResultadoDP = BigDecimal.ZERO;
		
		bdResultadoDP = bdTotalAPagar.subtract(bdTotalPagado);
		this.diferenciaPagado = bdResultadoDP.toString();
	}		
	/**
	 * @param diferenciaPagado the diferenciaPagado to set
	 */
	public void setDiferenciaPagado(String diferenciaPagado) {
		this.diferenciaPagado = diferenciaPagado;
	}
	/**
	 * @return the autorizaAclarado
	 */
	public boolean isAutorizaAclarado() {
		return autorizaAclarado;
	}
	/**
	 * @param autorizaAclarado the autorizaAclarado to set
	 */
	public void setAutorizaAclarado(boolean autorizaAclarado) {
		this.autorizaAclarado = autorizaAclarado;
	}
	/**
	 * @return the autorizaAclaradoOficioRes
	 */
	public boolean isAutorizaAclaradoOficioRes() {
		return autorizaAclaradoOficioRes;
	}
	/**
	 * @param autorizaAclaradoOficioRes the autorizaAclaradoOficioRes to set
	 */
	public void setAutorizaAclaradoOficioRes(boolean autorizaAclaradoOficioRes) {
		this.autorizaAclaradoOficioRes = autorizaAclaradoOficioRes;
	}
	/**
	 * @return the autorizaTotalPagado
	 */
	public boolean isAutorizaTotalPagado() {
		return autorizaTotalPagado;
	}
	/**
	 * @param autorizaTotalPagado the autorizaTotalPagado to set
	 */
	public void setAutorizaTotalPagado(boolean autorizaTotalPagado) {
		this.autorizaTotalPagado = autorizaTotalPagado;
	}
	
	public int getIdRow() {
		return idRow;
	}
	
	public void setIdRow(int idRow) {
		this.idRow = idRow;
	}
	public Integer getCvePercepcion() {
		return cvePercepcion;
	}
	public void setCvePercepcion(Integer cvePercepcion) {
		this.cvePercepcion = cvePercepcion;
	}
	
	
}
