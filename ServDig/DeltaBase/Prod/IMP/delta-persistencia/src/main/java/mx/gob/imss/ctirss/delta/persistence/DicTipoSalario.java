package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TIPO_SALARIO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_SALARIO")
public class DicTipoSalario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_SALARIO", nullable=false, precision=22)
	private long cveIdTipoSalario;

	@Column(name="DES_TIPO_SALARIO", length=255)
	private String desTipoSalario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitTipoSalarioModalidad
	@OneToMany(mappedBy="dicTipoSalario")
	private List<DitTipoSalarioModalidad> ditTipoSalarioModalidads;

    public DicTipoSalario() {
    }

	public long getCveIdTipoSalario() {
		return this.cveIdTipoSalario;
	}

	public void setCveIdTipoSalario(long cveIdTipoSalario) {
		this.cveIdTipoSalario = cveIdTipoSalario;
	}

	public String getDesTipoSalario() {
		return this.desTipoSalario;
	}

	public void setDesTipoSalario(String desTipoSalario) {
		this.desTipoSalario = desTipoSalario;
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

	public List<DitTipoSalarioModalidad> getDitTipoSalarioModalidads() {
		return this.ditTipoSalarioModalidads;
	}

	public void setDitTipoSalarioModalidads(List<DitTipoSalarioModalidad> ditTipoSalarioModalidads) {
		this.ditTipoSalarioModalidads = ditTipoSalarioModalidads;
	}
	
}