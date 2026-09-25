package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ACCION database table.
 * 
 */
@Entity
@Table(name="DIC_ACCION")
public class DicAccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ACCION", nullable=false, precision=22)
	private long cveIdAccion;

	@Column(name="DES_ACCION", length=100)
	private String desAccion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicMenuAccion
	@OneToMany(mappedBy="dicAccion")
	private List<DicMenuAccion> dicMenuAccions;

    public DicAccion() {
    }

	public long getCveIdAccion() {
		return this.cveIdAccion;
	}

	public void setCveIdAccion(long cveIdAccion) {
		this.cveIdAccion = cveIdAccion;
	}

	public String getDesAccion() {
		return this.desAccion;
	}

	public void setDesAccion(String desAccion) {
		this.desAccion = desAccion;
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

	public List<DicMenuAccion> getDicMenuAccions() {
		return this.dicMenuAccions;
	}

	public void setDicMenuAccions(List<DicMenuAccion> dicMenuAccions) {
		this.dicMenuAccions = dicMenuAccions;
	}
	
}