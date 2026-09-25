package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the DIT_IDENTIFICADOR_MORAL database table.
 * 
 */
@Entity
@Table(name = "DIT_IDENTIFICADOR_MORAL")
public class DitIdentificadorMoral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_IDENTIFICADOR_MORAL_CVEIDIDENTIFICADORMORAL_GENERATOR", sequenceName="SEQ_DITIDENTIFICADORMORAL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_IDENTIFICADOR_MORAL_CVEIDIDENTIFICADORMORAL_GENERATOR")
	@Column(name = "CVE_ID_IDENTIFICADOR_MORAL")
	private long cveIdIdentificadorMoral;

	@Column(name = "CVE_IDENTIFICADORA")
	private String cveIdentificadora;

	@Column(name = "IND_VIGENTE")
	private BigDecimal indVigente;

	// bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne
	@JoinColumn(name = "CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	// bi-directional many-to-one association to DicTipoIdentificador
	@ManyToOne
	@JoinColumn(name = "CVE_ID_TIPO_IDENTIFICADOR")
	private DicTipoIdentificador dicTipoIdentificador;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public DitIdentificadorMoral() {
	}

	public long getCveIdIdentificadorMoral() {
		return cveIdIdentificadorMoral;
	}

	public void setCveIdIdentificadorMoral(long cveIdIdentificadorMoral) {
		this.cveIdIdentificadorMoral = cveIdIdentificadorMoral;
	}

	public String getCveIdentificadora() {
		return cveIdentificadora;
	}

	public void setCveIdentificadora(String cveIdentificadora) {
		this.cveIdentificadora = cveIdentificadora;
	}

	public BigDecimal getIndVigente() {
		return indVigente;
	}

	public void setIndVigente(BigDecimal indVigente) {
		this.indVigente = indVigente;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}

	public DicTipoIdentificador getDicTipoIdentificador() {
		return dicTipoIdentificador;
	}

	public void setDicTipoIdentificador(
			DicTipoIdentificador dicTipoIdentificador) {
		this.dicTipoIdentificador = dicTipoIdentificador;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

}