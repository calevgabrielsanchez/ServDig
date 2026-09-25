/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:ReporteClemBean.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.model.clem
 *  @Fecha:14/06/2012
 */

package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Héctor Lara Andrés
 *
 */
public class ReporteClemBean extends AbstractModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6360560864927879625L;
	private String ruta;
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	private String cveIdClem;
	private String idAnalisis;
	private String folio;
	private String cveIdDelegacion;
	private String cveIdSubdelegacion;
	private String delegacion;
	private String subdelegacion;
	private String razonSocial;
	private String domicilio;
	private String municipioDelegacion;
	private String regPatronal;
	private String fechaAviso;
	private String idDivisionPatron;
	private String idGrupoPatron;
	private String idFraccionPatron;
	private String denominacionFraccion;
	private String clase;
	private String prima;
	private String primaSugerida;
	private String fechaTramite;
	private String motivos;
	private String idDivisionPropuesta;
	private String idFraccionPropuesta;
	private String idGrupoPropuesta;
	private String divisionPropuesta;
	private String grupoPropuesta;
	private String clasePropuesta;
	private String primaPropuesta;
	private String fraccionPropuesta;
	private String fraccionArticulo26;
	private String fraccion115;
	private String incisio115;
	private String fraccion155;
	private String incisio155;
	private String titular;
	private String suplente;
	private String puesto;
	private String lugarFechaExpedicion;
	
	private String psp;
	private String pspArt15A;
	private String pspArt19;
	private String fraccionArticulo20;
	private String fraccionArticulo28;
	private String tipoTramite;
	private String fechaSurteEfecto;
	private String tipoPersona; 
	private Long cveIdTipoCausa;
	
	private String mostrarComboArt155;
	private String cveTipoClem;
	private String cveArticulo155;
	private String cveSolicitud;
	private String fechaAutorizacion;
	private String botonClem;
	
	private String insMod;
	
	private String firmaAusencia;
	
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String getFechaSurteEfecto() {
		return fechaSurteEfecto;
	}
	public void setFechaSurteEfecto(String fechaSurteEfecto) {
		this.fechaSurteEfecto = fechaSurteEfecto;
	}
	/**
	 * @return the cveIdClem
	 */
	public String getCveIdClem() {
		return cveIdClem;
	}
	/**
	 * @param cveIdClem the cveIdClem to set
	 */
	public void setCveIdClem(String cveIdClem) {
		this.cveIdClem = cveIdClem;
	}
	/**
	 * @return the idAnalisis
	 */
	public String getIdAnalisis() {
		return idAnalisis;
	}
	/**
	 * @param idAnalisis the idAnalisis to set
	 */
	public void setIdAnalisis(String idAnalisis) {
		this.idAnalisis = idAnalisis;
	}
	
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @return the delegacion
	 */
	public String getDelegacion() {
		return delegacion;
	}
	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	/**
	 * @return the subdelegacion
	 */
	public String getSubdelegacion() {
		return subdelegacion;
	}
	/**
	 * @param subdelegacion the subdelegacion to set
	 */
	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}
	/**
	 * @param domicilio the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	/**
	 * @return the municipioDelegacion
	 */
	public String getMunicipioDelegacion() {
		return municipioDelegacion;
	}
	/**
	 * @param municipioDelegacion the municipioDelegacion to set
	 */
	public void setMunicipioDelegacion(String municipioDelegacion) {
		this.municipioDelegacion = municipioDelegacion;
	}
	/**
	 * @return the regPatronal
	 */
	public String getRegPatronal() {
		return regPatronal;
	}
	/**
	 * @param regPatronal the regPatronal to set
	 */
	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}
	/**
	 * @return the fechaAviso
	 */
	public String getFechaAviso() {
		return fechaAviso;
	}
	/**
	 * @param fechaAviso the fechaAviso to set
	 */
	public void setFechaAviso(String fechaAviso) {
		this.fechaAviso = fechaAviso;
	}
	/**
	 * @return the idDivisionPatron
	 */
	public String getIdDivisionPatron() {
		return idDivisionPatron;
	}
	/**
	 * @param idDivisionPatron the idDivisionPatron to set
	 */
	public void setIdDivisionPatron(String idDivisionPatron) {
		this.idDivisionPatron = idDivisionPatron;
	}
	/**
	 * @return the idGrupoPatron
	 */
	public String getIdGrupoPatron() {
		return idGrupoPatron;
	}
	/**
	 * @param idGrupoPatron the idGrupoPatron to set
	 */
	public void setIdGrupoPatron(String idGrupoPatron) {
		this.idGrupoPatron = idGrupoPatron;
	}
	/**
	 * @return the idFraccionPatron
	 */
	public String getIdFraccionPatron() {
		return idFraccionPatron;
	}
	/**
	 * @param idFraccionPatron the idFraccionPatron to set
	 */
	public void setIdFraccionPatron(String idFraccionPatron) {
		this.idFraccionPatron = idFraccionPatron;
	}
	/**
	 * @return the denominacionFraccion
	 */
	public String getDenominacionFraccion() {
		return denominacionFraccion;
	}
	/**
	 * @param denominacionFraccion the denominacionFraccion to set
	 */
	public void setDenominacionFraccion(String denominacionFraccion) {
		this.denominacionFraccion = denominacionFraccion;
	}
	/**
	 * @return the clase
	 */
	public String getClase() {
		return clase;
	}
	/**
	 * @param clase the clase to set
	 */
	public void setClase(String clase) {
		this.clase = clase;
	}
	/**
	 * @return the prima
	 */
	public String getPrima() {
		return prima;
	}
	/**
	 * @param prima the prima to set
	 */
	public void setPrima(String prima) {
		this.prima = prima;
	}
	
	
	/**
	 * @return the primaSugerida
	 */
	public String getPrimaSugerida() {
		return primaSugerida;
	}
	/**
	 * @param primaSugerida the primaSugerida to set
	 */
	public void setPrimaSugerida(String primaSugerida) {
		this.primaSugerida = primaSugerida;
	}
	/**
	 * @return the fechaTramite
	 */
	public String getFechaTramite() {
		return fechaTramite;
	}
	/**
	 * @param fechaTramite the fechaTramite to set
	 */
	public void setFechaTramite(String fechaTramite) {
		this.fechaTramite = fechaTramite;
	}
	/**
	 * @return the motivos
	 */
	public String getMotivos() {
		return motivos;
	}
	/**
	 * @param motivos the motivos to set
	 */
	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}
	/**
	 * @return the idDivisionPropuesta
	 */
	public String getIdDivisionPropuesta() {
		return idDivisionPropuesta;
	}
	/**
	 * @param idDivisionPropuesta the idDivisionPropuesta to set
	 */
	public void setIdDivisionPropuesta(String idDivisionPropuesta) {
		this.idDivisionPropuesta = idDivisionPropuesta;
	}
	/**
	 * @return the idFraccionPropuesta
	 */
	public String getIdFraccionPropuesta() {
		return idFraccionPropuesta;
	}
	/**
	 * @param idFraccionPropuesta the idFraccionPropuesta to set
	 */
	public void setIdFraccionPropuesta(String idFraccionPropuesta) {
		this.idFraccionPropuesta = idFraccionPropuesta;
	}
	/**
	 * @return the idGrupoPropuesta
	 */
	public String getIdGrupoPropuesta() {
		return idGrupoPropuesta;
	}
	/**
	 * @param idGrupoPropuesta the idGrupoPropuesta to set
	 */
	public void setIdGrupoPropuesta(String idGrupoPropuesta) {
		this.idGrupoPropuesta = idGrupoPropuesta;
	}
	/**
	 * @return the divisionPropuesta
	 */
	public String getDivisionPropuesta() {
		return divisionPropuesta;
	}
	/**
	 * @param divisionPropuesta the divisionPropuesta to set
	 */
	public void setDivisionPropuesta(String divisionPropuesta) {
		this.divisionPropuesta = divisionPropuesta;
	}
	/**
	 * @return the grupoPropuesta
	 */
	public String getGrupoPropuesta() {
		return grupoPropuesta;
	}
	/**
	 * @param grupoPropuesta the grupoPropuesta to set
	 */
	public void setGrupoPropuesta(String grupoPropuesta) {
		this.grupoPropuesta = grupoPropuesta;
	}
	/**
	 * @return the fraccionPropuesta
	 */
	public String getFraccionPropuesta() {
		return fraccionPropuesta;
	}
	/**
	 * @param fraccionPropuesta the fraccionPropuesta to set
	 */
	public void setFraccionPropuesta(String fraccionPropuesta) {
		this.fraccionPropuesta = fraccionPropuesta;
	}
	/**
	 * @return the clasePropuesta
	 */
	public String getClasePropuesta() {
		return clasePropuesta;
	}
	/**
	 * @param clasePropuesta the clasePropuesta to set
	 */
	public void setClasePropuesta(String clasePropuesta) {
		this.clasePropuesta = clasePropuesta;
	}
	/**
	 * @return the primaPropuesta
	 */
	public String getPrimaPropuesta() {
		return primaPropuesta;
	}
	/**
	 * @param primaPropuesta the primaPropuesta to set
	 */
	public void setPrimaPropuesta(String primaPropuesta) {
		this.primaPropuesta = primaPropuesta;
	}
	/**
	 * @return the fraccionArticulo26
	 */
	public String getFraccionArticulo26() {
		return fraccionArticulo26;
	}
	/**
	 * @param fraccionArticulo26 the fraccionArticulo26 to set
	 */
	public void setFraccionArticulo26(String fraccionArticulo26) {
		this.fraccionArticulo26 = fraccionArticulo26;
	}
	/**
	 * @return the fraccion115
	 */
	public String getFraccion115() {
		return fraccion115;
	}
	/**
	 * @param fraccion115 the fraccion115 to set
	 */
	public void setFraccion115(String fraccion115) {
		this.fraccion115 = fraccion115;
	}
	/**
	 * @return the incisio115
	 */
	public String getIncisio115() {
		return incisio115;
	}
	/**
	 * @param incisio115 the incisio115 to set
	 */
	public void setIncisio115(String incisio115) {
		this.incisio115 = incisio115;
	}
	/**
	 * @return the titular
	 */
	public String getTitular() {
		return titular;
	}
	/**
	 * @param titular the titular to set
	 */
	public void setTitular(String titular) {
		this.titular = titular;
	}
	/**
	 * @return the suplente
	 */
	public String getSuplente() {
		return suplente;
	}
	/**
	 * @param suplente the suplente to set
	 */
	public void setSuplente(String suplente) {
		this.suplente = suplente;
	}
	/**
	 * @return the lugarFechaExpedicion
	 */
	public String getLugarFechaExpedicion() {
		return lugarFechaExpedicion;
	}
	/**
	 * @param lugarFechaExpedicion the lugarFechaExpedicion to set
	 */
	public void setLugarFechaExpedicion(String lugarFechaExpedicion) {
		this.lugarFechaExpedicion = lugarFechaExpedicion;
	}
	
	public String getPsp() {
		return psp;
	}
	public void setPsp(String psp) {
		this.psp = psp;
	}
	public String getFraccionArticulo20() {
		return fraccionArticulo20;
	}
	public void setFraccionArticulo20(String fraccionArticulo20) {
		this.fraccionArticulo20 = fraccionArticulo20;
	}
	public String getFraccionArticulo28() {
		return fraccionArticulo28;
	}
	public void setFraccionArticulo28(String fraccionArticulo28) {
		this.fraccionArticulo28 = fraccionArticulo28;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getPspArt15A() {
		return pspArt15A;
	}
	public void setPspArt15A(String pspArt15A) {
		this.pspArt15A = pspArt15A;
	}
	public String getPspArt19() {
		return pspArt19;
	}
	public void setPspArt19(String pspArt19) {
		this.pspArt19 = pspArt19;
	}
	public Long getCveIdTipoCausa() {
		return cveIdTipoCausa;
	}
	public void setCveIdTipoCausa(Long cveIdTipoCausa) {
		this.cveIdTipoCausa = cveIdTipoCausa;
	}
	public String getCveIdDelegacion() {
		return cveIdDelegacion;
	}
	public void setCveIdDelegacion(String cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	public String getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(String cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	public String getFraccion155() {
		return fraccion155;
	}
	public void setFraccion155(String fraccion155) {
		this.fraccion155 = fraccion155;
	}
	public String getIncisio155() {
		return incisio155;
	}
	public void setIncisio155(String incisio155) {
		this.incisio155 = incisio155;
	}
	public String getMostrarComboArt155() {
		return mostrarComboArt155;
	}
	public void setMostrarComboArt155(String mostrarComboArt155) {
		this.mostrarComboArt155 = mostrarComboArt155;
	}
	public String getCveTipoClem() {
		return cveTipoClem;
	}
	public void setCveTipoClem(String cveTipoClem) {
		this.cveTipoClem = cveTipoClem;
	}
	public String getCveArticulo155() {
		return cveArticulo155;
	}
	public void setCveArticulo155(String cveArticulo155) {
		this.cveArticulo155 = cveArticulo155;
	}
	public String getCveSolicitud() {
		return cveSolicitud;
	}
	public void setCveSolicitud(String cveSolicitud) {
		this.cveSolicitud = cveSolicitud;
	}
	public String getFechaAutorizacion() {
		return fechaAutorizacion;
	}
	public void setFechaAutorizacion(String fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}
	public String getBotonClem() {
		return botonClem;
	}
	public void setBotonClem(String botonClem) {
		this.botonClem = botonClem;
	}
	public String getInsMod() {
		return insMod;
	}
	public void setInsMod(String insMod) {
		this.insMod = insMod;
	}
	public String getFirmaAusencia() {
		return firmaAusencia;
	}
	public void setFirmaAusencia(String firmaAusencia) {
		this.firmaAusencia = firmaAusencia;
	}
	
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	
	@Override
	public String toString() {
		return "ReporteClemBean [ruta=" + ruta + ", cveIdClem=" + cveIdClem
				+ ", idAnalisis=" + idAnalisis + ", folio=" + folio
				+ ", cveIdDelegacion=" + cveIdDelegacion
				+ ", cveIdSubdelegacion=" + cveIdSubdelegacion
				+ ", delegacion=" + delegacion + ", subdelegacion="
				+ subdelegacion + ", razonSocial=" + razonSocial
				+ ", domicilio=" + domicilio + ", municipioDelegacion="
				+ municipioDelegacion + ", regPatronal=" + regPatronal
				+ ", fechaAviso=" + fechaAviso + ", idDivisionPatron="
				+ idDivisionPatron + ", idGrupoPatron=" + idGrupoPatron
				+ ", idFraccionPatron=" + idFraccionPatron
				+ ", denominacionFraccion=" + denominacionFraccion + ", clase="
				+ clase + ", prima=" + prima + ", fechaTramite=" + fechaTramite
				+ ", motivos=" + motivos + ", idDivisionPropuesta="
				+ idDivisionPropuesta + ", idFraccionPropuesta="
				+ idFraccionPropuesta + ", idGrupoPropuesta="
				+ idGrupoPropuesta + ", divisionPropuesta=" + divisionPropuesta
				+ ", grupoPropuesta=" + grupoPropuesta + ", clasePropuesta="
				+ clasePropuesta + ", primaPropuesta=" + primaPropuesta
				+ ", fraccionPropuesta=" + fraccionPropuesta
				+ ", fraccionArticulo26=" + fraccionArticulo26
				+ ", fraccion115=" + fraccion115 + ", incisio115=" + incisio115
				+ ", fraccion155=" + fraccion155 + ", incisio155=" + incisio155
				+ ", titular=" + titular + ", suplente=" + suplente
				+ ", puesto=" + puesto + ", lugarFechaExpedicion="
				+ lugarFechaExpedicion + ", psp=" + psp + ", pspArt15A="
				+ pspArt15A + ", pspArt19=" + pspArt19
				+ ", fraccionArticulo20=" + fraccionArticulo20
				+ ", fraccionArticulo28=" + fraccionArticulo28
				+ ", tipoTramite=" + tipoTramite + ", fechaSurteEfecto="
				+ fechaSurteEfecto + ", tipoPersona=" + tipoPersona
				+ ", cveIdTipoCausa=" + cveIdTipoCausa
				+ ", mostrarComboArt155=" + mostrarComboArt155
				+ ", cveTipoClem=" + cveTipoClem + ", cveArticulo155="
				+ cveArticulo155 + ", cveSolicitud=" + cveSolicitud
				+ ", fechaAutorizacion=" + fechaAutorizacion + ", botonClem="
				+ botonClem + ", insMod=" + insMod + ", firmaAusencia="
				+ firmaAusencia + "]";
	}
}