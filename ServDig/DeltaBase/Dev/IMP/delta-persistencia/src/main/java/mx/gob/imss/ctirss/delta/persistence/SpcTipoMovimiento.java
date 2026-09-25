package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_TIPO_MOVIMIENTO database table.
 * 
 */
@Entity
@Table(name="SPC_TIPO_MOVIMIENTO")
@NamedQuery(name="SpcTipoMovimiento.findAll", query="SELECT s FROM SpcTipoMovimiento s")
public class SpcTipoMovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_TIPO_MOVIMIENTO_IDTIPOMOVIMIENTO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_TIPO_MOVIMIENTO_IDTIPOMOVIMIENTO_GENERATOR")
	@Column(name="ID_TIPO_MOVIMIENTO")
	private String idTipoMovimiento;

	@Column(name="DES_TIPO_MOVIMIENTO")
	private String desTipoMovimiento;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcTipoMovimiento")
	private List<SptPension> sptPensions;

	public SpcTipoMovimiento() {
	}

	public String getIdTipoMovimiento() {
		return this.idTipoMovimiento;
	}

	public void setIdTipoMovimiento(String idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	public String getDesTipoMovimiento() {
		return this.desTipoMovimiento;
	}

	public void setDesTipoMovimiento(String desTipoMovimiento) {
		this.desTipoMovimiento = desTipoMovimiento;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSpcTipoMovimiento(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSpcTipoMovimiento(null);

		return sptPension;
	}

}