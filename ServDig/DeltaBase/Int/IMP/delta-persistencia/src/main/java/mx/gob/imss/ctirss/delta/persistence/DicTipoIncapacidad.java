package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_INCAPACIDAD database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_INCAPACIDAD")
public class DicTipoIncapacidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_INCAPACIDAD", nullable=false, precision=22)
	private long cveIdTipoIncapacidad;

	@Column(name="DES_TIPO_INCAPACIDAD", length=20)
	private String desTipoIncapacidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_TIPO_INCAPACIDAD", precision=22)
	private BigDecimal numTipoIncapacidad;

	//bi-directional many-to-one association to DitIncapacidad
	@OneToMany(mappedBy="dicTipoIncapacidad")
	private List<DitIncapacidad> ditIncapacidads;

    public DicTipoIncapacidad() {
    }

	public long getCveIdTipoIncapacidad() {
		return this.cveIdTipoIncapacidad;
	}

	public void setCveIdTipoIncapacidad(long cveIdTipoIncapacidad) {
		this.cveIdTipoIncapacidad = cveIdTipoIncapacidad;
	}

	public String getDesTipoIncapacidad() {
		return this.desTipoIncapacidad;
	}

	public void setDesTipoIncapacidad(String desTipoIncapacidad) {
		this.desTipoIncapacidad = desTipoIncapacidad;
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

	public BigDecimal getNumTipoIncapacidad() {
		return this.numTipoIncapacidad;
	}

	public void setNumTipoIncapacidad(BigDecimal numTipoIncapacidad) {
		this.numTipoIncapacidad = numTipoIncapacidad;
	}

	public List<DitIncapacidad> getDitIncapacidads() {
		return this.ditIncapacidads;
	}

	public void setDitIncapacidads(List<DitIncapacidad> ditIncapacidads) {
		this.ditIncapacidads = ditIncapacidads;
	}
	
}