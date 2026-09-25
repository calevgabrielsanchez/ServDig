package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="SPC_MONTO_MINIMO")
@NamedQuery(name="SpcMontoMinimo.findAll", query="SELECT s FROM SpcMontoMinimo s")
public class SpcMontoMinimo implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador compuesto.
	 */
	@EmbeddedId
	private SpcMontoMinimoPK id;

	/**
	 * Importe Pension Minima Garantizada.
	 */
	@Column(name="IMP_MONTO_MINIMO_ORDINARIO")
	private BigDecimal impMontoMinimoOrdinario;

	/**
	 * Porcentaje Pension Minima Garantizada.
	 */
	@Column(name="POR_INCREMENTO_ORDINARIO")
	private BigDecimal porIncrementoOrdinario;

	/**
	 * Importe Pension Minima Garantizada.
	 */
	@Column(name="IMP_MONTO_MINIMO_MODIFICADO")
	private BigDecimal impMontoMinimoModificado;

	/**
	 * Porcentaje Pension Minima Garantizada.
	 */
	@Column(name="POR_INCREMENTO_MODIFICADO")
	private BigDecimal porIncrementoModificado;

	/**
	 * Porcentaje de Incremento.
	 */
	@Column(name="NUM_MESES_VIGENCIA")
	private Long numMesesVigencia;

	/**
	 * Porcentaje de Incremento.
	 */
	@Column(name="NUM_DIAS_VIGENCIA")
	private Long numDiasVigencia;

	/**
	 * Default Constructor.
	 */
	public SpcMontoMinimo() {
	}

	/**
	 * @return the id
	 */
	public SpcMontoMinimoPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SpcMontoMinimoPK id) {
		this.id = id;
	}

	/**
	 * @return the impMontoMinimoOrdinario
	 */
	public BigDecimal getImpMontoMinimoOrdinario() {
		return impMontoMinimoOrdinario;
	}

	/**
	 * @param impMontoMinimoOrdinario the impMontoMinimoOrdinario to set
	 */
	public void setImpMontoMinimoOrdinario(BigDecimal impMontoMinimoOrdinario) {
		this.impMontoMinimoOrdinario = impMontoMinimoOrdinario;
	}

	/**
	 * @return the porIncrementoOrdinario
	 */
	public BigDecimal getPorIncrementoOrdinario() {
		return porIncrementoOrdinario;
	}

	/**
	 * @param porIncrementoOrdinario the porIncrementoOrdinario to set
	 */
	public void setPorIncrementoOrdinario(BigDecimal porIncrementoOrdinario) {
		this.porIncrementoOrdinario = porIncrementoOrdinario;
	}

	/**
	 * @return the impMontoMinimoModificado
	 */
	public BigDecimal getImpMontoMinimoModificado() {
		return impMontoMinimoModificado;
	}

	/**
	 * @param impMontoMinimoModificado the impMontoMinimoModificado to set
	 */
	public void setImpMontoMinimoModificado(BigDecimal impMontoMinimoModificado) {
		this.impMontoMinimoModificado = impMontoMinimoModificado;
	}

	/**
	 * @return the porIncrementoModificado
	 */
	public BigDecimal getPorIncrementoModificado() {
		return porIncrementoModificado;
	}

	/**
	 * @param porIncrementoModificado the porIncrementoModificado to set
	 */
	public void setPorIncrementoModificado(BigDecimal porIncrementoModificado) {
		this.porIncrementoModificado = porIncrementoModificado;
	}

	/**
	 * @return the numMesesVigencia
	 */
	public Long getNumMesesVigencia() {
		return numMesesVigencia;
	}

	/**
	 * @param numMesesVigencia the numMesesVigencia to set
	 */
	public void setNumMesesVigencia(Long numMesesVigencia) {
		this.numMesesVigencia = numMesesVigencia;
	}

	/**
	 * @return the numDiasVigencia
	 */
	public Long getNumDiasVigencia() {
		return numDiasVigencia;
	}

	/**
	 * @param numDiasVigencia the numDiasVigencia to set
	 */
	public void setNumDiasVigencia(Long numDiasVigencia) {
		this.numDiasVigencia = numDiasVigencia;
	}

}
