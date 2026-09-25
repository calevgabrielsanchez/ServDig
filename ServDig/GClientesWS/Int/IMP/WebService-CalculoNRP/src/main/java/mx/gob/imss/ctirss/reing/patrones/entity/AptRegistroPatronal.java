package mx.gob.imss.ctirss.reing.patrones.entity;


import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_REGISTRO_PATRONAL database table.
 * 
 */
@Entity
@Table(name="APT_REGISTRO_PATRONAL")
public class AptRegistroPatronal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_SOLICITUD_REGISTRO")
	private long cveSolicitudRegistro;

	@Column(name="CVE_ARGUMENTO")
	private BigDecimal cveArgumento;

	@Column(name="CVE_DELEG_ALTA")
	private BigDecimal cveDelegAlta;

	@Column(name="CVE_ESTADO_SOLICITUD")
	private BigDecimal cveEstadoSolicitud;

	@Column(name="CVE_ESTATUS_CE")
	private BigDecimal cveEstatusCe;

	@Column(name="CVE_MODALIDAD")
	private BigDecimal cveModalidad;

	@Column(name="CVE_MUNICIPIO")
	private String cveMunicipio;

	@Column(name="CVE_NIVEL_EDUCATIVO")
	private BigDecimal cveNivelEducativo;

	@Column(name="CVE_ORGIEN_MOVIMIENTO")
	private BigDecimal cveOrgienMovimiento;

	@Column(name="CVE_REG_PATRONAL")
	private String cveRegPatronal;

	@Column(name="CVE_SOLICITUD_SARE")
	private BigDecimal cveSolicitudSare;

	@Column(name="CVE_TIPO_PAGO")
	private BigDecimal cveTipoPago;

	@Column(name="CVE_TIPO_PERSONA")
	private BigDecimal cveTipoPersona;

	@Column(name="CVE_USUARIO_ALTA")
	private String cveUsuarioAlta;

	@Column(name="CVE_USUARIO_RACF")
	private String cveUsuarioRacf;

	@Column(name="DES_GIRO")
	private String desGiro;

	@Column(name="DES_MENSAJE")
	private String desMensaje;

	@Column(name="DES_NOMBRE_COMERCIAL")
	private String desNombreComercial;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ALTA_REG_PATRONAL")
	private Date fecAltaRegPatronal;

	@Column(name="IND_MARCA_AUDITORIA")
	private BigDecimal indMarcaAuditoria;

	@Column(name="IND_MARCA_CLASE")
	private BigDecimal indMarcaClase;

	@Column(name="IND_SERV_PERSONAL")
	private BigDecimal indServPersonal;

	@Column(name="NOM_AP_MATERNO")
	private String nomApMaterno;

	@Column(name="NOM_AP_PATERNO")
	private String nomApPaterno;

	@Column(name="NOM_NOMBE_RAZON_SOCIAL")
	private String nomNombeRazonSocial;

	@Column(name="NOM_USUARIO_ALTA")
	private String nomUsuarioAlta;

	@Column(name="NUM_CENTROS_TRAB")
	private BigDecimal numCentrosTrab;

	@Column(name="NUM_DIGITO_VERIFICADOR")
	private BigDecimal numDigitoVerificador;

	@Column(name="NUM_MES_EMISION")
	private BigDecimal numMesEmision;

	@Column(name="REF_CURP")
	private String refCurp;

	@Column(name="REF_RESPUESTA_SAT")
	private String refRespuestaSat;

	@Column(name="REF_RFC")
	private String refRfc;

	@Column(name="SDELEG_ALTA")
	private BigDecimal sdelegAlta;

	@Column(name="SSF_ID")
	private BigDecimal ssfId;

	@Column(name="STP_FEC_RECEPCION_SOLICITUD")
	private Timestamp stpFecRecepcionSolicitud;

	//bi-directional many-to-one association to ApcTipoSociedad
    @ManyToOne
	@JoinColumn(name="CVE_TIPO_SOCIEDAD")
	private ApcTipoSociedad apcTipoSociedad;

	//bi-directional many-to-one association to ApcFraccion
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_DIVISION", referencedColumnName="CVE_DIVISION"),
		@JoinColumn(name="CVE_FRACCION", referencedColumnName="CVE_FRACCION"),
		@JoinColumn(name="CVE_GRUPO", referencedColumnName="CVE_GRUPO")
		})
	private ApcFraccion apcFraccion;

    public AptRegistroPatronal() {
    }

	public long getCveSolicitudRegistro() {
		return this.cveSolicitudRegistro;
	}

	public void setCveSolicitudRegistro(long cveSolicitudRegistro) {
		this.cveSolicitudRegistro = cveSolicitudRegistro;
	}

	public BigDecimal getCveArgumento() {
		return this.cveArgumento;
	}

	public void setCveArgumento(BigDecimal cveArgumento) {
		this.cveArgumento = cveArgumento;
	}

	public BigDecimal getCveDelegAlta() {
		return this.cveDelegAlta;
	}

	public void setCveDelegAlta(BigDecimal cveDelegAlta) {
		this.cveDelegAlta = cveDelegAlta;
	}

	public BigDecimal getCveEstadoSolicitud() {
		return this.cveEstadoSolicitud;
	}

	public void setCveEstadoSolicitud(BigDecimal cveEstadoSolicitud) {
		this.cveEstadoSolicitud = cveEstadoSolicitud;
	}

	public BigDecimal getCveEstatusCe() {
		return this.cveEstatusCe;
	}

	public void setCveEstatusCe(BigDecimal cveEstatusCe) {
		this.cveEstatusCe = cveEstatusCe;
	}

	public BigDecimal getCveModalidad() {
		return this.cveModalidad;
	}

	public void setCveModalidad(BigDecimal cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public String getCveMunicipio() {
		return this.cveMunicipio;
	}

	public void setCveMunicipio(String cveMunicipio) {
		this.cveMunicipio = cveMunicipio;
	}

	public BigDecimal getCveNivelEducativo() {
		return this.cveNivelEducativo;
	}

	public void setCveNivelEducativo(BigDecimal cveNivelEducativo) {
		this.cveNivelEducativo = cveNivelEducativo;
	}

	public BigDecimal getCveOrgienMovimiento() {
		return this.cveOrgienMovimiento;
	}

	public void setCveOrgienMovimiento(BigDecimal cveOrgienMovimiento) {
		this.cveOrgienMovimiento = cveOrgienMovimiento;
	}

	public String getCveRegPatronal() {
		return this.cveRegPatronal;
	}

	public void setCveRegPatronal(String cveRegPatronal) {
		this.cveRegPatronal = cveRegPatronal;
	}

	public BigDecimal getCveSolicitudSare() {
		return this.cveSolicitudSare;
	}

	public void setCveSolicitudSare(BigDecimal cveSolicitudSare) {
		this.cveSolicitudSare = cveSolicitudSare;
	}

	public BigDecimal getCveTipoPago() {
		return this.cveTipoPago;
	}

	public void setCveTipoPago(BigDecimal cveTipoPago) {
		this.cveTipoPago = cveTipoPago;
	}

	public BigDecimal getCveTipoPersona() {
		return this.cveTipoPersona;
	}

	public void setCveTipoPersona(BigDecimal cveTipoPersona) {
		this.cveTipoPersona = cveTipoPersona;
	}

	public String getCveUsuarioAlta() {
		return this.cveUsuarioAlta;
	}

	public void setCveUsuarioAlta(String cveUsuarioAlta) {
		this.cveUsuarioAlta = cveUsuarioAlta;
	}

	public String getCveUsuarioRacf() {
		return this.cveUsuarioRacf;
	}

	public void setCveUsuarioRacf(String cveUsuarioRacf) {
		this.cveUsuarioRacf = cveUsuarioRacf;
	}

	public String getDesGiro() {
		return this.desGiro;
	}

	public void setDesGiro(String desGiro) {
		this.desGiro = desGiro;
	}

	public String getDesMensaje() {
		return this.desMensaje;
	}

	public void setDesMensaje(String desMensaje) {
		this.desMensaje = desMensaje;
	}

	public String getDesNombreComercial() {
		return this.desNombreComercial;
	}

	public void setDesNombreComercial(String desNombreComercial) {
		this.desNombreComercial = desNombreComercial;
	}

	public Date getFecAltaRegPatronal() {
		return this.fecAltaRegPatronal;
	}

	public void setFecAltaRegPatronal(Date fecAltaRegPatronal) {
		this.fecAltaRegPatronal = fecAltaRegPatronal;
	}

	public BigDecimal getIndMarcaAuditoria() {
		return this.indMarcaAuditoria;
	}

	public void setIndMarcaAuditoria(BigDecimal indMarcaAuditoria) {
		this.indMarcaAuditoria = indMarcaAuditoria;
	}

	public BigDecimal getIndMarcaClase() {
		return this.indMarcaClase;
	}

	public void setIndMarcaClase(BigDecimal indMarcaClase) {
		this.indMarcaClase = indMarcaClase;
	}

	public BigDecimal getIndServPersonal() {
		return this.indServPersonal;
	}

	public void setIndServPersonal(BigDecimal indServPersonal) {
		this.indServPersonal = indServPersonal;
	}

	public String getNomApMaterno() {
		return this.nomApMaterno;
	}

	public void setNomApMaterno(String nomApMaterno) {
		this.nomApMaterno = nomApMaterno;
	}

	public String getNomApPaterno() {
		return this.nomApPaterno;
	}

	public void setNomApPaterno(String nomApPaterno) {
		this.nomApPaterno = nomApPaterno;
	}

	public String getNomNombeRazonSocial() {
		return this.nomNombeRazonSocial;
	}

	public void setNomNombeRazonSocial(String nomNombeRazonSocial) {
		this.nomNombeRazonSocial = nomNombeRazonSocial;
	}

	public String getNomUsuarioAlta() {
		return this.nomUsuarioAlta;
	}

	public void setNomUsuarioAlta(String nomUsuarioAlta) {
		this.nomUsuarioAlta = nomUsuarioAlta;
	}

	public BigDecimal getNumCentrosTrab() {
		return this.numCentrosTrab;
	}

	public void setNumCentrosTrab(BigDecimal numCentrosTrab) {
		this.numCentrosTrab = numCentrosTrab;
	}

	public BigDecimal getNumDigitoVerificador() {
		return this.numDigitoVerificador;
	}

	public void setNumDigitoVerificador(BigDecimal numDigitoVerificador) {
		this.numDigitoVerificador = numDigitoVerificador;
	}

	public BigDecimal getNumMesEmision() {
		return this.numMesEmision;
	}

	public void setNumMesEmision(BigDecimal numMesEmision) {
		this.numMesEmision = numMesEmision;
	}

	public String getRefCurp() {
		return this.refCurp;
	}

	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}

	public String getRefRespuestaSat() {
		return this.refRespuestaSat;
	}

	public void setRefRespuestaSat(String refRespuestaSat) {
		this.refRespuestaSat = refRespuestaSat;
	}

	public String getRefRfc() {
		return this.refRfc;
	}

	public void setRefRfc(String refRfc) {
		this.refRfc = refRfc;
	}

	public BigDecimal getSdelegAlta() {
		return this.sdelegAlta;
	}

	public void setSdelegAlta(BigDecimal sdelegAlta) {
		this.sdelegAlta = sdelegAlta;
	}

	public BigDecimal getSsfId() {
		return this.ssfId;
	}

	public void setSsfId(BigDecimal ssfId) {
		this.ssfId = ssfId;
	}

	public Timestamp getStpFecRecepcionSolicitud() {
		return this.stpFecRecepcionSolicitud;
	}

	public void setStpFecRecepcionSolicitud(Timestamp stpFecRecepcionSolicitud) {
		this.stpFecRecepcionSolicitud = stpFecRecepcionSolicitud;
	}

	public ApcTipoSociedad getApcTipoSociedad() {
		return this.apcTipoSociedad;
	}

	public void setApcTipoSociedad(ApcTipoSociedad apcTipoSociedad) {
		this.apcTipoSociedad = apcTipoSociedad;
	}
	
	public ApcFraccion getApcFraccion() {
		return this.apcFraccion;
	}

	public void setApcFraccion(ApcFraccion apcFraccion) {
		this.apcFraccion = apcFraccion;
	}
	
}