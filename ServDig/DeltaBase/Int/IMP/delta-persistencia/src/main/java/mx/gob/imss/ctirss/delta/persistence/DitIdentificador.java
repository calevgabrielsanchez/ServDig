package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_IDENTIFICADOR database table.
 * 
 */
@Entity
@Table(name="DIT_IDENTIFICADOR")
public class DitIdentificador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_IDENTIFICADOR_CVEIDIDENTIFICADOR_GENERATOR", sequenceName="SEQ_DITIDENTIFICADOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_IDENTIFICADOR_CVEIDIDENTIFICADOR_GENERATOR")
	@Column(name="CVE_ID_IDENTIFICADOR")
	private long cveIdIdentificador;

//	@Column(name="CVE_ID_TIPO_IDENTIFICADOR")
//	private BigDecimal cveIdTipoIdentificador;

	@Column(name="CVE_IDENTIFICADORA")
	private String cveIdentificadora;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_VIGENTE")
	private BigDecimal indVigente;

	//bi-directional many-to-one association to DicTipoIdentificador
    @ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_IDENTIFICADOR")
	private DicTipoIdentificador dicTipoIdentificador;
    
	//bi-directional many-to-one association to DitPersona
    @ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;

    public DitIdentificador() {
    }

	public long getCveIdIdentificador() {
		return this.cveIdIdentificador;
	}

	public void setCveIdIdentificador(long cveIdIdentificador) {
		this.cveIdIdentificador = cveIdIdentificador;
	}

//	public BigDecimal getCveIdTipoIdentificador() {
//		return this.cveIdTipoIdentificador;
//	}
//
//	public void setCveIdTipoIdentificador(BigDecimal cveIdTipoIdentificador) {
//		this.cveIdTipoIdentificador = cveIdTipoIdentificador;
//	}

	public String getCveIdentificadora() {
		return this.cveIdentificadora;
	}

	public void setCveIdentificadora(String cveIdentificadora) {
		this.cveIdentificadora = cveIdentificadora;
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

	public BigDecimal getIndVigente() {
		return this.indVigente;
	}

	public void setIndVigente(BigDecimal indVigente) {
		this.indVigente = indVigente;
	}

	public DicTipoIdentificador getDicTipoIdentificador() {
		return this.dicTipoIdentificador;
	}

	public void setDicTipoIdentificador(DicTipoIdentificador dicTipoIdentificador) {
		this.dicTipoIdentificador = dicTipoIdentificador;
	}
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
}