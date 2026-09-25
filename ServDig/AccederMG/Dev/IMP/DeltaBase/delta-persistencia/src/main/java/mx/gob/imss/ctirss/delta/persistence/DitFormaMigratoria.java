package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_FORMA_MIGRATORIA database table.
 * 
 */
@Entity
@Table(name="DIT_FORMA_MIGRATORIA")
public class DitFormaMigratoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_FORMA_MIGRATORIA", nullable=false, precision=22)
	private long cveIdFormaMigratoria;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_FORMA_MIGRATORIA", length=100)
	private String numFormaMigratoria;

	//bi-directional many-to-one association to DicTipoForma
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_FORMA")
	private DicTipoForma dicTipoForma;

	//bi-directional many-to-one association to DicCalidadCaracMigrat
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CALIDAD_CARAC_MIGRAT")
	private DicCalidadCaracMigrat dicCalidadCaracMigrat;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA")
	private DitPersona ditPersona;

	//bi-directional many-to-one association to DitDocumentoProbatorio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
    public DitFormaMigratoria() {
    }

	public long getCveIdFormaMigratoria() {
		return this.cveIdFormaMigratoria;
	}

	public void setCveIdFormaMigratoria(long cveIdFormaMigratoria) {
		this.cveIdFormaMigratoria = cveIdFormaMigratoria;
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

	public String getNumFormaMigratoria() {
		return this.numFormaMigratoria;
	}

	public void setNumFormaMigratoria(String numFormaMigratoria) {
		this.numFormaMigratoria = numFormaMigratoria;
	}

	public DicTipoForma getDicTipoForma() {
		return this.dicTipoForma;
	}

	public void setDicTipoForma(DicTipoForma dicTipoForma) {
		this.dicTipoForma = dicTipoForma;
	}
	
	public DicCalidadCaracMigrat getDicCalidadCaracMigrat() {
		return this.dicCalidadCaracMigrat;
	}

	public void setDicCalidadCaracMigrat(DicCalidadCaracMigrat dicCalidadCaracMigrat) {
		this.dicCalidadCaracMigrat = dicCalidadCaracMigrat;
	}
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return this.ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

}