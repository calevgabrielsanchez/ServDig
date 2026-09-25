package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_AFILIADO_SOLICITUD database table.
 * 
 */
@Entity
@Table(name="SPT_AFILIADO_SOLICITUD")
@NamedQuery(name="SptAfiliadoSolicitud.findAll", query="SELECT s FROM SptAfiliadoSolicitud s")
public class SptAfiliadoSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_SPTAFILIADOSOLICITUD", sequenceName = "SEQ_SPTAFILIADOSOLICITUD")
	@GeneratedValue(generator = "SEQ_SPTAFILIADOSOLICITUD")
	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="CVE_AFORE")
	private String cveAfore;

	@Column(name="CVE_CURP")
	private String cveCurp;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_ENTIDAD_FEDERATIVA")
	private String cveEntidadFederativa;

	@Column(name="CVE_ORIGEN")
	private String cveOrigen;

	@Column(name="CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Column(name="CVE_RFC")
	private String cveRfc;

	@Column(name="CVE_SEXO")
	private BigDecimal cveSexo;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA")
	private Date fecBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_DEFUNCION")
	private Date fecDefuncion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ELECCION_ASEGURADORA")
	private Date fecEleccionAseguradora;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INSCRIPCION_IMSS")
	private Date fecInscripcionImss;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REFORMA_LEY")
	private Date fecReformaLey;

	@Column(name="ID_ASEGURADORA")
	private String idAseguradora;

	@Column(name="ID_ESTADO_CIVIL")
	private String idEstadoCivil;

	@Column(name="ID_ESTADO_NOMINA")
	private String idEstadoNomina;

	@Column(name="ID_REFORMA_LEY")
	private String idReformaLey;

	@Column(name="ID_TIPO_MOVIMIENTO")
	private String idTipoMovimiento;

	@Column(name="IND_AUTORIZA_CP")
	private String indAutorizaCp;

	@Column(name="IND_NOMINA_PROCESADA")
	private String indNominaProcesada;

	@Column(name="NOM_APELLIDO_MATERNO")
	private String nomApellidoMaterno;

	@Column(name="NOM_APELLIDO_PATERNO")
	private String nomApellidoPaterno;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NUM_CAMBIO_ASEGURADORA")
	private BigDecimal numCambioAseguradora;

	public SptAfiliadoSolicitud() {
	}

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	public String getCveAfore() {
		return this.cveAfore;
	}

	public void setCveAfore(String cveAfore) {
		this.cveAfore = cveAfore;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
	}

	public String getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveEntidadFederativa() {
		return this.cveEntidadFederativa;
	}

	public void setCveEntidadFederativa(String cveEntidadFederativa) {
		this.cveEntidadFederativa = cveEntidadFederativa;
	}

	public String getCveOrigen() {
		return this.cveOrigen;
	}

	public void setCveOrigen(String cveOrigen) {
		this.cveOrigen = cveOrigen;
	}

	public BigDecimal getCvePeriodoNomina() {
		return this.cvePeriodoNomina;
	}

	public void setCvePeriodoNomina(BigDecimal cvePeriodoNomina) {
		this.cvePeriodoNomina = cvePeriodoNomina;
	}

	public String getCveRfc() {
		return this.cveRfc;
	}

	public void setCveRfc(String cveRfc) {
		this.cveRfc = cveRfc;
	}

	public BigDecimal getCveSexo() {
		return this.cveSexo;
	}

	public void setCveSexo(BigDecimal cveSexo) {
		this.cveSexo = cveSexo;
	}

	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecBaja() {
		return this.fecBaja;
	}

	public void setFecBaja(Date fecBaja) {
		this.fecBaja = fecBaja;
	}

	public Date getFecDefuncion() {
		return this.fecDefuncion;
	}

	public void setFecDefuncion(Date fecDefuncion) {
		this.fecDefuncion = fecDefuncion;
	}

	public Date getFecEleccionAseguradora() {
		return this.fecEleccionAseguradora;
	}

	public void setFecEleccionAseguradora(Date fecEleccionAseguradora) {
		this.fecEleccionAseguradora = fecEleccionAseguradora;
	}

	public Date getFecInicioAjuste() {
		return this.fecInicioAjuste;
	}

	public void setFecInicioAjuste(Date fecInicioAjuste) {
		this.fecInicioAjuste = fecInicioAjuste;
	}

	public Date getFecInscripcionImss() {
		return this.fecInscripcionImss;
	}

	public void setFecInscripcionImss(Date fecInscripcionImss) {
		this.fecInscripcionImss = fecInscripcionImss;
	}

	public Date getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Date fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public Date getFecReformaLey() {
		return this.fecReformaLey;
	}

	public void setFecReformaLey(Date fecReformaLey) {
		this.fecReformaLey = fecReformaLey;
	}

	public String getIdAseguradora() {
		return this.idAseguradora;
	}

	public void setIdAseguradora(String idAseguradora) {
		this.idAseguradora = idAseguradora;
	}

	public String getIdEstadoCivil() {
		return this.idEstadoCivil;
	}

	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	public String getIdEstadoNomina() {
		return this.idEstadoNomina;
	}

	public void setIdEstadoNomina(String idEstadoNomina) {
		this.idEstadoNomina = idEstadoNomina;
	}

	public String getIdReformaLey() {
		return this.idReformaLey;
	}

	public void setIdReformaLey(String idReformaLey) {
		this.idReformaLey = idReformaLey;
	}

	public String getIdTipoMovimiento() {
		return this.idTipoMovimiento;
	}

	public void setIdTipoMovimiento(String idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	public String getIndAutorizaCp() {
		return this.indAutorizaCp;
	}

	public void setIndAutorizaCp(String indAutorizaCp) {
		this.indAutorizaCp = indAutorizaCp;
	}

	public String getIndNominaProcesada() {
		return this.indNominaProcesada;
	}

	public void setIndNominaProcesada(String indNominaProcesada) {
		this.indNominaProcesada = indNominaProcesada;
	}

	public String getNomApellidoMaterno() {
		return this.nomApellidoMaterno;
	}

	public void setNomApellidoMaterno(String nomApellidoMaterno) {
		this.nomApellidoMaterno = nomApellidoMaterno;
	}

	public String getNomApellidoPaterno() {
		return this.nomApellidoPaterno;
	}

	public void setNomApellidoPaterno(String nomApellidoPaterno) {
		this.nomApellidoPaterno = nomApellidoPaterno;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public BigDecimal getNumCambioAseguradora() {
		return this.numCambioAseguradora;
	}

	public void setNumCambioAseguradora(BigDecimal numCambioAseguradora) {
		this.numCambioAseguradora = numCambioAseguradora;
	}

}