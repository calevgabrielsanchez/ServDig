package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CAUSA_INCAPACIDAD database table.
 * 
 */
@Entity
@Table(name="DIC_CAUSA_INCAPACIDAD")
public class DicCausaIncapacidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CAUSA_INCAPACIDAD", nullable=false, precision=22)
	private long cveIdCausaIncapacidad;

	@Column(name="CVE_CAUSA_INCAPACIDAD", length=50)
	private String cveCausaIncapacidad;

	@Column(name="DES_CAUSA_INCAPACIDAD", length=50)
	private String desCausaIncapacidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitIncapacidad
	@OneToMany(mappedBy="dicCausaIncapacidad")
	private List<DitIncapacidad> ditIncapacidads;

    public DicCausaIncapacidad() {
    }

	public long getCveIdCausaIncapacidad() {
		return this.cveIdCausaIncapacidad;
	}

	public void setCveIdCausaIncapacidad(long cveIdCausaIncapacidad) {
		this.cveIdCausaIncapacidad = cveIdCausaIncapacidad;
	}

	public String getCveCausaIncapacidad() {
		return this.cveCausaIncapacidad;
	}

	public void setCveCausaIncapacidad(String cveCausaIncapacidad) {
		this.cveCausaIncapacidad = cveCausaIncapacidad;
	}

	public String getDesCausaIncapacidad() {
		return this.desCausaIncapacidad;
	}

	public void setDesCausaIncapacidad(String desCausaIncapacidad) {
		this.desCausaIncapacidad = desCausaIncapacidad;
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

	public List<DitIncapacidad> getDitIncapacidads() {
		return this.ditIncapacidads;
	}

	public void setDitIncapacidads(List<DitIncapacidad> ditIncapacidads) {
		this.ditIncapacidads = ditIncapacidads;
	}
	
}