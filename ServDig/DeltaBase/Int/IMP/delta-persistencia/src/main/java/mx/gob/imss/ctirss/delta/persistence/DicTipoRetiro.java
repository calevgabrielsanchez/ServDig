package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_RETIRO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_RETIRO")
public class DicTipoRetiro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_RETIRO_REINTEGRO", nullable=false, precision=22)
	private long cveIdTipoRetiroReintegro;

	@Column(name="DES_TIPO_RETIRO", length=100)
	private String desTipoRetiro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_RETIRO_REINTEGRO", precision=22)
	private BigDecimal indRetiroReintegro;

	//bi-directional many-to-one association to DitBalancePrestacion
	@OneToMany(mappedBy="dicTipoRetiro")
	private List<DitBalancePrestacion> ditBalancePrestacions;

    public DicTipoRetiro() {
    }

	public long getCveIdTipoRetiroReintegro() {
		return this.cveIdTipoRetiroReintegro;
	}

	public void setCveIdTipoRetiroReintegro(long cveIdTipoRetiroReintegro) {
		this.cveIdTipoRetiroReintegro = cveIdTipoRetiroReintegro;
	}

	public String getDesTipoRetiro() {
		return this.desTipoRetiro;
	}

	public void setDesTipoRetiro(String desTipoRetiro) {
		this.desTipoRetiro = desTipoRetiro;
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

	public BigDecimal getIndRetiroReintegro() {
		return this.indRetiroReintegro;
	}

	public void setIndRetiroReintegro(BigDecimal indRetiroReintegro) {
		this.indRetiroReintegro = indRetiroReintegro;
	}

	public List<DitBalancePrestacion> getDitBalancePrestacions() {
		return this.ditBalancePrestacions;
	}

	public void setDitBalancePrestacions(List<DitBalancePrestacion> ditBalancePrestacions) {
		this.ditBalancePrestacions = ditBalancePrestacions;
	}
	
}