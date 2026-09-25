package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_PERSONAL database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONAL")
@NamedQuery(name = "DitPersonal.findByCveIdPersonal", query = "SELECT s FROM DitPersonal s WHERE s.cveIdPersonal = :cveIdPersonal")
public class DitPersonal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PERSONAL_GENERATOR", sequenceName = "SEQ_DITPERSONAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERSONAL_GENERATOR")
	@Column(name="CVE_ID_PERSONAL", nullable=false, precision=22)
	private long cveIdPersonal;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_NUMERO_TRABAJADORES", precision=22)
	private BigDecimal numeroTrabajadores;

	@Column(name="REF_OFICIO_OCUPACION", length=255)
	private String oficioOcupacion;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

    public DitPersonal() {
    }

	public long getCveIdPersonal() {
		return this.cveIdPersonal;
	}

	public void setCveIdPersonal(long cveIdPersonal) {
		this.cveIdPersonal = cveIdPersonal;
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

	public BigDecimal getNumeroTrabajadores() {
		return this.numeroTrabajadores;
	}

	public void setNumeroTrabajadores(BigDecimal numeroTrabajadores) {
		this.numeroTrabajadores = numeroTrabajadores;
	}

	public String getOficioOcupacion() {
		return this.oficioOcupacion;
	}

	public void setOficioOcupacion(String oficioOcupacion) {
		this.oficioOcupacion = oficioOcupacion;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
}