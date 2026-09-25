package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_FORMA_PAGO_PENSION database table.
 * 
 */
@Entity
@Table(name="SPC_FORMA_PAGO_PENSION")
@NamedQuery(name="SpcFormaPagoPension.findAll", query="SELECT s FROM SpcFormaPagoPension s")
public class SpcFormaPagoPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_FORMA_PAGO_PENSION_IDFORMAPAGOPENSION_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_FORMA_PAGO_PENSION_IDFORMAPAGOPENSION_GENERATOR")
	@Column(name="ID_FORMA_PAGO_PENSION")
	private String idFormaPagoPension;

	@Column(name="DES_FORMA_PAGO_PENSION")
	private String desFormaPagoPension;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcFormaPagoPension")
	private List<SptPension> sptPensions;

	public SpcFormaPagoPension() {
	}

	public String getIdFormaPagoPension() {
		return this.idFormaPagoPension;
	}

	public void setIdFormaPagoPension(String idFormaPagoPension) {
		this.idFormaPagoPension = idFormaPagoPension;
	}

	public String getDesFormaPagoPension() {
		return this.desFormaPagoPension;
	}

	public void setDesFormaPagoPension(String desFormaPagoPension) {
		this.desFormaPagoPension = desFormaPagoPension;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSpcFormaPagoPension(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSpcFormaPagoPension(null);

		return sptPension;
	}

}