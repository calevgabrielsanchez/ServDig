package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_SERVICIO database table.
 * 
 */
@Entity
@Table(name="DIC_SERVICIO")
public class DicServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_SERVICIO", nullable=false, precision=22)
	private long cveIdServicio;

	@Column(name="DES_SERVICIO", length=100)
	private String desServicio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitModSegPrestServ
	@OneToMany(mappedBy="dicServicio")
	private List<DitModSegPrestServ> ditModSegPrestServs;

    public DicServicio() {
    }

	public long getCveIdServicio() {
		return this.cveIdServicio;
	}

	public void setCveIdServicio(long cveIdServicio) {
		this.cveIdServicio = cveIdServicio;
	}

	public String getDesServicio() {
		return this.desServicio;
	}

	public void setDesServicio(String desServicio) {
		this.desServicio = desServicio;
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

	public List<DitModSegPrestServ> getDitModSegPrestServs() {
		return this.ditModSegPrestServs;
	}

	public void setDitModSegPrestServs(List<DitModSegPrestServ> ditModSegPrestServs) {
		this.ditModSegPrestServs = ditModSegPrestServs;
	}
	
}