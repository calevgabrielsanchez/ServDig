package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_SEGURO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_SEGURO")
public class DicTipoSeguro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_SEGURO", nullable=false, precision=22)
	private long cveIdTipoSeguro;

	@Column(name="DES_TIPO_SEGURO", length=100)
	private String desTipoSeguro;

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
	@OneToMany(mappedBy="dicTipoSeguro")
	private List<DitLeySeguro> ditLeySeguros;

    public DicTipoSeguro() {
    }

	public long getCveIdTipoSeguro() {
		return this.cveIdTipoSeguro;
	}

	public void setCveIdTipoSeguro(long cveIdTipoSeguro) {
		this.cveIdTipoSeguro = cveIdTipoSeguro;
	}

	public String getDesTipoSeguro() {
		return this.desTipoSeguro;
	}

	public void setDesTipoSeguro(String desTipoSeguro) {
		this.desTipoSeguro = desTipoSeguro;
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