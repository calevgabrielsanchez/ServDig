package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_UMF database table.
 * 
 */



@Entity
@Table(name="DIC_UMF")
@OnSearchLlavePrimaria        (atributos={"cveIdUmf"})
@ComponentComboCampoDescripcion	(atributo="nomUnidad" )
public class DicUmf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_UMF", nullable=false, precision=22)
	private long cveIdUmf;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_HORA_REGISTRO")
	private Date fecHoraRegistro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_GENERACION_CITA", nullable=false, precision=22)
	private BigDecimal indGeneracionCita;

	@Column(name="NOM_CORTO", length=10)
	private String nomCorto;

	@Column(name="NOM_UNIDAD", length=50)
	private String nomUnidad;

	@Column(name="NUM_ANIO_INICIO", precision=22)
	private BigDecimal numAnioInicio;

	@Column(name="NUM_CONSULTORIO", nullable=false, precision=22)
	private BigDecimal numConsultorio;

	@Column(name="NUM_ECONOM", precision=22)
	private BigDecimal numEconom;

	@Column(name="NUM_MATRICULA", length=8)
	private String numMatricula;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;
	
	@Column(name="IND_UMF_CFE", nullable=true, precision=1)
	private BigDecimal indUmfCfe;

	
	
	//bi-directional many-to-one association to DicTipoUmf
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_TIPO_UMF")
	private DicTipoUmf dicTipoUmf;

	//bi-directional many-to-one association to AccNivelAtencion
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_NIVEL_ATENCION")
	private AccNivelAtencion accNivelAtencion;

	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

	//bi-directional many-to-one association to DicClavePresupuestal
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CLAVE_PRESUPUESTAL")
	private DicClavePresupuestal dicClavePresupuestal;

	//bi-directional many-to-one association to DitDictBeneficiarioInca
	@OneToMany(mappedBy="dicUmf")
	private List<DitDictBeneficiarioInca> ditDictBeneficiarioIncas;


	//bi-directional many-to-one association to DitUmfTurno
	@OneToMany(mappedBy="dicUmf")
	private List<DitUmfTurno> ditUmfTurnos;
	
	//bi-directional many-to-one association to DicConsultorioUmf
	@OneToMany(mappedBy="dicUmf")
	private List<DicConsultorioUmf> dicConsultorioUmfs;

	@Column(name="HORARIO_DE_ATENCION", length=130)
	private String horarioDeAtencion;
	
	@Column(name="DOM_CALLE", length=230)
	private String domCalle;
	
	@Column(name="TELEFONOS", length=60)
	private String telefonos;
	
	@Column(name="DOM_ESTADO", length=40)
	private String domEstado;
	
	@Column(name = "LATITUDE")
	private BigDecimal latitud;
	
	@Column(name = "LONGITUDE")
	private BigDecimal longitud;
	
    public DicUmf() {
    }

	public long getCveIdUmf() {
		return this.cveIdUmf;
	}

	public void setCveIdUmf(long cveIdUmf) {
		this.cveIdUmf = cveIdUmf;
	}

	public Date getFecHoraRegistro() {
		return this.fecHoraRegistro;
	}

	public void setFecHoraRegistro(Date fecHoraRegistro) {
		this.fecHoraRegistro = fecHoraRegistro;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public BigDecimal getIndGeneracionCita() {
		return this.indGeneracionCita;
	}

	public void setIndGeneracionCita(BigDecimal indGeneracionCita) {
		this.indGeneracionCita = indGeneracionCita;
	}

	public String getNomCorto() {
		return this.nomCorto;
	}

	public void setNomCorto(String nomCorto) {
		this.nomCorto = nomCorto;
	}

	public String getNomUnidad() {
		return this.nomUnidad;
	}

	public void setNomUnidad(String nomUnidad) {
		this.nomUnidad = nomUnidad;
	}

	public BigDecimal getNumAnioInicio() {
		return this.numAnioInicio;
	}

	public void setNumAnioInicio(BigDecimal numAnioInicio) {
		this.numAnioInicio = numAnioInicio;
	}

	public BigDecimal getNumConsultorio() {
		return this.numConsultorio;
	}

	public void setNumConsultorio(BigDecimal numConsultorio) {
		this.numConsultorio = numConsultorio;
	}

	public BigDecimal getNumEconom() {
		return this.numEconom;
	}

	public void setNumEconom(BigDecimal numEconom) {
		this.numEconom = numEconom;
	}

	public String getNumMatricula() {
		return this.numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	public DicTipoUmf getDicTipoUmf() {
		return this.dicTipoUmf;
	}

	public void setDicTipoUmf(DicTipoUmf dicTipoUmf) {
		this.dicTipoUmf = dicTipoUmf;
	}
	
	public AccNivelAtencion getAccNivelAtencion() {
		return this.accNivelAtencion;
	}

	public void setAccNivelAtencion(AccNivelAtencion accNivelAtencion) {
		this.accNivelAtencion = accNivelAtencion;
	}
	
	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
	public DicClavePresupuestal getDicClavePresupuestal() {
		return this.dicClavePresupuestal;
	}

	public void setDicClavePresupuestal(DicClavePresupuestal dicClavePresupuestal) {
		this.dicClavePresupuestal = dicClavePresupuestal;
	}
	
	public List<DitDictBeneficiarioInca> getDitDictBeneficiarioIncas() {
		return this.ditDictBeneficiarioIncas;
	}

	public void setDitDictBeneficiarioIncas(List<DitDictBeneficiarioInca> ditDictBeneficiarioIncas) {
		this.ditDictBeneficiarioIncas = ditDictBeneficiarioIncas;
	}
	

	public List<DicConsultorioUmf> getDicConsultorioUmfs() {
		return this.dicConsultorioUmfs;
	}

	public void setDicConsultorioUmfs(List<DicConsultorioUmf> dicConsultorioUmfs) {
		this.dicConsultorioUmfs = dicConsultorioUmfs;
	}

	public List<DitUmfTurno> getDitUmfTurnos() {
		return this.ditUmfTurnos;
	}

	public void setDitUmfTurnos(List<DitUmfTurno> ditUmfTurnos) {
		this.ditUmfTurnos = ditUmfTurnos;
	}

	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}

	public BigDecimal getIndUmfCfe() {
		return indUmfCfe;
	}

	public void setIndUmfCfe(BigDecimal indUmfCfe) {
		this.indUmfCfe = indUmfCfe;
	}

	public String getHorarioDeAtencion() {
		return horarioDeAtencion;
	}

	public void setHorarioDeAtencion(String horarioDeAtencion) {
		this.horarioDeAtencion = horarioDeAtencion;
	}

	public String getDomCalle() {
		return domCalle;
	}

	public void setDomCalle(String domCalle) {
		this.domCalle = domCalle;
	}

	public String getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	public String getDomEstado() {
		return domEstado;
	}

	public void setDomEstado(String domEstado) {
		this.domEstado = domEstado;
	}

	public BigDecimal getLatitud() {
		return latitud;
	}

	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	public BigDecimal getLongitud() {
		return longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}
}