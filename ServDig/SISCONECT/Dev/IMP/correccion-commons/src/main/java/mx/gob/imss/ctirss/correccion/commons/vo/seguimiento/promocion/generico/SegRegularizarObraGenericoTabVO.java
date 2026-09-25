/**
 * SegRegularizarObraGenericoTabVO.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;

/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pantalla
 * manejada en el flujo de Seguimiento de promocion Generica para Regularizar Obra 
 * 
 * 
 * @author Oscar Beltran Ortega
 * @version 1.0.1
 *
 */
public class SegRegularizarObraGenericoTabVO extends ControlTabs implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String cveRegulaPagos;
	private String periodoRegDel;
	private String periodoRegAl;
	private String trabRevisados;
	private String trabOmisos;
	private String trabSubdeclarados;
	private String trabRegularizados;
	private String baseDeterminada;
	private String suertePpalDetCOP;
	private String suertePpalDetRCV;
	private String suertePpalPentPagoCOP;
	private String suertePpalPentPagoRCV;
	private String suertePrincipalCOP;
	private String suertePrincipalRCV;
	private String actualizacionCOP;
	private String actualizacionRCV;
	private String recargosCOP;
	private String recargosRCV;
	private String totalPagadoCOP;
	private String totalPagadoRCV;
	
	private String multasCOP;
	private String multasRCV;
	
	private String porcAvance;
	private String porcRegularizado;
	private String numParcialidades;
	
	private String fechaRegistroTxt;
	
	
	
	private List<CrtRegulapagosdet> listaPagosDetalle ;
	
	private Integer banderaTipoPago;
	private String registroPatronalPromocionVal;
	/**
	 * Retorna el valor periodoRegDel
	 * @return  periodoRegDel
	 */
	public String getPeriodoRegDel() {
		return periodoRegDel;
	}
	/**
	 * Asigna el valor del periodoRegDel al atributo periodoRegDel
	 * @param periodoRegDel 
	 */
	public void setPeriodoRegDel(String periodoRegDel) {
		this.periodoRegDel = periodoRegDel;
	}
	/**
	 * Retorna el valor periodoRegAl
	 * @return  periodoRegAl
	 */
	public String getPeriodoRegAl() {
		return periodoRegAl;
	}
	/**
	 * Asigna el valor del periodoRegAl al atributo periodoRegAl
	 * @param periodoRegAl 
	 */
	public void setPeriodoRegAl(String periodoRegAl) {
		this.periodoRegAl = periodoRegAl;
	}
	/**
	 * Retorna el valor trabRevisados
	 * @return  trabRevisados
	 */
	public String getTrabRevisados() {
		return trabRevisados;
	}
	/**
	 * Asigna el valor del trabRevisados al atributo trabRevisados
	 * @param trabRevisados 
	 */
	public void setTrabRevisados(String trabRevisados) {
		this.trabRevisados = trabRevisados;
	}
	/**
	 * Retorna el valor trabOmisos
	 * @return  trabOmisos
	 */
	public String getTrabOmisos() {
		return trabOmisos;
	}
	/**
	 * Asigna el valor del trabOmisos al atributo trabOmisos
	 * @param trabOmisos 
	 */
	public void setTrabOmisos(String trabOmisos) {
		this.trabOmisos = trabOmisos;
	}
	/**
	 * Retorna el valor trabSubdeclarados
	 * @return  trabSubdeclarados
	 */
	public String getTrabSubdeclarados() {
		return trabSubdeclarados;
	}
	/**
	 * Asigna el valor del trabSubdeclarados al atributo trabSubdeclarados
	 * @param trabSubdeclarados 
	 */
	public void setTrabSubdeclarados(String trabSubdeclarados) {
		this.trabSubdeclarados = trabSubdeclarados;
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
	 * Retorna el valor baseDeterminada
	 * @return  baseDeterminada
	 */
	public String getBaseDeterminada() {
		return baseDeterminada;
	}
	/**
	 * Asigna el valor del baseDeterminada al atributo baseDeterminada
	 * @param baseDeterminada 
	 */
	public void setBaseDeterminada(String baseDeterminada) {
		this.baseDeterminada = baseDeterminada;
	}
	/**
	 * Retorna el valor suertePpalDetCOP
	 * @return  suertePpalDetCOP
	 */
	public String getSuertePpalDetCOP() {
		return suertePpalDetCOP;
	}
	/**
	 * Asigna el valor del suertePpalDetCOP al atributo suertePpalDetCOP
	 * @param suertePpalDetCOP 
	 */
	public void setSuertePpalDetCOP(String suertePpalDetCOP) {
		this.suertePpalDetCOP = suertePpalDetCOP;
	}
	/**
	 * Retorna el valor suertePpalDetRCV
	 * @return  suertePpalDetRCV
	 */
	public String getSuertePpalDetRCV() {
		return suertePpalDetRCV;
	}
	/**
	 * Asigna el valor del suertePpalDetRCV al atributo suertePpalDetRCV
	 * @param suertePpalDetRCV 
	 */
	public void setSuertePpalDetRCV(String suertePpalDetRCV) {
		this.suertePpalDetRCV = suertePpalDetRCV;
	}
	/**
	 * Retorna el valor suertePpalPentPagoCOP
	 * @return  suertePpalPentPagoCOP
	 */
	public String getSuertePpalPentPagoCOP() {
		return suertePpalPentPagoCOP;
	}
	/**
	 * Asigna el valor del suertePpalPentPagoCOP al atributo suertePpalPentPagoCOP
	 * @param suertePpalPentPagoCOP 
	 */
	public void setSuertePpalPentPagoCOP(String suertePpalPentPagoCOP) {
		this.suertePpalPentPagoCOP = suertePpalPentPagoCOP;
	}
	/**
	 * Retorna el valor suertePpalPentPagoRCV
	 * @return  suertePpalPentPagoRCV
	 */
	public String getSuertePpalPentPagoRCV() {
		return suertePpalPentPagoRCV;
	}
	/**
	 * Asigna el valor del suertePpalPentPagoRCV al atributo suertePpalPentPagoRCV
	 * @param suertePpalPentPagoRCV 
	 */
	public void setSuertePpalPentPagoRCV(String suertePpalPentPagoRCV) {
		this.suertePpalPentPagoRCV = suertePpalPentPagoRCV;
	}
	/**
	 * Retorna el valor suertePrincipalCOP
	 * @return  suertePrincipalCOP
	 */
	public String getSuertePrincipalCOP() {
		return suertePrincipalCOP;
	}
	/**
	 * Asigna el valor del suertePrincipalCOP al atributo suertePrincipalCOP
	 * @param suertePrincipalCOP 
	 */
	public void setSuertePrincipalCOP(String suertePrincipalCOP) {
		this.suertePrincipalCOP = suertePrincipalCOP;
	}
	/**
	 * Retorna el valor suertePrincipalRCV
	 * @return  suertePrincipalRCV
	 */
	public String getSuertePrincipalRCV() {
		return suertePrincipalRCV;
	}
	/**
	 * Asigna el valor del suertePrincipalRCV al atributo suertePrincipalRCV
	 * @param suertePrincipalRCV 
	 */
	public void setSuertePrincipalRCV(String suertePrincipalRCV) {
		this.suertePrincipalRCV = suertePrincipalRCV;
	}
	/**
	 * Retorna el valor actualizacionCOP
	 * @return  actualizacionCOP
	 */
	public String getActualizacionCOP() {
		return actualizacionCOP;
	}
	/**
	 * Asigna el valor del actualizacionCOP al atributo actualizacionCOP
	 * @param actualizacionCOP 
	 */
	public void setActualizacionCOP(String actualizacionCOP) {
		this.actualizacionCOP = actualizacionCOP;
	}
	/**
	 * Retorna el valor actualizacionRCV
	 * @return  actualizacionRCV
	 */
	public String getActualizacionRCV() {
		return actualizacionRCV;
	}
	/**
	 * Asigna el valor del actualizacionRCV al atributo actualizacionRCV
	 * @param actualizacionRCV 
	 */
	public void setActualizacionRCV(String actualizacionRCV) {
		this.actualizacionRCV = actualizacionRCV;
	}
	/**
	 * Retorna el valor recargosCOP
	 * @return  recargosCOP
	 */
	public String getRecargosCOP() {
		return recargosCOP;
	}
	/**
	 * Asigna el valor del recargosCOP al atributo recargosCOP
	 * @param recargosCOP 
	 */
	public void setRecargosCOP(String recargosCOP) {
		this.recargosCOP = recargosCOP;
	}
	/**
	 * Retorna el valor recargosRCV
	 * @return  recargosRCV
	 */
	public String getRecargosRCV() {
		return recargosRCV;
	}
	/**
	 * Asigna el valor del recargosRCV al atributo recargosRCV
	 * @param recargosRCV 
	 */
	public void setRecargosRCV(String recargosRCV) {
		this.recargosRCV = recargosRCV;
	}
	/**
	 * Retorna el valor totalPagadoCOP
	 * @return  totalPagadoCOP
	 */
	public String getTotalPagadoCOP() {
		return totalPagadoCOP;
	}
	/**
	 * Asigna el valor del totalPagadoCOP al atributo totalPagadoCOP
	 * @param totalPagadoCOP 
	 */
	public void setTotalPagadoCOP(String totalPagadoCOP) {
		this.totalPagadoCOP = totalPagadoCOP;
	}
	/**
	 * Retorna el valor totalPagadoRCV
	 * @return  totalPagadoRCV
	 */
	public String getTotalPagadoRCV() {
		return totalPagadoRCV;
	}
	/**
	 * Asigna el valor del totalPagadoRCV al atributo totalPagadoRCV
	 * @param totalPagadoRCV 
	 */
	public void setTotalPagadoRCV(String totalPagadoRCV) {
		this.totalPagadoRCV = totalPagadoRCV;
	}
	/**
	 * Retorna el valor porcAvance
	 * @return  porcAvance
	 */
	public String getPorcAvance() {
		return porcAvance;
	}
	/**
	 * Asigna el valor del porcAvance al atributo porcAvance
	 * @param porcAvance 
	 */
	public void setPorcAvance(String porcAvance) {
		this.porcAvance = porcAvance;
	}
	/**
	 * Retorna el valor porcRegularizado
	 * @return  porcRegularizado
	 */
	public String getPorcRegularizado() {
		return porcRegularizado;
	}
	/**
	 * Asigna el valor del porcRegularizado al atributo porcRegularizado
	 * @param porcRegularizado 
	 */
	public void setPorcRegularizado(String porcRegularizado) {
		this.porcRegularizado = porcRegularizado;
	}
	/**
	 * Retorna el valor numParcialidades
	 * @return  numParcialidades
	 */
	public String getNumParcialidades() {
		return numParcialidades;
	}
	/**
	 * Asigna el valor del numParcialidades al atributo numParcialidades
	 * @param numParcialidades 
	 */
	public void setNumParcialidades(String numParcialidades) {
		this.numParcialidades = numParcialidades;
	}
	/**
	 * Retorna el valor cveRegulaPagos
	 * @return  cveRegulaPagos
	 */
	public String getCveRegulaPagos() {
		return cveRegulaPagos;
	}
	/**
	 * Asigna el valor del cveRegulaPagos al atributo cveRegulaPagos
	 * @param cveRegulaPagos 
	 */
	public void setCveRegulaPagos(String cveRegulaPagos) {
		this.cveRegulaPagos = cveRegulaPagos;
	}
	/**
	 * Retorna el valor listaPagosDetalle
	 * @return  listaPagosDetalle
	 */
	public List<CrtRegulapagosdet> getListaPagosDetalle() {
		return listaPagosDetalle;
	}
	/**
	 * Asigna el valor del listaPagosDetalle al atributo listaPagosDetalle
	 * @param listaPagosDetalle 
	 */
	public void setListaPagosDetalle(List<CrtRegulapagosdet> listaPagosDetalle) {
		this.listaPagosDetalle = listaPagosDetalle;
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
	 * Retorna el valor banderaTipoPago
	 * @return  banderaTipoPago
	 */
	public Integer getBanderaTipoPago() {
		return banderaTipoPago;
	}
	/**
	 * Asigna el valor del banderaTipoPago al atributo banderaTipoPago
	 * @param banderaTipoPago 
	 */
	public void setBanderaTipoPago(Integer banderaTipoPago) {
		this.banderaTipoPago = banderaTipoPago;
	}
	/**
	 * Retorna el valor registroPatronalPromocionVal
	 * @return  registroPatronalPromocionVal
	 */
	public String getRegistroPatronalPromocionVal() {
		return registroPatronalPromocionVal;
	}
	/**
	 * Asigna el valor del registroPatronalPromocionVal al atributo registroPatronalPromocionVal
	 * @param registroPatronalPromocionVal 
	 */
	public void setRegistroPatronalPromocionVal(String registroPatronalPromocionVal) {
		this.registroPatronalPromocionVal = registroPatronalPromocionVal;
	}
	/**
	 * Retorna el valor fechaRegistroTxt
	 * @return  fechaRegistroTxt
	 */
	public String getFechaRegistroTxt() {
		return fechaRegistroTxt;
	}
	/**
	 * Asigna el valor del fechaRegistroTxt al atributo fechaRegistroTxt
	 * @param fechaRegistroTxt 
	 */
	public void setFechaRegistroTxt(String fechaRegistroTxt) {
		this.fechaRegistroTxt = fechaRegistroTxt;
	}
	
	
	

}
