package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_GRUPO_FAMILIAR_PENSION_MOV database table.
 * 
 */
@Entity
@Table(name="SPT_GRUPO_FAMILIAR_PENSION_MOV")
@NamedQuery(name="SptGrupoFamiliarPensionMov.findAll", query="SELECT s FROM SptGrupoFamiliarPensionMov s")
public class SptGrupoFamiliarPensionMov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTGRUPOFAMILIARPENSIONMOV", sequenceName = "SEQ_SPTGRUPOFAMILIARPENSIONMOV")
	@GeneratedValue(generator = "SEQ_SPTGRUPOFAMILIARPENSIONMOV")
	@Column(name="CVE_ID_GRUPO_FAMILIAR_MOV")
	private long cveIdGrupoFamiliarMov;

	@Column(name="CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_DERECHO")
	private Date fecInicioDerecho;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Column(name="ID_FECHA_MODIFICACION")
	private Timestamp idFechaModificacion;

	@Column(name="ID_GRUPO_FAMILIAR")
	private String idGrupoFamiliar;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="IMP_PENSION_ALIMENTICIA")
	private BigDecimal impPensionAlimenticia;

	@Column(name="POR_PENSION_ALIMENTICIA")
	private BigDecimal porPensionAlimenticia;

	//bi-directional many-to-one association to SptGrupoFamiliarPension
    @ManyToOne
	@JoinColumn(name="CVE_ID_GRUPO_FAMILIAR_PENSION")
	private SptGrupoFamiliarPension sptGrupoFamiliarPension;

	//bi-directional many-to-one association to SpcIncidencia
    @ManyToOne
	@JoinColumn(name="ID_INCIDENCIA")
	private SpcIncidencia spcIncidencia;

    public SptGrupoFamiliarPensionMov() {
    }

	public long getCveIdGrupoFamiliarMov() {
		return this.cveIdGrupoFamiliarMov;
	}

	public void setCveIdGrupoFamiliarMov(long cveIdGrupoFamiliarMov) {
		this.cveIdGrupoFamiliarMov = cveIdGrupoFamiliarMov;
	}

	public BigDecimal getCvePeriodoNomina() {
		return this.cvePeriodoNomina;
	}

	public void setCvePeriodoNomina(BigDecimal cvePeriodoNomina) {
		this.cvePeriodoNomina = cvePeriodoNomina;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecInicioAjuste() {
		return this.fecInicioAjuste;
	}

	public void setFecInicioAjuste(Date fecInicioAjuste) {
		this.fecInicioAjuste = fecInicioAjuste;
	}

	public Date getFecInicioDerecho() {
		return this.fecInicioDerecho;
	}

	public void setFecInicioDerecho(Date fecInicioDerecho) {
		this.fecInicioDerecho = fecInicioDerecho;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public Timestamp getIdFechaModificacion() {
		return this.idFechaModificacion;
	}

	public void setIdFechaModificacion(Timestamp idFechaModificacion) {
		this.idFechaModificacion = idFechaModificacion;
	}

	public String getIdGrupoFamiliar() {
		return this.idGrupoFamiliar;
	}

	public void setIdGrupoFamiliar(String idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
	}

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	public BigDecimal getImpPensionAlimenticia() {
		return this.impPensionAlimenticia;
	}

	public void setImpPensionAlimenticia(BigDecimal impPensionAlimenticia) {
		this.impPensionAlimenticia = impPensionAlimenticia;
	}

	public BigDecimal getPorPensionAlimenticia() {
		return this.porPensionAlimenticia;
	}

	public void setPorPensionAlimenticia(BigDecimal porPensionAlimenticia) {
		this.porPensionAlimenticia = porPensionAlimenticia;
	}

	public SptGrupoFamiliarPension getSptGrupoFamiliarPension() {
		return this.sptGrupoFamiliarPension;
	}

	public void setSptGrupoFamiliarPension(SptGrupoFamiliarPension sptGrupoFamiliarPension) {
		this.sptGrupoFamiliarPension = sptGrupoFamiliarPension;
	}
	
	public SpcIncidencia getSpcIncidencia() {
		return this.spcIncidencia;
	}

	public void setSpcIncidencia(SpcIncidencia spcIncidencia) {
		this.spcIncidencia = spcIncidencia;
	}
	
}