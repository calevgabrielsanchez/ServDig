/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 *
 * @author Jonathan Sanchez Montiel
 */
public class ReporteAnalisis extends AbstractModel{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 8887696453615754000L;
	
	private String cveIdAnalisis;
	private String regPatron;
	private String regPatronCompleto;
	private String regPatronPadre;
	private String nombreComercial;
	private String razonSocial;
	private String nombre;
	private String cveIdDelegacion;
	private String delegDesc;
	private String cveIdSubdelegacion;
	private String sdelegDesc;
	private String localidad;
	private Date fecPresentacion;
	private Date fecAnalisis;
	private Date fecAutorizacion;
	private String cveIdTipoPersona;
	private String cveIdTipoRegistro;
	private String cveIdEstatus;
	private String tipoPersona;
	private String indRpc;
	private String indPsp;
	private String desIndRpc;
	private String desCausasAnalisis;
	private String claseD;
	private String fraccionD;
	private String primaD;
	private String claseR;
	private String fraccionR;
	private String primaR;
	private String folioResolucion;
	private String cveCiz;
	private String indModAut;
	private String desTipoTramite;
	private String digVer;
	private String desComentario;
	private String contModClem;
	
	
	//Propiedades Datamart
	
	private String cveMunicio;
	private String estatus;
	private String fecExtraccion;
	private String fecMovimiento;
	private String fecRevision;
	private String fecRegistro;
	private String sistemaOrigen;
	private String tipoMovimiento;	
	private String tipoTramite;
	
	
	public String getDesIndRpc() {
		return desIndRpc;
	}
	public void setDesIndRpc(String desIndRpc) {
		this.desIndRpc = desIndRpc;
	}
	public String getCveCiz() {
		return cveCiz;
	}
	public void setCveCiz(String cveCiz) {
		this.cveCiz = cveCiz;
	}
	public String getCveIdTipoPersona() {
		return cveIdTipoPersona;
	}
	public void setCveIdTipoPersona(String cveIdTipoPersona) {
		this.cveIdTipoPersona = cveIdTipoPersona;
	}
	public String getCveIdTipoRegistro() {
		return cveIdTipoRegistro;
	}
	public void setCveIdTipoRegistro(String cveIdTipoRegistro) {
		this.cveIdTipoRegistro = cveIdTipoRegistro;
	}
	public String getCveIdEstatus() {
		return cveIdEstatus;
	}
	public void setCveIdEstatus(String cveIdEstatus) {
		this.cveIdEstatus = cveIdEstatus;
	}
	public String getCveIdAnalisis() {
		return cveIdAnalisis;
	}
	public void setCveIdAnalisis(String cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}
	public String getRegPatron() {
		return regPatron;
	}
	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}
	public String getCveIdDelegacion() {
		return cveIdDelegacion;
	}
	public void setCveIdDelegacion(String cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	public String getDelegDesc() {
		return delegDesc;
	}
	public void setDelegDesc(String delegDesc) {
		this.delegDesc = delegDesc;
	}
	public String getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(String cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	public String getSdelegDesc() {
		return sdelegDesc;
	}
	public void setSdelegDesc(String sdelegDesc) {
		this.sdelegDesc = sdelegDesc;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public Date getFecPresentacion() {
		return fecPresentacion;
	}
	public void setFecPresentacion(Date fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}
	public Date getFecAnalisis() {
		return fecAnalisis;
	}
	public void setFecAnalisis(Date fecAnalisis) {
		this.fecAnalisis = fecAnalisis;
	}
	public Date getFecAutorizacion() {
		return fecAutorizacion;
	}
	public void setFecAutorizacion(Date fecAutorizacion) {
		this.fecAutorizacion = fecAutorizacion;
	}
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getIndRpc() {
		return indRpc;
	}
	public void setIndRpc(String indRpc) {
		this.indRpc = indRpc;
	}
	public String getDesCausasAnalisis() {
		return desCausasAnalisis;
	}
	public void setDesCausasAnalisis(String desCausasAnalisis) {
		this.desCausasAnalisis = desCausasAnalisis;
	}
	public String getClaseD() {
		return claseD;
	}
	public void setClaseD(String claseD) {
		this.claseD = claseD;
	}
	public String getFraccionD() {
		return fraccionD;
	}
	public void setFraccionD(String fraccionD) {
		this.fraccionD = fraccionD;
	}
	public String getPrimaD() {
		return primaD;
	}
	public void setPrimaD(String primaD) {
		this.primaD = primaD;
	}
	public String getClaseR() {
		return claseR;
	}
	public void setClaseR(String claseR) {
		this.claseR = claseR;
	}
	public String getFraccionR() {
		return fraccionR;
	}
	public void setFraccionR(String fraccionR) {
		this.fraccionR = fraccionR;
	}
	public String getPrimaR() {
		return primaR;
	}
	public void setPrimaR(String primaR) {
		this.primaR = primaR;
	}
	public String getFolioResolucion() {
		return folioResolucion;
	}
	public void setFolioResolucion(String folioResolucion) {
		this.folioResolucion = folioResolucion;
	}
	public String getIndPsp() {
		return indPsp;
	}
	public void setIndPsp(String indPsp) {
		this.indPsp = indPsp;
	}
	public String getIndModAut() {
		return indModAut;
	}
	public void setIndModAut(String indModAut) {
		this.indModAut = indModAut;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDesTipoTramite() {
		return desTipoTramite;
	}
	public void setDesTipoTramite(String desTipoTramite) {
		this.desTipoTramite = desTipoTramite;
	}
	public String getDigVer() {
		return digVer;
	}
	public void setDigVer(String digVer) {
		this.digVer = digVer;
	}
	public String getRegPatronPadre() {
		return regPatronPadre;
	}
	public void setRegPatronPadre(String regPatronPadre) {
		this.regPatronPadre = regPatronPadre;
	}
	public String getDesComentario() {
		return desComentario;
	}
	public void setDesComentario(String desComentario) {
		this.desComentario = desComentario;
	}
	public String getRegPatronCompleto() {
		return regPatronCompleto;
	}
	public void setRegPatronCompleto(String regPatronCompleto) {
		this.regPatronCompleto = regPatronCompleto;
	}
	public String getContModClem() {
		return contModClem;
	}
	public void setContModClem(String contModClem) {
		this.contModClem = contModClem;
	}
	public String getCveMunicio() {
		return cveMunicio;
	}
	public void setCveMunicio(String cveMunicio) {
		this.cveMunicio = cveMunicio;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getFecExtraccion() {
		return fecExtraccion;
	}
	public void setFecExtraccion(String fecExtraccion) {
		this.fecExtraccion = fecExtraccion;
	}
	public String getFecMovimiento() {
		return fecMovimiento;
	}
	public void setFecMovimiento(String fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}
	public String getFecRegistro() {
		return fecRegistro;
	}
	public void setFecRegistro(String fecRegistro) {
		this.fecRegistro = fecRegistro;
	}
	public String getSistemaOrigen() {
		return sistemaOrigen;
	}
	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getFecRevision() {
		return fecRevision;
	}
	public void setFecRevision(String fecRevision) {
		this.fecRevision = fecRevision;
	}	 
	
	
	
}
