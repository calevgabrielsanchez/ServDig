package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_LEY database table.
 * 
 */
@Entity
@Table(name="DIC_LEY")
public class DicLey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_LEY", nullable=false, precision=22)
	private long cveIdLey;

	@Column(name="DES_LEY", length=50)
	private String desLey;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitLeySeguro
	@OneToMany(mappedBy="dicLey")
	private List<DitLeySeguro> ditLeySeguros;

    public DicLey() {
    }

	public long getCveIdLey() {
		return this.cveIdLey;
	}

	public void setCveIdLey(long cveIdLey) {
		this.cveIdLey = cveIdLey;
	}

	public String getDesLey() {
		return this.desLey;
	}

	public void setDesLey(String desLey) {
		this.desLey = desLey;
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

	public List<DitLeySeguro> getDitLeySeguros() {
		return this.ditLeySeguros;
	}

	public void setDitLeySeguros(List<DitLeySeguro> ditLeySeguros) {
		this.ditLeySeguros = ditLeySeguros;
	}
	
}