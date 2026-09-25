package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SpcFormaPagoPension;
import mx.gob.imss.ctirss.delta.persistence.SpcRegimen;
import mx.gob.imss.ctirss.delta.persistence.SptEnvComunicEntidade;
import mx.gob.imss.ctirss.delta.persistence.SptTramitePension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the APT_ENVIO_PRE_RESOLUCION_ENV database table.
 * 
 */
@Entity
@Table(name="APT_ENVIO_PRE_RESOLUCION_ENV")
@NamedQuery(name="AptEnvioPreResolucionEnv.findAll", query="SELECT s FROM AptEnvioPreResolucionEnv s")
public class AptEnvioPreResolucionEnv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_APTENVIOPRERESOLUCIONENV", sequenceName = "SEQ_APTENVIOPRERESOLUCIONENV")
	@GeneratedValue(generator = "SEQ_APTENVIOPRERESOLUCIONENV")
	@Column(name="CVE_ENVIO_PRE_RESOLUCION")
	private long cveEnvioPreResolucion;

	@Column(name="CVE_CURP")
	private String cveCurp;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_FOLIO")
	private BigDecimal idFolio;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="ID_NUM_ENVIO")
	private BigDecimal idNumEnvio;

	//bi-directional many-to-one association to ApcStatusPeticione
    @ManyToOne
	@JoinColumn(name="ID_STATUS_PETICION")
	private ApcStatusPeticione apcStatusPeticione;

	//bi-directional many-to-one association to SpcAseguradora
    @ManyToOne
	@JoinColumn(name="ID_ASEGURADORA")
	private SpcAseguradora spcAseguradora;

	//bi-directional many-to-one association to SpcFormaPagoPension
    @ManyToOne
	@JoinColumn(name="ID_FORMA_PAGO_PENSION")
	private SpcFormaPagoPension spcFormaPagoPension;

	//bi-directional many-to-one association to SpcRegimen
    @ManyToOne
	@JoinColumn(name="ID_REGIMEN")
	private SpcRegimen spcRegimen;

	//bi-directional many-to-one association to SptEnvComunicEntidade
    @ManyToOne
	@JoinColumn(name="CVE_ID_ENV_COMUNIC_ENTIDADES")
	private SptEnvComunicEntidade sptEnvComunicEntidade;

	//bi-directional many-to-one association to SptTramitePension
    @ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	//bi-directional many-to-one association to AptRespuestaPreResolucion
	@OneToMany(mappedBy="aptEnvioPreResolucionEnv")
	private Set<AptRespuestaPreResolucion> aptRespuestaPreResolucions;

    public AptEnvioPreResolucionEnv() {
    }

	public long getCveEnvioPreResolucion() {
		return this.cveEnvioPreResolucion;
	}

	public void setCveEnvioPreResolucion(long cveEnvioPreResolucion) {
		this.cveEnvioPreResolucion = cveEnvioPreResolucion;
	}

	public String getCveCurp() {
		return this.cveCurp;
	}

	public void setCveCurp(String cveCurp) {
		this.cveCurp = cveCurp;
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

	public BigDecimal getIdFolio() {
		return this.idFolio;
	}

	public void setIdFolio(BigDecimal idFolio) {
		this.idFolio = idFolio;
	}

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	public BigDecimal getIdNumEnvio() {
		return this.idNumEnvio;
	}

	public void setIdNumEnvio(BigDecimal idNumEnvio) {
		this.idNumEnvio = idNumEnvio;
	}

	public ApcStatusPeticione getApcStatusPeticione() {
		return this.apcStatusPeticione;
	}

	public void setApcStatusPeticione(ApcStatusPeticione apcStatusPeticione) {
		this.apcStatusPeticione = apcStatusPeticione;
	}
	
	public SpcAseguradora getSpcAseguradora() {
		return this.spcAseguradora;
	}

	public void setSpcAseguradora(SpcAseguradora spcAseguradora) {
		this.spcAseguradora = spcAseguradora;
	}
	
	public SpcFormaPagoPension getSpcFormaPagoPension() {
		return this.spcFormaPagoPension;
	}

	public void setSpcFormaPagoPension(SpcFormaPagoPension spcFormaPagoPension) {
		this.spcFormaPagoPension = spcFormaPagoPension;
	}
	
	public SpcRegimen getSpcRegimen() {
		return this.spcRegimen;
	}

	public void setSpcRegimen(SpcRegimen spcRegimen) {
		this.spcRegimen = spcRegimen;
	}
	
	public SptEnvComunicEntidade getSptEnvComunicEntidade() {
		return this.sptEnvComunicEntidade;
	}

	public void setSptEnvComunicEntidade(SptEnvComunicEntidade sptEnvComunicEntidade) {
		this.sptEnvComunicEntidade = sptEnvComunicEntidade;
	}
	
	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}
	
	public Set<AptRespuestaPreResolucion> getAptRespuestaPreResolucions() {
		return this.aptRespuestaPreResolucions;
	}

	public void setAptRespuestaPreResolucions(Set<AptRespuestaPreResolucion> aptRespuestaPreResolucions) {
		this.aptRespuestaPreResolucions = aptRespuestaPreResolucions;
	}
	
}