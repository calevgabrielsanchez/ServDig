package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_CLASIFICACION database table.
 * 
 */
@Entity
@Table(name="DIT_CLASIFICACION")
public class DitClasificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_CLASIFICACION_GENERATOR", sequenceName = "SEQ_DITCLASIFICACION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_CLASIFICACION_GENERATOR")
	@Column(name="CVE_ID_CLASIFICACION", nullable=false, precision=22)
	private long cveIdClasificacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_DISTRIBUCION_ENTREGA", precision=22)
	private BigDecimal indDistribucionEntrega;

	@Column(name="IND_EVALUADA", precision=22)
	private BigDecimal indEvaluada;

	@Column(name="IND_PRESTA_SERVICIO_PERSONAL", precision=22)
	private BigDecimal indPrestaServicioPersonal;

	@Column(name="IND_REG_PAT_CLASE", precision=22)
	private BigDecimal indRegPatClase;

	@Column(name="IND_SERVICIO_OTRAS_PERSONAS", precision=22)
	private BigDecimal indServicioOtrasPersonas;

	@Column(name="IND_TRANSPORTE_AJENO", precision=22)
	private BigDecimal indTransporteAjeno;

	@Column(name="IND_TRANSPORTE_PROPIO", precision=22)
	private BigDecimal indTransportePropio;

	@Column(name="REF_MANIFESTACION", length=250) 
	private String manifestacion;

	@Column(name="NUM_CENTROS_TRABA", precision=22)
	private BigDecimal numCentrosTraba;

	//bi-directional many-to-one association to DicFraccion
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="CVE_ID_FRACCION")
//	private DicFraccion dicFraccion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_FRACCION_CLASE")
	private DicFraccionClase dicFraccionClase;
	
	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;
	
	@Column(name="NUM_PRIMA_PAGO", precision=22)
	private BigDecimal numPrimaPago;

	
    public DitClasificacion() {
    }

	public long getCveIdClasificacion() {
		return this.cveIdClasificacion;
	}

	public void setCveIdClasificacion(long cveIdClasificacion) {
		this.cveIdClasificacion = cveIdClasificacion;
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

	public BigDecimal getIndDistribucionEntrega() {
		return this.indDistribucionEntrega;
	}

	public void setIndDistribucionEntrega(BigDecimal indDistribucionEntrega) {
		this.indDistribucionEntrega = indDistribucionEntrega;
	}

	public BigDecimal getIndEvaluada() {
		return this.indEvaluada;
	}

	public void setIndEvaluada(BigDecimal indEvaluada) {
		this.indEvaluada = indEvaluada;
	}

	public BigDecimal getIndPrestaServicioPersonal() {
		return this.indPrestaServicioPersonal;
	}

	public void setIndPrestaServicioPersonal(BigDecimal indPrestaServicioPersonal) {
		this.indPrestaServicioPersonal = indPrestaServicioPersonal;
	}

	public BigDecimal getIndRegPatClase() {
		return this.indRegPatClase;
	}

	public void setIndRegPatClase(BigDecimal indRegPatClase) {
		this.indRegPatClase = indRegPatClase;
	}

	public BigDecimal getIndServicioOtrasPersonas() {
		return this.indServicioOtrasPersonas;
	}

	public void setIndServicioOtrasPersonas(BigDecimal indServicioOtrasPersonas) {
		this.indServicioOtrasPersonas = indServicioOtrasPersonas;
	}

	public BigDecimal getIndTransporteAjeno() {
		return this.indTransporteAjeno;
	}

	public void setIndTransporteAjeno(BigDecimal indTransporteAjeno) {
		this.indTransporteAjeno = indTransporteAjeno;
	}

	public BigDecimal getIndTransportePropio() {
		return this.indTransportePropio;
	}

	public void setIndTransportePropio(BigDecimal indTransportePropio) {
		this.indTransportePropio = indTransportePropio;
	}

	public String getManifestacion() {
		return this.manifestacion;
	}

	public void setManifestacion(String manifestacion) {
		this.manifestacion = manifestacion;
	}

	public BigDecimal getNumCentrosTraba() {
		return this.numCentrosTraba;
	}

	public void setNumCentrosTraba(BigDecimal numCentrosTraba) {
		this.numCentrosTraba = numCentrosTraba;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}

	public DicFraccionClase getDicFraccionClase() {
		return this.dicFraccionClase;
	}

	public void setDicFraccionClase(DicFraccionClase dicFraccionClase) {
		this.dicFraccionClase = dicFraccionClase;
	}

	public BigDecimal getNumPrimaPago() {
		return numPrimaPago;
	}

	public void setNumPrimaPago(BigDecimal numPrimaPago) {
		this.numPrimaPago = numPrimaPago;
	}
	
}
