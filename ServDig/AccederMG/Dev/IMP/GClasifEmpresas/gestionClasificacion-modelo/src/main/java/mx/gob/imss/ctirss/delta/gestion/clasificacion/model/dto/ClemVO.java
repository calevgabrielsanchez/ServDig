package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import java.io.Serializable;

public class ClemVO implements Serializable{

	@Override
	public String toString() {
		return "ClemVO [folioClem=" + folioClem + ", delegacion=" + delegacion
				+ ", subdelegacion=" + subdelegacion + ", razonSocial="
				+ razonSocial + ", domicilio=" + domicilio
				+ ", municipioDelegacion=" + municipioDelegacion
				+ ", regPatronal=" + regPatronal + ", fechaAviso=" + fechaAviso
				+ ", idDivisionPatron=" + idDivisionPatron + ", idGrupoPatron="
				+ idGrupoPatron + ", idFraccionPatron=" + idFraccionPatron
				+ ", denominacionFraccion=" + denominacionFraccion + ", clase="
				+ clase + ", prima=" + prima + ", fechaTramite=" + fechaTramite
				+ ", motivos=" + motivos + ", idDivisionPropuesta="
				+ idDivisionPropuesta + ", idFraccionPropuesta="
				+ idFraccionPropuesta + ", idGrupoPropuesta="
				+ idGrupoPropuesta + ", divisionPropuesta=" + divisionPropuesta
				+ ", grupoPropuesta=" + grupoPropuesta + ", clasePropuesta="
				+ clasePropuesta + ", primaPropuesta=" + primaPropuesta
				+ ", fraccionPropuesta=" + fraccionPropuesta + ", fraccion115="
				+ fraccion115 + ", incisio115=" + incisio115 + ", titular="
				+ titular + ", suplente=" + suplente + ", puesto=" + puesto
				+ ", lugarFechaExpedicion=" + lugarFechaExpedicion
				+ ", pspArt15A=" + pspArt15A + ", pspArt19=" + pspArt19
				+ ", fraccionArticulo20=" + fraccionArticulo20
				+ ", fraccionArticulo26=" + fraccionArticulo26
				+ ", fraccionArticulo28=" + fraccionArticulo28
				+ ", fechaSurteEfecto=" + fechaSurteEfecto + ", tipoPersona="
				+ tipoPersona + ", idDelegacion=" + idDelegacion
				+ ", idSubdelegacion=" + idSubdelegacion + ", idClem=" + idClem
				+ ", tipoTramite=" + tipoTramite + "cveDelegacionRimss="+ cveDelegacionRimss + "cveSubdelegacionRimss="+ cveSubdelegacionRimss 
				+"tipo="+ tipo +"primaSugerida="+ primaSugerida +"cveIdPatronSujetoObligado="+ cveIdPatronSujetoObligado +"]";
	}


	private static final long serialVersionUID = 1L;

	private String folioClem;
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
	private String fraccion115;
	private String incisio115;
	private String titular;
	private String suplente;
	private String puesto;
	private String lugarFechaExpedicion;
	private String pspArt15A;
	private String pspArt19;
	private String fraccionArticulo20;
	private String fraccionArticulo26;
	private String fraccionArticulo28;
	private String fechaSurteEfecto;
	private String tipoPersona;
	
	private String idDelegacion;
	private String idSubdelegacion;
	private String idClem;
	private String tipoTramite;
	private String cveSubdelegacionRimss;
	private String cveDelegacionRimss;
	private String tipo;
	private String primaSugerida;
   //Para consultar domicilio Migrado
	private String cveIdPatronSujetoObligado;

	


	public ClemVO getClemVO(){return this;}

	
	public ClemVO() { }

	
	public ClemVO(String folioClem, String delegacion, String subdelegacion,
			String razonSocial, String domicilio, String municipioDelegacion,
			String regPatronal, String fechaAviso, String idDivisionPatron,
			String idGrupoPatron, String idFraccionPatron,
			String denominacionFraccion, String clase, String prima,
			String fechaTramite, String motivos, String idDivisionPropuesta,
			String idFraccionPropuesta, String idGrupoPropuesta,
			String divisionPropuesta, String grupoPropuesta,
			String clasePropuesta, String primaPropuesta,
			String fraccionPropuesta, String fraccion115, String incisio115,
			String titular, String suplente, String puesto,
			String lugarFechaExpedicion, String pspArt15A, String pspArt19,
			String fraccionArticulo20, String fraccionArticulo26,
			String fraccionArticulo28, String fechaSurteEfecto,
			String tipoPersona, String cveSubdelegacionRimss, String cveDelegacionRimss, String tipo, String primaSugerida, String cveIdPatronSujetoObligado) {
		super();
		this.folioClem = folioClem;
		this.delegacion = delegacion;
		this.subdelegacion = subdelegacion;
		this.razonSocial = razonSocial;
		this.domicilio = domicilio;
		this.municipioDelegacion = municipioDelegacion;
		this.regPatronal = regPatronal;
		this.fechaAviso = fechaAviso;
		this.idDivisionPatron = idDivisionPatron;
		this.idGrupoPatron = idGrupoPatron;
		this.idFraccionPatron = idFraccionPatron;
		this.denominacionFraccion = denominacionFraccion;
		this.clase = clase;
		this.prima = prima;
		this.fechaTramite = fechaTramite;
		this.motivos = motivos;
		this.idDivisionPropuesta = idDivisionPropuesta;
		this.idFraccionPropuesta = idFraccionPropuesta;
		this.idGrupoPropuesta = idGrupoPropuesta;
		this.divisionPropuesta = divisionPropuesta;
		this.grupoPropuesta = grupoPropuesta;
		this.clasePropuesta = clasePropuesta;
		this.primaPropuesta = primaPropuesta;
		this.fraccionPropuesta = fraccionPropuesta;
		this.fraccion115 = fraccion115;
		this.incisio115 = incisio115;
		this.titular = titular;
		this.suplente = suplente;
		this.puesto = puesto;
		this.lugarFechaExpedicion = lugarFechaExpedicion;
		this.pspArt15A = pspArt15A;
		this.pspArt19 = pspArt19;
		this.fraccionArticulo20 = fraccionArticulo20;
		this.fraccionArticulo26 = fraccionArticulo26;
		this.fraccionArticulo28 = fraccionArticulo28;
		this.fechaSurteEfecto = fechaSurteEfecto;
		this.tipoPersona = tipoPersona;
		this.cveSubdelegacionRimss=cveSubdelegacionRimss;
		this.cveDelegacionRimss=cveDelegacionRimss;
		this.tipo=tipo;
		this.primaSugerida=primaSugerida;
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}


	


	public String getFolioClem() {
		return folioClem;
	}


	public void setFolioClem(String folioClem) {
		this.folioClem = folioClem;
	}


	public String getDelegacion() {
		return delegacion;
	}


	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}


	public String getSubdelegacion() {
		return subdelegacion;
	}


	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}


	public String getRazonSocial() {
		return razonSocial;
	}


	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}


	public String getDomicilio() {
		return domicilio;
	}


	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}


	public String getMunicipioDelegacion() {
		return municipioDelegacion;
	}


	public void setMunicipioDelegacion(String municipioDelegacion) {
		this.municipioDelegacion = municipioDelegacion;
	}


	public String getRegPatronal() {
		return regPatronal;
	}


	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}


	public String getFechaAviso() {
		return fechaAviso;
	}


	public void setFechaAviso(String fechaAviso) {
		this.fechaAviso = fechaAviso;
	}


	public String getIdDivisionPatron() {
		return idDivisionPatron;
	}


	public void setIdDivisionPatron(String idDivisionPatron) {
		this.idDivisionPatron = idDivisionPatron;
	}


	public String getIdGrupoPatron() {
		return idGrupoPatron;
	}


	public void setIdGrupoPatron(String idGrupoPatron) {
		this.idGrupoPatron = idGrupoPatron;
	}


	public String getIdFraccionPatron() {
		return idFraccionPatron;
	}


	public void setIdFraccionPatron(String idFraccionPatron) {
		this.idFraccionPatron = idFraccionPatron;
	}


	public String getDenominacionFraccion() {
		return denominacionFraccion;
	}


	public void setDenominacionFraccion(String denominacionFraccion) {
		this.denominacionFraccion = denominacionFraccion;
	}


	public String getClase() {
		return clase;
	}


	public void setClase(String clase) {
		this.clase = clase;
	}


	public String getPrima() {
		return prima;
	}


	public void setPrima(String prima) {
		this.prima = prima;
	}


	public String getFechaTramite() {
		return fechaTramite;
	}


	public void setFechaTramite(String fechaTramite) {
		this.fechaTramite = fechaTramite;
	}


	public String getMotivos() {
		return motivos;
	}


	public void setMotivos(String motivos) {
		this.motivos = motivos;
	}


	public String getIdDivisionPropuesta() {
		return idDivisionPropuesta;
	}


	public void setIdDivisionPropuesta(String idDivisionPropuesta) {
		this.idDivisionPropuesta = idDivisionPropuesta;
	}


	public String getIdFraccionPropuesta() {
		return idFraccionPropuesta;
	}


	public void setIdFraccionPropuesta(String idFraccionPropuesta) {
		this.idFraccionPropuesta = idFraccionPropuesta;
	}


	public String getIdGrupoPropuesta() {
		return idGrupoPropuesta;
	}


	public void setIdGrupoPropuesta(String idGrupoPropuesta) {
		this.idGrupoPropuesta = idGrupoPropuesta;
	}


	public String getDivisionPropuesta() {
		return divisionPropuesta;
	}


	public void setDivisionPropuesta(String divisionPropuesta) {
		this.divisionPropuesta = divisionPropuesta;
	}


	public String getGrupoPropuesta() {
		return grupoPropuesta;
	}


	public void setGrupoPropuesta(String grupoPropuesta) {
		this.grupoPropuesta = grupoPropuesta;
	}


	public String getClasePropuesta() {
		return clasePropuesta;
	}


	public void setClasePropuesta(String clasePropuesta) {
		this.clasePropuesta = clasePropuesta;
	}


	public String getPrimaPropuesta() {
		return primaPropuesta;
	}


	public void setPrimaPropuesta(String primaPropuesta) {
		this.primaPropuesta = primaPropuesta;
	}


	public String getFraccionPropuesta() {
		return fraccionPropuesta;
	}


	public void setFraccionPropuesta(String fraccionPropuesta) {
		this.fraccionPropuesta = fraccionPropuesta;
	}


	public String getFraccion115() {
		return fraccion115;
	}


	public void setFraccion115(String fraccion115) {
		this.fraccion115 = fraccion115;
	}


	public String getIncisio115() {
		return incisio115;
	}


	public void setIncisio115(String incisio115) {
		this.incisio115 = incisio115;
	}


	public String getTitular() {
		return titular;
	}


	public void setTitular(String titular) {
		this.titular = titular;
	}


	public String getSuplente() {
		return suplente;
	}


	public void setSuplente(String suplente) {
		this.suplente = suplente;
	}


	public String getLugarFechaExpedicion() {
		return lugarFechaExpedicion;
	}


	public void setLugarFechaExpedicion(String lugarFechaExpedicion) {
		this.lugarFechaExpedicion = lugarFechaExpedicion;
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


	public String getFraccionArticulo20() {
		return fraccionArticulo20;
	}


	public void setFraccionArticulo20(String fraccionArticulo20) {
		this.fraccionArticulo20 = fraccionArticulo20;
	}


	public String getFraccionArticulo26() {
		return fraccionArticulo26;
	}


	public void setFraccionArticulo26(String fraccionArticulo26) {
		this.fraccionArticulo26 = fraccionArticulo26;
	}


	public String getFraccionArticulo28() {
		return fraccionArticulo28;
	}


	public void setFraccionArticulo28(String fraccionArticulo28) {
		this.fraccionArticulo28 = fraccionArticulo28;
	}


	public String getFechaSurteEfecto() {
		return fechaSurteEfecto;
	}


	public void setFechaSurteEfecto(String fechaSurteEfecto) {
		this.fechaSurteEfecto = fechaSurteEfecto;
	}


	public String getTipoPersona() {
		return tipoPersona;
	}


	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}


	public String getPuesto() {
		return puesto;
	}


	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}


	public String getIdDelegacion() {
		return idDelegacion;
	}


	public void setIdDelegacion(String idDelegacion) {
		this.idDelegacion = idDelegacion;
	}


	public String getIdSubdelegacion() {
		return idSubdelegacion;
	}


	public void setIdSubdelegacion(String idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}


	public String getIdClem() {
		return idClem;
	}


	public void setIdClem(String idClem) {
		this.idClem = idClem;
	}


	public String getTipoTramite() {
		return tipoTramite;
	}


	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}


	public String getCveSubdelegacionRimss() {
		return cveSubdelegacionRimss;
	}


	public void setCveSubdelegacionRimss(String cveSubdelegacionRimss) {
		this.cveSubdelegacionRimss = cveSubdelegacionRimss;
	}


	public String getCveDelegacionRimss() {
		return cveDelegacionRimss;
	}


	public void setCveDelegacionRimss(String cveDelegacionRimms) {
		this.cveDelegacionRimss = cveDelegacionRimms;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	public String getPrimaSugerida() {
		return primaSugerida;
	}
	
	public String getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}


	public void setCveIdPatronSujetoObligado(String cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

}
