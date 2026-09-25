package mx.gob.imss.ctirss.delta.cobranza.service.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the D_COP_PATRONES database table.
 * 
 */
@Entity
@Table(name="D_COP_PATRONES")
public class DCopPatrone implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DCopPatronePK id;
	
	private String cp;

	@Column(name="CVE_DELEGACION_ANT")
	private BigDecimal cveDelegacionAnt;

	@Column(name="CVE_GRUPO")
	private BigDecimal cveGrupo;

	@Column(name="CVE_MODALIDAD",insertable = false, updatable=false)
	private String cveModalidad;

	@Column(name="CVE_MUNICIPIO_IMSS")
	private String cveMunicipioImss;

	@Column(name="CVE_PATRON",insertable = false, updatable=false)
	private String cvePatron;

	@Column(name="CVE_SECTOR_NOTIFICACION")
	private String cveSectorNotificacion;

	@Column(name="CVE_SUBDELEGACION_ANT")
	private BigDecimal cveSubdelegacionAnt;

	@Column(name="CVE_TIPO_EMPRESA")
	private BigDecimal cveTipoEmpresa;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

	private String domicilio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CAPTURA")
	private Date fecCaptura;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CARGA")
	private Date fecCarga;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVTO")
	private Date fecMovto;

	@Column(name="HORA_CAPTURA")
	private String horaCaptura;

	private String localidad;

	@Column(name="NUM_TRABAJA")
	private BigDecimal numTrabaja;

	@Column(name="PRIMA_RT")
	private BigDecimal primaRt;

	@Column(name="RAZON_SOCIAL")
	private String razonSocial;

	private String rfc;

	@Column(name="TIPO_APORTACION")
	private BigDecimal tipoAportacion;

	//bi-directional one-to-one association to DCopMovimientosPatronale
	@ManyToOne
	@JoinColumn(name="CVE_MOVTO_PATRONAL", referencedColumnName="CVE_MOV_PATRONAL")
	private DCopMovimientosPatronale DCopMovimientosPatronale;

	//bi-directional one-to-one association to DCopSubdelegacion
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_DELEGACION", referencedColumnName="CVE_DELEGACION"),
		@JoinColumn(name="CVE_SUBDELEGACION", referencedColumnName="CVE_SUBDELEGACION")
		})
	private DCopSubdelegacion DCopSubdelegacion;

	//bi-directional one-to-one association to DCopActividadesEconomica
	@ManyToOne
	@JoinColumn(name="CVE_ACT_ECO", referencedColumnName="CLAVE_ACT_ECO")
	private DCopActividadesEconomica DCopActividadesEconomica;

    public DCopPatrone() {
    }

	public String getCp() {
		return this.cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public BigDecimal getCveDelegacionAnt() {
		return this.cveDelegacionAnt;
	}

	public void setCveDelegacionAnt(BigDecimal cveDelegacionAnt) {
		this.cveDelegacionAnt = cveDelegacionAnt;
	}

	public BigDecimal getCveGrupo() {
		return this.cveGrupo;
	}

	public void setCveGrupo(BigDecimal cveGrupo) {
		this.cveGrupo = cveGrupo;
	}

	public String getCveModalidad() {
		return this.cveModalidad;
	}

	public void setCveModalidad(String cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public String getCveMunicipioImss() {
		return this.cveMunicipioImss;
	}

	public void setCveMunicipioImss(String cveMunicipioImss) {
		this.cveMunicipioImss = cveMunicipioImss;
	}

	public String getCvePatron() {
		return this.cvePatron;
	}

	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}

	public String getCveSectorNotificacion() {
		return this.cveSectorNotificacion;
	}

	public void setCveSectorNotificacion(String cveSectorNotificacion) {
		this.cveSectorNotificacion = cveSectorNotificacion;
	}

	public BigDecimal getCveSubdelegacionAnt() {
		return this.cveSubdelegacionAnt;
	}

	public void setCveSubdelegacionAnt(BigDecimal cveSubdelegacionAnt) {
		this.cveSubdelegacionAnt = cveSubdelegacionAnt;
	}

	public BigDecimal getCveTipoEmpresa() {
		return this.cveTipoEmpresa;
	}

	public void setCveTipoEmpresa(BigDecimal cveTipoEmpresa) {
		this.cveTipoEmpresa = cveTipoEmpresa;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public String getDomicilio() {
		return this.domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Date getFecCaptura() {
		return this.fecCaptura;
	}

	public void setFecCaptura(Date fecCaptura) {
		this.fecCaptura = fecCaptura;
	}

	public Date getFecCarga() {
		return this.fecCarga;
	}

	public void setFecCarga(Date fecCarga) {
		this.fecCarga = fecCarga;
	}

	public Date getFecMovto() {
		return this.fecMovto;
	}

	public void setFecMovto(Date fecMovto) {
		this.fecMovto = fecMovto;
	}

	public String getHoraCaptura() {
		return this.horaCaptura;
	}

	public void setHoraCaptura(String horaCaptura) {
		this.horaCaptura = horaCaptura;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public BigDecimal getNumTrabaja() {
		return this.numTrabaja;
	}

	public void setNumTrabaja(BigDecimal numTrabaja) {
		this.numTrabaja = numTrabaja;
	}

	public BigDecimal getPrimaRt() {
		return this.primaRt;
	}

	public void setPrimaRt(BigDecimal primaRt) {
		this.primaRt = primaRt;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRfc() {
		return this.rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public BigDecimal getTipoAportacion() {
		return this.tipoAportacion;
	}

	public void setTipoAportacion(BigDecimal tipoAportacion) {
		this.tipoAportacion = tipoAportacion;
	}

	public DCopPatronePK getId() {
		return id;
	}

	public void setId(DCopPatronePK id) {
		this.id = id;
	}

	public DCopMovimientosPatronale getDCopMovimientosPatronale() {
		return this.DCopMovimientosPatronale;
	}

	public void setDCopMovimientosPatronale(DCopMovimientosPatronale DCopMovimientosPatronale) {
		this.DCopMovimientosPatronale = DCopMovimientosPatronale;
	}
	
	public DCopSubdelegacion getDCopSubdelegacion() {
		return this.DCopSubdelegacion;
	}

	public void setDCopSubdelegacion(DCopSubdelegacion DCopSubdelegacion) {
		this.DCopSubdelegacion = DCopSubdelegacion;
	}
	
	public DCopActividadesEconomica getDCopActividadesEconomica() {
		return this.DCopActividadesEconomica;
	}

	public void setDCopActividadesEconomica(DCopActividadesEconomica DCopActividadesEconomica) {
		this.DCopActividadesEconomica = DCopActividadesEconomica;
	}
	
}