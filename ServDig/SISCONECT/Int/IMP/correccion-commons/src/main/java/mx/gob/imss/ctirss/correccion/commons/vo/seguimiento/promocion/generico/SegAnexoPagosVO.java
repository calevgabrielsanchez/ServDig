/**
 * SegAnexoPagosVO.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.generico
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import java.io.Serializable;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pantalla
 * manejada en el flujo de Anexa pagos
 * 
 * @author Oscar Beltran Ortega
 * @version 1.0.1
 *
 */
public class SegAnexoPagosVO extends AbstractModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cveRegulaPago;
	private String folioSUA;
	private String ordenIngreso;
	private String numCredito;
	private String fechaPagoGenerico;
	private String tipoDocto;
	
	private String periodoCOP;
	private String spCOP;
	private String actCOP;
	private String recCOP;
	private String totalCOP;
	private String multasCOP;
	
	private String periodoRCV;
	private String spRCV;
	private String actRCV;
	private String recRCV;
	private String totalRCV;
	private String multasRCV;
	
	private String trabRegularizados;
	private String altas;
	private String bajas;
	private String modifSalario;
	
	private boolean seccionCOP;
	private boolean seccionRCV;
	
	/**
	 * Retorna el valor folioSUA
	 * @return  folioSUA
	 */
	public String getFolioSUA() {
		return folioSUA;
	}
	/**
	 * Asigna el valor del folioSUA al atributo folioSUA
	 * @param folioSUA 
	 */
	public void setFolioSUA(String folioSUA) {
		this.folioSUA = folioSUA;
	}
	/**
	 * Retorna el valor ordenIngreso
	 * @return  ordenIngreso
	 */
	public String getOrdenIngreso() {
		return ordenIngreso;
	}
	/**
	 * Asigna el valor del ordenIngreso al atributo ordenIngreso
	 * @param ordenIngreso 
	 */
	public void setOrdenIngreso(String ordenIngreso) {
		this.ordenIngreso = ordenIngreso;
	}
	/**
	 * Retorna el valor numCredito
	 * @return  numCredito
	 */
	public String getNumCredito() {
		return numCredito;
	}
	/**
	 * Asigna el valor del numCredito al atributo numCredito
	 * @param numCredito 
	 */
	public void setNumCredito(String numCredito) {
		this.numCredito = numCredito;
	}
	
	
	
	/**
	 * Retorna el valor fechaPagoGenerico
	 * @return  fechaPagoGenerico
	 */
	public String getFechaPagoGenerico() {
		return fechaPagoGenerico;
	}
	/**
	 * Asigna el valor del fechaPagoGenerico al atributo fechaPagoGenerico
	 * @param fechaPagoGenerico 
	 */
	public void setFechaPagoGenerico(String fechaPagoGenerico) {
		this.fechaPagoGenerico = fechaPagoGenerico;
	}
	/**
	 * Retorna el valor periodoCOP
	 * @return  periodoCOP
	 */
	public String getPeriodoCOP() {
		return periodoCOP;
	}
	/**
	 * Asigna el valor del periodoCOP al atributo periodoCOP
	 * @param periodoCOP 
	 */
	public void setPeriodoCOP(String periodoCOP) {
		this.periodoCOP = periodoCOP;
	}
	/**
	 * Retorna el valor spCOP
	 * @return  spCOP
	 */
	public String getSpCOP() {
		return spCOP;
	}
	/**
	 * Asigna el valor del spCOP al atributo spCOP
	 * @param spCOP 
	 */
	public void setSpCOP(String spCOP) {
		this.spCOP = spCOP;
	}
	/**
	 * Retorna el valor actCOP
	 * @return  actCOP
	 */
	public String getActCOP() {
		return actCOP;
	}
	/**
	 * Asigna el valor del actCOP al atributo actCOP
	 * @param actCOP 
	 */
	public void setActCOP(String actCOP) {
		this.actCOP = actCOP;
	}
	/**
	 * Retorna el valor recCOP
	 * @return  recCOP
	 */
	public String getRecCOP() {
		return recCOP;
	}
	/**
	 * Asigna el valor del recCOP al atributo recCOP
	 * @param recCOP 
	 */
	public void setRecCOP(String recCOP) {
		this.recCOP = recCOP;
	}
	/**
	 * Retorna el valor totalCOP
	 * @return  totalCOP
	 */
	public String getTotalCOP() {
		return totalCOP;
	}
	/**
	 * Asigna el valor del totalCOP al atributo totalCOP
	 * @param totalCOP 
	 */
	public void setTotalCOP(String totalCOP) {
		this.totalCOP = totalCOP;
	}
	/**
	 * Retorna el valor multasCOP
	 * @return  multasCOP
	 */
	public String getMultasCOP() {
		return multasCOP;
	}
	/**
	 * Asigna el valor del multasCOP al atributo multasCOP
	 * @param multasCOP 
	 */
	public void setMultasCOP(String multasCOP) {
		this.multasCOP = multasCOP;
	}
	/**
	 * Retorna el valor periodoRCV
	 * @return  periodoRCV
	 */
	public String getPeriodoRCV() {
		return periodoRCV;
	}
	/**
	 * Asigna el valor del periodoRCV al atributo periodoRCV
	 * @param periodoRCV 
	 */
	public void setPeriodoRCV(String periodoRCV) {
		this.periodoRCV = periodoRCV;
	}
	/**
	 * Retorna el valor spRCV
	 * @return  spRCV
	 */
	public String getSpRCV() {
		return spRCV;
	}
	/**
	 * Asigna el valor del spRCV al atributo spRCV
	 * @param spRCV 
	 */
	public void setSpRCV(String spRCV) {
		this.spRCV = spRCV;
	}
	/**
	 * Retorna el valor actRCV
	 * @return  actRCV
	 */
	public String getActRCV() {
		return actRCV;
	}
	/**
	 * Asigna el valor del actRCV al atributo actRCV
	 * @param actRCV 
	 */
	public void setActRCV(String actRCV) {
		this.actRCV = actRCV;
	}
	/**
	 * Retorna el valor recRCV
	 * @return  recRCV
	 */
	public String getRecRCV() {
		return recRCV;
	}
	/**
	 * Asigna el valor del recRCV al atributo recRCV
	 * @param recRCV 
	 */
	public void setRecRCV(String recRCV) {
		this.recRCV = recRCV;
	}
	/**
	 * Retorna el valor totalRCV
	 * @return  totalRCV
	 */
	public String getTotalRCV() {
		return totalRCV;
	}
	/**
	 * Asigna el valor del totalRCV al atributo totalRCV
	 * @param totalRCV 
	 */
	public void setTotalRCV(String totalRCV) {
		this.totalRCV = totalRCV;
	}
	/**
	 * Retorna el valor multasRCV
	 * @return  multasRCV
	 */
	public String getMultasRCV() {
		return multasRCV;
	}
	/**
	 * Asigna el valor del multasRCV al atributo multasRCV
	 * @param multasRCV 
	 */
	public void setMultasRCV(String multasRCV) {
		this.multasRCV = multasRCV;
	}
	/**
	 * Retorna el valor trabRegularizados
	 * @return  trabRegularizados
	 */
	public String getTrabRegularizados() {
		return trabRegularizados;
	}
	/**
	 * Asigna el valor del trabRegularizados al atributo trabRegularizados
	 * @param trabRegularizados 
	 */
	public void setTrabRegularizados(String trabRegularizados) {
		this.trabRegularizados = trabRegularizados;
	}
	/**
	 * Retorna el valor altas
	 * @return  altas
	 */
	public String getAltas() {
		return altas;
	}
	/**
	 * Asigna el valor del altas al atributo altas
	 * @param altas 
	 */
	public void setAltas(String altas) {
		this.altas = altas;
	}
	/**
	 * Retorna el valor bajas
	 * @return  bajas
	 */
	public String getBajas() {
		return bajas;
	}
	/**
	 * Asigna el valor del bajas al atributo bajas
	 * @param bajas 
	 */
	public void setBajas(String bajas) {
		this.bajas = bajas;
	}
	/**
	 * Retorna el valor modifSalario
	 * @return  modifSalario
	 */
	public String getModifSalario() {
		return modifSalario;
	}
	/**
	 * Asigna el valor del modifSalario al atributo modifSalario
	 * @param modifSalario 
	 */
	public void setModifSalario(String modifSalario) {
		this.modifSalario = modifSalario;
	}
	/**
	 * Retorna el valor serialversionuid
	 * @return  serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * Retorna el valor seccionCOP
	 * @return  seccionCOP
	 */
	public boolean isSeccionCOP() {
		return seccionCOP;
	}
	/**
	 * Asigna el valor del seccionCOP al atributo seccionCOP
	 * @param seccionCOP 
	 */
	public void setSeccionCOP(boolean seccionCOP) {
		this.seccionCOP = seccionCOP;
	}
	/**
	 * Retorna el valor seccionRCV
	 * @return  seccionRCV
	 */
	public boolean isSeccionRCV() {
		return seccionRCV;
	}
	/**
	 * Asigna el valor del seccionRCV al atributo seccionRCV
	 * @param seccionRCV 
	 */
	public void setSeccionRCV(boolean seccionRCV) {
		this.seccionRCV = seccionRCV;
	}
	/**
	 * Retorna el valor tipoDocto
	 * @return  tipoDocto
	 */
	public String getTipoDocto() {
		return tipoDocto;
	}
	/**
	 * Asigna el valor del tipoDocto al atributo tipoDocto
	 * @param tipoDocto 
	 */
	public void setTipoDocto(String tipoDocto) {
		this.tipoDocto = tipoDocto;
	}
	/**
	 * Retorna el valor cveRegulaPago
	 * @return  cveRegulaPago
	 */
	public String getCveRegulaPago() {
		return cveRegulaPago;
	}
	/**
	 * Asigna el valor del cveRegulaPago al atributo cveRegulaPago
	 * @param cveRegulaPago 
	 */
	public void setCveRegulaPago(String cveRegulaPago) {
		this.cveRegulaPago = cveRegulaPago;
	}
	
	
	
	
}
