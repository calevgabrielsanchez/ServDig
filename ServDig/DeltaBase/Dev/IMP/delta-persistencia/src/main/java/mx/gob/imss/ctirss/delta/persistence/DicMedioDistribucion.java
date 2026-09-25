package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_MEDIO_DISTRIBUCION database table.
 * 
 */
@Entity
@Table(name="DIC_MEDIO_DISTRIBUCION")
public class DicMedioDistribucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MEDIO_DISTRIBUCION", nullable=false, precision=22)
	private long cveIdMedioDistribucion;

	@Column(name="DES_MEDIO_DISTRIBUCION", length=100)
	private String desMedioDistribucion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitPatSujObligDomicilio
	@OneToMany(mappedBy="dicMedioDistribucion")
	private List<DitPatSujObligDomicilio> ditPatSujObligDomicilios;

    public DicMedioDistribucion() {
    }

	public long getCveIdMedioDistribucion() {
		return this.cveIdMedioDistribucion;
	}

	public void setCveIdMedioDistribucion(long cveIdMedioDistribucion) {
		this.cveIdMedioDistribucion = cveIdMedioDistribucion;
	}

	public String getDesMedioDistribucion() {
		return this.desMedioDistribucion;
	}

	public void setDesMedioDistribucion(String desMedioDistribucion) {
		this.desMedioDistribucion = desMedioDistribucion;
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

	public List<DitPatSujObligDomicilio> getDitPatSujObligDomicilios() {
		return this.ditPatSujObligDomicilios;
	}

	public void setDitPatSujObligDomicilios(List<DitPatSujObligDomicilio> ditPatSujObligDomicilios) {
		this.ditPatSujObligDomicilios = ditPatSujObligDomicilios;
	}
	
}