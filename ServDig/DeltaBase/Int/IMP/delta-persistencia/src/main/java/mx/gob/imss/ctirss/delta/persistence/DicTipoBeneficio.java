package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the DIC_TIPO_BENEFICIO database table.
 * 
 */
@Entity
@Table(name = "DIC_TIPO_BENEFICIO")
public class DicTipoBeneficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ID_TIPO_BENEFICIO")
	private Long cveIdTipoBeneficio;

	@Column(name = "DES_TIPO_BENEFICIO")
	private String desTipoBeneficio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	// bi-directional many-to-one association to DitBeneficio
	@OneToMany(mappedBy = "dicTipoBeneficio")
	private List<DitBeneficio> ditBeneficios;

	public DicTipoBeneficio() {
	}

	public Long getCveIdTipoBeneficio() {
		return this.cveIdTipoBeneficio;
	}

	public void setCveIdTipoBeneficio(Long cveIdTipoBeneficio) {
		this.cveIdTipoBeneficio = cveIdTipoBeneficio;
	}

	public String getDesTipoBeneficio() {
		return this.desTipoBeneficio;
	}

	public void setDesTipoBeneficio(String desTipoBeneficio) {
		this.desTipoBeneficio = desTipoBeneficio;
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

	public List<DitBeneficio> getDitBeneficios() {
		return this.ditBeneficios;
	}

	public void setDitBeneficios(List<DitBeneficio> ditBeneficios) {
		this.ditBeneficios = ditBeneficios;
	}

}