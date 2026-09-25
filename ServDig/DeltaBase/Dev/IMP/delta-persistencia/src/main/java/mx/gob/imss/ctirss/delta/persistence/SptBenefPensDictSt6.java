package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SPT_BENEF_PENS_DICT_ST6 database table.
 * 
 */
@Entity
@Table(name="SPT_BENEF_PENS_DICT_ST6")
@NamedQuery(name="SptBenefPensDictSt6.findAll", query="SELECT s FROM SptBenefPensDictSt6 s")
public class SptBenefPensDictSt6 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTBENEFPENSDICTST6", sequenceName = "SEQ_SPTBENEFPENSDICTST6")
	@GeneratedValue(generator = "SEQ_SPTBENEFPENSDICTST6")	
	@Column(name="CVE_ID_BENEF_PENS_DICT_ST6")
	private long cveIdBenefPensDictSt6;

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
	//@ManyToOne
	//@JoinColumn(name="CVE_ID_BENEFICIARIO_PENSION")
	//private SptBeneficiarioPension sptBeneficiarioPension;

	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_SOLICITUD")
	private SptBeneficiarioSolicitud sptBeneficiarioSolicitud;
	
	//bi-directional many-to-one association to SptDictamenSt6
	@ManyToOne
	@JoinColumn(name="CVE_ID_DICTAMEN")
	private SptDictamenSt6 sptDictamenSt6;

	public SptBenefPensDictSt6() {
	}

	public long getCveIdBenefPensDictSt6() {
		return this.cveIdBenefPensDictSt6;
	}

	public void setCveIdBenefPensDictSt6(long cveIdBenefPensDictSt6) {
		this.cveIdBenefPensDictSt6 = cveIdBenefPensDictSt6;
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

//	public SptBeneficiarioPension getSptBeneficiarioPension() {
//		return this.sptBeneficiarioPension;
//	}
//
//	public void setSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
//		this.sptBeneficiarioPension = sptBeneficiarioPension;
//	}

	public SptDictamenSt6 getSptDictamenSt6() {
		return this.sptDictamenSt6;
	}

	public void setSptDictamenSt6(SptDictamenSt6 sptDictamenSt6) {
		this.sptDictamenSt6 = sptDictamenSt6;
	}

	public SptBeneficiarioSolicitud getSptBeneficiarioSolicitud() {
		return sptBeneficiarioSolicitud;
	}

	public void setSptBeneficiarioSolicitud(
			SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		this.sptBeneficiarioSolicitud = sptBeneficiarioSolicitud;
	}

	
}