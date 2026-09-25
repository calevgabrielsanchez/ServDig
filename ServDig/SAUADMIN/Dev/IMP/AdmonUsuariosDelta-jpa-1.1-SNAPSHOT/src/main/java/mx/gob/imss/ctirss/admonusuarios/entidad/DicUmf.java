package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;


/**
 * The persistent class for the DIC_UMF database table.
 * 
 */
@Entity
@Table(name="DIC_UMF")
public class DicUmf  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Long cveIdUmf;
	private BigDecimal anoInicio;
	private BigDecimal cveIdClavePresupuestal;
	private BigDecimal cveIdNivelAtencion;
	private BigDecimal cveIdTipoUmf;
	private String domicilioId;
	private Date fecHoraRegistro;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private BigDecimal indGeneracionCita;
	private String nomCorto;
	private String nomUnidad;
	private BigDecimal numConsultorio;
	private BigDecimal numEconom;
	private String numMatricula;
	private DicSubdelegacion dicSubdelegacion;

    public DicUmf() {
    }


	@Id
	@Column(name="CVE_ID_UMF", unique=true, nullable=false, length=18)
	public Long getCveIdUmf() {
		return this.cveIdUmf;
	}

	public void setCveIdUmf(Long cveIdUmf) {
		this.cveIdUmf = cveIdUmf;
	}


	@Column(name="ANO_INICIO")
	public BigDecimal getAnoInicio() {
		return this.anoInicio;
	}

	public void setAnoInicio(BigDecimal anoInicio) {
		this.anoInicio = anoInicio;
	}


	@Column(name="CVE_ID_CLAVE_PRESUPUESTAL")
	public BigDecimal getCveIdClavePresupuestal() {
		return this.cveIdClavePresupuestal;
	}

	public void setCveIdClavePresupuestal(BigDecimal cveIdClavePresupuestal) {
		this.cveIdClavePresupuestal = cveIdClavePresupuestal;
	}


	@Column(name="CVE_ID_NIVEL_ATENCION")
	public BigDecimal getCveIdNivelAtencion() {
		return this.cveIdNivelAtencion;
	}

	public void setCveIdNivelAtencion(BigDecimal cveIdNivelAtencion) {
		this.cveIdNivelAtencion = cveIdNivelAtencion;
	}


	@Column(name="CVE_ID_TIPO_UMF")
	public BigDecimal getCveIdTipoUmf() {
		return this.cveIdTipoUmf;
	}

	public void setCveIdTipoUmf(BigDecimal cveIdTipoUmf) {
		this.cveIdTipoUmf = cveIdTipoUmf;
	}


	@Column(name="DOMICILIO_ID", length=18)
	public String getDomicilioId() {
		return this.domicilioId;
	}

	public void setDomicilioId(String domicilioId) {
		this.domicilioId = domicilioId;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_HORA_REGISTRO")
	public Date getFecHoraRegistro() {
		return this.fecHoraRegistro;
	}

	public void setFecHoraRegistro(Date fecHoraRegistro) {
		this.fecHoraRegistro = fecHoraRegistro;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}


	@Column(name="IND_GENERACION_CITA", nullable=false)
	public BigDecimal getIndGeneracionCita() {
		return this.indGeneracionCita;
	}

	public void setIndGeneracionCita(BigDecimal indGeneracionCita) {
		this.indGeneracionCita = indGeneracionCita;
	}


	@Column(name="NOM_CORTO", length=10)
	public String getNomCorto() {
		return this.nomCorto;
	}

	public void setNomCorto(String nomCorto) {
		this.nomCorto = nomCorto;
	}


	@Column(name="NOM_UNIDAD", length=50)
	public String getNomUnidad() {
		return this.nomUnidad;
	}

	public void setNomUnidad(String nomUnidad) {
		this.nomUnidad = nomUnidad;
	}


	@Column(name="NUM_CONSULTORIO", nullable=false)
	public BigDecimal getNumConsultorio() {
		return this.numConsultorio;
	}

	public void setNumConsultorio(BigDecimal numConsultorio) {
		this.numConsultorio = numConsultorio;
	}


	@Column(name="NUM_ECONOM")
	public BigDecimal getNumEconom() {
		return this.numEconom;
	}

	public void setNumEconom(BigDecimal numEconom) {
		this.numEconom = numEconom;
	}


	@Column(name="NUM_MATRICULA", length=8)
	public String getNumMatricula() {
		return this.numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}


	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
}