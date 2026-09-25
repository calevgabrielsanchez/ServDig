package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_BENEFICIO_CANCELACION database table.
 * 
 */
@Entity
@Table(name="DIC_BENEFICIO_CANCELACION")
public class DicBeneficioCancelacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_BENEFICIO_CANCELACION")
	private long cveIdBeneficioCancelacion;

	@Column(name="DES_BENEFICIO_CANCELACION")
	private String desBeneficioCancelacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitBeneficio
	@OneToMany(mappedBy="dicBeneficioCancelacion")
	private List<DitBeneficio> ditBeneficios;

    public DicBeneficioCancelacion() {
    }

	public long getCveIdBeneficioCancelacion() {
		return this.cveIdBeneficioCancelacion;
	}

	public void setCveIdBeneficioCancelacion(long cveIdBeneficioCancelacion) {
		this.cveIdBeneficioCancelacion = cveIdBeneficioCancelacion;
	}

	public String getDesBeneficioCancelacion() {
		return this.desBeneficioCancelacion;
	}

	public void setDesBeneficioCancelacion(String desBeneficioCancelacion) {
		this.desBeneficioCancelacion = desBeneficioCancelacion;
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