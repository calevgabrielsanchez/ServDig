package mx.gob.imss.ctirss.delta.cobranza.modelo;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Patron extends AbstractModel
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2317985183671518985L;

	public Patron(){
	}
	
	String cp; //CP    12        CHAR (5 Byte)        None           
	String cveActEco; //CVE_ACT_ECO    4        NUMBER (4)        None           
	String cveDelegacion; //CVE_DELEGACION    5        NUMBER (2)        None           
	String cveDelegacionAnt; //CVE_DELEGACION_ANT    23        NUMBER (2)        None           
	String cveGrupo; //CVE_GRUPO    19        NUMBER (3)        None           
	String cveModalidad; //CVE_MODALIDAD    2        CHAR (2 Byte)        None           
	String cveMovtoPatronal; //CVE_MOVTO_PATRONAL    18        NUMBER (2)        None           
	String cveMunicipioImss; //CVE_MUNICIPIO_IMSS    17        CHAR (3 Byte)        None           
	String cvePatron; //CVE_PATRON    1        CHAR (8 Byte)        None           
	String cveSectorNotificacion; //CVE_SECTOR_NOTIFICACION    3        CHAR (2 Byte)        None           
	String cveSubdelegacion; //CVE_SUBDELEGACION    6        NUMBER (2)        None           
	String cveSubdelegacionAnt; //CVE_SUBDELEGACION_ANT    24        NUMBER (2)        None           
	String cveTipoEmpresa; //CVE_TIPO_EMPRESA    7        NUMBER (1)        None           
	String cveUsuario; //CVE_USUARIO    20        CHAR (8 Byte)        None           
	String domicilio; //DOMICILIO    10        CHAR (55 Byte)        None           
	String fecCaptura; //FEC_CAPTURA    21        DATE        None           
	String fecCarga; //FEC_CARGA    25        DATE        None           
	String fecMovto; //FEC_MOVTO    14        DATE        None           
	String horaCaptura; //HORA_CAPTURA    22        CHAR (8 Byte)        None           
	String localidad; //LOCALIDAD    11        CHAR (50 Byte)        None           
	String numTrabaja; //NUM_TRABAJA    16        NUMBER (6)        None           
	String primaRT; //PRIMA_RT    15        NUMBER (8,5)        None           
	String razonSocial; //RAZON_SOCIAL    9        CHAR (55 Byte)        None           
	String rfc; //RFC    8        CHAR (13 Byte)        None           
	String tipoAportacion; //TIPO_APORTACION    13        NUMBER (1)        None            
	String tipoAportacionDesc; 
	
	String descDelegacion;
	String descSubdelegacion;
	String descMovPatronal;
	String descActEconomica;
	String descTipoPatron;
	String cveTipoPatron;
	String regPatronal; //cvePatron + cveModalidad
	List<String> regsPatronales; //Lista para corporativos y RPU
	
	public String getTipoAportacionDesc() {
		return tipoAportacionDesc;
	}
	public void setTipoAportacionDesc(String tipoAportacionDesc) {
		this.tipoAportacionDesc = tipoAportacionDesc;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getCveActEco() {
		return cveActEco;
	}
	public void setCveActEco(String cveActEco) {
		this.cveActEco = cveActEco;
	}
	public String getCveDelegacion() {
		return cveDelegacion;
	}
	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public String getCveDelegacionAnt() {
		return cveDelegacionAnt;
	}
	public void setCveDelegacionAnt(String cveDelegacionAnt) {
		this.cveDelegacionAnt = cveDelegacionAnt;
	}
	public String getCveGrupo() {
		return cveGrupo;
	}
	public void setCveGrupo(String cveGrupo) {
		this.cveGrupo = cveGrupo;
	}
	public String getCveModalidad() {
		return cveModalidad;
	}
	public void setCveModalidad(String cveModalidad) {
		this.cveModalidad = cveModalidad;
	}
	public String getCveMovtoPatronal() {
		return cveMovtoPatronal;
	}
	public void setCveMovtoPatronal(String cveMovtoPatronal) {
		this.cveMovtoPatronal = cveMovtoPatronal;
	}
	public String getCveMunicipioImss() {
		return cveMunicipioImss;
	}
	public void setCveMunicipioImss(String cveMunicipioImss) {
		this.cveMunicipioImss = cveMunicipioImss;
	}
	public String getCvePatron() {
		return cvePatron;
	}
	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}
	public String getCveSectorNotificacion() {
		return cveSectorNotificacion;
	}
	public void setCveSectorNotificacion(String cveSectorNotificacion) {
		this.cveSectorNotificacion = cveSectorNotificacion;
	}
	public String getCveSubdelegacion() {
		return cveSubdelegacion;
	}
	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}
	public String getCveSubdelegacionAnt() {
		return cveSubdelegacionAnt;
	}
	public void setCveSubdelegacionAnt(String cveSubdelegacionAnt) {
		this.cveSubdelegacionAnt = cveSubdelegacionAnt;
	}
	public String getCveTipoEmpresa() {
		return cveTipoEmpresa;
	}
	public void setCveTipoEmpresa(String cveTipoEmpresa) {
		this.cveTipoEmpresa = cveTipoEmpresa;
	}
	public String getCveUsuario() {
		return cveUsuario;
	}
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getFecCaptura() {
		return fecCaptura;
	}
	public void setFecCaptura(String fecCaptura) {
		this.fecCaptura = fecCaptura;
	}
	public String getFecCarga() {
		return fecCarga;
	}
	public void setFecCarga(String fecCarga) {
		this.fecCarga = fecCarga;
	}
	public String getFecMovto() {
		return fecMovto;
	}
	public void setFecMovto(String fecMovto) {
		this.fecMovto = fecMovto;
	}
	public String getHoraCaptura() {
		return horaCaptura;
	}
	public void setHoraCaptura(String horaCaptura) {
		this.horaCaptura = horaCaptura;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getNumTrabaja() {
		return numTrabaja;
	}
	public void setNumTrabaja(String numTrabaja) {
		this.numTrabaja = numTrabaja;
	}
	public String getPrimaRT() {
		return primaRT;
	}
	public void setPrimaRT(String primaRT) {
		this.primaRT = primaRT;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getTipoAportacion() {
		return tipoAportacion;
	}
	public void setTipoAportacion(String tipoAportacion) {
		this.tipoAportacion = tipoAportacion;
	}
	public String getDescDelegacion() {
		return descDelegacion;
	}
	public void setDescDelegacion(String descDelegacion) {
		this.descDelegacion = descDelegacion;
	}
	public String getDescSubdelegacion() {
		return descSubdelegacion;
	}
	public void setDescSubdelegacion(String descSubdelegacion) {
		this.descSubdelegacion = descSubdelegacion;
	}
	public String getDescMovPatronal() {
		return descMovPatronal;
	}
	public void setDescMovPatronal(String descMovPatronal) {
		this.descMovPatronal = descMovPatronal;
	}
	public String getDescActEconomica() {
		return descActEconomica;
	}
	public void setDescActEconomica(String descActEconomica) {
		this.descActEconomica = descActEconomica;
	}
	public String getDescTipoPatron() {
		return descTipoPatron;
	}
	public void setDescTipoPatron(String descTipoPatron) {
		this.descTipoPatron = descTipoPatron;
	}
	public String getCveTipoPatron() {
		return cveTipoPatron;
	}
	public void setCveTipoPatron(String cveTipoPatron) {
		this.cveTipoPatron = cveTipoPatron;
	}
	/**
	 * cvePatron + cveModalidad
	 * @return regPatronal:String
	 */
	public String getRegPatronal() {
		return regPatronal;
	}
	/**
	 * cvePatron + cveModalidad
	 * @param regPatronal
	 */
	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}
	/**
	 * Lista de registros patronales para corporativos y RPU
	 * @return
	 */
	public List<String> getRegsPatronales() {
		return regsPatronales;
	}
	/**
	 * Lista de registros patronales para corporativos y RPU
	 * @param regsPatronales
	 */
	public void setRegsPatronales(List<String> regsPatronales) {
		this.regsPatronales = regsPatronales;
	}
	
	
}
