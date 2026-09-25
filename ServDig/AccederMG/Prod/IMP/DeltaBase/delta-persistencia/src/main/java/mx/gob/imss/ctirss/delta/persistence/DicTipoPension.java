package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_TIPO_RETIRO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_PENSION")
public class DicTipoPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_PENSION", nullable=false, precision=22)
	private long cveIdTipoPension;

	@Column(name="REF_MARCA_PENSION", length=100)
	private String refMarcaPension;
	
	@Column(name="DES_TIPO_PENSION", length=100)
	private String desTipoPension;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
  //bi-directional many-to-one association to DitAseguradoPension
	@OneToMany(mappedBy="dicTipoPension")
	private List<DitAseguradoPension> ditAseguradoPensions;
	
    public DicTipoPension() {
    }

	/**
	 * @return the cveIdTipoPension
	 */
	public long getCveIdTipoPension() {
		return cveIdTipoPension;
	}

	/**
	 * @param cveIdTipoPension the cveIdTipoPension to set
	 */
	public void setCveIdTipoPension(long cveIdTipoPension) {
		this.cveIdTipoPension = cveIdTipoPension;
	}

	/**
	 * @return the refMarcaPension
	 */
	public String getRefMarcaPension() {
		return refMarcaPension;
	}

	/**
	 * @param refMarcaPension the refMarcaPension to set
	 */
	public void setRefMarcaPension(String refMarcaPension) {
		this.refMarcaPension = refMarcaPension;
	}

	/**
	 * @return the desTipoPension
	 */
	public String getDesTipoPension() {
		return desTipoPension;
	}

	/**
	 * @param desTipoPension the desTipoPension to set
	 */
	public void setDesTipoPension(String desTipoPension) {
		this.desTipoPension = desTipoPension;
	}

	/**
	 * @return the fecRegistroActualizado
	 */
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	/**
	 * @param fecRegistroActualizado the fecRegistroActualizado to set
	 */
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	/**
	 * @return the fecRegistroAlta
	 */
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	/**
	 * @param fecRegistroAlta the fecRegistroAlta to set
	 */
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	/**
	 * @return the fecRegistroBaja
	 */
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	/**
	 * @param fecRegistroBaja the fecRegistroBaja to set
	 */
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	/**
	 * @return the ditAseguradoPensions
	 */
	public List<DitAseguradoPension> getDitAseguradoPensions() {
		return ditAseguradoPensions;
	}

	/**
	 * @param ditAseguradoPensions the ditAseguradoPensions to set
	 */
	public void setDitAseguradoPensions(
			List<DitAseguradoPension> ditAseguradoPensions) {
		this.ditAseguradoPensions = ditAseguradoPensions;
	}

	
	
}