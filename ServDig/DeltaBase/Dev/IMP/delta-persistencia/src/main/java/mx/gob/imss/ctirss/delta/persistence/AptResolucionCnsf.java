package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SpcFormaPagoPension;
import mx.gob.imss.ctirss.delta.persistence.SpcRegimen;
import mx.gob.imss.ctirss.delta.persistence.SptTramitePension;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_RESOLUCION_CNSF database table.
 * 
 */
@Entity
@Table(name="APT_RESOLUCION_CNSF")
@NamedQuery(name="AptResolucionCnsf.findAll", query="SELECT s FROM AptResolucionCnsf s")
public class AptResolucionCnsf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_APTRESOLUCIONCNSF", sequenceName = "SEQ_APTRESOLUCIONCNSF")
	@GeneratedValue(generator = "SEQ_APTRESOLUCIONCNSF")
	@Column(name="CVE_RESOLUCION_CNSF")
	private long cveResolucionCnsf;

	@Column(name="CVE_ERROR")
	private BigDecimal cveError;

	@Column(name="CVE_NUM_RESOLUCION")
	private BigDecimal cveNumResolucion;

	@Column(name="CVE_UMF")
	private String cveUmf;

	@Column(name="DESC_DIAGNOSTICO")
	private String descDiagnostico;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CALCULO_MC_DEFINITIVO")
	private Date fecCalculoMcDefinitivo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_EMISION_RESOLUCION")
	private Date fecEmisionResolucion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PROCESO")
	private Date fecProceso;

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

	@Column(name="ID_NUM_ENVIO")
	private BigDecimal idNumEnvio;

	@Column(name="ID_OPERACION")
	private BigDecimal idOperacion;

	@Column(name="IMP_MONTO_CONST_RENTA_VITALICI")
	private BigDecimal impMontoConstRentaVitalici;

	@Column(name="IMP_MONTO_CONST_SOBREVIV")
	private BigDecimal impMontoConstSobreviv;

	@Column(name="IMP_PMG_LSS_97")
	private BigDecimal impPmgLss97;

	@Column(name="IND_TIPO_REGISTRO")
	private BigDecimal indTipoRegistro;

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

	//bi-directional many-to-one association to SptTramitePension
    @ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

    public AptResolucionCnsf() {
    }

	public long getCveResolucionCnsf() {
		return this.cveResolucionCnsf;
	}

	public void setCveResolucionCnsf(long cveResolucionCnsf) {
		this.cveResolucionCnsf = cveResolucionCnsf;
	}

	public BigDecimal getCveError() {
		return this.cveError;
	}

	public void setCveError(BigDecimal cveError) {
		this.cveError = cveError;
	}

	public BigDecimal getCveNumResolucion() {
		return this.cveNumResolucion;
	}

	public void setCveNumResolucion(BigDecimal cveNumResolucion) {
		this.cveNumResolucion = cveNumResolucion;
	}

	public String getCveUmf() {
		return this.cveUmf;
	}

	public void setCveUmf(String cveUmf) {
		this.cveUmf = cveUmf;
	}

	public String getDescDiagnostico() {
		return this.descDiagnostico;
	}

	public void setDescDiagnostico(String descDiagnostico) {
		this.descDiagnostico = descDiagnostico;
	}

	public Date getFecCalculoMcDefinitivo() {
		return this.fecCalculoMcDefinitivo;
	}

	public void setFecCalculoMcDefinitivo(Date fecCalculoMcDefinitivo) {
		this.fecCalculoMcDefinitivo = fecCalculoMcDefinitivo;
	}

	public Date getFecEmisionResolucion() {
		return this.fecEmisionResolucion;
	}

	public void setFecEmisionResolucion(Date fecEmisionResolucion) {
		this.fecEmisionResolucion = fecEmisionResolucion;
	}

	public Date getFecProceso() {
		return this.fecProceso;
	}

	public void setFecProceso(Date fecProceso) {
		this.fecProceso = fecProceso;
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

	public BigDecimal getIdNumEnvio() {
		return this.idNumEnvio;
	}

	public void setIdNumEnvio(BigDecimal idNumEnvio) {
		this.idNumEnvio = idNumEnvio;
	}

	public BigDecimal getIdOperacion() {
		return this.idOperacion;
	}

	public void setIdOperacion(BigDecimal idOperacion) {
		this.idOperacion = idOperacion;
	}

	public BigDecimal getImpMontoConstRentaVitalici() {
		return this.impMontoConstRentaVitalici;
	}

	public void setImpMontoConstRentaVitalici(BigDecimal impMontoConstRentaVitalici) {
		this.impMontoConstRentaVitalici = impMontoConstRentaVitalici;
	}

	public BigDecimal getImpMontoConstSobreviv() {
		return this.impMontoConstSobreviv;
	}

	public void setImpMontoConstSobreviv(BigDecimal impMontoConstSobreviv) {
		this.impMontoConstSobreviv = impMontoConstSobreviv;
	}

	public BigDecimal getImpPmgLss97() {
		return this.impPmgLss97;
	}

	public void setImpPmgLss97(BigDecimal impPmgLss97) {
		this.impPmgLss97 = impPmgLss97;
	}

	public BigDecimal getIndTipoRegistro() {
		return this.indTipoRegistro;
	}

	public void setIndTipoRegistro(BigDecimal indTipoRegistro) {
		this.indTipoRegistro = indTipoRegistro;
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
	
	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}
	
}