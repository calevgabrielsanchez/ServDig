package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_BITACORA_CLASIFICACION database table.
 * 
 */
@Entity
@Table(name="DIT_BITACORA_CLASIFICACION")
public class DitBitacoraClasificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_BITACORA_CLASIFICACION_GENERATOR", sequenceName = "SEQ_DITBITACORACLASIFICACION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_BITACORA_CLASIFICACION_GENERATOR")
	@Column(name="CVE_BITACORA_CLASIFICACION")
	private long cveBitacoraClasificacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INI_PRIM")
	private Date fecIniPrim;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_CAUSA")
	private Integer numCausa;

	@Column(name="NUM_CONSEC")
	private Integer numConsec;

	@Column(name="NUM_PRIMA")
	private BigDecimal numPrima;

	@Column(name="NUM_TPO_MOVTO")
	private Integer numTpoMovto;

	//bi-directional many-to-one association to DitPatronSujetoObligado
    @ManyToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DicFraccion
    @ManyToOne
	@JoinColumn(name="CVE_ID_FRACCION")
	private DicFraccion dicFraccion;

    public DitBitacoraClasificacion() {
    }

	public long getCveBitacoraClasificacion() {
		return this.cveBitacoraClasificacion;
	}

	public void setCveBitacoraClasificacion(long cveBitacoraClasificacion) {
		this.cveBitacoraClasificacion = cveBitacoraClasificacion;
	}

	public Date getFecIniPrim() {
		return this.fecIniPrim;
	}

	public void setFecIniPrim(Date fecIniPrim) {
		this.fecIniPrim = fecIniPrim;
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

	public Integer getNumCausa() {
		return this.numCausa;
	}

	public void setNumCausa(Integer numCausa) {
		this.numCausa = numCausa;
	}

	public Integer getNumConsec() {
		return this.numConsec;
	}

	public void setNumConsec(Integer numConsec) {
		this.numConsec = numConsec;
	}

	public BigDecimal getNumPrima() {
		return this.numPrima;
	}

	public void setNumPrima(BigDecimal numPrima) {
		this.numPrima = numPrima;
	}

	public Integer getNumTpoMovto() {
		return this.numTpoMovto;
	}

	public void setNumTpoMovto(Integer numTpoMovto) {
		this.numTpoMovto = numTpoMovto;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DicFraccion getDicFraccion() {
		return this.dicFraccion;
	}

	public void setDicFraccion(DicFraccion dicFraccion) {
		this.dicFraccion = dicFraccion;
	}
	
}