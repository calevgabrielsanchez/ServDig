package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_BIENES database table.
 * 
 */
@Entity
@Table(name="DIT_BIENES")
@NamedQuery(name = "DitBiene.findByCveIdBienes", query = "SELECT s FROM DitBiene s WHERE s.cveIdBienes = :cveIdBienes")
public class DitBiene implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_BIEN_GENERATOR", sequenceName = "SEQ_DITBIENES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_BIEN_GENERATOR")
	@Column(name="CVE_ID_BIENES", nullable=false, precision=22)
	private long cveIdBienes;

//	@Column(name="DES_AFECTACION", length=255)
//	private String desAfectacion;

	@Column(name="DES_BIENES", length=255)
	private String desBienes;

//	@Column(name="DES_USOS_BIENES", length=255)
//	private String desUsosBienes;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_CANTIDAD", precision=22)
	private BigDecimal numCantidad;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitBiene() {
    }

	public long getCveIdBienes() {
		return this.cveIdBienes;
	}

	public void setCveIdBienes(long cveIdBienes) {
		this.cveIdBienes = cveIdBienes;
	}

//	public String getDesAfectacion() {
//		return this.desAfectacion;
//	}
//
//	public void setDesAfectacion(String desAfectacion) {
//		this.desAfectacion = desAfectacion;
//	}

	public String getDesBienes() {
		return this.desBienes;
	}

	public void setDesBienes(String desBienes) {
		this.desBienes = desBienes;
	}

//	public String getDesUsosBienes() {
//		return this.desUsosBienes;
//	}
//
//	public void setDesUsosBienes(String desUsosBienes) {
//		this.desUsosBienes = desUsosBienes;
//	}

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

	public BigDecimal getNumCantidad() {
		return this.numCantidad;
	}

	public void setNumCantidad(BigDecimal numCantidad) {
		this.numCantidad = numCantidad;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}