package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_BENEFICIARIOPENSION_PENSIO database table.
 * 
 */
@Entity
@Table(name="SPT_BENEFICIARIOPENSION_PENSIO")
@NamedQuery(name="SptBeneficiariopensionPensio.findAll", query="SELECT s FROM SptBeneficiariopensionPensio s")
public class SptBeneficiariopensionPensio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name = "SEQ_SPTBENEFICIARIOPENSIONPENS", sequenceName = "SEQ_SPTBENEFICIARIOPENSIONPENS")
	@GeneratedValue(generator = "SEQ_SPTBENEFICIARIOPENSIONPENS")	
	@Column(name="CVE_ID_BENEFICIARIOPENSION_PEN")
	private long cveIdBeneficiariopensionPen;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptBeneficiarioPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_PENSION")
	private SptBeneficiarioPension sptBeneficiarioPension;

	//bi-directional many-to-one association to SptPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_PENSION")
	private SptPension sptPension;

	public SptBeneficiariopensionPensio() {
	}

	public long getCveIdBeneficiariopensionPen() {
		return this.cveIdBeneficiariopensionPen;
	}

	public void setCveIdBeneficiariopensionPen(long cveIdBeneficiariopensionPen) {
		this.cveIdBeneficiariopensionPen = cveIdBeneficiariopensionPen;
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

	public SptBeneficiarioPension getSptBeneficiarioPension() {
		return this.sptBeneficiarioPension;
	}

	public void setSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
		this.sptBeneficiarioPension = sptBeneficiarioPension;
	}

	public SptPension getSptPension() {
		return this.sptPension;
	}

	public void setSptPension(SptPension sptPension) {
		this.sptPension = sptPension;
	}

}