
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DatosClem extends AbstractModel {
	
    private Long cveIdClem;
    
    private String folioResolucion;
    
    private String desTitular;
    
    private String desSuplente;
    
    private String puesto;
    
    private String desMotivos;
    
    private String desLugarFechaExp;
    
    private Date fecRegistroAlta;
    private Date fecRegistroBaja;
    private Date fecRegistroActualizado;
    
    private byte[] refDocumento;
    
    private BigDecimal cveAnalisis;
    
    private BigDecimal cveTipoDoc;
    
    private BigDecimal cveArticulo155;
    
    private String cveArticulo26;
    private String cveArticulo20;
    private String cveArticulo28;
    
    private BigDecimal cveSolicitud;
    
    private BigDecimal indActivo;
    
    private Date ultFechaActualizacion;
    
    private BigDecimal cveTipoClem;
    
    private BigDecimal cveDelegacion;

    private BigDecimal cveSubdelegacion;
    
    private String cveDesDelegacion;
    private String cveDesSubdelegacion;

    private String errorClem;
        
    private String mostrarComboArt155;
    
    private String incisoArticulo155;     
            //Datos para Jasper de Articulo 155
	private String descDelegacion;	
	private String descSubDelegacion;	
	private String descFraccion115;
        
	private String psp15A;
	private String psp19;
	private String art20Clem;
	private String tipoTramite;
	private String fechaTramite;
	private String fecSurteEfecto;
	
	private String firmaAusencia;
	
	private List<ArticuloModel> articulos;
    
	public String getIncisoArticulo155() {
		return incisoArticulo155;
	}
	public void setIncisoArticulo155(String incisoArticulo155) {
		this.incisoArticulo155 = incisoArticulo155;
	}
	public String getMostrarComboArt155() {
		return mostrarComboArt155;
	}
	public void setMostrarComboArt155(String mostrarComboArt155) {
		this.mostrarComboArt155 = mostrarComboArt155;
	}
	public String getErrorClem() {
		return errorClem;
	}
	public void setErrorClem(String errorClem) {
		this.errorClem = errorClem;
	}
	public Long getCveIdClem() {
		return cveIdClem;
	}
	public void setCveIdClem(Long cveIdClem) {
		this.cveIdClem = cveIdClem;
	}
	public String getFolioResolucion() {
		return folioResolucion;
	}
	public void setFolioResolucion(String folioResolucion) {
		this.folioResolucion = folioResolucion;
	}
	public String getDesMotivos() {
		return desMotivos;
	}
	public void setDesMotivos(String desMotivos) {
		this.desMotivos = desMotivos;
	}
	public String getDesLugarFechaExp() {
		return desLugarFechaExp;
	}
	public void setDesLugarFechaExp(String desLugarFechaExp) {
		this.desLugarFechaExp = desLugarFechaExp;
	}
	
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
	
	public byte[] getRefDocumento() {
		return refDocumento;
	}
	public void setRefDocumento(byte[] refDocumento) {
		this.refDocumento = refDocumento;
	}
	public BigDecimal getCveAnalisis() {
		return cveAnalisis;
	}
	public void setCveAnalisis(BigDecimal cveAnalisis) {
		this.cveAnalisis = cveAnalisis;
	}
	public BigDecimal getCveTipoDoc() {
		return cveTipoDoc;
	}
	public void setCveTipoDoc(BigDecimal cveTipoDoc) {
		this.cveTipoDoc = cveTipoDoc;
	}
	public BigDecimal getCveArticulo155() {
		return cveArticulo155;
	}
	public void setCveArticulo155(BigDecimal cveArticulo155) {
		this.cveArticulo155 = cveArticulo155;
	}
	public String getCveArticulo26() {
		return cveArticulo26;
	}
	public void setCveArticulo26(String cveArticulo26) {
		this.cveArticulo26 = cveArticulo26;
	}

	public String getCveArticulo20() {
		return cveArticulo20;
	}
	public void setCveArticulo20(String cveArticulo20) {
		this.cveArticulo20 = cveArticulo20;
	}

	public String getCveArticulo28() {
		return cveArticulo28;
	}
	public void setCveArticulo28(String cveArticulo28) {
		this.cveArticulo28 = cveArticulo28;
	}
	
	public BigDecimal getIndActivo() {
		return indActivo;
	}
	public void setIndActivo(BigDecimal indActivo) {
		this.indActivo = indActivo;
	}
	public Date getUltFechaActualizacion() {
		return ultFechaActualizacion;
	}
	public void setUltFechaActualizacion(Date ultFechaActualizacion) {
		this.ultFechaActualizacion = ultFechaActualizacion;
	}
	public BigDecimal getCveSolicitud() {
		return cveSolicitud;
	}
	public void setCveSolicitud(BigDecimal cveSolicitud) {
		this.cveSolicitud = cveSolicitud;
	}	
	
	public BigDecimal getCveTipoClem() {
		return cveTipoClem;
	}
	public void setCveTipoClem(BigDecimal cveTipoClem) {
		this.cveTipoClem = cveTipoClem;
	}
	
	public BigDecimal getCveDelegacion() {
		return cveDelegacion;
	}
	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public BigDecimal getCveSubdelegacion() {
		return cveSubdelegacion;
	}
	public void setCveSubdelegacion(BigDecimal cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}
	
	
	public String getDescDelegacion() {
		return descDelegacion;
	}
	public void setDescDelegacion(String descDelegacion) {
		this.descDelegacion = descDelegacion;
	}
	public String getDescSubDelegacion() {
		return descSubDelegacion;
	}
	public void setDescSubDelegacion(String descSubDelegacion) {
		this.descSubDelegacion = descSubDelegacion;
	}
	public String getDescFraccion115() {
		return descFraccion115;
	}
	public void setDescFraccion115(String descFraccion115) {
		this.descFraccion115 = descFraccion115;
	}
		
	public String getPsp15A() {
		return psp15A;
	}
	public void setPsp15A(String psp15a) {
		psp15A = psp15a;
	}
	public String getPsp19() {
		return psp19;
	}
	public void setPsp19(String psp19) {
		this.psp19 = psp19;
	}
	
	public String getArt20Clem() {
		return art20Clem;
	}
	public void setArt20Clem(String art20Clem) {
		this.art20Clem = art20Clem;
	}
	
	public String getDesTitular() {
		return desTitular;
	}
	public void setDesTitular(String desTitular) {
		this.desTitular = desTitular;
	}
	
	public String getDesSuplente() {
		return desSuplente;
	}
	public void setDesSuplente(String desSuplente) {
		this.desSuplente = desSuplente;
	}
	public List<ArticuloModel> getArticulos() {
		return articulos;
	}
	public void setArticulos(List<ArticuloModel> articulos) {
		this.articulos = articulos;
	}
	
	public String getCveDesDelegacion() {
		return cveDesDelegacion;
	}
	public void setCveDesDelegacion(String cveDesDelegacion) {
		this.cveDesDelegacion = cveDesDelegacion;
	}
	public String getCveDesSubdelegacion() {
		return cveDesSubdelegacion;
	}
	public void setCveDesSubdelegacion(String cveDesSubdelegacion) {
		this.cveDesSubdelegacion = cveDesSubdelegacion;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getFechaTramite() {
		return fechaTramite;
	}
	public void setFechaTramite(String fechaTramite) {
		this.fechaTramite = fechaTramite;
	}
	public String getFecSurteEfecto() {
		return fecSurteEfecto;
	}
	public void setFecSurteEfecto(String fecSurteEfecto) {
		this.fecSurteEfecto = fecSurteEfecto;
	}
	public String getFirmaAusencia() {
		return firmaAusencia;
	}
	public void setFirmaAusencia(String firmaAusencia) {
		this.firmaAusencia = firmaAusencia;
	}	
	public String getPuesto() {
		return this.puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	
	@Override
	public String toString() {
		return "DatosClem [cveIdClem=" + cveIdClem + ", folioResolucion="
				+ folioResolucion + ", desTitular=" + desTitular
				+ ", desSuplente=" + desSuplente + ", puesto=" + puesto
				+ ", desMotivos=" + desMotivos + ", desLugarFechaExp="
				+ desLugarFechaExp + ", fecRegistroAlta=" + fecRegistroAlta
				+ ", fecRegistroBaja=" + fecRegistroBaja
				+ ", fecRegistroActualizado=" + fecRegistroActualizado
				+ ", refDocumento=" + Arrays.toString(refDocumento)
				+ ", cveAnalisis=" + cveAnalisis + ", cveTipoDoc=" + cveTipoDoc
				+ ", cveArticulo155=" + cveArticulo155 + ", cveArticulo26="
				+ cveArticulo26 + ", cveArticulo20=" + cveArticulo20
				+ ", cveArticulo28=" + cveArticulo28 + ", cveSolicitud="
				+ cveSolicitud + ", indActivo=" + indActivo
				+ ", ultFechaActualizacion=" + ultFechaActualizacion
				+ ", cveTipoClem=" + cveTipoClem + ", cveDelegacion="
				+ cveDelegacion + ", cveSubdelegacion=" + cveSubdelegacion
				+ ", cveDesDelegacion=" + cveDesDelegacion
				+ ", cveDesSubdelegacion=" + cveDesSubdelegacion
				+ ", errorClem=" + errorClem + ", mostrarComboArt155="
				+ mostrarComboArt155 + ", incisoArticulo155="
				+ incisoArticulo155 + ", descDelegacion=" + descDelegacion
				+ ", descSubDelegacion=" + descSubDelegacion
				+ ", descFraccion115=" + descFraccion115 + ", psp15A=" + psp15A
				+ ", psp19=" + psp19 + ", art20Clem=" + art20Clem
				+ ", tipoTramite=" + tipoTramite + ", fechaTramite="
				+ fechaTramite + ", fecSurteEfecto=" + fecSurteEfecto
				+ ", firmaAusencia=" + firmaAusencia + ", articulos="
				+ articulos + "]";
	}
}