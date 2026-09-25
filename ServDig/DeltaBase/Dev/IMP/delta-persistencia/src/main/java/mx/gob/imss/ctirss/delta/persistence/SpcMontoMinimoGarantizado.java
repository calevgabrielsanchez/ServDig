package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SPC_MONTO_MINIMO_GARANTIZADO")
@NamedQuery(name="SpcMontoMinimoGarantizado.findAll", query="SELECT s FROM SpcMontoMinimoGarantizado s")
public class SpcMontoMinimoGarantizado implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador compuesto.
	 */
	@EmbeddedId
	private SpcMontoMinimoGarantizadoPK id;

	/**
	 * Importe Pension Minima Garantizada.
	 */
	@Column(name="IMP_PENSION_MIN_GARANTIZADA")
	private BigDecimal impPensionMinGarantizada;

	/**
	 * Porcentaje de Incremento.
	 */
	@Column(name="POR_INCREMENTO")
	private BigDecimal porIncremento;

	/**
	 * Numero de meses de vigencia.
	 */
	@Column(name="NUM_MESES_VIGENCIA")
	private BigDecimal numMesesVigencia;

	/**
	 * Numero de dias de vigencia.
	 */
	@Column(name="NUM_DIAS_VIGENCIA")
	private BigDecimal numDiasVigencia;

	/**
	 * Default Constructor
	 */
	public SpcMontoMinimoGarantizado() {
	}

	/**
	 * @return the id
	 */
	public SpcMontoMinimoGarantizadoPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SpcMontoMinimoGarantizadoPK id) {
		this.id = id;
	}

	/**
	 * @return the impPensionMinGarantizada
	 */
	public BigDecimal getImpPensionMinGarantizada() {
		return impPensionMinGarantizada;
	}

	/**
	 * @param impPensionMinGarantizada the impPensionMinGarantizada to set
	 */
	public void setImpPensionMinGarantizada(BigDecimal impPensionMinGarantizada) {
		this.impPensionMinGarantizada = impPensionMinGarantizada;
	}

	/**
	 * @return the porIncremento
	 */
	public BigDecimal getPorIncremento() {
		return porIncremento;
	}

	/**
	 * @param porIncremento the porIncremento to set
	 */
	public void setPorIncremento(BigDecimal porIncremento) {
		this.porIncremento = porIncremento;
	}

	/**
	 * @return the numMesesVigencia
	 */
	public BigDecimal getNumMesesVigencia() {
		return numMesesVigencia;
	}

	/**
	 * @param numMesesVigencia the numMesesVigencia to set
	 */
	public void setNumMesesVigencia(BigDecimal numMesesVigencia) {
		this.numMesesVigencia = numMesesVigencia;
	}

	/**
	 * @return the numDiasVigencia
	 */
	public BigDecimal getNumDiasVigencia() {
		return numDiasVigencia;
	}

	/**
	 * @param numDiasVigencia the numDiasVigencia to set
	 */
	public void setNumDiasVigencia(BigDecimal numDiasVigencia) {
		this.numDiasVigencia = numDiasVigencia;
	}

}
